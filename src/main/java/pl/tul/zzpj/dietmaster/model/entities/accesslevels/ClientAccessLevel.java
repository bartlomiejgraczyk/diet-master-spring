package pl.tul.zzpj.dietmaster.model.entities.accesslevels;

import pl.tul.zzpj.dietmaster.model.entities.enums.acceslevels.UserAccessLevelTier;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("level.client")
public class ClientAccessLevel extends AccessLevel implements Serializable {

    public ClientAccessLevel() {
        super(UserAccessLevelTier.CLIENT.getName());
    }
}
