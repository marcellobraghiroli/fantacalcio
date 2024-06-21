package com.isw.mb.fantacalcio.repositories;

import com.isw.mb.fantacalcio.models.Giornata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GiornataRepository extends JpaRepository<Giornata, Integer> {
}