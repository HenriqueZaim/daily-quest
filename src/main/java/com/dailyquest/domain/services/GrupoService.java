package com.dailyquest.domain.services;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import com.dailyquest.api.config.security.auth.LoginService;
import com.dailyquest.domain.models.Grupo;
import com.dailyquest.domain.models.Participante;
import com.dailyquest.domain.models.ParticipantePK;
import com.dailyquest.domain.repositories.GrupoRepository;
import com.dailyquest.domain.repositories.ParticipanteRepository;
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
    private ParticipanteRepository participanteRepository;

    @Autowired
    private LoginService loginService;

    public Grupo findById(Integer grupoId){
        Optional<Grupo> grupo = grupoRepository.findById(grupoId);
        if(!grupo.isPresent())
            throw new ObjectNotFoundException("Grupo não encontrado");

        Optional<Participante> participante = participanteRepository.findByParticipante(
            new ParticipantePK(
                grupo.get(), 
                loginService.userAuthenticated()
            )
        );
        
        if(!participante.isPresent())
            throw new AuthorizationException("Usuário não está registrado no grupo");
        
        return grupo.get();
    }

    public Grupo save(Grupo grupo){
        grupo.setDataHoraCriacao(OffsetDateTime.now());
        grupo.getParticipantes().forEach(p -> p.getParticipante().setGrupo(grupo));
        return grupoRepository.save(grupo);
    }

    public void update(Grupo grupo, Integer grupoId){
       
        if(!participanteService.isAdmin(grupoId))
            throw new AuthorizationException("Permissão negada para atualização de grupo");

        if(grupo.getId() != grupoId)
            throw new DomainException("Operação não é suportada");
        
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
