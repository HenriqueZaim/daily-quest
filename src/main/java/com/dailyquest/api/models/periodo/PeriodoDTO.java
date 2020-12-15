package com.dailyquest.api.models.periodo;

import java.time.OffsetDateTime;
import java.util.List;

import com.dailyquest.api.models.relatorio.RelatorioSimpleDTO;
import com.dailyquest.api.models.grupo.GrupoSimpleDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import javax.validation.constraints.NotBlank;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PeriodoDTO {

    private Integer id;
    
    @NotBlank
    private String nome;

    @NotBlank
    private String statusPeriodo;

    @NotBlank
    private String tipoPeriodo;

    private OffsetDateTime dataHoraInicio;
    
    private OffsetDateTime dataHoraFim;

    private List<RelatorioSimpleDTO> relatorios;

    private GrupoSimpleDTO grupo;

    @JsonProperty(access = Access.READ_ONLY)
    private OffsetDateTime dataHoraCriacao;

    @JsonProperty(access = Access.READ_ONLY)
    private OffsetDateTime dataHoraAtualizacao;
}
