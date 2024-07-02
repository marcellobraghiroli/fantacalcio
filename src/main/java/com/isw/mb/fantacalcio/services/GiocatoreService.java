package com.isw.mb.fantacalcio.services;

import com.isw.mb.fantacalcio.models.Giocatore;
import com.isw.mb.fantacalcio.repositories.GiocatoreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GiocatoreService {

    private final GiocatoreRepository giocatoreRepository;

    @Autowired
    public GiocatoreService(GiocatoreRepository giocatoreRepository) {
        this.giocatoreRepository = giocatoreRepository;
    }

    @Transactional
    public Giocatore findGiocatoreById(Integer idGioc) {
        return giocatoreRepository.findById(idGioc).orElse(null);
    }
}
