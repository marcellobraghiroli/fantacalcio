package com.isw.mb.fantacalcio.repositories;

import com.isw.mb.fantacalcio.models.Formazione;
import com.isw.mb.fantacalcio.models.Giornata;
import com.isw.mb.fantacalcio.models.Squadra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FormazioneRepository extends JpaRepository<Formazione, Integer> {


    @Query("SELECT f FROM Formazione f LEFT JOIN FETCH f.formGiocatori fg LEFT JOIN FETCH fg.giocatore g LEFT JOIN g.giocGiornate gg WITH gg.giornata.numero = :numGiornata AND gg.deleted = 'N' WHERE f.partita.id = :idPartita AND f.squadra.id = :idSquadra AND f.deleted = :deleted AND fg.deleted = 'N' AND g.deleted = 'N'")
    Formazione findFormazioneByPartitaIdAndSquadraIdAndStatsGiocatoriAndDeleted(Integer idPartita, Integer idSquadra, Integer numGiornata, Character deleted);

    @Query("SELECT f FROM Partita p JOIN Formazione f ON f.partita = p JOIN FETCH f.formGiocatori fg WHERE f.deleted = :deleted AND f.squadra = :squadra AND p.giornata = :giornata AND fg.deleted = 'N'")
    Formazione findFormazioneBySquadraAndGiornataAndDeleted(Squadra squadra, Giornata giornata, Character deleted);

    //@Query("SELECT f FROM Formazione f LEFT JOIN FETCH f.formGiocatori fg LEFT JOIN FETCH fg.giocatore g LEFT JOIN FETCH g.giocGiornate gg WHERE f.partita.id = :idPartita AND f.squadra.id = :idSquadra AND f.deleted = :deleted AND fg.deleted = 'N' AND gg.giornata.numero = :numGiornata AND gg.deleted = 'N' AND g.deleted = 'N'")

}