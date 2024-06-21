package com.isw.mb.fantacalcio.repositories;

import com.isw.mb.fantacalcio.models.FormGioc;
import com.isw.mb.fantacalcio.models.FormGiocId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormGiocRepository extends JpaRepository<FormGioc, FormGiocId> {
}