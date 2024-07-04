package com.isw.mb.fantacalcio.services;

import com.isw.mb.fantacalcio.models.Giocatore;
import com.isw.mb.fantacalcio.repositories.GiocatoreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiocatoreService {

    private final GiocatoreRepository giocatoreRepository;

    @Autowired
    public GiocatoreService(GiocatoreRepository giocatoreRepository) {
        this.giocatoreRepository = giocatoreRepository;
    }

    @Transactional
    public Giocatore findGiocatoreById(Integer idGioc) {
        return giocatoreRepository.findByIdAndDeleted(idGioc, 'N');
    }

    @Transactional
    public List<Giocatore> findGiocatoriByRuoloOrderByNome(String ruolo) {
        return giocatoreRepository.findGiocatoriByRuoloAndDeletedOrderByNome(ruolo, 'N');
    }
}
