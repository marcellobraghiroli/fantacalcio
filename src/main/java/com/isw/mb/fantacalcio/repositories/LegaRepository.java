package com.isw.mb.fantacalcio.repositories;

import com.isw.mb.fantacalcio.models.Lega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LegaRepository extends JpaRepository<Lega, Integer> {
    Lega findByCodiceInvitoAndDeleted(String codiceInvito, Character deleted);

    boolean existsByNome(String nome);


    @Query("SELECT l FROM Squadra AS s join Lega AS l ON s.lega.id = l.id  WHERE s.allenatore.id = :idAll AND s.deleted = 'N' AND l.deleted = 'N' ORDER BY l.nome ASC")
    List<Lega> findLegheByAllenatoreId(@Param("idAll") Integer idAll);

    @Query("SELECT l.codiceInvito FROM Lega l WHERE l.nome = :nomeLega")
    String getCodiceInvitoByNomeLega(String nomeLega);
}