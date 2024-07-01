package com.isw.mb.fantacalcio.repositories;

import com.isw.mb.fantacalcio.models.Giocatore;
import com.isw.mb.fantacalcio.models.Squadra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface GiocatoreRepository extends JpaRepository<Giocatore, Integer> {
}