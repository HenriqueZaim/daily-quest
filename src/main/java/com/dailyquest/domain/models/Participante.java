package com.dailyquest.domain.models;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.dailyquest.domain.models.enums.TipoPermissao;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class Participante implements Serializable{

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ParticipantePK participante;

    @Enumerated(EnumType.STRING)
    private TipoPermissao autoridade;


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("usuario");
        builder.append(participante.getUsuario().toString());
        return builder.toString();
    }
}