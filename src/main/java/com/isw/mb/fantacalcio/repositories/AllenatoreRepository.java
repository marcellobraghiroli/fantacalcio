package com.isw.mb.fantacalcio.repositories;

import com.isw.mb.fantacalcio.models.Allenatore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllenatoreRepository extends JpaRepository<Allenatore, Integer> {
    Allenatore findByUsernameAndPasswordAndDeleted(String username, String password, Character deleted);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByTelefono(String telefono);
}