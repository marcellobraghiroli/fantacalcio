package com.isw.mb.fantacalcio.controllers;

import com.isw.mb.fantacalcio.models.Allenatore;
import com.isw.mb.fantacalcio.models.Amministra;
import com.isw.mb.fantacalcio.models.Lega;
import com.isw.mb.fantacalcio.models.combined.AdminViewModel;
import com.isw.mb.fantacalcio.services.AmministraService;
import com.isw.mb.fantacalcio.services.GiornataService;
import com.isw.mb.fantacalcio.services.LegaService;
import com.isw.mb.fantacalcio.services.PartitaService;
import com.isw.mb.fantacalcio.services.combined.AdminViewService;
import com.isw.mb.fantacalcio.services.cookies.CookieService;
import com.isw.mb.fantacalcio.services.cookies.impl.AllenatoreCookieService;
import com.isw.mb.fantacalcio.services.cookies.impl.LegaCookieService;
import com.isw.mb.fantacalcio.services.email.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminController {

    //Controller per la gestione delle operazioni di amministrazione

    private final CookieService allenatoreCookieService, legaCookieService;
    private final AdminViewService adminViewService;
    private final AmministraService amministraService;
    private final PartitaService partitaService;
    private final EmailService emailService;
    private final GiornataService giornataService;
    private final LegaService legaService;

    @Autowired
    public AdminController(AllenatoreCookieService allenatoreCookieService, AdminViewService adminViewService,
                           EmailService emailService, AmministraService amministraService, PartitaService partitaService, GiornataService giornataService,
                           LegaCookieService legaCookieService, LegaService legaService) {
        this.allenatoreCookieService = allenatoreCookieService;
        this.adminViewService = adminViewService;
        this.emailService = emailService;
        this.amministraService = amministraService;
        this.partitaService = partitaService;
        this.giornataService = giornataService;
        this.legaCookieService = legaCookieService;
        this.legaService = legaService;
    }

    //PAGINA ADMINVIEW
    @GetMapping("adminView")
    public String adminView(HttpServletRequest request, Model model) {

        Allenatore allenatoreLoggato = (Allenatore) allenatoreCookieService.get(request);
        Lega legaCorrente = (Lega) legaCookieService.get(request);

        AdminViewModel adminViewModel = adminViewService.getAdminViewModel(legaCorrente, allenatoreLoggato);

        model.addAttribute("allenatoreLoggato", allenatoreLoggato);
        model.addAttribute("legaCorrente", legaCorrente);
        model.addAttribute("logged", true);
        model.addAttribute("allenatoriLega", adminViewModel.getAllenatori());
        model.addAttribute("gradoAdmin", adminViewModel.getGradoAdmin());
        model.addAttribute("started", adminViewModel.isSeasonStarted());
        model.addAttribute("giornate", adminViewModel.getGiornate());

        return "lega/adminView";
    }

    //INVITA UN ALLENATORE ALLA LEGA
    @PostMapping("invitaAll")
    public String invitaAll(@RequestParam String username, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        Lega legaCorrente = (Lega) legaCookieService.get(request);
        Allenatore allenatoreLoggato = (Allenatore) allenatoreCookieService.get(request);

        try {
            emailService.sendInvitationEmail(legaCorrente, allenatoreLoggato, username);
            redirectAttributes.addFlashAttribute("invSuccess", true);

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("invSuccess", false);
            redirectAttributes.addFlashAttribute("errMessage", e.getMessage());
        } catch (MailAuthenticationException e) {
            redirectAttributes.addFlashAttribute("invSuccess", false);
            redirectAttributes.addFlashAttribute("errMessage", "Impossibile inviare l'email");
        }

        return "redirect:/adminView";

    }

    //PROMUOVE UN ALLENATORE AD ADMIN
    @PostMapping("promDegrAll")
    public String promDegrAll(@RequestParam Integer idAll, RedirectAttributes redirectAttributes, @RequestParam String action, HttpServletRequest request) {

        Lega legaCorrente = (Lega) legaCookieService.get(request);
        Allenatore allenatoreLoggato = (Allenatore) allenatoreCookieService.get(request);

        try {

            if(action.equals("Promuovi")) {
                Amministra amministra = amministraService.promuoviAdmin(idAll, legaCorrente.getId());
                emailService.sendPromDemEmail(idAll, legaCorrente, allenatoreLoggato, true);
                redirectAttributes.addFlashAttribute("succMessage", "Allenatore promosso con successo");
            } else if (action.equals("Degrada")) {
                Amministra amministra = amministraService.degradaAdmin(idAll, legaCorrente.getId());
                emailService.sendPromDemEmail(idAll, legaCorrente, allenatoreLoggato, false);
                redirectAttributes.addFlashAttribute("succMessage", "Allenatore degradato con successo");
            }

            redirectAttributes.addFlashAttribute("proSuccess", true);

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("proSuccess", false);
            redirectAttributes.addFlashAttribute("errMessage", e.getMessage());
        }

        return "redirect:/adminView";
    }

    //GENERA CALENDARIO DELLA LEGA
    @GetMapping("generaCal")
    public String generaCal(RedirectAttributes redirectAttributes, HttpServletRequest request) {

        Lega legaCorrente = (Lega) legaCookieService.get(request);

        try {

            partitaService.generaCalendario(legaCorrente.getId());

            emailService.sendCalGeneratoEmail(legaCorrente);

            redirectAttributes.addFlashAttribute("genSuccess", true);

        } catch (IllegalArgumentException e) {

            redirectAttributes.addFlashAttribute("genSuccess", false);
            redirectAttributes.addFlashAttribute("errMessage", e.getMessage());

        }

        return "redirect:/adminView";
    }

    //CALCOLA UNA GIORNATA
    @PostMapping("calcGiornata")
    public String calcGiornata(RedirectAttributes redirectAttributes, @RequestParam Integer numGiornata, HttpServletRequest request) {

        Lega legaCorrente = (Lega) legaCookieService.get(request);

        try {

            giornataService.calcolaGiornata(legaCorrente.getId(), numGiornata);

            emailService.sendCalcGiornataEmail(legaCorrente, numGiornata);

            redirectAttributes.addFlashAttribute("calcSuccess", true);

        } catch (IllegalArgumentException e) {

            redirectAttributes.addFlashAttribute("calcSuccess", false);
            redirectAttributes.addFlashAttribute("errMessage", e.getMessage());

        }

        return "redirect:/adminView";

    }

    //ELIMINA LEGA
    @GetMapping("eliminaLega")
    public String eliminaLega(HttpServletResponse response, HttpServletRequest request) {

        Lega legaCorrente = (Lega) legaCookieService.get(request);

        legaService.eliminaLega(legaCorrente.getId());

        legaCookieService.delete(response);

        return "redirect:/legheView";
    }

    @PostMapping("espAll")
    public String espAll(@RequestParam Integer idAll, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        Lega legaCorrente = (Lega) legaCookieService.get(request);

        try {

            legaService.espelliAllenatore(idAll, legaCorrente.getId());
            emailService.sendExpulsionEmail(idAll, legaCorrente);
            redirectAttributes.addFlashAttribute("espSuccess", true);
            redirectAttributes.addFlashAttribute("succMessage", "Allenatore espulso con successo");

        } catch(Exception e) {

            redirectAttributes.addFlashAttribute("espSuccess", false);
            redirectAttributes.addFlashAttribute("errMessage", e.getMessage());

        }

        return "redirect:adminView";

    }


}
