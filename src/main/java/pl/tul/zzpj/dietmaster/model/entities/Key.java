package pl.tul.zzpj.dietmaster.model.entities;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import pl.tul.zzpj.dietmaster.common.AbstractEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "key", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"dietitian", "key_string"}, name = "key_dietitian_key_string_akey")
})
@RequiredArgsConstructor
@NoArgsConstructor
public class Key extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "key_seq_generator")
    @SequenceGenerator(name = "key_seq_generator", sequenceName = "key_seq", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Getter
    @NonNull
    @ManyToOne
    @JoinColumn(name = "dietitian", foreignKey = @ForeignKey(name = "key_dietitian_fkey"))
    private Account dietitian;

    @Getter
    @NonNull
    @Basic(optional = false)
    @Column(name = "key_string", length = 15)
    @Length(min = 5, max = 15)
    private String keyString;

    @Getter
    @NonNull
    @Basic(optional = false)
    @Column(name = "one_time")
    private Boolean oneTime;

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Key)) return false;
        if (!super.equals(o)) return false;
        var key = (Key) o;
        return dietitian.equals(key.dietitian) && keyString.equals(key.keyString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), dietitian, keyString);
    }
}
