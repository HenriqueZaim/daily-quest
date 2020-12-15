package com.dailyquest.domain.models;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ParticipantePK implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @ManyToOne
    private Grupo grupo;

    @ManyToOne
    private Usuario usuario;
}