package pl.tul.zzpj.dietmaster.ingredient.mealingredient;

import lombok.Getter;
import lombok.Setter;
import pl.tul.zzpj.dietmaster.common.AbstractEntity;
import pl.tul.zzpj.dietmaster.ingredient.Ingredient;
import pl.tul.zzpj.dietmaster.meal.Meal;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Entity
@Table(name = "meal_ingredient", uniqueConstraints = {})
public class MealIngredient extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meal_ingredient_seq_generator")
    @SequenceGenerator(name = "meal_ingredient_seq_generator", sequenceName = "meal_ingredient_seq", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Getter
    @ManyToOne
    @JoinColumn(name = "meal", foreignKey = @ForeignKey(name = "meal_ingredient_meal_fkey"))
    private Meal meal;

    @Getter
    @ManyToOne
    @JoinColumn(name = "ingredient", foreignKey = @ForeignKey(name = "meal_ingredient_ingredient_fkey"))
    private Ingredient ingredient;

    @Getter
    @Setter
    @Min(1)
    @Basic(optional = false)
    @Column(name = "count")
    private Short count;

    @Getter
    @Setter
    @Min(1)
    @Basic(optional = false)
    @Column(name = "grams")
    private Short grams;

    @Getter
    @Setter
    @DecimalMin(value = "0.01")
    @Digits(integer = 4, fraction = 2)
    @Basic(optional = false)
    @Column(name = "cost")
    private BigDecimal cost;

    @Override
    public Long getId() {
        return null;
    }
}
