package com.isw.mb.fantacalcio.services.combined;

import com.isw.mb.fantacalcio.models.Formazione;
import com.isw.mb.fantacalcio.models.GiocSquadra;
import com.isw.mb.fantacalcio.models.Giornata;
import com.isw.mb.fantacalcio.models.Squadra;
import com.isw.mb.fantacalcio.models.combined.FormGiocatori;
import com.isw.mb.fantacalcio.services.FormazioneService;
import com.isw.mb.fantacalcio.services.GiocSquadraService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class FormGiocatoriService {

    private final GiocSquadraService giocSquadraService;
    private final FormazioneService formazioneService;

    @Autowired
    public FormGiocatoriService(GiocSquadraService giocSquadraService, FormazioneService formazioneService) {
        this.giocSquadraService = giocSquadraService;
        this.formazioneService = formazioneService;
    }

    @Transactional
    public FormGiocatori getFormGiocatori(Squadra squadra, Giornata giornata) {

        Set<GiocSquadra> giocatori = giocSquadraService.findGiocatoriBySquadra(squadra);
        Formazione formazione = formazioneService.findFormazioneBySquadraAndGiornata(squadra, giornata);

        return new FormGiocatori(formazione, giocatori);
    }

}
