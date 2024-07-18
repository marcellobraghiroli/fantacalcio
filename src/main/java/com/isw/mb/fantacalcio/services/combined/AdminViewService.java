package com.isw.mb.fantacalcio.services.combined;

import com.isw.mb.fantacalcio.models.Allenatore;
import com.isw.mb.fantacalcio.models.Amministra;
import com.isw.mb.fantacalcio.models.Giornata;
import com.isw.mb.fantacalcio.models.Lega;
import com.isw.mb.fantacalcio.models.combined.AdminViewModel;
import com.isw.mb.fantacalcio.repositories.PartitaRepository;
import com.isw.mb.fantacalcio.services.AllenatoreService;
import com.isw.mb.fantacalcio.services.AmministraService;
import com.isw.mb.fantacalcio.services.GiornataService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminViewService {

    private final AllenatoreService allenatoreService;
    private final AmministraService amministraService;
    private final PartitaRepository partitaRepository;
    private final GiornataService giornataService;

    @Autowired
    public AdminViewService(AllenatoreService allenatoreService, AmministraService amministraService,
                            PartitaRepository partitaRepository, GiornataService giornataService) {
        this.allenatoreService = allenatoreService;
        this.amministraService = amministraService;
        this.partitaRepository = partitaRepository;
        this.giornataService = giornataService;
    }

    @Transactional
    public AdminViewModel getAdminViewModel(Lega legaCorrente, Allenatore allenatoreLoggato) {

        List<Allenatore> allenatori = allenatoreService.findAllenatoriByLegaAndNotLogged(legaCorrente, allenatoreLoggato);
        Amministra amministra = amministraService.findByAllenatoreIdAndLegaId(allenatoreLoggato.getId(), legaCorrente.getId());
        boolean started = partitaRepository.existsByLegaAndDeleted(legaCorrente, 'N');
        List<Giornata> giornate = giornataService.findGiornate();

        return new AdminViewModel(allenatori, amministra.getGradoAdmin(), started, giornate);

    }

}
