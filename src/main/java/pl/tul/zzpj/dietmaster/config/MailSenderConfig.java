package pl.tul.zzpj.dietmaster.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailSenderConfig {
    
    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        
        mailSender.setProtocol("smtp");
        mailSender.setHost("smtp-relay.sendinblue.com");
        mailSender.setPort(587);
        mailSender.setUsername("bgraczyk.mail@gmail.com");
        mailSender.setPassword("ZTWqbHKIjm5Avx2X");
        
        return mailSender;
    }
}
