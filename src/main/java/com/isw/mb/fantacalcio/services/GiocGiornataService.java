package com.isw.mb.fantacalcio.services;

import com.isw.mb.fantacalcio.models.GiocGiornata;
import com.isw.mb.fantacalcio.repositories.GiocGiornataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GiocGiornataService {

    private final GiocGiornataRepository giocGiornataRepository;

    @Autowired
    public GiocGiornataService(GiocGiornataRepository giocGiornataRepository) {
        this.giocGiornataRepository = giocGiornataRepository;
    }

    public GiocGiornata findGiocGiornataByGiocatoreIdAndGiornataNumero(Integer id, Integer numGiornata) {
        return giocGiornataRepository.findGiocGiornataByGiocatoreIdAndGiornataNumeroAndDeleted(id, numGiornata, 'N');
    }
}
