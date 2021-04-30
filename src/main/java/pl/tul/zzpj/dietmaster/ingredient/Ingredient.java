package pl.tul.zzpj.dietmaster.ingredient;

import javax.persistence.*;

import lombok.*;
import pl.tul.zzpj.dietmaster.common.AbstractEntity;
import pl.tul.zzpj.dietmaster.nutrient.ingredientnutrition.IngredientNutrition;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "ingredient", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "category"}, name = "ingredient_name_category_akey")
})
@NoArgsConstructor
@RequiredArgsConstructor
public class Ingredient extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ingredient_seq_generator")
    @SequenceGenerator(name = "ingredient_seq_generator", sequenceName = "ingredient_seq", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

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
    @NonNull
    @Basic(optional = false)
    @Column(name = "category")
    private IngredientCategory category;

    @Getter
    @OneToMany(mappedBy = "ingredient")
    private final Set<IngredientNutrition> ingredientNutrients = new HashSet<>();

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ingredient)) return false;
        if (!super.equals(o)) return false;
        Ingredient that = (Ingredient) o;
        return name.equals(that.name) && category == that.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, category);
    }
}
