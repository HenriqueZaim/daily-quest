package com.dailyquest.domain.repositories;

import java.util.List;

import javax.transaction.Transactional;

import com.dailyquest.domain.models.Grupo;
import com.dailyquest.domain.models.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Integer>{
    
    @Transactional
    List<Grupo> findByParticipantesParticipanteUsuario(Usuario usuario);
}