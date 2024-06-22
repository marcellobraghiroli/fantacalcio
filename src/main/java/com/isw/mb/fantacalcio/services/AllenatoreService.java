package com.isw.mb.fantacalcio.services;

import com.isw.mb.fantacalcio.models.Allenatore;
import com.isw.mb.fantacalcio.repositories.AllenatoreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.isw.mb.fantacalcio.exceptions.DuplicateEntityException;

@Service
public class AllenatoreService {

    @Autowired
    private AllenatoreRepository allenatoreRepository;

    public Allenatore findByUsernameAndPassword(String username, String password) {
        return allenatoreRepository.findByUsernameAndPassword(username, password);
    }

    @Transactional
    public Allenatore register(Allenatore allenatore) throws DuplicateEntityException {

        try {
            return allenatoreRepository.save(allenatore);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEntityException("Username, email o telefono già in uso");
        }
    }

}
