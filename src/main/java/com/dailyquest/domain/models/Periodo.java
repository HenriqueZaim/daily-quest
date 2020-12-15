package com.dailyquest.domain.models;

import java.time.OffsetDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.dailyquest.domain.models.enums.StatusPeriodo;
import com.dailyquest.domain.models.enums.TipoPeriodo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Periodo extends EntidadeDominio{

    private static final long serialVersionUID = 1L;

    @Enumerated(EnumType.STRING)
    private StatusPeriodo statusPeriodo;

    @Enumerated(EnumType.STRING)
    private TipoPeriodo tipoPeriodo;

    private OffsetDateTime dataHoraInicio;
    
    private OffsetDateTime dataHoraFim;

    @OneToMany(mappedBy = "periodo")
    private List<Relatorio> relatorios;

    @ManyToOne
    private Grupo grupo;
}