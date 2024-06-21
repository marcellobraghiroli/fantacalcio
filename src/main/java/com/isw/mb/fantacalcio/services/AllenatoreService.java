package com.isw.mb.fantacalcio.services;

import com.isw.mb.fantacalcio.models.Allenatore;
import com.isw.mb.fantacalcio.repositories.AllenatoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AllenatoreService {

    @Autowired
    private AllenatoreRepository allenatoreRepository;

    public Allenatore findByUsernameAndPassword(String username, String password) {
        return allenatoreRepository.findByUsernameAndPassword(username, password);
    }

}
