package com.dailyquest.domain.repositories;


import java.util.Optional;

import javax.transaction.Transactional;

import com.dailyquest.domain.models.Participante;
import com.dailyquest.domain.models.ParticipantePK;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipanteRepository extends JpaRepository<Participante, ParticipantePK>{
    
    @Transactional
    Optional<Participante> findByParticipante(ParticipantePK participantePK);

    @Transactional
    void deleteByParticipante(ParticipantePK participantePK);
}