package com.dailyquest.domain.models;

import java.io.Serializable;
import java.time.OffsetDateTime;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
@EqualsAndHashCode
public abstract class EntidadeDominio implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    
    @JsonProperty(access = Access.READ_ONLY)
    private OffsetDateTime dataHoraCriacao;
    
    @JsonProperty(access = Access.READ_ONLY)
    private OffsetDateTime dataHoraAtualizacao;

}