package com.isw.mb.fantacalcio.controllers;

import com.isw.mb.fantacalcio.models.Giornata;
import com.isw.mb.fantacalcio.services.GiornataService;
import com.isw.mb.fantacalcio.services.marks.MarksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class SysAdminController {

    //Controller per la gestione delle operazioni di amministrazione di sistema

    private final GiornataService giornataService;
    private final MarksService marksService;

    @Autowired
    public SysAdminController(GiornataService giornataService, MarksService marksService) {
        this.giornataService = giornataService;
        this.marksService = marksService;
    }


    //PAGINA DI AMMINISTRAZIONE DI SISTEMA
    @GetMapping("/SysAdminView")
    public String sysAdminView(Model model) {

        List<Giornata> giornate = giornataService.findGiornate();

        model.addAttribute("logged", true);
        model.addAttribute("giornate", giornate);

        return "sysAdmin/sysAdminView";

    }

    //CARICA VOTI PER UNA GIORNATA E AGGIORNA STATISTICHE GIOCATORI
    @PostMapping("caricaVoti")
    public String caricaVoti(RedirectAttributes redirectAttributes, @RequestParam Integer giornata, @RequestParam MultipartFile fileVoti) {

        try {

            marksService.caricaVoti(giornata, fileVoti);

            redirectAttributes.addFlashAttribute("success", true);


        } catch (IllegalArgumentException e) {

            redirectAttributes.addFlashAttribute("success", false);
            redirectAttributes.addFlashAttribute("errMessage", e.getMessage());


        } catch (Exception e) {

            redirectAttributes.addFlashAttribute("success", false);
            redirectAttributes.addFlashAttribute("errMessage", "Caricamento voti fallito");

        }

        return "redirect:/SysAdminView";
    }

}
