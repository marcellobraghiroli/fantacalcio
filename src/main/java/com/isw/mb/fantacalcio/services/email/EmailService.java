package com.isw.mb.fantacalcio.services.email;

import com.isw.mb.fantacalcio.models.Allenatore;
import com.isw.mb.fantacalcio.models.Lega;
import com.isw.mb.fantacalcio.repositories.AllenatoreRepository;
import com.isw.mb.fantacalcio.repositories.LegaRepository;
import com.isw.mb.fantacalcio.repositories.SquadraRepository;
import com.isw.mb.fantacalcio.services.LegaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final String SUBJECT = "Invito lega FantaFoot";

    private final JavaMailSender mailSender;

    private final LegaRepository legaRepository;
    private final AllenatoreRepository allenatoreRepository;
    private final SquadraRepository squadraRepository;

    @Autowired
    public EmailService(JavaMailSender mailSender, LegaRepository legaRepository, AllenatoreRepository allenatoreRepository, SquadraRepository squadraRepository) {
        this.mailSender = mailSender;
        this.legaRepository = legaRepository;
        this.allenatoreRepository = allenatoreRepository;
        this.squadraRepository = squadraRepository;
    }

    @Transactional
    public void sendInvitationEmail(Lega lega, Allenatore admin, String recUsername) {

        if(squadraRepository.existsByAllenatoreUsernameAndLegaIdAndDeleted(recUsername, lega.getId(), 'N')) {
            throw new IllegalArgumentException("Allenatore già presente nella lega");
        }

        String codiceInvito = legaRepository.getCodiceInvitoByNomeLega(lega.getNome());

        String recipient = allenatoreRepository.getEmailByUsername(recUsername);

        if (recipient == null) {
            throw new IllegalArgumentException("Allenatore inesistente");
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipient);
        message.setSubject(SUBJECT);

        String text = "Ciao " + recUsername + ",\n\nSei stato invitato a partecipare alla lega " + lega.getNome() + " da " + admin.getUsername() + ".\n"
                + "Per accettare l'invito, vai su FantaFoot.com, accedi con le tue credenziali e clicca su Entra in una lega.\n"
                + "A questo punto, inserisci il codice di invito che trovi di seguito, il nome della tua nuova squadra ed il gioco è fatto.\n\n"
                + "Codice di invito: " + codiceInvito + "\n\n"
                + "Buon divertimento!\n\n"
                + "Lo staff di FantaFoot\n";

        message.setText(text);

        mailSender.send(message);
    }

}
