package com.isw.mb.fantacalcio.repositories;

import com.isw.mb.fantacalcio.models.Amministra;
import com.isw.mb.fantacalcio.models.AmministraId;
import com.isw.mb.fantacalcio.models.Lega;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AmministraRepository extends JpaRepository<Amministra, AmministraId> {

    boolean existsByIdAndDeleted(AmministraId amministraId, char n);

    Amministra findByIdAndDeleted(AmministraId amministraId, char n);

    List<Amministra> findByLega(Lega lega);

    Amministra findByAllenatoreIdAndLegaIdAndDeleted(Integer idAll, Integer idLega, char n);
}