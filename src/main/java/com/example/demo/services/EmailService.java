package com.example.demo.services;

import com.example.demo.domain.Proband;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendConfirmationEmail(Proband proband) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(proband.getEmail());
            message.setSubject("EsportNabor — Potvrzení registrace");
            message.setText(
                    "Dobrý den, " + proband.getFirstName() + " " + proband.getLastName() + ",\n\n"
                            + "děkujeme za Vaši registraci do výzkumného projektu zaměřeného na esportový výkon.\n\n"
                            + "Vaše přihláška byla úspěšně přijata. V nejbližší době Vás budeme kontaktovat "
                            + "s dalšími podrobnostmi ohledně průběhu experimentu, termínu a místa konání.\n\n"
                            + "Pokud máte jakékoliv dotazy, neváhejte odpovědět na tento e-mail.\n\n"
                            + "S pozdravem,\n"
                            + "Tým EsportNabor");

            mailSender.send(message);
            log.info("Confirmation email sent to {}", proband.getEmail());
        } catch (Exception e) {
            log.error("Failed to send confirmation email to {}: {}", proband.getEmail(), e.getMessage(), e);
        }
    }
}
