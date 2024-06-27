package com.isw.mb.fantacalcio.controllers;

import com.isw.mb.fantacalcio.models.Allenatore;
import com.isw.mb.fantacalcio.models.Lega;
import com.isw.mb.fantacalcio.models.Squadra;
import com.isw.mb.fantacalcio.services.LegaService;
import com.isw.mb.fantacalcio.services.SquadraService;
import com.isw.mb.fantacalcio.utils.CookieUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class LegheController {

    private final LegaService legaService;
    private final SquadraService squadraService;

    @Autowired
    public LegheController(LegaService legaService, SquadraService squadraService) {
        this.legaService = legaService;
        this.squadraService = squadraService;
    }

    //HOME
    @GetMapping("/legheView")
    public String legheView(Model model, HttpServletRequest request) {

        String idAllenatore = CookieUtils.getCookie(request, "idAllenatore");
        String username = CookieUtils.getCookie(request, "username");

        Allenatore allenatoreLoggato = new Allenatore();
        allenatoreLoggato.setId(Integer.parseInt(idAllenatore));
        allenatoreLoggato.setUsername(username);

        List<Squadra> squadre = squadraService.findSquadreByAllenatoreId(allenatoreLoggato.getId());
        List<Lega> leghe = squadre.stream().map(Squadra::getLega).toList();

        model.addAttribute("allenatoreLoggato", allenatoreLoggato);
        model.addAttribute("logged", true);
        model.addAttribute("leghe", leghe);

        return "leghe/legheView";
    }

}
