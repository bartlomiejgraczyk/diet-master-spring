package pl.tul.zzpj.dietmaster.model.entities;

import lombok.*;
import pl.tul.zzpj.dietmaster.common.AbstractEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "diet_set", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"diet", "owner"}, name = "diet_set_akey")
})
@NoArgsConstructor
@RequiredArgsConstructor
public class DietSet extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "diet_set_seq_generator")
    @SequenceGenerator(name = "diet_set_seq_generator", sequenceName = "diet_set_seq", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Getter
    @NonNull
    @ManyToOne
    @JoinColumn(name = "diet", foreignKey = @ForeignKey(name = "diet_set_diet_fkey"))
    private Diet diet;

    @Getter
    @NonNull
    @ManyToOne
    @JoinColumn(name = "owner", foreignKey = @ForeignKey(name = "diet_set_owner_fkey"))
    private Account owner;

    @Getter
    @Setter
    @Basic(optional = false)
    @Column(name = "is_chosen")
    private Boolean isChosen;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DietSet)) return false;
        if (!super.equals(o)) return false;
        var dietSet = (DietSet) o;
        return diet.equals(dietSet.diet) && owner.equals(dietSet.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), diet, owner);
    }
}
