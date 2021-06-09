package pl.tul.zzpj.dietmaster.model.entities;

import lombok.*;
import pl.tul.zzpj.dietmaster.common.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "meal_ingredient", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"meal", "ingredient"}, name = "meal_ingredient_akey")
})
@NoArgsConstructor
@RequiredArgsConstructor
public class MealIngredient extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meal_ingredient_seq_generator")
    @SequenceGenerator(name = "meal_ingredient_seq_generator", sequenceName = "meal_ingredient_seq", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Getter
    @Setter
    /*@NonNull*/
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "meal", foreignKey = @ForeignKey(name = "meal_ingredient_meal_fkey"))
    private Meal meal;

    @Getter
    @Setter
    @NonNull
    @ManyToOne
    @JoinColumn(name = "ingredient", foreignKey = @ForeignKey(name = "meal_ingredient_ingredient_fkey"))
    private Ingredient ingredient;

    @Getter
    @Setter
    @Basic(optional = false)
    @Column(name = "count")
    @DecimalMin(value = "0.0001")
    @Digits(integer = 3, fraction = 4)
    private BigDecimal count;

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

    public MealIngredient(/*@NonNull*/ Meal meal,
                          @NonNull Ingredient ingredient,
                          BigDecimal count,
                          Short grams,
                          BigDecimal cost){
        this.meal = meal;
        this.ingredient = ingredient;
        this.count = count;
        this.grams = grams;
        this.cost = cost;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }

    public void setGrams(Short grams) {
        this.grams = grams;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MealIngredient)) return false;
        if (!super.equals(o)) return false;
        MealIngredient that = (MealIngredient) o;
        return meal.equals(that.meal) && ingredient.equals(that.ingredient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), meal, ingredient);
    }
}
