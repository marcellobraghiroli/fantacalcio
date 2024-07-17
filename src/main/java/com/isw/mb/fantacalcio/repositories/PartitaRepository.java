package com.isw.mb.fantacalcio.repositories;

import com.isw.mb.fantacalcio.models.Lega;
import com.isw.mb.fantacalcio.models.Partita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PartitaRepository extends JpaRepository<Partita, Integer> {
    boolean existsByLegaAndDeleted(Lega legaCorrente, Character deleted);

    List<Partita> findPartiteByLegaIdAndDeletedOrderByGiornataAscIdAsc(Integer idLega, char n);

    @Query("SELECT p FROM Partita p WHERE (p.squadraCasa.id = :idSquadra OR p.squadraTrasf.id = :idSquadra) AND p.giornata.numero = :numGiornata AND p.deleted = 'N'")
    Partita findPartitaBySquadraAndGiornata(Integer idSquadra, Integer numGiornata);

    List<Partita> findByLegaIdAndGiornataNumeroAndDeletedOrderByIdAsc(Integer idLega, Integer numGiornata, char n);

    Partita findByIdAndDeleted(Integer idPartita, char n);
}