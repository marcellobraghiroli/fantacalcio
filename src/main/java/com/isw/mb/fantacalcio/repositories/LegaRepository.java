package com.isw.mb.fantacalcio.repositories;

import com.isw.mb.fantacalcio.models.Lega;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LegaRepository extends JpaRepository<Lega, Integer> {
    Lega findByCodiceInvito(String codiceInvito);

    boolean existsByNome(String nome);
}