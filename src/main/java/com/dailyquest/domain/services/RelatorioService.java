package com.dailyquest.domain.services;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private UsuarioService usuarioService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private PeriodoService periodoService;

    @Autowired
    private ParticipanteService participanteService;

    public Relatorio findById(Integer relatorioId, Integer grupoId, Integer periodoId){
        if(!participanteService.isAdmin(grupoId)){
            return relatorioRepository.findById(relatorioId).orElseThrow(() -> new ObjectNotFoundException("Relatório não encontrado"));
        }else{
            throw new AuthorizationException("Você não possui permissão para acessar este relatório.");
        }
    }

    public Relatorio findByUser(Integer usuarioId, Integer relatorioId){
        if(loginService.userAuthenticated().getId() != usuarioId)
            throw new AuthorizationException("Permissão negada para listagem deste relatório");

        Optional<Relatorio> relatorio = relatorioRepository.findById(relatorioId);
        return relatorio.orElseThrow(() -> new ObjectNotFoundException("Relatório não encontrado"));
    }

    // Todo: permitir admin de acessar os relatórios
    public List<Relatorio> findReportsByUser(Integer usuarioId){
        if(loginService.userAuthenticated().getId() != usuarioId)
            throw new AuthorizationException("Permissão negada para listagem de relatórios");
        
        Usuario usuario = usuarioService.findById(usuarioId);

        return relatorioRepository.findByUsuario(usuario);
    }

    public List<Relatorio> findAllByPeriod(Integer grupoId, Integer periodoId){
        Participante participante = participanteService.findById(grupoId, loginService.userAuthenticated().getId());
        if(!participanteService.isAdmin(grupoId)){
            return relatorioRepository.findByPeriodo(periodoService.findById(periodoId, grupoId));
        }else{
            return findReportsByUser(participante.getParticipante().getUsuario().getId());
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
