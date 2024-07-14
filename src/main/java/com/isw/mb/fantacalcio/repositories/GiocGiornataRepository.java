package com.isw.mb.fantacalcio.repositories;

import com.isw.mb.fantacalcio.models.GiocGiornata;
import com.isw.mb.fantacalcio.models.GiocGiornataId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GiocGiornataRepository extends JpaRepository<GiocGiornata, GiocGiornataId> {
    GiocGiornata findGiocGiornataByGiocatoreIdAndGiornataNumeroAndDeleted(Integer id, Integer numGiornata, Character deleted);

    boolean existsByGiornataNumero(Integer numGiornata);

    @Query("SELECT gg FROM GiocGiornata gg JOIN FormGioc fg ON gg.giocatore = fg.giocatore JOIN Formazione f ON fg.formazione = f WHERE f.partita.id = :idPartita AND f.squadra.id = :idSquadra AND gg.deleted = 'N' AND fg.deleted = 'N' AND f.deleted = 'N' AND gg.giornata.numero = :numGiornata")
    List<GiocGiornata> findVotiPartita(Integer idPartita, Integer idSquadra, Integer numGiornata);
}