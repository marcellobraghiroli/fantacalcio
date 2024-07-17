package com.isw.mb.fantacalcio.services.combined;

import com.isw.mb.fantacalcio.models.*;
import com.isw.mb.fantacalcio.models.combined.SqGioAmm;
import com.isw.mb.fantacalcio.services.AmministraService;
import com.isw.mb.fantacalcio.services.GiornataService;
import com.isw.mb.fantacalcio.services.SquadraService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SqGioAmmService {

    private final SquadraService squadraService;
    private final GiornataService giornataService;
    private final AmministraService amministraService;

    @Autowired
    public SqGioAmmService(SquadraService squadraService, GiornataService giornataService, AmministraService amministraService) {
        this.squadraService = squadraService;
        this.giornataService = giornataService;
        this.amministraService = amministraService;
    }

    @Transactional
    public SqGioAmm getSqGioAmm(Allenatore allenatore, Lega lega) {
        Squadra squadraCorrente = squadraService.findSquadraByAllenatoreIdAndLegaId(allenatore.getId(), lega.getId());
        Giornata giornata = giornataService.findCurrentOrNextGiornata(lega);
        boolean isAdmin;

        String grado = "none";

        Amministra amministra = amministraService.findByAllenatoreIdAndLegaId(allenatore.getId(), lega.getId());
        if (amministra != null) {
            grado = amministra.getGradoAdmin();
            isAdmin = true;
        } else {
            isAdmin = false;
        }

        return new SqGioAmm(squadraCorrente, giornata, isAdmin, grado);
    }
}
