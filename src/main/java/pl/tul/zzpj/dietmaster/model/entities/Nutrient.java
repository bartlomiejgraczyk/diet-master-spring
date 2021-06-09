package pl.tul.zzpj.dietmaster.model.entities;

import lombok.*;
import pl.tul.zzpj.dietmaster.common.AbstractEntity;
import pl.tul.zzpj.dietmaster.model.entities.enums.categorytype.NutrientCategory;
import pl.tul.zzpj.dietmaster.model.entities.converters.NutrientJpaConverter;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY/*, generator = "nutrient_seq_generator"*/)
    //@SequenceGenerator(name = "nutrient_seq_generator", sequenceName = "nutrient_seq", allocationSize = 1)
    //@Basic(optional = false)
    @Column(name = "id"/*, nullable = false*/, updatable = false)
    private Long id;

    @Getter
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
    @NonNull
    @Basic(optional = false)
    @Column(name = "category")
    @Convert(converter = NutrientJpaConverter.class)
    private NutrientCategory category;

    @Override
    public Long getId() {
        return null;
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
