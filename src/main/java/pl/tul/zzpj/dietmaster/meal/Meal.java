package pl.tul.zzpj.dietmaster.meal;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import pl.tul.zzpj.dietmaster.common.AbstractEntity;
import pl.tul.zzpj.dietmaster.diet.Diet;
import pl.tul.zzpj.dietmaster.ingredient.mealingredient.MealIngredient;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="meal")
public class Meal extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meal_seq_generator")
    @SequenceGenerator(name = "meal_seq_generator", sequenceName = "meal_seq", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Getter
    @ManyToOne
    @JoinColumn(name = "containing_diet", foreignKey = @ForeignKey(name = "meal_diet_fkey"))
    private Diet containingDiet;

    @Getter
    @Setter
    @Column(name = "name")
    private String name;

    @Getter
    @Setter
    @Column(name = "description")
    private String description;

    @Getter
    @Setter
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
}
