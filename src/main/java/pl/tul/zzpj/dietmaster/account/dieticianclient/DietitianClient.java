package pl.tul.zzpj.dietmaster.account.dieticianclient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.tul.zzpj.dietmaster.account.Account;
import pl.tul.zzpj.dietmaster.common.AbstractEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "dietitian_client", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"dietitian", "client"}, name = "dietitian_client_akey")
})
@NoArgsConstructor
@RequiredArgsConstructor
@ValidDietitianClient
public class DietitianClient extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "diet_client_seq_generator")
    @SequenceGenerator(name = "diet_client_seq_generator", sequenceName = "diet_client_seq", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Getter
    @NonNull
    @ManyToOne
    @JoinColumn(name = "dietitian", foreignKey = @ForeignKey(name = "diet_client_dietitian_fkey"))
    private Account dietitian;

    @Getter
    @NonNull
    @ManyToOne
    @JoinColumn(name = "client", foreignKey = @ForeignKey(name = "diet_client_client_fkey"))
    private Account client;

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DietitianClient)) return false;
        if (!super.equals(o)) return false;
        DietitianClient that = (DietitianClient) o;
        return dietitian.equals(that.dietitian) && client.equals(that.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), dietitian, client);
    }
}


