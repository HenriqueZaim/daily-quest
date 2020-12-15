package com.dailyquest.domain.models;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Grupo extends EntidadeDominio{

    private static final long serialVersionUID = 1L;

    private String descricao;
    private String imagemUrl;

    @OneToMany(mappedBy = "participante.grupo", cascade = CascadeType.ALL)
    private Set<Participante> participantes;

    @OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL)
    private List<Periodo> periodos;
}