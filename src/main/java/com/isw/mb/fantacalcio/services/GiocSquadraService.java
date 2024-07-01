package com.isw.mb.fantacalcio.services;

import com.isw.mb.fantacalcio.models.GiocSquadra;
import com.isw.mb.fantacalcio.models.GiocSquadraId;
import com.isw.mb.fantacalcio.models.Giocatore;
import com.isw.mb.fantacalcio.models.Squadra;
import com.isw.mb.fantacalcio.repositories.GiocSquadraRepository;
import com.isw.mb.fantacalcio.repositories.SquadraRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GiocSquadraService {

    private final GiocSquadraRepository giocSquadraRepository;
    private final SquadraRepository squadraRepository;

    @Autowired
    public GiocSquadraService(GiocSquadraRepository giocSquadraRepository, SquadraRepository squadraRepository) {
        this.giocSquadraRepository = giocSquadraRepository;
        this.squadraRepository = squadraRepository;
    }

    @Transactional
    public Set<GiocSquadra> findGiocatoriBySquadra(Squadra squadra) {

        Set<GiocSquadra> giocatori = giocSquadraRepository.findBySquadraAndDeleted(squadra, 'N');
        return giocatori;

    }

    @Transactional
    public GiocSquadra removeGiocatore(Integer idGioc, Integer idSquadra) {
        GiocSquadraId giocSquadraId = new GiocSquadraId(idGioc, idSquadra);
        GiocSquadra giocSquadra = giocSquadraRepository.findById(giocSquadraId).get();
        giocSquadra.setDeleted('Y');

        Squadra squadra = squadraRepository.findById(idSquadra).get();
        squadra.setCreditiSpesi(squadra.getCreditiSpesi() - giocSquadra.getPrezzo());
        squadraRepository.save(squadra);

        return giocSquadraRepository.save(giocSquadra);
    }

}
