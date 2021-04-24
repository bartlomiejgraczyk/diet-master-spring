package pl.tul.zzpj.dietmaster.diet;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import pl.tul.zzpj.dietmaster.common.AbstractEntity;

@Table(name="diet")
public class Diet extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "diet_seq_generator")
    @SequenceGenerator(name = "diet_seq_generator", sequenceName = "diet_seq", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
    
    @Override
    public Long getId() {
        return null;
    }
}
