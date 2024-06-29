package com.isw.mb.fantacalcio.repositories;

import com.isw.mb.fantacalcio.models.Squadra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SquadraRepository extends JpaRepository<Squadra, Integer> {
    List<Squadra> findSquadreByAllenatoreIdAndDeleted(Integer idAllenatore, Character deleted);

    Squadra findSquadraByAllenatoreIdAndLegaIdAndDeleted(Integer id, Integer id1, Character deleted);
}