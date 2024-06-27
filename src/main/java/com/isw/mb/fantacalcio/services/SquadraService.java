package com.isw.mb.fantacalcio.services;

import com.isw.mb.fantacalcio.models.Squadra;
import com.isw.mb.fantacalcio.repositories.SquadraRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class SquadraService {

    private final SquadraRepository squadraRepository;

    @Autowired
    public SquadraService(SquadraRepository squadraRepository) {
        this.squadraRepository = squadraRepository;
    }

    @Transactional
    public List<Squadra> findSquadreByAllenatoreId(Integer idAllenatore) {
        return squadraRepository.findSquadreByAllenatoreId(idAllenatore);
    }

}
