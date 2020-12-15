package com.dailyquest.domain.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.dailyquest.api.config.security.auth.LoginService;
import com.dailyquest.domain.models.Grupo;
import com.dailyquest.domain.models.Participante;
import com.dailyquest.domain.models.ParticipantePK;
import com.dailyquest.domain.models.Usuario;
import com.dailyquest.domain.models.enums.TipoPermissao;
import com.dailyquest.domain.repositories.ParticipanteRepository;
import com.dailyquest.domain.services.exceptions.AuthorizationException;
import com.dailyquest.domain.services.exceptions.DomainException;
import com.dailyquest.domain.services.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParticipanteService {
    
    @Autowired
    private GrupoService grupoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private ParticipanteRepository participanteRepository;

    @Autowired
    private EmailService emailService;

    public Participante findById(Integer grupoId, Integer usuarioId){

        Optional<Participante> participante = participanteRepository.findByParticipante(
            new ParticipantePK(
                grupoService.findById(grupoId),
                usuarioService.findById(usuarioId)
            )
        );

        return participante.orElseThrow(
            () -> new ObjectNotFoundException("Participante não encontrado")
        );
    }

    public Set<Participante> findAllByGroup(Integer grupoId){
        Grupo grupo = grupoService.findById(grupoId);

        Optional<Participante> participante = participanteRepository.findByParticipante(
            new ParticipantePK(
                grupo, 
                loginService.userAuthenticated()
            )
        );
        
        if(!participante.isPresent())
            throw new DomainException("Usuário não está registrado no grupo");

        return grupo.getParticipantes();
    }

    public void delete(Integer grupoId, Integer usuarioId){

        if(!isAdmin(grupoId))
            throw new AuthorizationException("Permissão negada para exclusão de participante");

        Participante participante = findById(grupoId, usuarioId);

        try {
            participanteRepository.deleteByParticipante(participante.getParticipante());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }  
    }

    public void updateAuthority(Participante participante){
        if(!isAdmin(participante.getParticipante().getGrupo().getId()))
            throw new AuthorizationException("Permissão negada para atualização de autoridade");

        participanteRepository.save(participante);
    }

    public Participante save(Usuario usuario, Integer grupoId){

        if(!isAdmin(grupoId))
            throw new AuthorizationException("Permissão negada para adição de novo participante");

        Grupo grupo = grupoService.findById(grupoId);

        Optional<Participante> participante = participanteRepository.findByParticipante(
            new ParticipantePK(grupo, usuario)
        );
        
        if(participante.isPresent())
            throw new DomainException("Usuário já está registrado no grupo");

        usuario = usuarioService.findById(usuario.getId());

        var p = participanteRepository.save(
                new Participante(
                        new ParticipantePK(grupo, usuario),
                        TipoPermissao.PARTICIPANTE
                )
        );

        emailService.sendOrderConfirmationHtmlEmail(p);

        return p;
        
    }

    public boolean isAdmin(Integer grupoId){
        Usuario usuario = loginService.userAuthenticated();

        List<Participante> grupos = usuario.getGrupos().stream()
            .filter(p -> p.getParticipante().getGrupo().getId().equals(grupoId))
            .filter(p -> p.getAutoridade().equals(TipoPermissao.ADMIN))
            .collect(Collectors.toList());

        return !grupos.isEmpty();
    }

}
