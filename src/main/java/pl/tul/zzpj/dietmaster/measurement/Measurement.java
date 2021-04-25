package pl.tul.zzpj.dietmaster.measurement;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;

import lombok.Getter;
import lombok.Setter;
import pl.tul.zzpj.dietmaster.account.Account;
import pl.tul.zzpj.dietmaster.common.AbstractEntity;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name="measurement", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"client", "date"}, name = "measurement_client_date_akey")
})
public class Measurement extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "measurement_seq_generator")
    @SequenceGenerator(name = "measurement_seq_generator", sequenceName = "measurement_seq", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Getter
    @ManyToOne
    @JoinColumn(name = "client", foreignKey = @ForeignKey(name = "measurement_client_fkey"))
    private Account client;

    @Getter
    @Setter
    @Basic(optional = false)
    @Column(name = "date")
    private Date date;

    @Getter
    @Setter
    @Basic(optional = false)
    @Column(name = "weight")
    @DecimalMin(value = "20.00")
    @Digits(integer = 3, fraction = 2)
    private BigDecimal weight;

    @Getter
    @Setter
    @Basic(optional = false)
    @Column(name = "calories")
    @DecimalMin(value = "0.00")
    @DecimalMax(value = "16000.00")
    @Digits(integer = 5, fraction = 2)
    private BigDecimal calories;

    @Getter
    @Setter
    @Basic(optional = false)
    @Column(name = "held_diet")
    private boolean heldDiet;

    
    @Override
    public Long getId() {
        return id;
    }
}
