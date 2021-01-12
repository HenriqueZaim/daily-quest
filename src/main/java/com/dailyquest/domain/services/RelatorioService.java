package com.dailyquest.domain.services;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.dailyquest.api.config.security.auth.LoginService;
import com.dailyquest.domain.models.Participante;
import com.dailyquest.domain.models.Periodo;
import com.dailyquest.domain.models.Relatorio;
import com.dailyquest.domain.models.Usuario;
import com.dailyquest.domain.models.enums.StatusPeriodo;
import com.dailyquest.domain.repositories.RelatorioRepository;
import com.dailyquest.domain.services.exceptions.AuthorizationException;
import com.dailyquest.domain.services.exceptions.DomainException;
import com.dailyquest.domain.services.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RelatorioService {
    
    @Autowired
    private RelatorioRepository relatorioRepository;

    @Autowired
    private LoginService loginService;

    @Autowired
    private PeriodoService periodoService;

    @Autowired
    private ParticipanteService participanteService;

    public Relatorio findById(Integer relatorioId){
        return relatorioRepository.findById(relatorioId).orElseThrow(() -> new ObjectNotFoundException("Relatório não encontrado"));
    }

    public List<Relatorio> findAllByUser(Usuario usuario){     
        return relatorioRepository.findByUsuario(usuario);
    }

    public Relatorio findByPeriodFromId(Integer grupoId, Integer periodoId, Integer relatorioId){
        participanteService.findById(grupoId, loginService.userAuthenticated().getId());
        return relatorioRepository.findByIdAndPeriodo(relatorioId, periodoService.findById(periodoId, grupoId)).orElseThrow(() -> new ObjectNotFoundException("Relatório não encontrado"));
    }

    public List<Relatorio> findAllByPeriod(Integer grupoId, Integer periodoId){
        Participante participante = participanteService.findById(grupoId, loginService.userAuthenticated().getId());
        if(participanteService.isAdmin(participante)){
            return relatorioRepository.findByPeriodo(periodoService.findById(periodoId, grupoId));
        }else{
            return relatorioRepository.findByUsuarioAndPeriodo(participante.getParticipante().getUsuario(), periodoService.findById(periodoId, grupoId));
        }
    }
    public boolean isSameDay(OffsetDateTime date){
        Instant lastSubmitDate = date.toInstant().truncatedTo(ChronoUnit.DAYS);
        Instant newSubmitDate = OffsetDateTime.now().toInstant().truncatedTo(ChronoUnit.DAYS);
        return lastSubmitDate.equals(newSubmitDate);
    }

    public Relatorio save(Relatorio relatorio, Integer grupoId, Integer periodoId){
        Participante participante = participanteService.findById(grupoId, loginService.userAuthenticated().getId());
        Periodo periodo = periodoService.findById(periodoId, grupoId);
        if(periodo.getStatusPeriodo().equals(StatusPeriodo.INATIVO))
            throw new DomainException("Período não está ativo para receber novos relatórios.");
            
        if(!participanteService.isAdmin(participante)){
            List<Relatorio> relatorios =  relatorioRepository.findByUsuarioAndPeriodo(participante.getParticipante().getUsuario(), periodo);
            if(relatorios.stream().anyMatch(x -> isSameDay(x.getDataHoraCriacao())))
                throw new DomainException("Você já enviou um formulário hoje!");
        }      
        
        relatorio.setPeriodo(periodo);
        relatorio.setUsuario(loginService.userAuthenticated());
        relatorio.setDataHoraCriacao(OffsetDateTime.now());

        return relatorioRepository.save(relatorio);
    }

    public void update(Relatorio relatorio, Integer grupoId, Integer periodoId, Integer relatorioId){

        Participante participante = participanteService.findById(grupoId, loginService.userAuthenticated().getId());
        findByIdAndUsuario(relatorioId, participante.getParticipante().getUsuario());
        
        Periodo periodo = periodoService.findById(periodoId, grupoId);
        if(periodo.getStatusPeriodo().equals(StatusPeriodo.INATIVO))
            throw new DomainException("Período não está ativo para atualização de relatórios.");
              
        relatorio.setId(relatorioId);
        relatorio.setDataHoraAtualizacao(OffsetDateTime.now());

        relatorioRepository.save(relatorio);
    }

    public Relatorio findByIdAndUsuario(Integer relatorioId, Usuario usuario){
        return relatorioRepository.findByIdAndUsuario(relatorioId, usuario)
            .orElseThrow(() -> new AuthorizationException("Ops! Você não pode acessar um relatório que não seja seu!"));
    }

}
