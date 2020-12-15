package com.dailyquest.domain.repositories;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.dailyquest.domain.models.Periodo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodoRepository extends JpaRepository<Periodo, Integer>{
    
    @Transactional
    List<Periodo> findByGrupoId(Integer grupoId);

    @Transactional
    Optional<Periodo> findByIdAndGrupoId(Integer periodoId, Integer grupoId);
}