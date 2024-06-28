package com.isw.mb.fantacalcio.services;

import com.isw.mb.fantacalcio.models.AmministraId;
import com.isw.mb.fantacalcio.repositories.AmministraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AmministraService {

    private final AmministraRepository amministraRepository;

    @Autowired
    public AmministraService(AmministraRepository amministraRepository) {
        this.amministraRepository = amministraRepository;
    }

    public boolean existsAmministra(int allenatoreId, int legaId) {
        AmministraId amministraId = new AmministraId(allenatoreId, legaId);
        return amministraRepository.existsById(amministraId);
    }

}
