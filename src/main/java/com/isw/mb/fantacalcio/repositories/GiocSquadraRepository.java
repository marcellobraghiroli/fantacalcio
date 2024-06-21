package com.isw.mb.fantacalcio.repositories;

import com.isw.mb.fantacalcio.models.GiocSquadra;
import com.isw.mb.fantacalcio.models.GiocSquadraId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GiocSquadraRepository extends JpaRepository<GiocSquadra, GiocSquadraId> {
}