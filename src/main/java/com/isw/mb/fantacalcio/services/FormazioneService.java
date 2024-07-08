package com.isw.mb.fantacalcio.services;

import com.isw.mb.fantacalcio.models.Formazione;
import com.isw.mb.fantacalcio.models.Giornata;
import com.isw.mb.fantacalcio.models.Squadra;
import com.isw.mb.fantacalcio.repositories.FormazioneRepository;
import com.isw.mb.fantacalcio.repositories.PartitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormazioneService {

    private final FormazioneRepository formazioneRepository;

    @Autowired
    public FormazioneService(FormazioneRepository formazioneRepository) {
        this.formazioneRepository = formazioneRepository;
    }

    public Formazione findFormazioneByPartitaIdAndSquadraId(Integer idPartita, Integer idSqCasa) {
        return formazioneRepository.findFormazioneByPartitaIdAndSquadraId(idPartita, idSqCasa);
    }

    public Formazione findFormazioneBySquadraAndGiornata(Squadra squadra, Giornata giornata) {
        return formazioneRepository.findFormazioneBySquadraAndGiornataAndDeleted(squadra, giornata, 'N');
    }
}
