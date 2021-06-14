package pl.tul.zzpj.dietmaster.model.entities;

import lombok.*;
import pl.tul.zzpj.dietmaster.common.AbstractEntity;
import pl.tul.zzpj.dietmaster.model.entities.enums.categories.NutrientCategory;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "nutrient", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name"}, name = "nutrient_name_akey")
})
@NoArgsConstructor
@RequiredArgsConstructor
public class Nutrient extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Getter
    @Setter
    @NonNull
    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    @Getter
    @Setter
    @NonNull
    @Basic(optional = false)
    @Column(name = "description")
    private String description;

    @Getter
    @Setter
    @NonNull
    @Basic(optional = false)
    @Column(name = "category")
    @Convert(converter = NutrientCategory.Converter.class)
    private NutrientCategory category;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Nutrient)) return false;
        if (!super.equals(o)) return false;
        var nutrient = (Nutrient) o;
        return name.equals(nutrient.name) && category == nutrient.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, category);
    }
}
