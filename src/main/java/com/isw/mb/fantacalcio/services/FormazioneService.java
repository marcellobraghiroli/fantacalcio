package com.isw.mb.fantacalcio.services;

import com.isw.mb.fantacalcio.models.*;
import com.isw.mb.fantacalcio.repositories.FormGiocRepository;
import com.isw.mb.fantacalcio.repositories.FormazioneRepository;
import com.isw.mb.fantacalcio.repositories.PartitaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class FormazioneService {

    private final FormazioneRepository formazioneRepository;
    private final PartitaRepository partitaRepository;
    private final FormGiocRepository formGiocRepository;

    @Autowired
    public FormazioneService(FormazioneRepository formazioneRepository, PartitaRepository partitaRepository, FormGiocRepository formGiocRepository) {
        this.formazioneRepository = formazioneRepository;
        this.partitaRepository = partitaRepository;
        this.formGiocRepository = formGiocRepository;
    }

    public Formazione findFormazioneByPartitaIdAndSquadraIdAndStatsGiocatori(Integer idPartita, Integer idSqCasa, Integer numGiornata) {
        return formazioneRepository.findFormazioneByPartitaIdAndSquadraIdAndStatsGiocatoriAndDeleted(idPartita, idSqCasa, numGiornata, 'N');
    }

    public Formazione findFormazioneBySquadraAndGiornata(Squadra squadra, Giornata giornata) {
        return formazioneRepository.findFormazioneBySquadraAndGiornataAndDeleted(squadra, giornata, 'N');
    }

    @Transactional
    public void saveFormazione(Integer idFormazione, String modulo, Integer idSquadra, Integer numGiornata, String idGiocatori) {

        Formazione formazione = new Formazione();
        formazione.setId(idFormazione);
        formazione.setModulo(modulo);

        Squadra squadra = new Squadra();
        squadra.setId(idSquadra);

        formazione.setSquadra(squadra);

        Partita partita = partitaRepository.findPartitaBySquadraAndGiornata(idSquadra, numGiornata);

        formazione.setPartita(partita);

        List<Integer> idGiocatoriList = Stream.of(idGiocatori.split(",")).map(Integer::parseInt).toList();

        List<FormGioc> giocatori = new ArrayList<FormGioc>();

        for (int i = 0; i < idGiocatoriList.size(); i++) {
            Giocatore giocatore = new Giocatore();
            giocatore.setId(idGiocatoriList.get(i));

            FormGioc formGioc = new FormGioc(formazione, giocatore);
            formGioc.setOrdine(i + 1);
            giocatori.add(formGioc);
        }

        formazioneRepository.save(formazione);

        if (idFormazione != null) {
            markFormGiocAsDeleted(idFormazione);
        }

        formGiocRepository.saveAll(giocatori);

    }


    private void markFormGiocAsDeleted(Integer idFormazione) {

        Formazione formazione = new Formazione();
        formazione.setId(idFormazione);

        List<FormGioc> giocatori = formGiocRepository.findByFormazione(formazione);

        for (FormGioc giocatore : giocatori) {
            giocatore.setDeleted('Y');
        }

        formGiocRepository.saveAll(giocatori);
    }


}
