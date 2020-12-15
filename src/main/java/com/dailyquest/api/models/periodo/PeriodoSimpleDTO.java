package com.dailyquest.api.models.periodo;

import java.time.OffsetDateTime;
import javax.validation.constraints.NotBlank;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PeriodoSimpleDTO {

    private Integer id;
    
    @NotBlank
    private String nome;

    @NotBlank
    private String statusPeriodo;

    @NotBlank
    private String tipoPeriodo;

    private OffsetDateTime dataHoraInicio;
    
    private OffsetDateTime dataHoraFim;

}
