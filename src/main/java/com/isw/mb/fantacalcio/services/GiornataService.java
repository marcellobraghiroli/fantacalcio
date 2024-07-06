package com.isw.mb.fantacalcio.services;

import com.isw.mb.fantacalcio.models.*;
import com.isw.mb.fantacalcio.repositories.GiornataRepository;
import com.isw.mb.fantacalcio.repositories.PartitaRepository;
import com.isw.mb.fantacalcio.repositories.SquadraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class GiornataService {

    private final PartitaRepository partitaRepository;
    private final GiornataRepository giornataRepository;

    @Autowired
    public GiornataService(PartitaRepository partitaRepository, GiornataRepository giornataRepository, SquadraRepository squadraRepository) {
        this.partitaRepository = partitaRepository;
        this.giornataRepository = giornataRepository;
    }

    @Transactional
    public Giornata findCurrentOrNextGiornata(Lega legaCorrente) {

        boolean calendarioGenerato = partitaRepository.existsByLegaAndDeleted(legaCorrente, 'N');

        if (calendarioGenerato) {

            LocalDateTime now = LocalDateTime.now();
            //LocalDateTime now = LocalDateTime.of(2024, 7, 21, 10, 0, 0);
            LocalDateTime nowMinus48Hours = now.minusHours(48);

            Giornata currentGiornata = giornataRepository.findFirstByTsInizioLessThanEqualAndTsInizioPlus48HoursGreaterThanEqual(now, nowMinus48Hours);

            if (currentGiornata != null) {
                return currentGiornata;
            } else {
                return giornataRepository.findFirstByTsInizioGreaterThan(now);
            }
        }

        return null;
    }


}
