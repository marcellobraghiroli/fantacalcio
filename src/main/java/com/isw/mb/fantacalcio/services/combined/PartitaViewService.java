package com.isw.mb.fantacalcio.services.combined;

import com.isw.mb.fantacalcio.models.Formazione;
import com.isw.mb.fantacalcio.models.GiocGiornata;
import com.isw.mb.fantacalcio.models.Partita;
import com.isw.mb.fantacalcio.models.combined.PartitaViewModel;
import com.isw.mb.fantacalcio.services.FormazioneService;
import com.isw.mb.fantacalcio.services.PartitaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PartitaViewService {

    private final PartitaService partitaService;
    private final FormazioneService formazioneService;

    @Autowired
    public PartitaViewService(PartitaService partitaService, FormazioneService formazioneService) {
        this.partitaService = partitaService;
        this.formazioneService = formazioneService;
    }

    @Transactional
    public PartitaViewModel getPartitaViewModel(Integer idPartita, Integer idSqCasa, Integer idSqTrasf, Integer numGiornata) {


        Partita partita = partitaService.findPartitaById(idPartita);
        Formazione formCasa = formazioneService.findFormazioneByPartitaIdAndSquadraIdAndStatsGiocatori(idPartita, idSqCasa, numGiornata);
        Formazione formTrasf = formazioneService.findFormazioneByPartitaIdAndSquadraIdAndStatsGiocatori(idPartita, idSqTrasf, numGiornata);

        setStatisticheGiocatori(numGiornata, formCasa);

        setStatisticheGiocatori(numGiornata, formTrasf);

        return new PartitaViewModel(partita, formCasa, formTrasf);

    }

    private void setStatisticheGiocatori(Integer numGiornata, Formazione formazione) {
        if (formazione != null) {
            formazione.getFormGiocatori().forEach(formGioc -> {
                Set<GiocGiornata> giocGiornate = formGioc.getGiocatore().getGiocGiornate();
                for(GiocGiornata giocGiornata : giocGiornate) {
                    if(giocGiornata.getGiornata().getNumero().equals(numGiornata)) {
                        formGioc.getGiocatore().setGiocGiornata(giocGiornata);
                        break;
                    }
                }
            });
        }
    }

}
