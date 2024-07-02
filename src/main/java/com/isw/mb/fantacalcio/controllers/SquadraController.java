package com.isw.mb.fantacalcio.controllers;

import com.isw.mb.fantacalcio.models.*;
import com.isw.mb.fantacalcio.services.GiocSquadraService;
import com.isw.mb.fantacalcio.services.GiocatoreService;
import com.isw.mb.fantacalcio.services.cookies.CookieService;
import com.isw.mb.fantacalcio.services.cookies.impl.AllenatoreCookieService;
import com.isw.mb.fantacalcio.services.cookies.impl.LegaCookieService;
import com.isw.mb.fantacalcio.services.cookies.impl.SquadraCookieService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class SquadraController {

    private final CookieService allenatoreCookieService, legaCookieService, squadraCookieService;
    private final GiocSquadraService giocSquadraService;
    private final GiocatoreService giocatoreService;

    public SquadraController(AllenatoreCookieService allenatoreCookieService, LegaCookieService legaCookieService, GiocSquadraService giocSquadraService, GiocatoreService giocatoreService, SquadraCookieService squadraCookieService) {
        this.allenatoreCookieService = allenatoreCookieService;
        this.legaCookieService = legaCookieService;
        this.giocSquadraService = giocSquadraService;
        this.giocatoreService = giocatoreService;
        this.squadraCookieService = squadraCookieService;
    }

    @GetMapping("rosaView")
    public String rosaView(HttpServletRequest request, Model model) {

        Allenatore allenatoreLoggato = (Allenatore) allenatoreCookieService.get(request);
        Lega legaCorrente = (Lega) legaCookieService.get(request);
        Squadra squadraCorrente = (Squadra) squadraCookieService.get(request);

        Set<GiocSquadra> giocatori = giocSquadraService.findGiocatoriBySquadra(squadraCorrente);
        Set<GiocSquadra> portieri = giocatori.stream().filter(g -> g.getGiocatore().getRuolo().equals("POR")).collect(Collectors.toSet());
        Set<GiocSquadra> difensori = giocatori.stream().filter(g -> g.getGiocatore().getRuolo().equals("DIF")).collect(Collectors.toSet());
        Set<GiocSquadra> centrocampisti = giocatori.stream().filter(g -> g.getGiocatore().getRuolo().equals("CEN")).collect(Collectors.toSet());
        Set<GiocSquadra> attaccanti = giocatori.stream().filter(g -> g.getGiocatore().getRuolo().equals("ATT")).collect(Collectors.toSet());

        model.addAttribute("allenatoreLoggato", allenatoreLoggato);
        model.addAttribute("legaCorrente", legaCorrente);
        model.addAttribute("portieri", portieri);
        model.addAttribute("difensori", difensori);
        model.addAttribute("centrocampisti", centrocampisti);
        model.addAttribute("attaccanti", attaccanti);
        model.addAttribute("squadra", squadraCorrente);
        model.addAttribute("logged", true);
        model.addAttribute("allowMod", squadraCorrente.getAllenatore().getId() == allenatoreLoggato.getId());
        model.addAttribute("idAllSq", squadraCorrente.getAllenatore().getId());
        model.addAttribute("creditiLega", squadraCorrente.getLega().getNumCrediti());
        model.addAttribute("nomeAll", squadraCorrente.getAllenatore().getUsername());

        return "lega/rosa/rosaView";
    }

    @PostMapping("removeGioc")
    public String removeGioc(@RequestParam Integer idGioc, @RequestParam Integer idSquadra, HttpServletResponse response, HttpServletRequest request) {
        GiocSquadra giocSquadra = giocSquadraService.removeGiocatore(idGioc, idSquadra);
        squadraCookieService.update(response, request, List.of(giocSquadra.getPrezzo()));
        return "redirect:/rosaView";
    }

    @PostMapping("giocView")
    public String giocView(HttpServletRequest request, Model model, @RequestParam Integer idGioc) {

        Allenatore allenatoreLoggato = (Allenatore) allenatoreCookieService.get(request);
        Lega legaCorrente = (Lega) legaCookieService.get(request);

        Giocatore giocatore = giocatoreService.findGiocatoreById(idGioc);

        model.addAttribute("allenatoreLoggato", allenatoreLoggato);
        model.addAttribute("legaCorrente", legaCorrente);
        model.addAttribute("giocatore", giocatore);
        model.addAttribute("logged", true);

        return "lega/rosa/giocatoreView";
    }

    @GetMapping("backToRose")
    public String backToRose(HttpServletResponse response) {
        squadraCookieService.delete(response);
        return "redirect:/roseView";
    }

}
