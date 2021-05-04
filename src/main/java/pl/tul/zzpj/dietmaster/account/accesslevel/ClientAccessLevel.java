package pl.tul.zzpj.dietmaster.account.accesslevel;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("level.client")
public class ClientAccessLevel extends AccessLevel implements Serializable {

    public ClientAccessLevel() {
        super(Level.CLIENT.getName());
    }
}
