package com.isw.mb.fantacalcio.repositories;

import com.isw.mb.fantacalcio.models.Giocatore;
import com.isw.mb.fantacalcio.models.Squadra;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface GiocatoreRepository extends JpaRepository<Giocatore, Integer> {
    List<Giocatore> findGiocatoriByRuoloAndDeletedOrderByNome(String ruolo, Character deleted);

    Giocatore findByIdAndDeleted(Integer idGioc, char n);
}