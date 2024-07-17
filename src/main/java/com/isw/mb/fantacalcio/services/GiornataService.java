package com.isw.mb.fantacalcio.services;

import com.isw.mb.fantacalcio.models.*;
import com.isw.mb.fantacalcio.repositories.*;
import jakarta.persistence.criteria.CriteriaBuilder;
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
    private final GiocGiornataRepository giocGiornataRepository;
    private final SquadraRepository squadraRepository;

    @Autowired
    public GiornataService(PartitaRepository partitaRepository, GiornataRepository giornataRepository, GiocGiornataRepository giocGiornataRepository, SquadraRepository squadraRepository) {
        this.partitaRepository = partitaRepository;
        this.giornataRepository = giornataRepository;
        this.giocGiornataRepository = giocGiornataRepository;
        this.squadraRepository = squadraRepository;
    }

    @Transactional
    public Giornata findCurrentOrNextGiornata(Lega legaCorrente) {

        boolean calendarioGenerato = partitaRepository.existsByLegaAndDeleted(legaCorrente, 'N');

        if (calendarioGenerato) {

            LocalDateTime now = LocalDateTime.now();
            //LocalDateTime now = LocalDateTime.of(2024, 7, 16, 11, 0, 0);
            LocalDateTime nowMinus48Hours = now.minusHours(48);

            Giornata currentGiornata = giornataRepository.findFirstByTsInizioLessThanEqualAndTsInizioPlus48HoursGreaterThanEqual(now, nowMinus48Hours);

            if (currentGiornata != null) {
                return currentGiornata;
            } else {
                return giornataRepository.findFirstByTsInizioGreaterThanAndDeleted(now, 'N');
            }
        }

        return null;
    }

    @Transactional
    public List<Giornata> findGiornate() {
        return giornataRepository.findAllOrderByNumero();
    }

    @Transactional
    public void calcolaGiornata(Integer idLega, Integer numGiornata) {

        Giornata giornata = new Giornata();
        giornata.setNumero(numGiornata);

        Lega lega = new Lega();
        lega.setId(idLega);

        if (!giocGiornataRepository.existsByGiornataNumeroAndDeleted(numGiornata, 'N')) {
            throw new IllegalArgumentException("I voti per la giornata " + numGiornata + " non sono ancora stati caricati");
        }

        List<Partita> partite = partitaRepository.findByLegaIdAndGiornataNumeroAndDeletedOrderByIdAsc(idLega, numGiornata, 'N');

        for (Partita p : partite) {

            Squadra squadraCasa = p.getSquadraCasa();
            Squadra squadraTrasf = p.getSquadraTrasf();

            List<GiocGiornata> votiCasa = giocGiornataRepository.findVotiPartita(p.getId(), squadraCasa.getId(), numGiornata);
            List<GiocGiornata> votiTrasf = giocGiornataRepository.findVotiPartita(p.getId(), squadraTrasf.getId(), numGiornata);

            //System.out.println(squadraCasa.getNome() + " - " + squadraTrasf.getNome() + " " + votiCasa.size() + " " + votiTrasf.size());

            float puntiCasa = 0f;
            for (GiocGiornata gg : votiCasa) {
                //System.out.println(gg.getGiocatore().getNome());
                puntiCasa += gg.getFantavoto();
            }

            float puntiTrasf = 0f;
            for (GiocGiornata gg : votiTrasf) {
                puntiTrasf += gg.getFantavoto();
            }

            int goalCasa, goalTrasf;

            if (puntiCasa == 0f && puntiTrasf != 0f) {
                goalCasa = 0;
                goalTrasf = 3;
            } else if (puntiCasa != 0f && puntiTrasf == 0f) {
                goalCasa = 3;
                goalTrasf = 0;
            } else {

                if (puntiCasa < 66.0) {
                    goalCasa = 0;
                } else {
                    goalCasa = (int) ((puntiCasa - 66) / 6) + 1;
                }

                if (puntiTrasf < 66.0) {
                    goalTrasf = 0;
                } else {
                    goalTrasf = (int) ((puntiTrasf - 66) / 6) + 1;
                }
            }

            String risultato = goalCasa + " - " + goalTrasf;

            //Setting statistiche partita
            p.setPuntiCasa(puntiCasa);
            p.setPuntiTrasf(puntiTrasf);
            p.setGoalCasa(goalCasa);
            p.setGoalTrasf(goalTrasf);
            p.setRisultato(risultato);

            //Aggiornamento statistiche squadre
            squadraCasa.setFantapunti(squadraCasa.getFantapunti() + puntiCasa);
            squadraTrasf.setFantapunti(squadraTrasf.getFantapunti() + puntiTrasf);

            squadraCasa.setPartGiocate(squadraCasa.getPartGiocate() + 1);
            squadraTrasf.setPartGiocate(squadraTrasf.getPartGiocate() + 1);

            squadraCasa.setGoalFatti(squadraCasa.getGoalFatti() + goalCasa);
            squadraTrasf.setGoalFatti(squadraTrasf.getGoalFatti() + goalTrasf);

            squadraCasa.setGoalSubiti(squadraCasa.getGoalSubiti() + goalTrasf);
            squadraTrasf.setGoalSubiti(squadraTrasf.getGoalSubiti() + goalCasa);

            if (goalCasa > goalTrasf) {
                squadraCasa.setPuntiClass(squadraCasa.getPuntiClass() + 3);

                squadraCasa.setPartVinte(squadraCasa.getPartVinte() + 1);
                squadraTrasf.setPartPerse(squadraTrasf.getPartPerse() + 1);
            } else if (goalCasa == goalTrasf) {
                squadraCasa.setPuntiClass(squadraCasa.getPuntiClass() + 1);
                squadraTrasf.setPuntiClass(squadraTrasf.getPuntiClass() + 1);

                squadraCasa.setPartParegg(squadraCasa.getPartParegg() + 1);
                squadraTrasf.setPartParegg(squadraTrasf.getPartParegg() + 1);
            } else {
                squadraTrasf.setPuntiClass(squadraTrasf.getPuntiClass() + 3);

                squadraCasa.setPartPerse(squadraCasa.getPartPerse() + 1);
                squadraTrasf.setPartVinte(squadraTrasf.getPartVinte() + 1);
            }

            partitaRepository.save(p);
            squadraRepository.save(squadraCasa);
            squadraRepository.save(squadraTrasf);

        }


    }
}
