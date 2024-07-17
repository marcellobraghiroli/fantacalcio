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
        return amministraRepository.existsByIdAndDeleted(amministraId, 'N');
    }

    public Amministra findByAllenatoreIdAndLegaId(Integer idAll, Integer idLega) {
        AmministraId amministraId = new AmministraId(idAll, idLega);
        return amministraRepository.findByIdAndDeleted(amministraId, 'N');
    }

    @Transactional
    public Amministra promuoviAdmin(Integer idAll, Integer idLega) {

        AmministraId amministraId = new AmministraId(idAll, idLega);

        if(amministraRepository.existsByIdAndDeleted(amministraId, 'N')) {
            throw new IllegalArgumentException("L'allenatore è già un admin");
        }

        Allenatore allenatore = new Allenatore();
        allenatore.setId(idAll);
        Lega lega = new Lega();
        lega.setId(idLega);

        Amministra amministra = new Amministra(allenatore, lega);
        amministra.setGradoAdmin("base");

        return amministraRepository.save(amministra);

    }

    @Transactional
    public Amministra degradaAdmin(Integer idAll, Integer idLega) {

        AmministraId amministraId = new AmministraId(idAll, idLega);

        Amministra amministra = amministraRepository.findByIdAndDeleted(amministraId, 'N');

        if(amministra == null) {
            throw new IllegalArgumentException("L'allenatore non è un admin");
        }

        amministra.setDeleted('Y');

        return amministraRepository.save(amministra);

    }
}
