package pl.tul.zzpj.dietmaster.account.accesslevel;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("level.admin")
public class AdminAccessLevel extends AccessLevel implements Serializable {

    public AdminAccessLevel() {
        super(Level.ADMINISTRATOR.getName());
    }
}
