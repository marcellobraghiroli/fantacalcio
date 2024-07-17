package com.isw.mb.fantacalcio.repositories;

import com.isw.mb.fantacalcio.models.Amministra;
import com.isw.mb.fantacalcio.models.AmministraId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmministraRepository extends JpaRepository<Amministra, AmministraId> {

    boolean existsByIdAndDeleted(AmministraId amministraId, char n);

    Amministra findByIdAndDeleted(AmministraId amministraId, char n);
}