package com.isw.mb.fantacalcio.controllers;

import com.isw.mb.fantacalcio.exceptions.DuplicateEntityException;
import com.isw.mb.fantacalcio.models.Allenatore;
import com.isw.mb.fantacalcio.services.AllenatoreService;
import com.isw.mb.fantacalcio.services.cookies.CookieService;
import com.isw.mb.fantacalcio.services.cookies.impl.AllenatoreCookieService;
import com.isw.mb.fantacalcio.services.cookies.impl.LegaCookieService;
import com.isw.mb.fantacalcio.services.cookies.impl.SquadraCookieService;
import com.isw.mb.fantacalcio.services.email.EmailService;
import com.isw.mb.fantacalcio.utils.CookieUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class AuthController {

    //Controller per la gestione delle operazioni di autenticazione

    private final AllenatoreService allenatoreService;
    private final CookieService allenatoreCookieService, legaCookieService, squadraCookieService;
    private final EmailService emailService;

    @Autowired
    public AuthController(AllenatoreService allenatoreService, AllenatoreCookieService allenatoreCookieService, LegaCookieService legaCookieService,
                          SquadraCookieService squadraCookieService, EmailService emailService) {
        this.allenatoreService = allenatoreService;
        this.allenatoreCookieService = allenatoreCookieService;
        this.legaCookieService = legaCookieService;
        this.squadraCookieService = squadraCookieService;
        this.emailService = emailService;
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
        model.addAttribute("logged", false);
        return "auth/loginView";
    }

    //PAGINA DI REGISTRAZIONE
    @GetMapping("/registerView")
    public String registerView(Model model) {
        model.addAttribute("logged", false);
        return "auth/registerView";
    }

    //LOGIN
    @PostMapping("login")
    public String login(@RequestParam String username, @RequestParam String password, HttpServletResponse response) {

        if (username.equals("admin") && password.equals("admin")) {
            return "redirect:/SysAdminView";
        }

        Allenatore allenatore = allenatoreService.findByUsernameAndPassword(username, password);

        if (allenatore != null) {

            allenatoreCookieService.create(response, List.of(allenatore.getId(), allenatore.getUsername()));

            return "redirect:/legheView"; // redirect to home page
        } else {
            return "redirect:/?error=true"; // redirect back to login page with error message
        }

    }

    //LOGOUT
    @GetMapping("logout")
    public String logout(HttpServletResponse response) {
        allenatoreCookieService.delete(response);
        legaCookieService.delete(response);
        squadraCookieService.delete(response);
        return "redirect:/?loggedOut=true";
    }

    //REGISTRAZIONE
    @PostMapping("register")
    public String register(@ModelAttribute Allenatore allenatoreForm, Model model, HttpServletResponse response) {
        try {
            if (allenatoreForm.getTelefono().equals("")) {
                allenatoreForm.setTelefono(null);
            }
            Allenatore registered = allenatoreService.register(allenatoreForm);
            allenatoreCookieService.create(response, List.of(registered.getId(), registered.getUsername()));
            emailService.sendSubscriptionEmail(registered.getUsername());
            return "redirect:/legheView";
        } catch (DuplicateEntityException e) {

            model.addAttribute("errorMessage", e.getMessage());

            model.addAttribute("allenatoreForm", allenatoreForm);
            model.addAttribute("logged", false);
            return "auth/registerView";
        }
    }


}
