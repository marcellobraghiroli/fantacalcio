package com.isw.mb.fantacalcio.repositories;

import com.isw.mb.fantacalcio.models.Allenatore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllenatoreRepository extends JpaRepository<Allenatore, Integer> {
    Allenatore findByUsernameAndPassword(String username, String password);
}