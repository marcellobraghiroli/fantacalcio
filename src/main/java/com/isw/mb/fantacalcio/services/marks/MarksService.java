package com.isw.mb.fantacalcio.services.marks;

import com.isw.mb.fantacalcio.models.GiocGiornata;
import com.isw.mb.fantacalcio.models.Giocatore;
import com.isw.mb.fantacalcio.models.Giornata;
import com.isw.mb.fantacalcio.repositories.GiocGiornataRepository;
import com.isw.mb.fantacalcio.repositories.GiocatoreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class MarksService {

    private final GiocGiornataRepository giocGiornataRepository;
    private final GiocatoreRepository giocatoreRepository;

    @Autowired
    public MarksService(GiocGiornataRepository giocGiornataRepository, GiocatoreRepository giocatoreRepository) {
        this.giocGiornataRepository = giocGiornataRepository;
        this.giocatoreRepository = giocatoreRepository;
    }

    @Transactional
    public void caricaVoti(Integer numGiornata, MultipartFile fileVoti) throws IOException {

        if(giocGiornataRepository.existsByGiornataNumeroAndDeleted(numGiornata, 'N')) {
            throw new IllegalArgumentException("I voti della giornata " + numGiornata + " sono già stati caricati");
        }

        List<GiocGiornata> voti = new ArrayList<GiocGiornata>();

        Giornata giornata = new Giornata();
        giornata.setNumero(numGiornata);

        BufferedReader file = new BufferedReader(new InputStreamReader(fileVoti.getInputStream()));

        String line;
        while((line = file.readLine()) != null) {

            String[] stats = line.split(",");

            Giocatore giocatore = new Giocatore();
            giocatore.setId(Integer.parseInt(stats[0]));

            GiocGiornata voto = new GiocGiornata(giocatore, giornata);

            voto.setVoto(Float.parseFloat(stats[1]));
            voto.setFantavoto(Float.parseFloat(stats[2]));
            voto.setGoalFatti(Integer.parseInt(stats[3]));
            voto.setGoalSubiti(Integer.parseInt(stats[4]));
            voto.setRigSbagliati(Integer.parseInt(stats[5]));
            voto.setRigParati(Integer.parseInt(stats[6]));
            voto.setAssist(Integer.parseInt(stats[7]));
            voto.setAutogoal(Integer.parseInt(stats[8]));
            voto.setAmmonito(stats[9].charAt(0));
            voto.setEspulso(stats[10].charAt(0));

            voti.add(voto);

            Giocatore updateGiocatore = giocatoreRepository.findByIdAndDeleted(giocatore.getId(), 'N');

            updateGiocatore.setMedia((updateGiocatore.getMedia() * updateGiocatore.getPresenze() + voto.getVoto()) / (updateGiocatore.getPresenze() + 1));
            updateGiocatore.setFantamedia((updateGiocatore.getFantamedia() * updateGiocatore.getPresenze() + voto.getFantavoto()) / (updateGiocatore.getPresenze() + 1));
            updateGiocatore.setPresenze(updateGiocatore.getPresenze() + 1);
            updateGiocatore.setGoalFatti(updateGiocatore.getGoalFatti() + voto.getGoalFatti());
            updateGiocatore.setGoalSubiti(updateGiocatore.getGoalSubiti() + voto.getGoalSubiti());
            updateGiocatore.setRigSbagliati(updateGiocatore.getRigSbagliati() + voto.getRigSbagliati());
            updateGiocatore.setRigParati(updateGiocatore.getRigParati() + voto.getRigParati());
            updateGiocatore.setAssist(updateGiocatore.getAssist() + voto.getAssist());
            updateGiocatore.setAutogoal(updateGiocatore.getAutogoal() + voto.getAutogoal());
            updateGiocatore.setAmmonizioni(updateGiocatore.getAmmonizioni() + (voto.getAmmonito() == 'Y' ? 1 : 0));
            updateGiocatore.setEspulsioni(updateGiocatore.getEspulsioni() + (voto.getEspulso() == 'Y' ? 1 : 0));

            giocatoreRepository.save(updateGiocatore);

        }

        file.close();

        giocGiornataRepository.saveAll(voti);

    }
}
