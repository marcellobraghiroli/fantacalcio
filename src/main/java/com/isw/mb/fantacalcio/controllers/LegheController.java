package com.isw.mb.fantacalcio.controllers;

import com.isw.mb.fantacalcio.models.Allenatore;
import com.isw.mb.fantacalcio.models.Lega;
import com.isw.mb.fantacalcio.models.Squadra;
import com.isw.mb.fantacalcio.services.LegaService;
import com.isw.mb.fantacalcio.services.SquadraService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    //LEGHE
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

    //PAGINA DI ISCRIZIONE A UNA LEGA
    @GetMapping("joinLegaView")
    public String joinLegaView(Model model, HttpServletRequest request) {

        String idAllenatore = CookieUtils.getCookie(request, "idAllenatore");
        String username = CookieUtils.getCookie(request, "username");

        Allenatore allenatoreLoggato = new Allenatore();
        allenatoreLoggato.setId(Integer.parseInt(idAllenatore));
        allenatoreLoggato.setUsername(username);

        model.addAttribute("allenatoreLoggato", allenatoreLoggato);
        model.addAttribute("logged", true);

        return "leghe/joinLegaView";
    }

    //PAGINA DI CREAZIONE LEGA
    @GetMapping("createLegaView")
    public String createLegaView(Model model, HttpServletRequest request) {

        String idAllenatore = CookieUtils.getCookie(request, "idAllenatore");
        String username = CookieUtils.getCookie(request, "username");

        Allenatore allenatoreLoggato = new Allenatore();
        allenatoreLoggato.setId(Integer.parseInt(idAllenatore));
        allenatoreLoggato.setUsername(username);

        model.addAttribute("allenatoreLoggato", allenatoreLoggato);
        model.addAttribute("logged", true);

        return "leghe/createLegaView";
    }

    //ISCRZIONE A UNA LEGA
    @PostMapping("joinLega")
    public String joinLega(@RequestParam String codiceInvito, @RequestParam String nomeSquadra, HttpServletRequest request, RedirectAttributes redirectAttributes, HttpServletResponse response) {

        String idAllenatore = CookieUtils.getCookie(request, "idAllenatore");
        Allenatore allenatoreLoggato = new Allenatore();
        allenatoreLoggato.setId(Integer.parseInt(idAllenatore));

        try {
            Squadra squadra = squadraService.joinLegaAndCreateSquadra(codiceInvito, nomeSquadra, allenatoreLoggato);

            CookieUtils.setCookie(response, "idLega", String.valueOf(squadra.getLega().getId()));
            CookieUtils.setCookie(response, "nomeLega", squadra.getLega().getNome());

            return "redirect:/homeLegaView";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/joinLegaView?error=true";
        }

    }

    //CREAZIONE LEGA
    @PostMapping("createLega")
    public String createLega(@ModelAttribute Lega lega, @RequestParam String nomeSquadra, HttpServletRequest request, RedirectAttributes redirectAttributes, HttpServletResponse response) {

        String idAllenatore = CookieUtils.getCookie(request, "idAllenatore");
        Allenatore allenatoreLoggato = new Allenatore();
        allenatoreLoggato.setId(Integer.parseInt(idAllenatore));

        try {
            Lega newLega = legaService.createLegaAndSetAdminAndCreateSquadra(lega, allenatoreLoggato, nomeSquadra);

            CookieUtils.setCookie(response, "idLega", String.valueOf(newLega.getId()));
            CookieUtils.setCookie(response, "nomeLega", newLega.getNome());

            return "redirect:/homeLegaView";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            redirectAttributes.addFlashAttribute("legaForm", lega);
            redirectAttributes.addFlashAttribute("nomeSquadra", nomeSquadra);
            return "redirect:/createLegaView?error=true";
        }

    }

    //PAGINA DI HOME LEGA
    @PostMapping("homeLega")
    public String homeLega(@RequestParam int idLega, @RequestParam String nomeLega, HttpServletResponse response) {

        CookieUtils.setCookie(response, "idLega", String.valueOf(idLega));
        CookieUtils.setCookie(response, "nomeLega", nomeLega);

        return "redirect:/homeLegaView";
    }

}
