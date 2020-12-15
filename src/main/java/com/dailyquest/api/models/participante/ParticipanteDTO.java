package com.dailyquest.api.models.participante;

import com.dailyquest.api.models.usuario.UsuarioDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParticipanteDTO {
    
    private String autoridade;
    private UsuarioDTO participanteUsuario;

}
