package com.isw.mb.fantacalcio.controllers;


import com.isw.mb.fantacalcio.models.*;
import com.isw.mb.fantacalcio.models.combined.SqGioAmm;
import com.isw.mb.fantacalcio.services.*;
import com.isw.mb.fantacalcio.services.combined.SqGioAmmService;
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
    private final GiocSquadraService giocSquadraService;


    @Autowired
    public LegaController(SqGioAmmService sqGioAmmService, SquadraService squadraService, GiocSquadraService giocSquadraService) {
        this.sqGioAmmService = sqGioAmmService;
        this.squadraService = squadraService;
        this.giocSquadraService = giocSquadraService;
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
        CookieUtils.removeCookie(response, "idLega");
        CookieUtils.removeCookie(response, "nomeLega");
        return "redirect:/legheView";
    }

    @GetMapping("classificaView")
    public String classificaView(HttpServletRequest request, Model model) {

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

        Set<Squadra> squadreLega = squadraService.findSquadreByLegaId(legaCorrente.getId());
        List<Squadra> squadre = new ArrayList<>(squadreLega);
        squadre.sort(Comparator.comparing(Squadra::getPuntiClass).reversed());

        model.addAttribute("allenatoreLoggato", allenatoreLoggato);
        model.addAttribute("legaCorrente", legaCorrente);
        model.addAttribute("squadre", squadre);
        model.addAttribute("logged", true);

        return "/lega/classificaView";
    }

    @GetMapping("roseView")
    public String roseView(HttpServletRequest request, Model model) {

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

        Set<Squadra> squadreLega = squadraService.findSquadreByLegaId(legaCorrente.getId());

        model.addAttribute("allenatoreLoggato", allenatoreLoggato);
        model.addAttribute("legaCorrente", legaCorrente);
        model.addAttribute("squadre", squadreLega);
        model.addAttribute("logged", true);

        return "/lega/roseView";

    }

    @PostMapping("rosaView")
    public String rosaView(HttpServletRequest request, Model model, @ModelAttribute Squadra squadra, @RequestParam Integer idAllSq, @RequestParam Integer creditiLega, @RequestParam String nomeAll) {

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

        Set<GiocSquadra> giocatori = giocSquadraService.findGiocatoriBySquadra(squadra);
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
        model.addAttribute("squadra", squadra);
        model.addAttribute("logged", true);
        model.addAttribute("allowMod", idAllSq == allenatoreLoggato.getId());
        model.addAttribute("idAllSq", idAllSq);
        model.addAttribute("creditiLega", creditiLega);
        model.addAttribute("nomeAll", nomeAll);

        return "lega/rosa/rosaView";
    }

    @PostMapping("removeGioc")
    public String removeGioc(@RequestParam Integer idGioc, @RequestParam Integer idSquadra, HttpServletRequest request, Model model, @ModelAttribute Squadra squadra, @RequestParam Integer idAllSq, @RequestParam Integer creditiLega, @RequestParam String nomeAll) {
        GiocSquadra giocSquadra = giocSquadraService.removeGiocatore(idGioc, idSquadra);
        squadra.setCreditiSpesi(squadra.getCreditiSpesi() - giocSquadra.getPrezzo());
        return rosaView(request, model, squadra, idAllSq, creditiLega, nomeAll);
    }

}
