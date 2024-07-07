package com.isw.mb.fantacalcio.services.combined;

import com.isw.mb.fantacalcio.models.Formazione;
import com.isw.mb.fantacalcio.models.GiocGiornata;
import com.isw.mb.fantacalcio.models.Giocatore;
import com.isw.mb.fantacalcio.models.Partita;
import com.isw.mb.fantacalcio.models.combined.PartForm;
import com.isw.mb.fantacalcio.services.FormazioneService;
import com.isw.mb.fantacalcio.services.GiocGiornataService;
import com.isw.mb.fantacalcio.services.PartitaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartFormService {

    private final PartitaService partitaService;
    private final FormazioneService formazioneService;
    private final GiocGiornataService giocGiornataService;

    @Autowired
    public PartFormService(PartitaService partitaService, FormazioneService formazioneService, GiocGiornataService giocGiornataService) {
        this.partitaService = partitaService;
        this.formazioneService = formazioneService;
        this.giocGiornataService = giocGiornataService;
    }

    @Transactional
    public PartForm getPartForm(Integer idPartita, Integer idSqCasa, Integer idSqTrasf, Integer numGiornata) {

        Partita partita = partitaService.findPartitaById(idPartita);
        Formazione formCasa = formazioneService.findFormazioneByPartitaIdAndSquadraId(idPartita, idSqCasa);
        Formazione formTrasf = formazioneService.findFormazioneByPartitaIdAndSquadraId(idPartita, idSqTrasf);

        if (formCasa != null) {
            formCasa.getFormGiocatori().forEach(formGioc -> enrichGiocatoreWithGiocGiornata(formGioc.getGiocatore(), numGiornata));
        } else {
            formCasa = new Formazione();
        }
        if (formTrasf != null) {
            formTrasf.getFormGiocatori().forEach(formGioc -> enrichGiocatoreWithGiocGiornata(formGioc.getGiocatore(), numGiornata));
        }  else {
            formTrasf = new Formazione();
        }

        return new PartForm(partita, formCasa, formTrasf);

    }


    private void enrichGiocatoreWithGiocGiornata(Giocatore giocatore, Integer numGiornata) {
        GiocGiornata giocGiornata = giocGiornataService.findGiocGiornataByGiocatoreIdAndGiornataNumero(giocatore.getId(), numGiornata);
        giocatore.setGiocGiornata(giocGiornata);
    }


}
