package com.dailyquest.api.models.grupo;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import javax.validation.constraints.NotBlank;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrupoSimpleDTO {

    private Integer id;

    @NotBlank
    private String nome;

    @NotBlank
    private String descricao;

    @NotBlank
    private String imagemUrl;

    @JsonProperty(access = Access.READ_ONLY)
    private OffsetDateTime dataHoraCriacao;

    @JsonProperty(access = Access.READ_ONLY)
    private OffsetDateTime dataHoraAtualizacao;
}
