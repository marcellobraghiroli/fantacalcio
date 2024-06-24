package com.isw.mb.fantacalcio.controllers;

import com.isw.mb.fantacalcio.exceptions.DuplicateEntityException;
import com.isw.mb.fantacalcio.models.Allenatore;
import com.isw.mb.fantacalcio.services.AllenatoreService;
import com.isw.mb.fantacalcio.utils.CookieUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AuthController {

    private final AllenatoreService allenatoreService;

    @Autowired
    public AuthController(AllenatoreService allenatoreService) {
        this.allenatoreService = allenatoreService;
    }

    //PAGINA DI LOGIN
    @GetMapping("/")
    public String loginView(Model model, @RequestParam(value = "error", required = false) Boolean error,
                            @RequestParam(value = "loggedOut", required = false) Boolean loggedOut) {
        if (Boolean.TRUE.equals(error)) {
            model.addAttribute("errorMessage", "Username o password errati");
        }
        if (Boolean.TRUE.equals(loggedOut)) {
            model.addAttribute("loggedOutMessage", "Logout effettuato con successo");
        }
        return "loginView";
    }

    //PAGINA DI REGISTRAZIONE
    @GetMapping("/registerView")
    public String registerView(Model model) {
        return "registerView";
    }

    //HOME
    @GetMapping("/headerView")
    public String headerView(Model model, HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    model.addAttribute("username", cookie.getValue());
                }
            }
        }

        return "headerView";
    }

    //LOGIN
    @PostMapping("login")
    public String login(@RequestParam String username, @RequestParam String password, HttpServletResponse response) {


        Allenatore allenatore = allenatoreService.findByUsernameAndPassword(username, password);
        if (allenatore != null) {
            CookieUtils.setCookie(response, "idAllenatore", String.valueOf(allenatore.getId()));
            CookieUtils.setCookie(response, "username", allenatore.getUsername());
            return "redirect:/headerView"; // redirect to home page
        } else {
            return "redirect:/?error=true"; // redirect back to login page with error message
        }

    }

    //LOGOUT
    @GetMapping("logout")
    public String logout(HttpServletResponse response) {
        CookieUtils.removeCookie(response, "idAllenatore");
        CookieUtils.removeCookie(response, "username");
        return "redirect:/?loggedOut=true"; // redirect to login page
    }

    //REGISTRAZIONE
    @PostMapping("register")
    public String register(@ModelAttribute Allenatore allenatore, Model model, HttpServletResponse response) {
        try {
            if(allenatore.getTelefono().equals("")){
                allenatore.setTelefono(null);
            }
            Allenatore registered = allenatoreService.register(allenatore);
            CookieUtils.setCookie(response, "idAllenatore", String.valueOf(registered.getId()));
            CookieUtils.setCookie(response, "username", registered.getUsername());
            return "redirect:/headerView";
        } catch (DuplicateEntityException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("allenatore", allenatore);
            return "registerView";
        }
    }




}
