package pl.tul.zzpj.dietmaster.ingredient;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import pl.tul.zzpj.dietmaster.common.AbstractEntity;
import pl.tul.zzpj.dietmaster.nutrient.ingredientnutrition.IngredientNutrition;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ingredient", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "category"}, name = "ingredient_name_category_akey")
})
public class Ingredient extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ingredient_seq_generator")
    @SequenceGenerator(name = "ingredient_seq_generator", sequenceName = "ingredient_seq", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Getter
    @Setter
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
    @Column(name = "category")
    private IngredientCategory category;

    @Getter
    @OneToMany(mappedBy = "ingredient")
    private Set<IngredientNutrition> ingredientNutrients = new HashSet<>();

    @Override
    public Long getId() {
        return null;
    }
}
