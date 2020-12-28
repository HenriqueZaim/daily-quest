package com.dailyquest.domain.services;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import com.dailyquest.api.config.security.auth.LoginService;
import com.dailyquest.domain.models.Participante;
import com.dailyquest.domain.models.ParticipantePK;
import com.dailyquest.domain.models.Periodo;
import com.dailyquest.domain.repositories.ParticipanteRepository;
import com.dailyquest.domain.repositories.PeriodoRepository;
import com.dailyquest.domain.services.exceptions.AuthorizationException;
import com.dailyquest.domain.services.exceptions.DomainException;
import com.dailyquest.domain.services.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PeriodoService {
    
    @Autowired
    private PeriodoRepository periodoRepository;

    @Autowired
    private ParticipanteRepository participanteRepository;

    @Autowired
    private ParticipanteService participanteService; 
    
    @Autowired
    private LoginService loginService; 

    @Autowired
    private GrupoService grupoService; 

    public Periodo findById(Integer periodoId, Integer grupoId){
        
        participanteService.findById(grupoId, loginService.userAuthenticated().getId());

        Optional<Periodo> periodo = periodoRepository.findByIdAndGrupoId(periodoId, grupoId);
        
        return periodo.orElseThrow(() -> new ObjectNotFoundException("Período não encontrado"));
    }

    public List<Periodo> findByGroup(Integer grupoId){
        Optional<Participante> participante = participanteRepository.findByParticipante(
            new ParticipantePK(
                grupoService.findById(grupoId), 
                loginService.userAuthenticated()
            )
        );
        
        if(!participante.isPresent())
            throw new DomainException("Usuário não está registrado no grupo");

        return periodoRepository.findByGrupoId(grupoId);
    }

    public Periodo save(Periodo periodo, Integer grupoId){
        if(!participanteService.isAdmin(grupoId))
            throw new AuthorizationException("Permissão negada para criação de periodo");

        periodo.setDataHoraCriacao(OffsetDateTime.now());
        periodo.setGrupo(grupoService.findById(grupoId));

        return periodoRepository.save(periodo);
    }

    public void update(Periodo periodo, Integer periodoId, Integer grupoId){
        if(!participanteService.isAdmin(grupoId))
            throw new AuthorizationException("Permissão negada para atualização de período");

        Periodo periodoExistente = findById(periodoId, grupoId);

        periodo.setId(periodoId);
        periodo.setDataHoraAtualizacao(OffsetDateTime.now());
        periodo.setRelatorios(periodoExistente.getRelatorios());
        periodo.setGrupo(periodoExistente.getGrupo());

        periodoRepository.save(periodo);
    }

    public void patch(Periodo periodo){
        if(!participanteService.isAdmin(periodo.getGrupo().getId()))
            throw new AuthorizationException("Permissão negada para atualização de período");

        periodo.setDataHoraAtualizacao(OffsetDateTime.now());
        periodoRepository.save(periodo); 
    }
}
