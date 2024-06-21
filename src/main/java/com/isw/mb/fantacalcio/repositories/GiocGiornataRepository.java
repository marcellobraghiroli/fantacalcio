package com.isw.mb.fantacalcio.repositories;

import com.isw.mb.fantacalcio.models.GiocGiornata;
import com.isw.mb.fantacalcio.models.GiocGiornataId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GiocGiornataRepository extends JpaRepository<GiocGiornata, GiocGiornataId> {
}