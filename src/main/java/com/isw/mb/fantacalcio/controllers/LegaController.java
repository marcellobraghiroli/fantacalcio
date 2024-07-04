package com.isw.mb.fantacalcio.controllers;


import com.isw.mb.fantacalcio.models.*;
import com.isw.mb.fantacalcio.models.combined.SqGioAmm;
import com.isw.mb.fantacalcio.services.*;
import com.isw.mb.fantacalcio.services.combined.SqGioAmmService;
import com.isw.mb.fantacalcio.services.cookies.CookieService;
import com.isw.mb.fantacalcio.services.cookies.impl.AllenatoreCookieService;
import com.isw.mb.fantacalcio.services.cookies.impl.LegaCookieService;
import com.isw.mb.fantacalcio.services.cookies.impl.SquadraCookieService;
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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class LegaController {

    private final SqGioAmmService sqGioAmmService;
    private final SquadraService squadraService;
    private final CookieService allenatoreCookieService, legaCookieService, squadraCookieService;


    @Autowired
    public LegaController(SqGioAmmService sqGioAmmService, SquadraService squadraService, AllenatoreCookieService allenatoreCookieService, LegaCookieService legaCookieService, SquadraCookieService squadraCookieService) {
        this.sqGioAmmService = sqGioAmmService;
        this.squadraService = squadraService;
        this.allenatoreCookieService = allenatoreCookieService;
        this.legaCookieService = legaCookieService;
        this.squadraCookieService = squadraCookieService;
    }

    @GetMapping("/homeLegaView")
    public String homeLegaView(HttpServletRequest request, Model model) {

        Allenatore allenatoreLoggato = (Allenatore) allenatoreCookieService.get(request);
        Lega legaCorrente = (Lega) legaCookieService.get(request);

        SqGioAmm sqGioAmm = sqGioAmmService.getSqGioAmm(allenatoreLoggato, legaCorrente);

        if (sqGioAmm.getGiornata() != null) {
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
        legaCookieService.delete(response);
        return "redirect:/legheView";
    }

    @GetMapping("classificaView")
    public String classificaView(HttpServletRequest request, Model model) {

        Allenatore allenatoreLoggato = (Allenatore) allenatoreCookieService.get(request);
        Lega legaCorrente = (Lega) legaCookieService.get(request);

        List<Squadra> squadre = squadraService.findSquadreByLegaIdOrderByPuntiClassDesc(legaCorrente.getId());

        model.addAttribute("allenatoreLoggato", allenatoreLoggato);
        model.addAttribute("legaCorrente", legaCorrente);
        model.addAttribute("squadre", squadre);
        model.addAttribute("logged", true);

        return "/lega/classificaView";
    }

    @GetMapping("roseView")
    public String roseView(HttpServletRequest request, Model model) {

        Allenatore allenatoreLoggato = (Allenatore) allenatoreCookieService.get(request);
        Lega legaCorrente = (Lega) legaCookieService.get(request);

        List<Squadra> squadreLega = squadraService.findSquadreByLegaIdOrderByNome(legaCorrente.getId());

        model.addAttribute("allenatoreLoggato", allenatoreLoggato);
        model.addAttribute("legaCorrente", legaCorrente);
        model.addAttribute("squadre", squadreLega);
        model.addAttribute("logged", true);

        return "/lega/roseView";

    }

    @PostMapping("goToRosaView")
    public String goToRosaView(HttpServletResponse response, @ModelAttribute Squadra squadra, @RequestParam Integer idAllSq, @RequestParam Integer creditiLega, @RequestParam String nomeAll) {

        squadraCookieService.create(response, List.of(squadra.getId(), squadra.getNome(), squadra.getCreditiSpesi(), creditiLega, idAllSq, nomeAll));

        return "redirect:/rosaView";
    }



}
