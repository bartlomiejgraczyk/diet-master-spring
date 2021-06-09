package pl.tul.zzpj.dietmaster.model.entities.accesslevels;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import pl.tul.zzpj.dietmaster.common.AbstractEntity;

@Entity
@Table(name = "access_level", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"level", "account"}, name = "access_level_akey")
})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "level", discriminatorType = DiscriminatorType.STRING)
public abstract class AccessLevel extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "access_level_generator")
    @SequenceGenerator(name = "access_level_generator", sequenceName = "access_level_seq", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "id", nullable = false, updatable = false)
    protected Long id;

    @Basic(optional = false)
    @Column(name = "level", nullable = false, length = 24, updatable = false, insertable = false)
    protected String level;

    protected AccessLevel() {
    }

    protected AccessLevel(String level) {
        this.level = level;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccessLevel)) return false;
        if (!super.equals(o)) return false;
        var that = (AccessLevel) o;
        return level.equals(that.level);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), level);
    }
}