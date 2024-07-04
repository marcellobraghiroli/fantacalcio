package com.isw.mb.fantacalcio.services.cookies.impl;

import com.isw.mb.fantacalcio.models.Allenatore;
import com.isw.mb.fantacalcio.models.Lega;
import com.isw.mb.fantacalcio.models.Squadra;
import com.isw.mb.fantacalcio.services.cookies.CookieService;
import com.isw.mb.fantacalcio.utils.CookieUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SquadraCookieService implements CookieService {

    private static final String COOKIE_NAME = "squadraCorrente";

    @Override
    public void create(HttpServletResponse response, List<?> objects) {
        try {

            Squadra squadraCorrente = new Squadra();

            squadraCorrente.setId((Integer) objects.get(0));
            squadraCorrente.setNome((String) objects.get(1));
            squadraCorrente.setCreditiSpesi((Integer) objects.get(2));

            Lega lega = new Lega();
            lega.setNumCrediti((Integer) objects.get(3));

            squadraCorrente.setLega(lega);

            Allenatore allenatore = new Allenatore();
            allenatore.setId((Integer) objects.get(4));
            allenatore.setUsername((String) objects.get(5));

            squadraCorrente.setAllenatore(allenatore);

            String encodedSquadra = encode(squadraCorrente);
            CookieUtils.setCookie(response, COOKIE_NAME, encodedSquadra);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(HttpServletResponse response) {
        CookieUtils.removeCookie(response, COOKIE_NAME);
    }

    @Override
    public Squadra get(HttpServletRequest request) {
        try {

            String encodedSquadra = CookieUtils.getCookie(request, COOKIE_NAME);
            Squadra squadraCorrente = decode(encodedSquadra);
            return squadraCorrente;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(HttpServletResponse response, HttpServletRequest request, List<?> objects) {
        try {

            Integer crediti = (Integer) objects.getFirst();

            String encodedSquadra = CookieUtils.getCookie(request, COOKIE_NAME);
            String[] values = encodedSquadra.split("#");
            values[2] = String.valueOf(Integer.parseInt(values[2]) + crediti);
            encodedSquadra = String.join("#", values);
            CookieUtils.setCookie(response, COOKIE_NAME, encodedSquadra);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String encode(Squadra squadra) {
        return squadra.getId() + "#" + squadra.getNome() + "#" + squadra.getCreditiSpesi() + "#" + squadra.getLega().getNumCrediti() + "#" + squadra.getAllenatore().getId() + "#" + squadra.getAllenatore().getUsername();
    }

    private Squadra decode(String encodedSquadra) {

        String[] values = encodedSquadra.split("#");
        Squadra squadra = new Squadra();
        squadra.setId(Integer.parseInt(values[0]));
        squadra.setNome(values[1]);
        squadra.setCreditiSpesi(Integer.parseInt(values[2]));

        Lega lega = new Lega();
        lega.setNumCrediti(Integer.parseInt(values[3]));
        squadra.setLega(lega);

        Allenatore allenatore = new Allenatore();
        allenatore.setId(Integer.parseInt(values[4]));
        allenatore.setUsername(values[5]);
        squadra.setAllenatore(allenatore);

        return squadra;
    }
}
