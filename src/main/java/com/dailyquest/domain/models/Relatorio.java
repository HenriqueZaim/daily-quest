package com.dailyquest.domain.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Relatorio extends EntidadeDominio{

    private static final long serialVersionUID = 1L;

    private String descricao;
    private String assunto;

    @ManyToOne
    private Periodo periodo;

    @ManyToOne
    private Usuario usuario;

}