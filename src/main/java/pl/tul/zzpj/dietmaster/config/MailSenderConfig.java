package pl.tul.zzpj.dietmaster.config;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class MailSenderConfig {
    
    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        
        mailSender.setProtocol("SMTP");
        mailSender.setHost("127.0.0.1");
        mailSender.setPort(25);
        
        return mailSender;
    }
}
