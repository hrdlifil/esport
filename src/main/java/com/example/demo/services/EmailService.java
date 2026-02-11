package com.example.demo.services;

import com.example.demo.domain.Proband;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    @Value("${sendgrid.api-key}")
    private String sendGridApiKey;

    @Value("${sendgrid.from-email}")
    private String fromEmail;

    public void sendConfirmationEmail(Proband proband) {
        Email from = new Email(fromEmail, "Tým EsportNabor");
        Email to = new Email(proband.getEmail());
        String subject = "EsportNabor — Potvrzení registrace";
        Content content = new Content("text/plain",
                "Dobrý den, " + proband.getFirstName() + " " + proband.getLastName() + ",\n\n"
                        + "děkujeme za Vaši registraci do výzkumného projektu zaměřeného na esportový výkon.\n\n"
                        + "Vaše přihláška byla úspěšně přijata. V nejbližší době Vás budeme kontaktovat "
                        + "s dalšími podrobnostmi ohledně průběhu experimentu, termínu a místa konání.\n\n"
                        + "Pokud máte jakékoliv dotazy, neváhejte odpovědět na tento e-mail.\n\n"
                        + "S pozdravem,\n"
                        + "Tým EsportNabor");

        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);

            if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
                log.info("Confirmation email sent to {} (status {})", proband.getEmail(), response.getStatusCode());
            } else {
                log.error("SendGrid returned status {} for {}: {}", response.getStatusCode(), proband.getEmail(),
                        response.getBody());
            }
        } catch (IOException e) {
            log.error("Failed to send confirmation email to {}: {}", proband.getEmail(), e.getMessage(), e);
        }
    }
}
