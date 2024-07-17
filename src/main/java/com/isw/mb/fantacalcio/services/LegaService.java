package com.isw.mb.fantacalcio.services;

import com.isw.mb.fantacalcio.models.*;
import com.isw.mb.fantacalcio.repositories.*;
import com.isw.mb.fantacalcio.utils.InvitationCodeUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class LegaService {

    private final LegaRepository legaRepository;
    private final AmministraRepository amministraRepository;
    private final SquadraRepository squadraRepository;
    private final CodiceRepository codiceRepository;
    private final PartitaRepository partitaRepository;

    @Autowired
    public LegaService(LegaRepository legaRepository, AmministraRepository amministraRepository,
                       SquadraRepository squadraRepository, CodiceRepository codiceRepository,
                       PartitaRepository partitaRepository) {
        this.legaRepository = legaRepository;
        this.amministraRepository = amministraRepository;
        this.squadraRepository = squadraRepository;
        this.codiceRepository = codiceRepository;
        this.partitaRepository = partitaRepository;
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

    @Transactional
    public List<Lega> findLegheByAllenatoreId(Integer idAll) {
        return legaRepository.findLegheByAllenatoreId(idAll);
    }


    @Transactional
    public void eliminaLega(Integer idLega) {

        Lega lega = legaRepository.findByIdAndDeleted(idLega, 'N');
        lega.setDeleted('Y');

        List<Amministra> admin = amministraRepository.findByLega(lega);
        for (Amministra amm : admin) {
            amm.setDeleted('Y');
        }

        List<Partita> partite = partitaRepository.findByLega(lega);
        for (Partita partita : partite) {
            partita.setDeleted('Y');
        }

        List<Squadra> squadre = squadraRepository.findByLegaAndDeleted(lega, 'N');
        for (Squadra squadra : squadre) {
            Set<Formazione> formazioni = squadra.getFormazioni();
            for(Formazione formazione : formazioni) {
                formazione.setDeleted('Y');
            }
            squadra.setDeleted('Y');
            squadra.setFormazioni(formazioni);
        }

        legaRepository.save(lega);
        amministraRepository.saveAll(admin);
        partitaRepository.saveAll(partite);
        squadraRepository.saveAll(squadre);

    }

    @Transactional
    public void espelliAllenatore(Integer idAll, Integer idLega) {

        Squadra squadra = squadraRepository.findByAllenatoreIdAndLegaIdAndDeleted(idAll, idLega, 'N');

        squadra.setDeleted('Y');

        Amministra amministra = amministraRepository.findByAllenatoreIdAndLegaIdAndDeleted(idAll, idLega, 'N');
        if(amministra != null) {
            amministra.setDeleted('Y');
            amministraRepository.save(amministra);
        }

        squadraRepository.save(squadra);


    }

}
