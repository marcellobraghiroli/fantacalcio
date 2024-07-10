package com.isw.mb.fantacalcio.repositories;

import com.isw.mb.fantacalcio.models.Formazione;
import com.isw.mb.fantacalcio.models.Giornata;
import com.isw.mb.fantacalcio.models.Squadra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FormazioneRepository extends JpaRepository<Formazione, Integer> {
    Formazione findFormazioneByPartitaIdAndSquadraIdAndDeleted(Integer idPartita, Integer idSqCasa, Character deleted);

    @Query("SELECT f FROM Formazione f JOIN Partita p ON f.partita = p WHERE f.deleted = :deleted AND f.squadra = :squadra AND p.giornata = :giornata")
    Formazione findFormazioneBySquadraAndGiornataAndDeleted(Squadra squadra, Giornata giornata, Character deleted);
}