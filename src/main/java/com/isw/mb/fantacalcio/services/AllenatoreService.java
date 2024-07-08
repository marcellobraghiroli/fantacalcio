package com.isw.mb.fantacalcio.services;

import com.isw.mb.fantacalcio.models.Allenatore;
import com.isw.mb.fantacalcio.models.Lega;
import com.isw.mb.fantacalcio.repositories.AllenatoreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.isw.mb.fantacalcio.exceptions.DuplicateEntityException;

import java.util.List;

@Service
public class AllenatoreService {


    private final AllenatoreRepository allenatoreRepository;

    @Autowired
    public AllenatoreService(AllenatoreRepository allenatoreRepository) {
        this.allenatoreRepository = allenatoreRepository;
    }

    @Transactional
    public Allenatore findByUsernameAndPassword(String username, String password) {
        return allenatoreRepository.findByUsernameAndPasswordAndDeleted(username, password, 'N');
    }

    @Transactional
    public Allenatore register(Allenatore allenatore) throws DuplicateEntityException {

        if (allenatoreRepository.existsByUsername(allenatore.getUsername())) {
            throw new DuplicateEntityException("Username già in uso");
        }
        if (allenatoreRepository.existsByEmail(allenatore.getEmail())) {
            throw new DuplicateEntityException("Email già in uso");
        }
        if (allenatore.getTelefono() != null && allenatoreRepository.existsByTelefono(allenatore.getTelefono())) {
            throw new DuplicateEntityException("Telefono già in uso");
        }
        if (allenatore.getUsername().equals("admin")) {
            throw new DuplicateEntityException("Username non valido");
        }

        return allenatoreRepository.save(allenatore);

    }

    @Transactional
    public List<Allenatore> findAllenatoriByLegaAndNotLogged(Lega legaCorrente, Allenatore allenatoreLoggato) {
        return allenatoreRepository.findByLegaAndNotLogged(legaCorrente.getId(), allenatoreLoggato.getId());
    }
}
