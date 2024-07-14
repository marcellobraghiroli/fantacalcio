package com.isw.mb.fantacalcio.controllers;

import com.isw.mb.fantacalcio.models.Allenatore;
import com.isw.mb.fantacalcio.models.Lega;
import com.isw.mb.fantacalcio.models.Squadra;
import com.isw.mb.fantacalcio.services.LegaService;
import com.isw.mb.fantacalcio.services.SquadraService;
import com.isw.mb.fantacalcio.services.cookies.CookieService;
import com.isw.mb.fantacalcio.services.cookies.impl.AllenatoreCookieService;
import com.isw.mb.fantacalcio.services.cookies.impl.LegaCookieService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class LegheController {

    //Controller per la gestione delle operazioni relative alle leghe

    private final LegaService legaService;
    private final SquadraService squadraService;
    private final CookieService allenatoreCookieService, legaCookieService;

    @Autowired
    public LegheController(LegaService legaService, SquadraService squadraService, AllenatoreCookieService allenatoreCookieService, LegaCookieService legaCookieService) {
        this.legaService = legaService;
        this.squadraService = squadraService;
        this.allenatoreCookieService = allenatoreCookieService;
        this.legaCookieService = legaCookieService;
    }


    //PAGINA LEGHE ALLENATORE
    @GetMapping("/legheView")
    public String legheView(Model model, HttpServletRequest request) {

        Allenatore allenatoreLoggato = (Allenatore) allenatoreCookieService.get(request);

        //List<Squadra> squadre = squadraService.findSquadreByAllenatoreId(allenatoreLoggato.getId());
        //List<Lega> leghe = squadre.stream().map(Squadra::getLega).toList();

        List<Lega> leghe = legaService.findLegheByAllenatoreId(allenatoreLoggato.getId());

        model.addAttribute("allenatoreLoggato", allenatoreLoggato);
        model.addAttribute("logged", true);
        model.addAttribute("leghe", leghe);

        return "leghe/legheView";
    }

    //PAGINA DI ISCRIZIONE A UNA LEGA
    @GetMapping("joinLegaView")
    public String joinLegaView(Model model, HttpServletRequest request) {

        Allenatore allenatoreLoggato = (Allenatore) allenatoreCookieService.get(request);

        model.addAttribute("allenatoreLoggato", allenatoreLoggato);
        model.addAttribute("logged", true);

        return "leghe/joinLegaView";
    }

    //PAGINA DI CREAZIONE LEGA
    @GetMapping("createLegaView")
    public String createLegaView(Model model, HttpServletRequest request) {

        Allenatore allenatoreLoggato = (Allenatore) allenatoreCookieService.get(request);

        model.addAttribute("allenatoreLoggato", allenatoreLoggato);
        model.addAttribute("logged", true);

        return "leghe/createLegaView";
    }

    //ENTRA IN UNA LEGA
    @PostMapping("joinLega")
    public String joinLega(@RequestParam String codiceInvito, @RequestParam String nomeSquadra, HttpServletRequest request, RedirectAttributes redirectAttributes, HttpServletResponse response) {

        Allenatore allenatoreLoggato = (Allenatore) allenatoreCookieService.get(request);

        try {
            Squadra squadra = squadraService.joinLegaAndCreateSquadra(codiceInvito, nomeSquadra, allenatoreLoggato);

            legaCookieService.create(response, List.of(squadra.getLega().getId(), squadra.getLega().getNome()));

            return "redirect:/homeLegaView";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/joinLegaView?error=true";
        }

    }

    //CREA UNA LEGA
    @PostMapping("createLega")
    public String createLega(@ModelAttribute Lega lega, @RequestParam String nomeSquadra, HttpServletRequest request, RedirectAttributes redirectAttributes, HttpServletResponse response) {

        Allenatore allenatoreLoggato = (Allenatore) allenatoreCookieService.get(request);

        try {
            Lega newLega = legaService.createLegaAndSetAdminAndCreateSquadra(lega, allenatoreLoggato, nomeSquadra);
            legaCookieService.create(response, List.of(newLega.getId(), newLega.getNome()));

            return "redirect:/homeLegaView";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            redirectAttributes.addFlashAttribute("legaForm", lega);
            redirectAttributes.addFlashAttribute("nomeSquadra", nomeSquadra);
            return "redirect:/createLegaView?error=true";
        }

    }

    //VAI A HOME LEGA (setta coookie)
    @PostMapping("homeLega")
    public String homeLega(@RequestParam int idLega, @RequestParam String nomeLega, HttpServletResponse response) {

        legaCookieService.create(response, List.of(idLega, nomeLega));

        return "redirect:/homeLegaView";
    }

}
