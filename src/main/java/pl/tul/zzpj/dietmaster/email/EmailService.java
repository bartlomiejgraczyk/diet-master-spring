package pl.tul.zzpj.dietmaster.email;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.tul.zzpj.dietmaster.exception.MailSendingException;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender {

    private static final String FROM = "noreply.dietmaster@gmail.com";

    private final JavaMailSender mailSender;

    private void sendMail(String to, String subject, String mailMessage) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        message.setFrom(new InternetAddress(FROM));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject);

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(mailMessage, "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        mailSender.send(message);
    }

    @Override
    @Async
    public void sendActivationMail(String to, String activationLink) throws MailSendingException {
        String subject = "Activate your account!";
        String messageText =
                paragraph("Please click link below to verify your account: ")
                        + hyperlink(activationLink, "Activate");

        try {
            sendMail(to, subject, messageText);
        } catch (MessagingException e) {
            throw MailSendingException.activationLink();
        }
    }

    private String paragraph(String text) {
        return "<p>" + text + "</p>";
    }

    private String hyperlink(String link, String content) {
        return "<a href=\"" + link + "\">" + content + "</a>";
    }
}
