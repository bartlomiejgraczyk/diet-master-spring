package pl.tul.zzpj.dietmaster.diet.dietset;

import lombok.Getter;
import lombok.Setter;
import pl.tul.zzpj.dietmaster.account.Account;
import pl.tul.zzpj.dietmaster.common.AbstractEntity;
import pl.tul.zzpj.dietmaster.diet.Diet;

import javax.persistence.*;

@Entity
@Table(name = "diet_set", uniqueConstraints = {@UniqueConstraint(columnNames = {"diet", "owner"}, name = "diet_set_akey")})
public class DietSet extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "diet_set_seq_generator")
    @SequenceGenerator(name = "diet_set_seq_generator", sequenceName = "diet_set_seq", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Getter
    @ManyToOne
    @JoinColumn(name = "diet", foreignKey = @ForeignKey(name = "diet_set_diet_fkey"))
    private Diet diet;

    @Getter
    @ManyToOne
    @JoinColumn(name = "owner", foreignKey = @ForeignKey(name = "diet_set_owner_fkey"))
    private Account owner;

    @Getter
    @Setter
    @Basic(optional = false)
    @Column(name = "is_chosen")
    private boolean isChosen;

    @Override
    public Long getId() {
        return null;
    }
}
