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

import java.util.List;

@Service
public class EmailService {

    private static final String INVITATION_SUBJECT = "Invito lega FantaFoot";
    private static final String SUBSCRIPTION_SUBJECT = "Conferma iscrizione FantaFoot";
    private static final String PROMOTION_SUBJECT = "Promozione ad admin lega FantaFoot";
    private static final String DEMOTION_SUBJECT = "Degradazione da admin lega FantaFoot";
    private static final String EXPULSION_SUBJECT = "Espulsione da lega FantaFoot";
    private static final String DELETION_SUBJECT = "Eliminazione lega FantaFoot";
    private static final String CALC_GIORNATA_SUBJECT = "Calcolo giornata lega FantaFoot";
    private static final String CAL_GENERATO_SUBJECT = "Calendario generato lega FantaFoot";

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

        if (squadraRepository.existsByAllenatoreUsernameAndLegaIdAndDeleted(recUsername, lega.getId(), 'N')) {
            throw new IllegalArgumentException("Allenatore già presente nella lega");
        }

        String codiceInvito = legaRepository.getCodiceInvitoByNomeLega(lega.getNome());

        String recipient = allenatoreRepository.getEmailByUsername(recUsername);

        if (recipient == null) {
            throw new IllegalArgumentException("Allenatore inesistente");
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipient);
        message.setSubject(INVITATION_SUBJECT);

        String text = "Ciao " + recUsername + ",\n\nSei stato invitato a partecipare alla lega " + lega.getNome() + " da " + admin.getUsername() + ".\n"
                + "Per accettare l'invito, vai su FantaFoot.com, accedi con le tue credenziali e clicca su Entra in una lega.\n"
                + "A questo punto, inserisci il codice di invito che trovi di seguito, il nome della tua nuova squadra ed il gioco è fatto.\n\n"
                + "Codice di invito: " + codiceInvito + "\n\n"
                + "Buon divertimento!\n\n"
                + "Lo staff di FantaFoot\n";

        message.setText(text);

        mailSender.send(message);
    }

    @Transactional
    public void sendSubscriptionEmail(String recUsername) {

        String recipient = allenatoreRepository.getEmailByUsername(recUsername);
        if (recipient == null) {
            throw new IllegalArgumentException("Allenatore inesistente");
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipient);
        message.setSubject(SUBSCRIPTION_SUBJECT);

        String text = "Ciao " + recUsername + ",\n\nGrazie per esserti iscritto a FantaFoot!\n\n"
                + "Tutto ciò che devi fare adesso è creare la tua lega o unirti a una già esistente per iniziare a giocare.\n\n"
                + "Buon divertimento!\n\n"
                + "Lo staff di FantaFoot\n";

        message.setText(text);

        mailSender.send(message);

    }

    @Transactional
    public void sendPromDemEmail(Integer recId, Lega lega, Allenatore admin, boolean promotion) {

        Allenatore allenatore = allenatoreRepository.findByIdAndDeleted(recId, 'N');

        String recipient = allenatore.getEmail();
        String recUsername = allenatore.getUsername();

        if (recipient == null) {
            throw new IllegalArgumentException("Allenatore inesistente");
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipient);
        message.setSubject(promotion ? PROMOTION_SUBJECT : DEMOTION_SUBJECT);

        String text;

        if (promotion) {

            text = "Ciao " + recUsername + ",\n\n"
                    + "Sei stato promosso ad admin base della lega " + lega.getNome() + " da " + admin.getUsername() + ".\n"
                    + "Hai ora accesso alle funzionalità di amministrazione della lega dalla sezione Area Admin.\n\n"
                    + "Buon divertimento!\n\n"
                    + "Lo staff di FantaFoot\n";

        } else {

            text = "Ciao " + recUsername + ",\n\n"
                    + "Sei stato degradato da admin base della lega " + lega.getNome() + " da " + admin.getUsername() + ".\n"
                    + "Potrai continuare a giocare all'interno della lega, ma non avrai più accesso alle funzionalità di amministrazione.\n\n"
                    + "Buon divertimento!\n\n"
                    + "Lo staff di FantaFoot\n";

        }

        message.setText(text);

        mailSender.send(message);

    }

    @Transactional
    public void sendExpulsionEmail(Integer recId, Lega lega) {

        Allenatore allenatore = allenatoreRepository.findById(recId).get();

        String recipient = allenatore.getEmail();
        String recUsername = allenatore.getUsername();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipient);
        message.setSubject(EXPULSION_SUBJECT);

        String text = "Ciao " + recUsername + ",\n\n"
                + "Sei stato espulso dalla lega " + lega.getNome() + ". Non potrai più accedervi.\n\n"
                + "Buon divertimento!\n\n"
                + "Lo staff di FantaFoot\n";

        message.setText(text);

        mailSender.send(message);

    }

    @Transactional
    public void sendDeletionEmail(Lega lega, List<Allenatore> allenatori) {

        for (Allenatore allenatore : allenatori) {

            String recipient = allenatore.getEmail();
            String recUsername = allenatore.getUsername();

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(recipient);
            message.setSubject(DELETION_SUBJECT);

            String text = "Ciao " + recUsername + ",\n\n"
                    + "La lega " + lega.getNome() + " è stata eliminata. Non potrai più accedervi.\n\n"
                    + "Buon divertimento!\n\n"
                    + "Lo staff di FantaFoot\n";

            message.setText(text);

            mailSender.send(message);

        }

    }

    @Transactional
    public void sendCalcGiornataEmail(Lega lega, Integer numGiornata) {

        List<Allenatore> allenatori = allenatoreRepository.findByLega(lega.getId());

        for (Allenatore allenatore : allenatori) {

            String recipient = allenatore.getEmail();
            String recUsername = allenatore.getUsername();

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(recipient);
            message.setSubject(CALC_GIORNATA_SUBJECT);

            String text = "Ciao " + recUsername + ",\n\n"
                    + "La giornata " + numGiornata + " è stata calcolata per la lega " + lega.getNome() + ".\n\n"
                    + "Buon divertimento!\n\n"
                    + "Lo staff di FantaFoot\n";

            message.setText(text);

            mailSender.send(message);

        }

    }

    @Transactional
    public void sendCalGeneratoEmail(Lega lega) {

        List<Allenatore> allenatori = allenatoreRepository.findByLega(lega.getId());

        for (Allenatore allenatore : allenatori) {

            String recipient = allenatore.getEmail();
            String recUsername = allenatore.getUsername();

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(recipient);
            message.setSubject(CAL_GENERATO_SUBJECT);

            String text = "Ciao " + recUsername + ",\n\n"
                    + "Il calendario della lega " + lega.getNome() + " è stato generato, la stagione è iniziata.\n\n"
                    + "Buon divertimento!\n\n"
                    + "Lo staff di FantaFoot\n";

            message.setText(text);

            mailSender.send(message);

        }

    }

}
