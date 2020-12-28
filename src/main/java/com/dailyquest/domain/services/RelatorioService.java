package com.dailyquest.domain.services;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.dailyquest.api.config.security.auth.LoginService;
import com.dailyquest.domain.models.Participante;
import com.dailyquest.domain.models.Periodo;
import com.dailyquest.domain.models.Relatorio;
import com.dailyquest.domain.models.enums.StatusPeriodo;
import com.dailyquest.domain.repositories.RelatorioRepository;
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

    public List<Relatorio> findAllByUser(Integer usuarioId){     
        return relatorioRepository.findByUsuarioId(usuarioId);
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

    public Relatorio save(Relatorio relatorio, Integer grupoId, Integer periodoId){
        // Verifica se o período existe, se o usuário possui permissão e se o período está ativo
        // Todo: Não está bloqueando admin de subir relatório
        Periodo periodo = periodoService.findById(periodoId, grupoId);
        if(periodo.getStatusPeriodo() == StatusPeriodo.INATIVO)
            throw new DomainException("Relatório não está ativo para receber novos relatórios.");
        
        // Verifica se o usuário logado entregou algum relatório no mesmo dia
        // Todo: Melhorar verificação de data
        List<Relatorio> relatorios = findAllByPeriod(grupoId, periodoId);
        relatorios = relatorios.stream()
            .filter(r -> r.getDataHoraEntrega().getDayOfMonth() == OffsetDateTime.now().getDayOfMonth())
            .collect(Collectors.toList());

        if(!relatorios.isEmpty())
            throw new DomainException("Apenas um relatório pode ser entregue a cada dia do período.");

        relatorio.setPeriodo(periodo);
        relatorio.setUsuario(loginService.userAuthenticated());
        relatorio.setDataHoraCriacao(OffsetDateTime.now());

        return relatorioRepository.save(relatorio);
    }
}
