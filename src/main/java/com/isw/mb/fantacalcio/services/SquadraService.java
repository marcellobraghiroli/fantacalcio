package com.isw.mb.fantacalcio.services;

import com.isw.mb.fantacalcio.models.Allenatore;
import com.isw.mb.fantacalcio.models.Lega;
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
    private final LegaService legaService;

    @Autowired
    public SquadraService(SquadraRepository squadraRepository, LegaService legaService) {
        this.squadraRepository = squadraRepository;
        this.legaService = legaService;
    }

    @Transactional
    public List<Squadra> findSquadreByAllenatoreId(Integer idAllenatore) {
        return squadraRepository.findSquadreByAllenatoreId(idAllenatore);
    }

    @Transactional
    public Squadra joinLegaAndCreateSquadra(String codiceInvito, String nomeSquadra, Allenatore allenatore) {
        Lega lega = legaService.findByCodiceInvito(codiceInvito);
        if (lega != null) {

            Squadra squadraAll = findSquadraByAllenatoreIdAndLegaId(allenatore.getId(), lega.getId());
            if(squadraAll != null){
                throw new IllegalArgumentException("Sei già iscritto a questa lega");
            }

            Squadra squadra = new Squadra();
            squadra.setNome(nomeSquadra);
            squadra.setAllenatore(allenatore);
            squadra.setLega(lega);
            return squadraRepository.save(squadra);

        } else {
            throw new IllegalArgumentException("Codice di invito non valido");
        }
    }

    public Squadra findSquadraByAllenatoreIdAndLegaId(Integer idAll, Integer idLega) {
        return squadraRepository.findSquadraByAllenatoreIdAndLegaId(idAll, idLega);
    }

}
