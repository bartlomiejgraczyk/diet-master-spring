package pl.tul.zzpj.dietmaster.model.entities;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;

import lombok.*;
import pl.tul.zzpj.dietmaster.common.AbstractEntity;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name="measurement", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"client", "date"}, name = "measurement_client_date_akey")
})
@NoArgsConstructor
@RequiredArgsConstructor
public class Measurement extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "measurement_seq_generator")
    @SequenceGenerator(name = "measurement_seq_generator", sequenceName = "measurement_seq", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Getter
    @NonNull
    @ManyToOne
    @JoinColumn(name = "client", foreignKey = @ForeignKey(name = "measurement_client_fkey"))
    private Account client;

    @Getter
    @Setter
    @NonNull
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
    private Boolean heldDiet;

    
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Measurement)) return false;
        if (!super.equals(o)) return false;
        Measurement that = (Measurement) o;
        return client.equals(that.client) && date.equals(that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), client, date);
    }
}
