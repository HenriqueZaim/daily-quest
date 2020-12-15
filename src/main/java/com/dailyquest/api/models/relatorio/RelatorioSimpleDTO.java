package com.dailyquest.api.models.relatorio;

import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelatorioSimpleDTO {
    
    private Integer id;
    private String nome;
    private String descricao;
    private String assunto;
    private OffsetDateTime dataHoraEntrega;

}
