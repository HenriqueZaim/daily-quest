package com.dailyquest.api.models.grupo;

import java.time.OffsetDateTime;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.dailyquest.api.models.participante.ParticipanteSimpleDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import com.dailyquest.api.models.periodo.PeriodoSimpleDTO;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrupoDTO {
    
    private Integer id;

    @NotBlank
    private String nome;

    @NotBlank
    private String descricao;

    @NotEmpty
    private Set<ParticipanteSimpleDTO> participantes;

    private List<PeriodoSimpleDTO> periodos;

    @JsonProperty(access = Access.READ_ONLY)
    private OffsetDateTime dataHoraCriacao;

    @JsonProperty(access = Access.READ_ONLY)
    private OffsetDateTime dataHoraAtualizacao;

}
