package pl.tul.zzpj.dietmaster.measurement;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import pl.tul.zzpj.dietmaster.common.AbstractEntity;

public class Measurement extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "measurement_seq_generator")
    @SequenceGenerator(name = "measurement_seq_generator", sequenceName = "measurement_seq", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
    
    @Override
    public Long getId() {
        return id;
    }
}
