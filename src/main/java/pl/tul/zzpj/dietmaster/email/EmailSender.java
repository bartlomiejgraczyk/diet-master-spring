package pl.tul.zzpj.dietmaster.email;

import pl.tul.zzpj.dietmaster.exception.MailSendingException;

public interface EmailSender {
    void sendActivationMail(String to, String activationLink) throws MailSendingException;
}
