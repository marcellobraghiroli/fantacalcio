package com.isw.mb.fantacalcio.repositories;

import com.isw.mb.fantacalcio.models.Partita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartitaRepository extends JpaRepository<Partita, Integer> {
}