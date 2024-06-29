package com.isw.mb.fantacalcio.controllers;


import com.isw.mb.fantacalcio.models.*;
import com.isw.mb.fantacalcio.models.combined.SqGioAmm;
import com.isw.mb.fantacalcio.services.AmministraService;
import com.isw.mb.fantacalcio.services.GiornataService;
import com.isw.mb.fantacalcio.services.SquadraService;
import com.isw.mb.fantacalcio.services.combined.SqGioAmmService;
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

    private final SqGioAmmService sqGioAmmService;

    @Autowired
    public LegaController(SqGioAmmService sqGioAmmService) {
        this.sqGioAmmService = sqGioAmmService;
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

        SqGioAmm sqGioAmm = sqGioAmmService.getSqGioAmm(allenatoreLoggato, legaCorrente);

        if(sqGioAmm.getGiornata() != null) {
            model.addAttribute("giornata", sqGioAmm.getGiornata());
        }
        model.addAttribute("allenatoreLoggato", allenatoreLoggato);
        model.addAttribute("legaCorrente", legaCorrente);
        model.addAttribute("squadraCorrente", sqGioAmm.getSquadraCorrente().getNome());
        model.addAttribute("logged", true);
        model.addAttribute("isAdmin", sqGioAmm.isAdmin());

        return "/lega/homeLegaView";
    }

    @GetMapping("backToLeghe")
    public String backToLeghe(HttpServletResponse response) {
        CookieUtils.removeCookie(response, "idLega");
        CookieUtils.removeCookie(response, "nomeLega");
        return "redirect:/legheView";
    }

}
