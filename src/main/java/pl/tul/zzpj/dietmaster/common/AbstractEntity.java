package pl.tul.zzpj.dietmaster.common;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public abstract class AbstractEntity {

    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
}
