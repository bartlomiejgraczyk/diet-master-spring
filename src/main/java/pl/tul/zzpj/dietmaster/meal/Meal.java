package pl.tul.zzpj.dietmaster.meal;

import javax.persistence.*;

import lombok.*;
import pl.tul.zzpj.dietmaster.common.AbstractEntity;
import pl.tul.zzpj.dietmaster.diet.Diet;
import pl.tul.zzpj.dietmaster.ingredient.mealingredient.MealIngredient;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "meal", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"containing_diet", "name", "type"}, name = "meal_diet_name_type_akey")
})
@NoArgsConstructor
@RequiredArgsConstructor
public class Meal extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meal_seq_generator")
    @SequenceGenerator(name = "meal_seq_generator", sequenceName = "meal_seq", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Getter
    @NonNull
    @ManyToOne
    @JoinColumn(name = "containing_diet", foreignKey = @ForeignKey(name = "meal_diet_fkey"))
    private Diet containingDiet;

    @Getter
    @Setter
    @NonNull
    @Column(name = "name")
    private String name;

    @Getter
    @Setter
    @Column(name = "description")
    private String description;

    @Getter
    @Setter
    @NonNull
    @Basic(optional = false)
    @Column(name = "type")
    private MealType type;

    @Getter
    @OneToMany(mappedBy = "meal")
    private final Set<MealIngredient> mealIngredients = new HashSet<>();

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Meal)) return false;
        if (!super.equals(o)) return false;
        var meal = (Meal) o;
        return containingDiet.equals(meal.containingDiet) && name.equals(meal.name) && type == meal.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), containingDiet, name, type);
    }
}
