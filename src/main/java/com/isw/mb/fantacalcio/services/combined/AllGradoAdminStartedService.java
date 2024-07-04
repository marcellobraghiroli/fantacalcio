package com.isw.mb.fantacalcio.services.combined;

import com.isw.mb.fantacalcio.models.Allenatore;
import com.isw.mb.fantacalcio.models.Amministra;
import com.isw.mb.fantacalcio.models.Lega;
import com.isw.mb.fantacalcio.models.combined.AllGradoAdminStarted;
import com.isw.mb.fantacalcio.repositories.PartitaRepository;
import com.isw.mb.fantacalcio.services.AllenatoreService;
import com.isw.mb.fantacalcio.services.AmministraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AllGradoAdminStartedService {

    private final AllenatoreService allenatoreService;
    private final AmministraService amministraService;
    private final PartitaRepository partitaRepository;

    @Autowired
    public AllGradoAdminStartedService(AllenatoreService allenatoreService, AmministraService amministraService, PartitaRepository partitaRepository) {
        this.allenatoreService = allenatoreService;
        this.amministraService = amministraService;
        this.partitaRepository = partitaRepository;
    }

    public AllGradoAdminStarted getAllGradoAdminStarted(Lega legaCorrente, Allenatore allenatoreLoggato) {

        List<Allenatore> allenatori = allenatoreService.findAllenatoriByLegaAndNotLogged(legaCorrente, allenatoreLoggato);
        Amministra amministra = amministraService.findByAllenatoreIdAndLegaId(allenatoreLoggato.getId(), legaCorrente.getId());
        boolean started = partitaRepository.existsByLegaAndDeleted(legaCorrente, 'N');

        return new AllGradoAdminStarted(allenatori, amministra.getGradoAdmin(), started);

    }

}
