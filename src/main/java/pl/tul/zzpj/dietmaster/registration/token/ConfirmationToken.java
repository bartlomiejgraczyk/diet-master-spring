package pl.tul.zzpj.dietmaster.registration.token;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.tul.zzpj.dietmaster.account.Account;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ConfirmationToken {

    @SequenceGenerator(
            name = "confirmation_token_sequence",
            sequenceName = "confirmation_token_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "confirmation_token_sequence"
    )
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime creationDateTime;

    @Column(nullable = false)
    private LocalDateTime expirationDateTime;

    private LocalDateTime confirmationDateTime;

    @ManyToOne
    @JoinColumn(nullable = false, name = "account_id")
    private Account account;

    public ConfirmationToken(String token,
                             LocalDateTime creationDateTime,
                             LocalDateTime expirationDateTime,
                             Account account) {
        this.token = token;
        this.creationDateTime = creationDateTime;
        this.expirationDateTime = expirationDateTime;
        this.account = account;
    }
}
