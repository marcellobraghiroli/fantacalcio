package com.isw.mb.fantacalcio.services.combined;

import com.isw.mb.fantacalcio.models.Giornata;
import com.isw.mb.fantacalcio.models.Lega;
import com.isw.mb.fantacalcio.models.Partita;
import com.isw.mb.fantacalcio.models.combined.CalendarioViewModel;
import com.isw.mb.fantacalcio.services.GiornataService;
import com.isw.mb.fantacalcio.services.PartitaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalendarioViewService {

    private final PartitaService partitaService;
    private final GiornataService giornataService;

    @Autowired
    public CalendarioViewService(PartitaService partitaService, GiornataService giornataService) {
        this.partitaService = partitaService;
        this.giornataService = giornataService;
    }

    @Transactional
    public CalendarioViewModel getCalendarioViewModel(Lega legaCorrente, Integer numGiornata) {

        List<Partita> partite = partitaService.findPartiteByLegaId(legaCorrente.getId());

        Giornata giornataCorrente;

        if (numGiornata == null) {
            giornataCorrente = giornataService.findCurrentOrNextGiornata(legaCorrente);
        } else {
            giornataCorrente = giornataService.findByNumero(numGiornata);
        }

        return new CalendarioViewModel(partite, giornataCorrente);

    }
}
