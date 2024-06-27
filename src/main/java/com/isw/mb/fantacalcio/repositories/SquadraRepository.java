package com.isw.mb.fantacalcio.repositories;

import com.isw.mb.fantacalcio.models.Squadra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SquadraRepository extends JpaRepository<Squadra, Integer> {
    List<Squadra> findSquadreByAllenatoreId(Integer idAllenatore);
}