package com.isw.mb.fantacalcio.repositories;

import com.isw.mb.fantacalcio.models.GiocSquadra;
import com.isw.mb.fantacalcio.models.GiocSquadraId;
import com.isw.mb.fantacalcio.models.Squadra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface GiocSquadraRepository extends JpaRepository<GiocSquadra, GiocSquadraId> {

    Set<GiocSquadra> findBySquadraAndDeleted(Squadra squadra, Character deleted);

}