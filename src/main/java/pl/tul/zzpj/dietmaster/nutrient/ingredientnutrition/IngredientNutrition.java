package pl.tul.zzpj.dietmaster.nutrient.ingredientnutrition;

import lombok.Getter;
import lombok.Setter;
import pl.tul.zzpj.dietmaster.common.AbstractEntity;
import pl.tul.zzpj.dietmaster.ingredient.Ingredient;
import pl.tul.zzpj.dietmaster.nutrient.Nutrient;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@Entity
@Table(name = "ingredient_nutrition", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"ingredient", "nutrient"}, name = "ingredient_nutrition_akey")
})
public class IngredientNutrition extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ingredient_nut_seq_generator")
    @SequenceGenerator(name = "ingredient_nut_seq_generator", sequenceName = "ingredient_nut_seq", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Getter
    @ManyToOne
    @JoinColumn(name = "ingredient", foreignKey = @ForeignKey(name = "ingredient_nut_ing_fkey"))
    private Ingredient ingredient;

    @Getter
    @ManyToOne
    @JoinColumn(name = "nutrient", foreignKey = @ForeignKey(name = "ingredient_nut_nut_fkey"))
    private Nutrient nutrient;

    @Getter
    @Setter
    @Basic(optional = false)
    @Column(name = "per_100")
    @DecimalMin("0.01")
    @Digits(integer = 3, fraction = 2)
    private BigDecimal per_100;

    @Override
    public Long getId() {
        return null;
    }
}
