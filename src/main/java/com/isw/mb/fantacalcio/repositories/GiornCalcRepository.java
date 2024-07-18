package com.isw.mb.fantacalcio.repositories;

import com.isw.mb.fantacalcio.models.GiornCalc;
import com.isw.mb.fantacalcio.models.GiornCalcId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GiornCalcRepository extends JpaRepository<GiornCalc, GiornCalcId> {
  boolean existsByIdAndDeleted(GiornCalcId giornCalcId, char n);
}