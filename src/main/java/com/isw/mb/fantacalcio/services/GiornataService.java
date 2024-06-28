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
    private final SquadraRepository squadraRepository;

    @Autowired
    public GiornataService(PartitaRepository partitaRepository, GiornataRepository giornataRepository, SquadraRepository squadraRepository) {
        this.partitaRepository = partitaRepository;
        this.giornataRepository = giornataRepository;
        this.squadraRepository = squadraRepository;
    }

    @Transactional
    public Giornata findCurrentOrNextGiornata(Allenatore allenatoreLoggato, Lega legaCorrente) {

        Squadra squadra = squadraRepository.findSquadraByAllenatoreIdAndLegaId(allenatoreLoggato.getId(), legaCorrente.getId());

        Set<Partita> partiteCasa = squadra.getPartiteCasa();
        Set<Partita> partiteTrasf = squadra.getPartiteTrasf();
        Set<Partita> partite = new LinkedHashSet<>();
        partite.addAll(partiteCasa);
        partite.addAll(partiteTrasf);

        if (!partite.isEmpty()) {

            LocalDateTime now = LocalDateTime.now();
            //LocalDateTime now = LocalDateTime.of(2024, 7, 7, 10, 0, 0);
            LocalDateTime nowMinus48Hours = now.minusHours(48);

            Giornata currentGiornata = giornataRepository.findFirstByTsInizioLessThanEqualAndTsInizioPlus48HoursGreaterThanEqual(now, nowMinus48Hours);

            if (currentGiornata != null) {
                //System.out.println("Current giornata");
                return currentGiornata;
            } else {
                //System.out.println("Next giornata");
                return giornataRepository.findFirstByTsInizioGreaterThan(now);
            }
        }

        return null;
    }

}
