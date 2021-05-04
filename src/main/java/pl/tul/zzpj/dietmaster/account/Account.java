package pl.tul.zzpj.dietmaster.account;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.tul.zzpj.dietmaster.account.accesslevel.AccessLevel;
import pl.tul.zzpj.dietmaster.account.key.Key;
import pl.tul.zzpj.dietmaster.common.AbstractEntity;
import pl.tul.zzpj.dietmaster.common.Default;
import pl.tul.zzpj.dietmaster.diet.Diet;
import pl.tul.zzpj.dietmaster.diet.dietset.DietSet;
import pl.tul.zzpj.dietmaster.measurement.Measurement;

@Entity
@Table(name = "account", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"}, name = "account_email_akey")})
@NamedQuery(name = "Account.findByEmail", query = "SELECT a FROM Account a WHERE a.email = :email")
@NamedQuery(name = "Account.findByPassword", query = "SELECT a FROM Account a WHERE a.password = :password")
@NamedQuery(name = "Account.findByFirstName", query = "SELECT a FROM Account a WHERE a.firstName = :firstName")
@NamedQuery(name = "Account.findByLastName", query = "SELECT a FROM Account a WHERE a.lastName = :lastName")
@NoArgsConstructor
public class Account extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq_generator")
    @SequenceGenerator(name = "account_seq_generator", sequenceName = "account_seq", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Getter
    @Setter
    @Email
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
    @Setter
    @Basic(optional = false)
    @Column(name = "enabled", columnDefinition = "boolean default false")
    private boolean enabled;

    @Getter
    @Setter
    @Basic(optional = false)
    @Column(name = "locked", columnDefinition = "boolean default false")
    private boolean locked;

    @Getter
    @OneToMany(
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH}, 
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "account", foreignKey = @ForeignKey(name = "access_account_fkey"))
    private final Set<AccessLevel> accessLevels = new HashSet<>();

    @Getter
    @OneToMany(mappedBy = "author")
    private final Set<Diet> diets = new HashSet<>();

    @Getter
    @OneToMany(mappedBy = "owner")
    private final Set<DietSet> dietSets = new HashSet<>();

    //TODO: Consider removing two below (Role Dependant)

    @Getter
    @OneToMany(mappedBy = "client")
    private final Set<Measurement> measurements = new HashSet<>();

    @Getter
    @OneToMany(mappedBy = "dietitian")
    @Size(max = 100)
    private final Set<Key> keys = new HashSet<>();

    @Default
    public Account(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        if (!super.equals(o)) return false;
        var account = (Account) o;
        return email.equals(account.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), email);
    }
}
