package com.isw.mb.fantacalcio.repositories;

import com.isw.mb.fantacalcio.models.GiocSquadra;
import com.isw.mb.fantacalcio.models.GiocSquadraId;
import com.isw.mb.fantacalcio.models.Squadra;
import com.isw.mb.fantacalcio.services.cookies.CookieService;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface GiocSquadraRepository extends JpaRepository<GiocSquadra, GiocSquadraId> {

    Set<GiocSquadra> findBySquadraAndDeleted(Squadra squadra, Character deleted);

    GiocSquadra findByIdAndDeleted(GiocSquadraId giocSquadraId, char n);
}