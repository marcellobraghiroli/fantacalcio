package com.isw.mb.fantacalcio.services;

import com.isw.mb.fantacalcio.exceptions.DuplicateEntityException;
import com.isw.mb.fantacalcio.models.*;
import com.isw.mb.fantacalcio.repositories.GiocSquadraRepository;
import com.isw.mb.fantacalcio.repositories.SquadraRepository;
import jakarta.transaction.Transactional;
import org.hibernate.event.internal.EntityCopyAllowedLoggedObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Transactional
    public GiocSquadra addGiocatore(Integer idGioc, Integer idSquadra, Integer price) {

        GiocSquadraId giocSquadraId = new GiocSquadraId(idGioc, idSquadra);

        GiocSquadra existing = giocSquadraRepository.findById(giocSquadraId).orElse(null);

        if (existing == null) {

            Giocatore giocatore = new Giocatore();
            giocatore.setId(idGioc);
            Squadra squadraAll = new Squadra();
            squadraAll.setId(idSquadra);

            GiocSquadra giocSquadra = new GiocSquadra(giocatore, squadraAll);
            giocSquadra.setPrezzo(price);

            Squadra squadra = squadraRepository.findById(idSquadra).get();
            squadra.setCreditiSpesi(squadra.getCreditiSpesi() + price);
            squadraRepository.save(squadra);

            return giocSquadraRepository.save(giocSquadra);
        } else {

            if(existing.getDeleted() == 'N') {
                throw new DuplicateEntityException("Giocatore già presente nella rosa");
            } else {
                existing.setDeleted('N');
                existing.setPrezzo(price);

                Squadra squadra = squadraRepository.findById(idSquadra).get();
                squadra.setCreditiSpesi(squadra.getCreditiSpesi() + price);
                squadraRepository.save(squadra);

                return giocSquadraRepository.save(existing);
            }

        }

    }
}
