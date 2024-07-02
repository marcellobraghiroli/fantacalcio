package com.isw.mb.fantacalcio.services.cookies.impl;

import com.isw.mb.fantacalcio.models.Allenatore;
import com.isw.mb.fantacalcio.models.Lega;
import com.isw.mb.fantacalcio.services.cookies.CookieService;
import com.isw.mb.fantacalcio.utils.CookieUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LegaCookieService implements CookieService {

    private static final String COOKIE_NAME = "legaCorrente";

    @Override
    public void create(HttpServletResponse response, List<?> objects) {

        try {

            Lega legaCorrente = new Lega();
            legaCorrente.setId((Integer) objects.getFirst());
            legaCorrente.setNome((String) objects.getLast());

            String encodedLega = encode(legaCorrente);
            CookieUtils.setCookie(response, COOKIE_NAME, encodedLega);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(HttpServletResponse response) {
        CookieUtils.removeCookie(response, COOKIE_NAME);
    }

    @Override
    public Lega get(HttpServletRequest request) {

        try {

            String encodedLega = CookieUtils.getCookie(request, COOKIE_NAME);
            Lega legaCorrente = decode(encodedLega);
            return legaCorrente;


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(HttpServletResponse response, HttpServletRequest request, List<?> objects) {
        throw new UnsupportedOperationException();
    }

    private String encode(Lega lega) {
        return lega.getId() + "#" + lega.getNome();
    }

    private Lega decode(String encodedLega) {
        String[] values = encodedLega.split("#");
        Lega lega = new Lega();
        lega.setId(Integer.parseInt(values[0]));
        lega.setNome(values[1]);
        return lega;
    }

}
