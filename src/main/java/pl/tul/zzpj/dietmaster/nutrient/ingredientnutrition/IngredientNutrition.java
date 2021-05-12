package pl.tul.zzpj.dietmaster.nutrient.ingredientnutrition;

import lombok.*;
import pl.tul.zzpj.dietmaster.common.AbstractEntity;
import pl.tul.zzpj.dietmaster.ingredient.Ingredient;
import pl.tul.zzpj.dietmaster.nutrient.Nutrient;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "ingredient_nutrition", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"ingredient", "nutrient"}, name = "ingredient_nutrition_akey")
})
@NoArgsConstructor
@RequiredArgsConstructor
public class IngredientNutrition extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY/*, generator = "ingredient_nut_seq_generator"*/)
    //@SequenceGenerator(name = "ingredient_nut_seq_generator", sequenceName = "ingredient_nut_seq", allocationSize = 1)
    //@Basic(optional = false)
    @Column(name = "id"/*, nullable = false*/, updatable = false)
    private Long id;

    @Getter
    @NonNull
    @ManyToOne
    @JoinColumn(name = "ingredient", foreignKey = @ForeignKey(name = "ingredient_nut_ing_fkey"))
    private Ingredient ingredient;

    @Getter
    @NonNull
    @ManyToOne
    @JoinColumn(name = "nutrient", foreignKey = @ForeignKey(name = "ingredient_nut_nut_fkey"))
    private Nutrient nutrient;

    @Getter
    @Setter
    @Basic(optional = false)
    @Column(name = "per100")
    @DecimalMin("0.01")
    @Digits(integer = 8, fraction = 2)
    private BigDecimal per100;

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IngredientNutrition)) return false;
        if (!super.equals(o)) return false;
        IngredientNutrition that = (IngredientNutrition) o;
        return ingredient.equals(that.ingredient) && nutrient.equals(that.nutrient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), ingredient, nutrient);
    }
}
