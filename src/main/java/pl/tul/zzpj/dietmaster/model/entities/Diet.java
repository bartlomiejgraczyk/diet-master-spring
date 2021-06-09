package pl.tul.zzpj.dietmaster.model.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;

import lombok.*;
import pl.tul.zzpj.dietmaster.common.AbstractEntity;
import pl.tul.zzpj.dietmaster.common.Default;

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
    private int type;

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
    private int accessLevel;

    @Getter
    @OneToMany(mappedBy = "diet")
    private final Set<DietSet> dietSets = new HashSet<>();

    @Getter
    @Size(min = 1, max = 10)
    @OneToMany(mappedBy = "containingDiet", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private final Set<Meal> meals = new HashSet<>();

    @Override
    public Long getId() {
        return id;
    }

    @Default
    public Diet(@NonNull Account author,
                int type,
                @NonNull String name,
                String description,
                int accessLevel,
                Set<Meal> meals){
        this.author = author;
        this.type = type;
        this.name = name;
        this.description = description;
        this.accessLevel = accessLevel;
        this.meals.addAll(meals);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Diet)) return false;
        if (!super.equals(o)) return false;
        var diet = (Diet) o;
        return author.equals(diet.author) && name.equals(diet.name);
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), author, name);
    }
}
