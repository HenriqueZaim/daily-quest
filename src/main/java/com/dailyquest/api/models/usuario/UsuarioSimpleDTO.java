package com.dailyquest.api.models.usuario;

import java.time.OffsetDateTime;

import javax.validation.constraints.Email;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioSimpleDTO {
    
    @NotBlank
    private Integer id;

    @NotBlank
    private String nome;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String imagemUrl;

    @JsonProperty(access = Access.READ_ONLY)
    private OffsetDateTime dataHoraCriacao;

    @JsonProperty(access = Access.READ_ONLY)
    private OffsetDateTime dataHoraAtualizacao;

}