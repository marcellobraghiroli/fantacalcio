package com.isw.mb.fantacalcio.repositories;

import com.isw.mb.fantacalcio.models.Formazione;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormazioneRepository extends JpaRepository<Formazione, Integer> {
    Formazione findFormazioneByPartitaIdAndSquadraId(Integer idPartita, Integer idSqCasa);
}