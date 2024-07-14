package com.isw.mb.fantacalcio.controllers;

import com.isw.mb.fantacalcio.exceptions.DuplicateEntityException;
import com.isw.mb.fantacalcio.models.*;
import com.isw.mb.fantacalcio.services.GiocSquadraService;
import com.isw.mb.fantacalcio.services.GiocatoreService;
import com.isw.mb.fantacalcio.services.cookies.CookieService;
import com.isw.mb.fantacalcio.services.cookies.impl.AllenatoreCookieService;
import com.isw.mb.fantacalcio.services.cookies.impl.LegaCookieService;
import com.isw.mb.fantacalcio.services.cookies.impl.SquadraCookieService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class SquadraController {

    //Controller per la gestione delle operazioni relative a una squadra

    private final CookieService allenatoreCookieService, legaCookieService, squadraCookieService;
    private final GiocSquadraService giocSquadraService;
    private final GiocatoreService giocatoreService;

    @Autowired
    public SquadraController(AllenatoreCookieService allenatoreCookieService, LegaCookieService legaCookieService, GiocSquadraService giocSquadraService, GiocatoreService giocatoreService, SquadraCookieService squadraCookieService) {
        this.allenatoreCookieService = allenatoreCookieService;
        this.legaCookieService = legaCookieService;
        this.giocSquadraService = giocSquadraService;
        this.giocatoreService = giocatoreService;
        this.squadraCookieService = squadraCookieService;
    }

    //PAGINA ROSA DI UNA SQUADRA
    @GetMapping("rosaView")
    public String rosaView(HttpServletRequest request, Model model) {

        Allenatore allenatoreLoggato = (Allenatore) allenatoreCookieService.get(request);
        Lega legaCorrente = (Lega) legaCookieService.get(request);
        Squadra squadraCorrente = (Squadra) squadraCookieService.get(request);

        Set<GiocSquadra> giocatori = giocSquadraService.findGiocatoriBySquadra(squadraCorrente);

        List<GiocSquadra> portieri = giocatori.stream().filter(g -> g.getGiocatore().getRuolo().equals("POR")).sorted(Comparator.comparing(g -> g.getGiocatore().getNome())).collect(Collectors.toList());
        List<GiocSquadra> difensori = giocatori.stream().filter(g -> g.getGiocatore().getRuolo().equals("DIF")).sorted(Comparator.comparing(g -> g.getGiocatore().getNome())).collect(Collectors.toList());
        List<GiocSquadra> centrocampisti = giocatori.stream().filter(g -> g.getGiocatore().getRuolo().equals("CEN")).sorted(Comparator.comparing(g -> g.getGiocatore().getNome())).collect(Collectors.toList());
        List<GiocSquadra> attaccanti = giocatori.stream().filter(g -> g.getGiocatore().getRuolo().equals("ATT")).sorted(Comparator.comparing(g -> g.getGiocatore().getNome())).collect(Collectors.toList());

        model.addAttribute("allenatoreLoggato", allenatoreLoggato);
        model.addAttribute("legaCorrente", legaCorrente);
        model.addAttribute("portieri", portieri);
        model.addAttribute("difensori", difensori);
        model.addAttribute("centrocampisti", centrocampisti);
        model.addAttribute("attaccanti", attaccanti);
        model.addAttribute("squadra", squadraCorrente);
        model.addAttribute("logged", true);
        model.addAttribute("allowMod", squadraCorrente.getAllenatore().getId() == allenatoreLoggato.getId());

        return "lega/rosa/rosaView";
    }

    //RIMUOVI UN GIOCATORE DALLA SQUADRA
    @PostMapping("removeGioc")
    public String removeGioc(@RequestParam Integer idGioc, @RequestParam Integer idSquadra, HttpServletResponse response, HttpServletRequest request) {
        GiocSquadra giocSquadra = giocSquadraService.removeGiocatore(idGioc, idSquadra);
        squadraCookieService.update(response, request, List.of(-giocSquadra.getPrezzo()));
        return "redirect:/rosaView";
    }

    //PAGINA GIOCATORE
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

    //TORNA ALLE ROSE (delete cookie)
    @GetMapping("backToRose")
    public String backToRose(HttpServletResponse response) {
        squadraCookieService.delete(response);
        return "redirect:/roseView";
    }

    //PAGINA DI INSERIMENTO GIOCATORE
    @RequestMapping(value = "insGiocView", method = {RequestMethod.GET, RequestMethod.POST})
    public String insGiocView(Model model, HttpServletRequest request, @RequestParam(required = false, defaultValue = "POR") String ruolo) {

        Allenatore allenatoreLoggato = (Allenatore) allenatoreCookieService.get(request);
        Lega legaCorrente = (Lega) legaCookieService.get(request);
        Squadra squadraCorrente = (Squadra) squadraCookieService.get(request);

        List<Giocatore> giocatori = giocatoreService.findGiocatoriByRuoloOrderByNome(ruolo);

        //model.addAttribute("allenatoreLoggato", allenatoreLoggato);
        //model.addAttribute("legaCorrente", legaCorrente);
        model.addAttribute("squadraCorrente", squadraCorrente);
        model.addAttribute("logged", true);
        model.addAttribute("giocatori", giocatori);
        model.addAttribute("ruolo", ruolo);

        return "lega/rosa/insGiocView";
    }

    //INSERISCI GIOCATORE
    @PostMapping("insGioc")
    public String insGioc(@RequestParam Integer idGioc, @RequestParam Integer idSquadra, @RequestParam Integer price, HttpServletResponse response, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {

        try {
            GiocSquadra giocSquadra = giocSquadraService.addGiocatore(idGioc, idSquadra, price);
            squadraCookieService.update(response, request, List.of(price));
            redirectAttributes.addFlashAttribute("success", true);
        } catch (DuplicateEntityException e) {
            redirectAttributes.addFlashAttribute("success", false);
        }

        return "redirect:insGiocView";
    }

}
