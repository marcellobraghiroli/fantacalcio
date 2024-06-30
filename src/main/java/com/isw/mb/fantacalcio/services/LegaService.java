package com.isw.mb.fantacalcio.services;

import com.isw.mb.fantacalcio.models.*;
import com.isw.mb.fantacalcio.repositories.AmministraRepository;
import com.isw.mb.fantacalcio.repositories.CodiceRepository;
import com.isw.mb.fantacalcio.repositories.LegaRepository;
import com.isw.mb.fantacalcio.repositories.SquadraRepository;
import com.isw.mb.fantacalcio.utils.InvitationCodeUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LegaService {

    private final LegaRepository legaRepository;
    private final AmministraRepository amministraRepository;
    private final SquadraRepository squadraRepository;
    private final CodiceRepository codiceRepository;

    @Autowired
    public LegaService(LegaRepository legaRepository, AmministraRepository amministraRepository, SquadraRepository squadraRepository, CodiceRepository codiceRepository) {
        this.legaRepository = legaRepository;
        this.amministraRepository = amministraRepository;
        this.squadraRepository = squadraRepository;
        this.codiceRepository = codiceRepository;
    }

    public Lega findByCodiceInvito(String codiceInvito) {
        return legaRepository.findByCodiceInvitoAndDeleted(codiceInvito, 'N');
    }

    @Transactional
    public Lega createLegaAndSetAdminAndCreateSquadra(Lega lega, Allenatore admin, String nomeSquadra) {

        if (legaRepository.existsByNome(lega.getNome())) {
            throw new IllegalArgumentException("Nome lega già esistente");
        }

        Codice ultimoCodice = codiceRepository.findById(1).orElseThrow();
        String lastCode = ultimoCodice.getUltimoCodice();
        String newCode = InvitationCodeUtils.generateCode(lastCode);
        lega.setCodiceInvito(newCode);

        ultimoCodice.setUltimoCodice(newCode);
        codiceRepository.save(ultimoCodice);


        Amministra amministra = new Amministra(admin, lega);
        amministra.setAllenatore(admin);
        amministra.setLega(lega);
        amministra.setGradoAdmin("super");

        Squadra squadra = new Squadra();
        squadra.setNome(nomeSquadra);
        squadra.setAllenatore(admin);
        squadra.setLega(lega);

        legaRepository.save(lega);
        amministraRepository.save(amministra);
        squadraRepository.save(squadra);

        return lega;

    }


}
