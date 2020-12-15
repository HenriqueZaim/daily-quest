package com.dailyquest.api.models.usuario;

import java.time.OffsetDateTime;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.dailyquest.domain.models.enums.TipoUsuario;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioDTO {

    private Integer id;

    @NotBlank
    private String nome;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String senha;

    @JsonProperty(access = Access.READ_ONLY)
    @JsonIgnore
    private Set<TipoUsuario> tipoUsuario = new HashSet<>();

    @JsonProperty(access = Access.READ_ONLY)
    private OffsetDateTime dataHoraCriacao;

    @JsonProperty(access = Access.READ_ONLY)
    private OffsetDateTime dataHoraAtualizacao;

    public UsuarioDTO(){
        tipoUsuario.add(TipoUsuario.ROLE_USUARIO);
    }
}