package pl.tul.zzpj.dietmaster.account;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;
import pl.tul.zzpj.dietmaster.account.accesslevel.AccessLevel;
import pl.tul.zzpj.dietmaster.common.AbstractEntity;

@Entity
@Table(name = "account", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email"})
})
@NamedQuery(name = "Account.findByEmail", query = "SELECT a FROM Account a WHERE a.email = :email")
@NamedQuery(name = "Account.findByPassword", query = "SELECT a FROM Account a WHERE a.password = :password")
@NamedQuery(name = "Account.findByFirstName", query = "SELECT a FROM Account a WHERE a.firstName = :firstName")
@NamedQuery(name = "Account.findByLastName", query = "SELECT a FROM Account a WHERE a.lastName = :lastName")
public class Account extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq_generator")
    @SequenceGenerator(name = "account_seq_generator", sequenceName = "account_seq", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Getter
    @Basic(optional = false)
    @Column(name = "email", nullable = false, updatable = false)
    private String email;

    @Getter
    @Setter
    @Basic(optional = false)
    @Column(name = "password")
    private String password;

    @Getter
    @Setter
    @Basic(optional = false)
    @Column(name = "first_name")
    private String firstName;

    @Getter
    @Setter
    @Basic(optional = false)
    @Column(name = "last_name")
    private String lastName;

    @Getter
    @OneToMany
    @JoinColumn(name = "account_fk")
    private final Set<AccessLevel> accessLevels = new HashSet<>();

    @Override
    public Long getId() {
        return id;
    }
}
