package com.isw.mb.fantacalcio.repositories;

import com.isw.mb.fantacalcio.models.Squadra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface SquadraRepository extends JpaRepository<Squadra, Integer> {
    List<Squadra> findSquadreByAllenatoreIdAndDeleted(Integer idAllenatore, Character deleted);

    Squadra findSquadraByAllenatoreIdAndLegaIdAndDeleted(Integer id, Integer id1, Character deleted);

    List<Squadra> findSquadreByLegaIdAndDeletedOrderByNome(Integer id, Character deleted);

    List<Squadra> findSquadreByLegaIdAndDeletedOrderByPuntiClassDescFantapuntiDesc(Integer id, char n);

    Squadra findByIdAndDeleted(Integer idSquadra, char n);

    boolean existsByAllenatoreUsernameAndLegaIdAndDeleted(String recUsername, Integer id, char n);
}