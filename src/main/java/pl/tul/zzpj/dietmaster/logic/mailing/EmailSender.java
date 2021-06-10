package pl.tul.zzpj.dietmaster.logic.mailing;

import pl.tul.zzpj.dietmaster.model.exception.MailSendingException;

public interface EmailSender {
    void sendActivationMail(String to, String activationLink) throws MailSendingException;
}
