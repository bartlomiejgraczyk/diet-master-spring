package pl.tul.zzpj.dietmaster.model.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;

import lombok.*;
import pl.tul.zzpj.dietmaster.common.AbstractEntity;
import pl.tul.zzpj.dietmaster.common.Default;
import pl.tul.zzpj.dietmaster.model.entities.enums.acceslevels.DietAccessLevelTier;
import pl.tul.zzpj.dietmaster.model.entities.enums.types.DietType;

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
    @Convert(converter = DietType.Converter.class)
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
    @Convert(converter = DietAccessLevelTier.Converter.class)
    private DietAccessLevelTier accessLevel;

    @Getter
    @OneToMany(mappedBy = "diet")
    private final Set<DietSet> dietSets = new HashSet<>();

    @Getter
    @Size(max = 10)
    @OneToMany(mappedBy = "containingDiet", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Meal> meals = new HashSet<>();

    @Override
    public Long getId() {
        return id;
    }

    public Diet(Long id, @NonNull Account author, DietType type, @NonNull String name, String description, DietAccessLevelTier accessLevel, Set<Meal> meals) {
        this.id = id;
        this.author = author;
        this.type = type;
        this.name = name;
        this.description = description;
        this.accessLevel = accessLevel;
        this.meals = meals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Diet)) return false;
        if (!super.equals(o)) return false;
        var diet = (Diet) o;
        return author.equals(diet.author) && name.equals(diet.name);
    }

    public void setType(DietType type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAccessLevel(DietAccessLevelTier accessLevel) {
        this.accessLevel = accessLevel;
    }

    public void setMeals(Set<Meal> meals) {
        this.meals = meals;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), author, name);
    }
}
