package com.dailyquest.domain.repositories;

import java.util.List;

import javax.transaction.Transactional;

import com.dailyquest.domain.models.Periodo;
import com.dailyquest.domain.models.Relatorio;
import com.dailyquest.domain.models.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelatorioRepository extends JpaRepository<Relatorio, Integer>{
    
    @Transactional
    List<Relatorio> findByUsuario(Usuario usuario);

    @Transactional
    List<Relatorio> findByPeriodo(Periodo periodo);

    @Transactional
    List<Relatorio> findByPeriodoId(Integer periodoId);
}