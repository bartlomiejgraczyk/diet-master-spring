package pl.tul.zzpj.dietmaster.account.key;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import pl.tul.zzpj.dietmaster.account.Account;
import pl.tul.zzpj.dietmaster.common.AbstractEntity;

import javax.persistence.*;

@Entity
@Table(name = "key", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"dietitian", "key_string"}, name = "key_dietitian_key_string_akey")
})
public class Key extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "key_seq_generator")
    @SequenceGenerator(name = "key_seq_generator", sequenceName = "key_seq", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Getter
    @ManyToOne
    @JoinColumn(name = "dietitian", foreignKey = @ForeignKey(name = "key_dietitian_fkey"))
    private Account dietitian;

    @Getter
    @Setter
    @Basic(optional = false)
    @Column(name = "key_string", length = 15)
    @Length(min = 5, max = 15)
    private String keyString;

    @Getter
    @Setter
    @Basic(optional = false)
    @Column(name = "one_time")
    private boolean oneTime;

    @Override
    public Long getId() {
        return null;
    }
}
