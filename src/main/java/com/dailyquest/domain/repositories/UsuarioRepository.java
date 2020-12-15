package com.dailyquest.domain.repositories;

import java.util.Optional;

import javax.transaction.Transactional;

import com.dailyquest.domain.models.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
    
    @Transactional
    Optional<Usuario> findByEmail(String email);

}