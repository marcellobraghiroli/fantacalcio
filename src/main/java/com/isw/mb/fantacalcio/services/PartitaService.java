package com.isw.mb.fantacalcio.services;

import com.isw.mb.fantacalcio.models.*;
import com.isw.mb.fantacalcio.repositories.GiornataRepository;
import com.isw.mb.fantacalcio.repositories.LegaRepository;
import com.isw.mb.fantacalcio.repositories.PartitaRepository;
import com.isw.mb.fantacalcio.repositories.SquadraRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PartitaService {

    private final SquadraRepository squadraRepository;
    private final GiornataRepository giornataRepository;
    private final LegaRepository legaRepository;
    private final PartitaRepository partitaRepository;

    @Autowired
    public PartitaService(SquadraRepository squadraRepository, GiornataRepository giornataRepository, LegaRepository legaRepository, PartitaRepository partitaRepository) {
        this.squadraRepository = squadraRepository;
        this.giornataRepository = giornataRepository;
        this.legaRepository = legaRepository;
        this.partitaRepository = partitaRepository;
    }

    @Transactional
    public void generaCalendario(Integer idLega) {

        /*
        Lega lega = new Lega();
        lega.setId(idLega);

         */

        Lega lega = legaRepository.findById(idLega).get();

        List<Squadra> squadreLega = squadraRepository.findSquadreByLegaIdAndDeletedOrderByNome(idLega, 'N');
        Squadra[] squadre = squadreLega.toArray(new Squadra[0]);
        //Squadra[] squadre = new Squadra[8];

        /*
        for(int i = 0; i < squadre.length; i++) {
            squadre[i] = new Squadra();
            squadre[i].setNome(String.valueOf(i+1));
        }

         */

        int numSquadre = squadre.length;

        if(lega.getNumSquadre() != numSquadre) {
            throw new IllegalArgumentException("Non ci sono " + lega.getNumSquadre() + " squadre nella lega");
        }

        Partita[][] pairings = new Partita[numSquadre - 1][numSquadre / 2];

        int k = 0;

        //Setting squadre casa
        for(int i = 0; i < numSquadre - 1; i++) {
            for(int j = 0; j < numSquadre / 2; j++) {
                pairings[i][j] = new Partita();
                pairings[i][j].setSquadraCasa(squadre[k]);
                k++;
                if(k == numSquadre - 1) {
                    k = 0;
                }
            }
        }

        //Setting squadre trasferta
        for(int i = 0; i < numSquadre - 1; i++) {
            for(int j = 0; j < numSquadre / 2; j++) {
                if (i+1 <= numSquadre-2) {
                    pairings[i][j].setSquadraTrasf(pairings[i+1][numSquadre/2-1-j].getSquadraCasa());
                } else {
                    pairings[i][j].setSquadraTrasf(pairings[0][numSquadre/2-1-j].getSquadraCasa());
                }
            }
        }

        //Setting n-esima squadra
        for(int i = 0; i < numSquadre - 1; i++) {
            for(int j = 0; j < numSquadre / 2; j++) {

                if(j == 0) {
                    if(i%2 == 0) {
                        pairings[i][j].setSquadraCasa(squadre[numSquadre - 1]);
                    } else {
                        pairings[i][j].setSquadraTrasf(squadre[numSquadre - 1]);
                    }
                }

            }
        }

        //Stampa calendario
        /*
        for(int i = 0; i < numSquadre - 1; i++) {
            for(int j = 0; j < numSquadre / 2; j++) {
                System.out.print(pairings[i][j].getSquadraCasa().getNome() + " vs " + pairings[i][j].getSquadraTrasf().getNome() + " ");
            }

            System.out.println();
        }

         */

        List<Giornata> giornate = giornataRepository.findAllOrderByNumero();

        int numGiornate = giornate.size();

        for(int i = 0; i < numGiornate; i++) {

            for(int j = 0; j < numSquadre/2; j++) {

                Partita partita = new Partita();
                partita.setGiornata(giornate.get(i));
                partita.setLega(lega);

                int interval = (int) (i / (numSquadre - 1));

                if(interval % 2 == 0) {
                    partita.setSquadraCasa(pairings[i % (numSquadre - 1)][j].getSquadraCasa());
                    partita.setSquadraTrasf(pairings[i % (numSquadre - 1)][j].getSquadraTrasf());
                } else {
                    partita.setSquadraCasa(pairings[i % (numSquadre - 1)][j].getSquadraTrasf());
                    partita.setSquadraTrasf(pairings[i % (numSquadre - 1)][j].getSquadraCasa());
                }

                partitaRepository.save(partita);

                //System.out.print(partita.getSquadraCasa().getNome() + " vs " + partita.getSquadraTrasf().getNome() + "  -  ");

            }


            /*
            System.out.println();
            if(i % (numSquadre - 1) == 6) {
                System.out.println();
            }
             */


        }

    }

    @Transactional
    public List<Partita> findPartiteByLegaId(Integer idLega) {
        return partitaRepository.findPartiteByLegaIdAndDeletedOrderByGiornataAscIdAsc(idLega, 'N');
    }
}
