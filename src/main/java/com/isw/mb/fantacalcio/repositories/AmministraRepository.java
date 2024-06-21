package com.isw.mb.fantacalcio.repositories;

import com.isw.mb.fantacalcio.models.Amministra;
import com.isw.mb.fantacalcio.models.AmministraId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmministraRepository extends JpaRepository<Amministra, AmministraId> {
}