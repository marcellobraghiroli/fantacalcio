package com.isw.mb.fantacalcio.controllers;

import com.isw.mb.fantacalcio.models.Allenatore;
import com.isw.mb.fantacalcio.services.AllenatoreService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AuthController {

    @Autowired
    private AllenatoreService allenatoreService;

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

    @GetMapping("/registerView")
    public String registerView(Model model) {
        return "registerView";
    }

    @GetMapping("/header")
    public String header(Model model) {
        return "header";
    }

    @PostMapping("login")
    public String login(String username, String password, HttpServletResponse response) {

        Allenatore allenatore = allenatoreService.findByUsernameAndPassword(username, password);
        if (allenatore != null) {
            Cookie idCookie = new Cookie("idAllenatore", String.valueOf(allenatore.getId()));
            Cookie usernameCookie = new Cookie("username", allenatore.getUsername());
            idCookie.setPath("/");
            usernameCookie.setPath("/");
            response.addCookie(idCookie);
            response.addCookie(usernameCookie);
            return "redirect:/header"; // redirect to home page
        } else {
            return "redirect:/?error=true"; // redirect back to login page with error message
        }
    }

    @GetMapping("logout")
    public String logout(HttpServletResponse response) {
        Cookie idCookie = new Cookie("idAllenatore", "");
        Cookie usernameCookie = new Cookie("username", "");
        idCookie.setMaxAge(0);
        usernameCookie.setMaxAge(0);
        idCookie.setPath("/");
        usernameCookie.setPath("/");
        response.addCookie(idCookie);
        response.addCookie(usernameCookie);
        return "redirect:/?loggedOut=true"; // redirect to login page
    }

    @PostMapping("register")
    public String register() {
        System.out.println("reegister");
        return "";
    }


}
