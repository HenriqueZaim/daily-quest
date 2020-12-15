package com.dailyquest.api.models.participante;

import com.dailyquest.api.models.usuario.UsuarioSimpleDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParticipanteSimpleDTO {
    
    private String autoridade;
    private UsuarioSimpleDTO participanteUsuario;

}

