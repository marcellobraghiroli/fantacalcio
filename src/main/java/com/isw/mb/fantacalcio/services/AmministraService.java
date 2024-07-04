package com.isw.mb.fantacalcio.services;

import com.isw.mb.fantacalcio.models.Allenatore;
import com.isw.mb.fantacalcio.models.Amministra;
import com.isw.mb.fantacalcio.models.AmministraId;
import com.isw.mb.fantacalcio.models.Lega;
import com.isw.mb.fantacalcio.repositories.AmministraRepository;
import jakarta.transaction.Transactional;
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

    public Amministra findByAllenatoreIdAndLegaId(Integer idAll, Integer idLega) {
        AmministraId amministraId = new AmministraId(idAll, idLega);
        return amministraRepository.findById(amministraId).orElse(null);
    }

    @Transactional
    public Amministra promuoviAdmin(Integer idAll, Integer idLega) {

        AmministraId amministraId = new AmministraId(idAll, idLega);

        if(amministraRepository.existsById(amministraId)) {
            throw new IllegalArgumentException("L'allenatore è già admin di questa lega");
        }

        Allenatore allenatore = new Allenatore();
        allenatore.setId(idAll);
        Lega lega = new Lega();
        lega.setId(idLega);

        Amministra amministra = new Amministra(allenatore, lega);
        amministra.setGradoAdmin("base");

        return amministraRepository.save(amministra);

    }
}
