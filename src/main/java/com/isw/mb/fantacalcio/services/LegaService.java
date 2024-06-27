package com.isw.mb.fantacalcio.services;

import com.isw.mb.fantacalcio.repositories.LegaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LegaService {

    private final LegaRepository legaRepository;

    @Autowired
    public LegaService(LegaRepository legaRepository) {
        this.legaRepository = legaRepository;
    }



}
