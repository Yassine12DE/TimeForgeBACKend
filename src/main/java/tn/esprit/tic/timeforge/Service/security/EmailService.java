package tn.esprit.tic.timeforge.Service.security;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;


@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String sender;
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendHtmlEmail(String to, String subject, String templatePath, Map<String, String> model) {
        try {
            // Créer un message MIME
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom(sender);

            Path path = Paths.get(templatePath);
            String htmlContent = new String(Files.readAllBytes(path));

            for (Map.Entry<String, String> entry : model.entrySet()) {
                htmlContent = htmlContent.replace("${" + entry.getKey() + "}", entry.getValue());
            }
            helper.setText(htmlContent, true);

            mailSender.send(message);
            System.out.println("Email envoyé avec succès à " + to);

        } catch (MessagingException | java.io.IOException e) {
            System.out.println("Erreur lors de l'envoi de l'email : " + e.getMessage());
        }
    }
}
