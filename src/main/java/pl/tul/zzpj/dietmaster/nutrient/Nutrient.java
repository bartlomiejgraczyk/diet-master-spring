package pl.tul.zzpj.dietmaster.nutrient;

import lombok.Getter;
import lombok.Setter;
import pl.tul.zzpj.dietmaster.common.AbstractEntity;

import javax.persistence.*;

@Entity
@Table(name = "nutrient", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"}, name = "nutrient_name_akey")})
public class Nutrient extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nutrient_seq_generator")
    @SequenceGenerator(name = "nutrient_seq_generator", sequenceName = "nutrient_seq", allocationSize = 1)
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
    private NutrientCategory category;

    @Override
    public Long getId() {
        return null;
    }
}
