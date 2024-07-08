package com.isw.mb.fantacalcio.controllers;


import com.isw.mb.fantacalcio.models.*;
import com.isw.mb.fantacalcio.models.combined.FormGiocatori;
import com.isw.mb.fantacalcio.models.combined.PartForm;
import com.isw.mb.fantacalcio.models.combined.SqGioAmm;
import com.isw.mb.fantacalcio.repositories.PartitaRepository;
import com.isw.mb.fantacalcio.services.*;
import com.isw.mb.fantacalcio.services.combined.FormGiocatoriService;
import com.isw.mb.fantacalcio.services.combined.PartFormService;
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

import static com.oracle.wls.shaded.org.apache.xalan.lib.ExsltSets.distinct;

@Controller
public class LegaController {

    private final SqGioAmmService sqGioAmmService;
    private final SquadraService squadraService;
    private final CookieService allenatoreCookieService, legaCookieService, squadraCookieService;
    private final PartitaService partitaService;
    private final GiornataService giornataService;
    private final PartFormService partFormService;
    private final GiocSquadraService giocSquadraService;
    private final FormGiocatoriService formGiocatoriService;


    @Autowired
    public LegaController(SqGioAmmService sqGioAmmService, SquadraService squadraService,
                          AllenatoreCookieService allenatoreCookieService, LegaCookieService legaCookieService, SquadraCookieService squadraCookieService,
                          PartitaService partitaService, GiornataService giornataService,
                          PartFormService partFormService, GiocSquadraService giocSquadraService,
                          FormGiocatoriService formGiocatoriService) {
        this.sqGioAmmService = sqGioAmmService;
        this.squadraService = squadraService;
        this.allenatoreCookieService = allenatoreCookieService;
        this.legaCookieService = legaCookieService;
        this.squadraCookieService = squadraCookieService;
        this.partitaService = partitaService;
        this.giornataService = giornataService;
        this.partFormService = partFormService;
        this.giocSquadraService = giocSquadraService;
        this.formGiocatoriService = formGiocatoriService;
    }

    @GetMapping("/homeLegaView")
    public String homeLegaView(HttpServletRequest request, Model model) {

        Allenatore allenatoreLoggato = (Allenatore) allenatoreCookieService.get(request);
        Lega legaCorrente = (Lega) legaCookieService.get(request);

        SqGioAmm sqGioAmm = sqGioAmmService.getSqGioAmm(allenatoreLoggato, legaCorrente);

        if (sqGioAmm.getGiornata() != null) {
            model.addAttribute("giornata", sqGioAmm.getGiornata());
            //System.out.println("Giornata: " + sqGioAmm.getGiornata().getNumero() + " - " + sqGioAmm.getGiornata().getTsInizio());
        }
        model.addAttribute("allenatoreLoggato", allenatoreLoggato);
        model.addAttribute("legaCorrente", legaCorrente);
        model.addAttribute("squadraCorrente", sqGioAmm.getSquadraCorrente());
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

    @GetMapping("calendarioView")
    public String calendarioView(HttpServletRequest request, Model model) {

        Allenatore allenatoreLoggato = (Allenatore) allenatoreCookieService.get(request);
        Lega legaCorrente = (Lega) legaCookieService.get(request);

        List<Partita> partite = partitaService.findPartiteByLegaId(legaCorrente.getId());
        Giornata giornataCorrente = giornataService.findCurrentOrNextGiornata(legaCorrente);

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

    @PostMapping("partitaView")
    public String partitaView(HttpServletRequest request, Model model, @RequestParam Integer idPartita, @RequestParam Integer idSqCasa, @RequestParam Integer idSqTrasf, @RequestParam Integer numGiornata) {

        Allenatore allenatoreLoggato = (Allenatore) allenatoreCookieService.get(request);
        Lega legaCorrente = (Lega) legaCookieService.get(request);


        PartForm partForm = partFormService.getPartForm(idPartita, idSqCasa, idSqTrasf, numGiornata);

        Partita partita = partForm.getPartita();
        Formazione formCasa = partForm.getFormCasa();
        Formazione formTrasf = partForm.getFormTrasf();

        Set<FormGioc> giocatoriCasa = formCasa.getFormGiocatori();
        List<FormGioc> giocatoriCasaList = giocatoriCasa.stream()
                .sorted(Comparator.comparing(FormGioc::getOrdine))
                .toList();

        Set<FormGioc> giocatoriTrasf = formTrasf.getFormGiocatori();
        List<FormGioc> giocatoriTrasfList = giocatoriTrasf.stream()
                .sorted(Comparator.comparing(FormGioc::getOrdine))
                .toList();


        //model.addAttribute("allenatoreLoggato", allenatoreLoggato);
        //model.addAttribute("legaCorrente", legaCorrente);
        model.addAttribute("logged", true);
        model.addAttribute("partita", partita);
        model.addAttribute("formCasa", formCasa);
        model.addAttribute("formTrasf", formTrasf);
        model.addAttribute("giocatoriCasa", giocatoriCasaList);
        model.addAttribute("giocatoriTrasf", giocatoriTrasfList);


        return "lega/partitaView";
    }

    @PostMapping("formazioneView")
    public String formazioneView(Model model, @RequestParam Integer idSquadra, @RequestParam Integer numGiornata) {

        //Allenatore allenatoreLoggato = (Allenatore) allenatoreCookieService.get(request);
        //Lega legaCorrente = (Lega) legaCookieService.get(request);

        Giornata prossGiornata = new Giornata();
        prossGiornata.setNumero(numGiornata);

        Squadra squadra = new Squadra();
        squadra.setId(idSquadra);

        FormGiocatori formGiocatori = formGiocatoriService.getFormGiocatori(squadra, prossGiornata);
        Set<GiocSquadra> giocatori = formGiocatori.getGiocatori();
        Formazione formazione = formGiocatori.getFormazione();

        List<GiocSquadra> portieri = giocatori.stream().filter(g -> g.getGiocatore().getRuolo().equals("POR")).sorted(Comparator.comparing(g -> g.getGiocatore().getNome())).toList();
        List<GiocSquadra> difensori = giocatori.stream().filter(g -> g.getGiocatore().getRuolo().equals("DIF")).sorted(Comparator.comparing(g -> g.getGiocatore().getNome())).toList();
        List<GiocSquadra> centrocampisti = giocatori.stream().filter(g -> g.getGiocatore().getRuolo().equals("CEN")).sorted(Comparator.comparing(g -> g.getGiocatore().getNome())).toList();
        List<GiocSquadra> attaccanti = giocatori.stream().filter(g -> g.getGiocatore().getRuolo().equals("ATT")).sorted(Comparator.comparing(g -> g.getGiocatore().getNome())).toList();

        if (formazione != null) {

            String moduloForm = formazione.getModulo();
            Set<FormGioc> giocatoriFormazione = formazione.getFormGiocatori();
            List<FormGioc> giocatoriForm = giocatoriFormazione.stream().sorted(Comparator.comparing(FormGioc::getOrdine)).toList();

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

}
