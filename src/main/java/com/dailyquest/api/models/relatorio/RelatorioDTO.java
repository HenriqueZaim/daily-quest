package com.dailyquest.api.models.relatorio;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import com.dailyquest.api.models.periodo.PeriodoSimpleDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelatorioDTO {
    
    private Integer id;
    private String nome;
    private String descricao;
    private String assunto;
    private OffsetDateTime dataHoraEntrega;
    private PeriodoSimpleDTO periodo;

    @JsonProperty(access = Access.READ_ONLY)
    private OffsetDateTime dataHoraCriacao;

    @JsonProperty(access = Access.READ_ONLY)
    private OffsetDateTime dataHoraAtualizacao;
}
