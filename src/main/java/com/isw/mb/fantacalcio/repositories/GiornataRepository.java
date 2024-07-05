package com.isw.mb.fantacalcio.repositories;

import com.isw.mb.fantacalcio.models.Giornata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

public interface GiornataRepository extends JpaRepository<Giornata, Integer> {
    //Giornata findFirstByTsInizioLessThanEqualAndTsInizioGreaterThanEqual(LocalDateTime instant, LocalDateTime now);

    @Query("SELECT g FROM Giornata g WHERE g.tsInizio <= :now AND g.tsInizio >= :nowMinus48Hours")
    Giornata findFirstByTsInizioLessThanEqualAndTsInizioPlus48HoursGreaterThanEqual(@Param("now") LocalDateTime now, @Param("nowMinus48Hours") LocalDateTime nowMinus48Hours);

    Giornata findFirstByTsInizioGreaterThan(LocalDateTime now);

    @Query("SELECT g FROM Giornata g ORDER BY g.numero")
    List<Giornata> findAllOrderByNumero();
}