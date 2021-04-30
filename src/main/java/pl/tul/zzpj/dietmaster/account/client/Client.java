package pl.tul.zzpj.dietmaster.account.client;

import lombok.Getter;
import pl.tul.zzpj.dietmaster.account.Account;
import pl.tul.zzpj.dietmaster.common.AbstractEntity;

import javax.persistence.*;
import javax.validation.Constraint;

@Entity
@Table(name = "client", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"dietitian", "client"}, name = "client_dietitian_akey")
})
public class Client extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_seq_generator")
    @SequenceGenerator(name = "client_seq_generator", sequenceName = "client_seq", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Getter
    @ManyToOne
    @JoinColumn(name = "dietitian", foreignKey = @ForeignKey(name = "client_dietitian_fkey"))
    private Account dietitian;

    @Getter
    @ManyToOne
    @JoinColumn(name = "client", foreignKey = @ForeignKey(name = "client_client_fkey"))
    private Account client;

    @Override
    public Long getId() {
        return null;
    }
}
