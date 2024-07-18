package com.isw.mb.fantacalcio.services.combined;

import com.isw.mb.fantacalcio.models.Formazione;
import com.isw.mb.fantacalcio.models.GiocGiornata;
import com.isw.mb.fantacalcio.models.Partita;
import com.isw.mb.fantacalcio.models.combined.PartitaViewModel;
import com.isw.mb.fantacalcio.services.FormazioneService;
import com.isw.mb.fantacalcio.services.GiocGiornataService;
import com.isw.mb.fantacalcio.services.PartitaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PartitaViewService {

    private final PartitaService partitaService;
    private final FormazioneService formazioneService;
    private final GiocGiornataService giocGiornataService;

    @Autowired
    public PartitaViewService(PartitaService partitaService, FormazioneService formazioneService, GiocGiornataService giocGiornataService) {
        this.partitaService = partitaService;
        this.formazioneService = formazioneService;
        this.giocGiornataService = giocGiornataService;
    }

    @Transactional
    public PartitaViewModel getPartitaViewModel(Integer idPartita, Integer idSqCasa, Integer idSqTrasf, Integer numGiornata) {

        Partita partita = partitaService.findPartitaById(idPartita);
        Formazione formCasa = formazioneService.findFormazioneByPartitaIdAndSquadraIdAndStatsGiocatori(idPartita, idSqCasa, numGiornata);
        Formazione formTrasf = formazioneService.findFormazioneByPartitaIdAndSquadraIdAndStatsGiocatori(idPartita, idSqTrasf, numGiornata);

        if (formCasa != null) {
            formCasa.getFormGiocatori().forEach(formGioc -> {
                Set<GiocGiornata> giocGiornate = formGioc.getGiocatore().getGiocGiornate();
                if (!giocGiornate.isEmpty()) {
                    formGioc.getGiocatore().setGiocGiornata(giocGiornate.iterator().next());
                }
            });
        } //else {
        //formCasa = new Formazione();
        //}
        if (formTrasf != null) {
            formTrasf.getFormGiocatori().forEach(formGioc -> {
                Set<GiocGiornata> giocGiornate = formGioc.getGiocatore().getGiocGiornate();
                if (!giocGiornate.isEmpty()) {
                    formGioc.getGiocatore().setGiocGiornata(giocGiornate.iterator().next());
                }
            });
        } //else {
        //formTrasf = new Formazione();
        //}

        return new PartitaViewModel(partita, formCasa, formTrasf);

    }

}
