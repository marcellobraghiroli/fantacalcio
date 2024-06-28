package com.isw.mb.fantacalcio.controllers;


import com.isw.mb.fantacalcio.models.*;
import com.isw.mb.fantacalcio.services.AmministraService;
import com.isw.mb.fantacalcio.services.GiornataService;
import com.isw.mb.fantacalcio.services.SquadraService;
import com.isw.mb.fantacalcio.utils.CookieUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

@Controller
public class LegaController {

    private final SquadraService squadraService;
    private final GiornataService giornataService;
    private final AmministraService amministraService;

    @Autowired
    public LegaController(SquadraService squadraService, GiornataService giornataService, AmministraService amministraService) {
        this.squadraService = squadraService;
        this.giornataService = giornataService;
        this.amministraService = amministraService;
    }

    @GetMapping("/homeLegaView")
    public String homeLegaView(HttpServletRequest request, Model model) {

        String idAllenatore = CookieUtils.getCookie(request, "idAllenatore");
        String username = CookieUtils.getCookie(request, "username");
        String idLega = CookieUtils.getCookie(request, "idLega");
        String nomeLega = CookieUtils.getCookie(request, "nomeLega");

        Allenatore allenatoreLoggato = new Allenatore();
        allenatoreLoggato.setId(Integer.parseInt(idAllenatore));
        allenatoreLoggato.setUsername(username);

        Lega legaCorrente = new Lega();
        legaCorrente.setId(Integer.parseInt(idLega));
        legaCorrente.setNome(nomeLega);

        //Squadra squadraCorrente = squadraService.findSquadraByAllenatoreIdAndLegaId(allenatoreLoggato.getId(), legaCorrente.getId());

        Giornata giornata = giornataService.findCurrentOrNextGiornata(allenatoreLoggato, legaCorrente);
        Squadra squadraCorrente = squadraService.findSquadraByAllenatoreIdAndLegaId(allenatoreLoggato.getId(), legaCorrente.getId());

        boolean isAdmin = amministraService.existsAmministra(allenatoreLoggato.getId(), legaCorrente.getId());

        if(giornata != null) {
            model.addAttribute("giornata", giornata);
        }
        model.addAttribute("allenatoreLoggato", allenatoreLoggato);
        model.addAttribute("legaCorrente", legaCorrente);
        model.addAttribute("squadraCorrente", squadraCorrente);
        model.addAttribute("logged", true);
        model.addAttribute("isAdmin", isAdmin);

        return "/lega/homeLegaView";
    }

    @GetMapping("backToLeghe")
    public String backToLeghe(HttpServletResponse response) {
        CookieUtils.removeCookie(response, "idLega");
        CookieUtils.removeCookie(response, "nomeLega");
        return "redirect:/legheView";
    }

}
