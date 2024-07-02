package com.isw.mb.fantacalcio.services.cookies.impl;

import com.isw.mb.fantacalcio.models.Allenatore;
import com.isw.mb.fantacalcio.services.cookies.CookieService;
import com.isw.mb.fantacalcio.utils.CookieUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AllenatoreCookieService implements CookieService {

    private static final String COOKIE_NAME = "allenatoreLoggato";

    @Override
    public void create(HttpServletResponse response, List<?> objects) {

        try {

            Allenatore allenatoreLoggato = new Allenatore();
            allenatoreLoggato.setId((Integer) objects.getFirst());
            allenatoreLoggato.setUsername((String) objects.getLast());

            String encodedAll = encode(allenatoreLoggato);
            CookieUtils.setCookie(response, COOKIE_NAME, encodedAll);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(HttpServletResponse response) {
        CookieUtils.removeCookie(response, COOKIE_NAME);
    }

    @Override
    public Allenatore get(HttpServletRequest request) {

        try {

            String encodedAll = CookieUtils.getCookie(request, COOKIE_NAME);
            Allenatore allenatoreLoggato = decode(encodedAll);
            return allenatoreLoggato;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(HttpServletResponse response, HttpServletRequest request, List<?> objects) {
        throw new UnsupportedOperationException();
    }

    private String encode(Allenatore allenatore) {
        return allenatore.getId() + "#" + allenatore.getUsername();
    }

    private Allenatore decode(String encodedAll) {
        String[] values = encodedAll.split("#");
        Allenatore allenatore = new Allenatore();
        allenatore.setId(Integer.parseInt(values[0]));
        allenatore.setUsername(values[1]);
        return allenatore;
    }
}
