package com.isw.mb.fantacalcio.controllers;


import com.isw.mb.fantacalcio.models.*;
import com.isw.mb.fantacalcio.models.combined.CalendarioViewModel;
import com.isw.mb.fantacalcio.models.combined.FormazioneViewModel;
import com.isw.mb.fantacalcio.models.combined.PartitaViewModel;
import com.isw.mb.fantacalcio.models.combined.HomeLegaViewModel;
import com.isw.mb.fantacalcio.services.*;
import com.isw.mb.fantacalcio.services.combined.CalendarioViewService;
import com.isw.mb.fantacalcio.services.combined.FormazioneViewService;
import com.isw.mb.fantacalcio.services.combined.PartitaViewService;
import com.isw.mb.fantacalcio.services.combined.HomeLegaViewService;
import com.isw.mb.fantacalcio.services.cookies.CookieService;
import com.isw.mb.fantacalcio.services.cookies.impl.AllenatoreCookieService;
import com.isw.mb.fantacalcio.services.cookies.impl.LegaCookieService;
import com.isw.mb.fantacalcio.services.cookies.impl.SquadraCookieService;
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

import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Controller
public class LegaController {

    //Controller per la gestione delle operazioni relative alla lega

    private final HomeLegaViewService homeLegaViewService;
    private final SquadraService squadraService;
    private final CookieService allenatoreCookieService, legaCookieService, squadraCookieService;
    private final PartitaService partitaService;
    private final GiornataService giornataService;
    private final PartitaViewService partitaViewService;
    private final FormazioneViewService formazioneViewService;
    private final FormazioneService formazioneService;
    private final LegaService legaService;
    private final CalendarioViewService calendarioViewService;


    @Autowired
    public LegaController(HomeLegaViewService homeLegaViewService, SquadraService squadraService,
                          AllenatoreCookieService allenatoreCookieService, LegaCookieService legaCookieService, SquadraCookieService squadraCookieService,
                          PartitaService partitaService, GiornataService giornataService,
                          PartitaViewService partitaViewService, LegaService legaService,
                          FormazioneViewService formazioneViewService, FormazioneService formazioneService,
                          CalendarioViewService calendarioViewService) {
        this.homeLegaViewService = homeLegaViewService;
        this.squadraService = squadraService;
        this.allenatoreCookieService = allenatoreCookieService;
        this.legaCookieService = legaCookieService;
        this.squadraCookieService = squadraCookieService;
        this.partitaService = partitaService;
        this.giornataService = giornataService;
        this.partitaViewService = partitaViewService;
        this.formazioneViewService = formazioneViewService;
        this.formazioneService = formazioneService;
        this.legaService = legaService;
        this.calendarioViewService = calendarioViewService;
    }

    //PAGINA DI HOME DELLA LEGA
    @GetMapping("/homeLegaView")
    public String homeLegaView(HttpServletRequest request, Model model) {

        Allenatore allenatoreLoggato = (Allenatore) allenatoreCookieService.get(request);
        Lega legaCorrente = (Lega) legaCookieService.get(request);

        HomeLegaViewModel homeLegaViewModel = homeLegaViewService.getHomeLegaViewModel(allenatoreLoggato, legaCorrente);

        if (homeLegaViewModel.getGiornata() != null) {
            model.addAttribute("giornata", homeLegaViewModel.getGiornata());
        }

        model.addAttribute("allenatoreLoggato", allenatoreLoggato);
        model.addAttribute("legaCorrente", legaCorrente);
        model.addAttribute("squadraCorrente", homeLegaViewModel.getSquadraCorrente());
        model.addAttribute("logged", true);
        model.addAttribute("isAdmin", homeLegaViewModel.isAdmin());

        if (homeLegaViewModel.getGradoAdmin().equals("super")) {
            model.addAttribute("allowExit", false);
        } else {
            model.addAttribute("allowExit", true);
        }

        return "/lega/homeLegaView";
    }

    //TORNA ALLE LEGHE (delete cookie)
    @GetMapping("backToLeghe")
    public String backToLeghe(HttpServletResponse response) {
        legaCookieService.delete(response);
        return "redirect:/legheView";
    }

    //CLASSIFICA
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

    //ROSE
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

    //ROSA (setta cookie)
    @PostMapping("goToRosaView")
    public String goToRosaView(HttpServletResponse response, @ModelAttribute Squadra squadra, @RequestParam Integer idAllSq, @RequestParam Integer creditiLega, @RequestParam String nomeAll) {

        squadraCookieService.create(response, List.of(squadra.getId(), squadra.getNome(), squadra.getCreditiSpesi(), creditiLega, idAllSq, nomeAll));

        return "redirect:/rosaView";
    }

    //CALENDARIO
    @GetMapping("calendarioView")
    public String calendarioView(HttpServletRequest request, Model model, @RequestParam(required = false) Integer numGiornata) {

        Allenatore allenatoreLoggato = (Allenatore) allenatoreCookieService.get(request);
        Lega legaCorrente = (Lega) legaCookieService.get(request);

        CalendarioViewModel calendarioViewModel = calendarioViewService.getCalendarioViewModel(legaCorrente, numGiornata);

        List<Partita> partite = calendarioViewModel.getPartite();
        Giornata giornataCorrente = calendarioViewModel.getGiornataCorrente();

        List<Integer> numeriGiornate = partite.stream()
                .map(Partita::getGiornata)
                .map(Giornata::getNumero)
                .distinct()
                .toList();

        model.addAttribute("allenatoreLoggato", allenatoreLoggato);
        model.addAttribute("legaCorrente", legaCorrente);
        model.addAttribute("logged", true);
        model.addAttribute("partite", partite);
        model.addAttribute("giornataCorrente", giornataCorrente);
        model.addAttribute("numeriGiornate", numeriGiornate);

        return "lega/calendarioView";
    }

    //PARTITA
    @PostMapping("partitaView")
    public String partitaView(HttpServletRequest request, Model model, @RequestParam Integer idPartita, @RequestParam Integer idSqCasa, @RequestParam Integer idSqTrasf, @RequestParam Integer numGiornata) {

        Allenatore allenatoreLoggato = (Allenatore) allenatoreCookieService.get(request);
        Lega legaCorrente = (Lega) legaCookieService.get(request);

        PartitaViewModel partitaViewModel = partitaViewService.getPartitaViewModel(idPartita, idSqCasa, idSqTrasf, numGiornata);

        Partita partita = partitaViewModel.getPartita();
        Formazione formCasa = partitaViewModel.getFormCasa();
        Formazione formTrasf = partitaViewModel.getFormTrasf();

        if (formCasa != null) {
            Set<FormGioc> giocatoriCasa = formCasa.getFormGiocatori();
            List<FormGioc> giocatoriCasaList = giocatoriCasa.stream()
                    .sorted(Comparator.comparing(FormGioc::getOrdine))
                    .toList();
            model.addAttribute("giocatoriCasa", giocatoriCasaList);
        }

        if (formTrasf != null) {
            Set<FormGioc> giocatoriTrasf = formTrasf.getFormGiocatori();
            List<FormGioc> giocatoriTrasfList = giocatoriTrasf.stream()
                    .sorted(Comparator.comparing(FormGioc::getOrdine))
                    .toList();
            model.addAttribute("giocatoriTrasf", giocatoriTrasfList);
        }

        model.addAttribute("logged", true);
        model.addAttribute("partita", partita);
        model.addAttribute("formCasa", formCasa);
        model.addAttribute("formTrasf", formTrasf);

        return "lega/partitaView";
    }

    //PAGINA DI INSERIMENTO FORMAZIONE
    @PostMapping("formazioneView")
    public String formazioneView(Model model, @RequestParam Integer idSquadra, @RequestParam Integer numGiornata) {

        //Allenatore allenatoreLoggato = (Allenatore) allenatoreCookieService.get(request);
        //Lega legaCorrente = (Lega) legaCookieService.get(request);

        Giornata prossGiornata = new Giornata();
        prossGiornata.setNumero(numGiornata);

        Squadra squadra = new Squadra();
        squadra.setId(idSquadra);

        FormazioneViewModel formazioneViewModel = formazioneViewService.getFormazioneViewModel(squadra, prossGiornata);

        Set<GiocSquadra> giocatori = formazioneViewModel.getGiocatori();
        Formazione formazione = formazioneViewModel.getFormazione();

        List<GiocSquadra> portieri = giocatori.stream().filter(g -> g.getGiocatore().getRuolo().equals("POR")).sorted(Comparator.comparing(g -> g.getGiocatore().getNome())).toList();
        List<GiocSquadra> difensori = giocatori.stream().filter(g -> g.getGiocatore().getRuolo().equals("DIF")).sorted(Comparator.comparing(g -> g.getGiocatore().getNome())).toList();
        List<GiocSquadra> centrocampisti = giocatori.stream().filter(g -> g.getGiocatore().getRuolo().equals("CEN")).sorted(Comparator.comparing(g -> g.getGiocatore().getNome())).toList();
        List<GiocSquadra> attaccanti = giocatori.stream().filter(g -> g.getGiocatore().getRuolo().equals("ATT")).sorted(Comparator.comparing(g -> g.getGiocatore().getNome())).toList();

        if (formazione != null) {

            String moduloForm = formazione.getModulo();
            //Set<FormGioc> giocatoriFormazione = formazione.getFormGiocatori().stream().filter(giocatore -> 'N' == giocatore.getDeleted()).collect(Collectors.toSet());
            List<FormGioc> giocatoriForm = formazione.getFormGiocatori().stream().sorted(Comparator.comparing(FormGioc::getOrdine)).toList();

            model.addAttribute("moduloForm", moduloForm);
            model.addAttribute("giocatoriForm", giocatoriForm);

        }

        //model.addAttribute("allenatoreLoggato", allenatoreLoggato);
        //model.addAttribute("legaCorrente", legaCorrente);
        model.addAttribute("logged", true);
        model.addAttribute("prossGiornata", prossGiornata);
        model.addAttribute("squadra", squadra);
        model.addAttribute("portieri", portieri);
        model.addAttribute("difensori", difensori);
        model.addAttribute("centrocampisti", centrocampisti);
        model.addAttribute("attaccanti", attaccanti);
        //model.addAttribute("formazione", formazione);

        return "lega/formazioneView";
    }

    //INVIA FORMAZIONE
    @PostMapping("sendFormazione")
    public String sendFormazione(RedirectAttributes redirectAttributes,
                                 @RequestParam(required = false) Integer idFormazione, @RequestParam String modulo,
                                 @RequestParam Integer idSquadra, @RequestParam Integer numGiornata, @RequestParam String idGiocatori) {


        try {

            formazioneService.saveFormazione(idFormazione, modulo, idSquadra, numGiornata, idGiocatori);

            redirectAttributes.addFlashAttribute("formSuccess", true);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("formSuccess", false);
            redirectAttributes.addFlashAttribute("errorMessage", "Qualcosa è andato storto, la formazione non è stata inviata");
        }


        return "redirect:/homeLegaView";
    }

    //ABBANDONA LEGA
    @GetMapping("abbandonaLega")
    public String abbandonaLega(HttpServletResponse response, HttpServletRequest request) {

        Allenatore allenatoreLoggato = (Allenatore) allenatoreCookieService.get(request);
        Lega legaCorrente = (Lega) legaCookieService.get(request);

        legaService.espelliAllenatore(allenatoreLoggato.getId(), legaCorrente.getId());

        legaCookieService.delete(response);

        return "redirect:legheView";
    }

}
