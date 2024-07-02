package com.isw.mb.fantacalcio.services.cookies;

import com.isw.mb.fantacalcio.utils.CookieUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface CookieService {

    void create(HttpServletResponse response, List<?> objects);

    void delete(HttpServletResponse response);

    Object get(HttpServletRequest request);

    void update(HttpServletResponse response, HttpServletRequest request, List<?> objects);
}
