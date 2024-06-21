package com.isw.mb.fantacalcio.repositories;

import com.isw.mb.fantacalcio.models.Giocatore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GiocatoreRepository extends JpaRepository<Giocatore, Integer> {
}