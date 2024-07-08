package com.isw.mb.fantacalcio.repositories;

import com.isw.mb.fantacalcio.models.FormGioc;
import com.isw.mb.fantacalcio.models.FormGiocId;
import com.isw.mb.fantacalcio.models.Formazione;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FormGiocRepository extends JpaRepository<FormGioc, FormGiocId> {
    List<FormGioc> findByFormazione(Formazione formazione);
}