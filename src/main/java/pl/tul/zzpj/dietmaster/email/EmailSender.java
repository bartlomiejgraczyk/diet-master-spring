package pl.tul.zzpj.dietmaster.email;

public interface EmailSender {
    void send(String from, String to, String email, String subject);
}
