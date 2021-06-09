package pl.tul.zzpj.dietmaster.model.entities;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.tul.zzpj.dietmaster.model.entities.Account;

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
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "token", nullable = false, updatable = false)
    private String token;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime creationDateTime;

    @Column(name = "expired_at", nullable = false)
    private LocalDateTime expirationDateTime;

    @Column(name = "confirmed_at")
    private LocalDateTime confirmationDateTime;

    @ManyToOne
    @JoinColumn(
            nullable = false, 
            name = "account_id", 
            foreignKey = @ForeignKey(name = "conf_token_account_fkey")
    )
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
