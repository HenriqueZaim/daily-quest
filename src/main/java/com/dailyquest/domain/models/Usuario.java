package com.dailyquest.domain.models;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.dailyquest.domain.models.enums.TipoUsuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario extends EntidadeDominio{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(unique = true)
    private String email;

    private String senha;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable
    @Enumerated(EnumType.STRING)
    private Set<TipoUsuario> tipoUsuario;

    @OneToMany(mappedBy = "participante.usuario", cascade = CascadeType.ALL)
    private Set<Participante> grupos;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Relatorio> relatorios;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Nome");
        builder.append(getNome());
        builder.append("E-mail");
        builder.append(getEmail());
        return builder.toString();
    }
}