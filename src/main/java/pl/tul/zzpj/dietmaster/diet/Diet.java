package pl.tul.zzpj.dietmaster.diet;

import javax.persistence.*;
import javax.validation.constraints.Size;

import lombok.*;
import pl.tul.zzpj.dietmaster.account.Account;
import pl.tul.zzpj.dietmaster.common.AbstractEntity;
import pl.tul.zzpj.dietmaster.diet.dietset.DietSet;
import pl.tul.zzpj.dietmaster.meal.Meal;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "diet", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"author", "name"}, name = "diet_author_name_akey")
})
@NoArgsConstructor
@RequiredArgsConstructor
public class Diet extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "diet_seq_generator")
    @SequenceGenerator(name = "diet_seq_generator", sequenceName = "diet_seq", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Getter
    @NonNull
    @ManyToOne
    @JoinColumn(name = "author", foreignKey = @ForeignKey(name = "diet_account_fkey"))
    private Account author;

    @Getter
    @Setter
    @Basic(optional = false)
    @Column(name = "type")
    private DietType type;

    @Getter
    @Setter
    @NonNull
    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    @Getter
    @Setter
    @Basic(optional = false)
    @Column(name = "description")
    private String description;

    @Getter
    @Setter
    @Basic(optional = false)
    @Column(name = "access_level")
    private DietAccessLevel accessLevel;

    @Getter
    @OneToMany(mappedBy = "diet")
    private final Set<DietSet> dietSets = new HashSet<>();

    @Getter
    @Size(min = 1, max = 10)
    @OneToMany(mappedBy = "containingDiet")
    private final Set<Meal> meals = new HashSet<>();

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Diet)) return false;
        if (!super.equals(o)) return false;
        var diet = (Diet) o;
        return author.equals(diet.author) && name.equals(diet.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), author, name);
    }
}
