package com.dailyquest.domain.services;

import java.time.OffsetDateTime;
import java.util.List;

import com.dailyquest.api.config.security.auth.LoginService;
import com.dailyquest.domain.models.Grupo;
import com.dailyquest.domain.repositories.GrupoRepository;
import com.dailyquest.domain.services.exceptions.AuthorizationException;
import com.dailyquest.domain.services.exceptions.DomainException;
import com.dailyquest.domain.services.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private ParticipanteService participanteService; 

    @Autowired
    private LoginService loginService;

    public Grupo findById(Integer grupoId){
        try {
            return participanteService.findById(grupoId, loginService.authenticated().getId()).getParticipante().getGrupo();
        } catch (ObjectNotFoundException exp){
            throw new AuthorizationException("Você não possui permissao de acesso a este grupo");
        }
    }

    public Grupo save(Grupo grupo){
        grupo.setDataHoraCriacao(OffsetDateTime.now());
        grupo.getParticipantes().forEach(p -> p.getParticipante().setGrupo(grupo));
        return grupoRepository.save(grupo);
    }

    public void update(Grupo grupo, Integer grupoId){
       
        participanteService.isAdmin(grupoId);

        if(grupo.getId() != grupoId)
            throw new DomainException("Operação não suportada");
        
        Grupo grupoExistente = findById(grupoId);
        grupo.setParticipantes(grupoExistente.getParticipantes());
        grupo.setPeriodos(grupoExistente.getPeriodos());
        grupo.setDataHoraCriacao(grupoExistente.getDataHoraCriacao());
        grupo.setDataHoraAtualizacao(OffsetDateTime.now());
        grupoRepository.save(grupo);
    }

    public List<Grupo> findGroupsByLoggedUser(){
        return grupoRepository.findByParticipantesParticipanteUsuario(loginService.userAuthenticated());
    }

}
