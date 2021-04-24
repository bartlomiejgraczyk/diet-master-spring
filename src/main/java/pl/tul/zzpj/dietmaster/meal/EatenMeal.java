package pl.tul.zzpj.dietmaster.meal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import pl.tul.zzpj.dietmaster.common.AbstractEntity;

public class EatenMeal extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "eaten_meal_seq_generator")
    @SequenceGenerator(name = "eaten_meal_seq_generator", sequenceName = "eaten_meal_seq", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Override
    public Long getId() {
        return null;
    }
}
