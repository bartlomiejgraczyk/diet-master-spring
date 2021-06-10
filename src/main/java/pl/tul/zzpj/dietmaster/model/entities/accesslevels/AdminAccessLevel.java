package pl.tul.zzpj.dietmaster.model.entities.accesslevels;

import pl.tul.zzpj.dietmaster.model.entities.enums.acceslevels.UserAccessLevelTier;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("level.admin")
public class AdminAccessLevel extends AccessLevel implements Serializable {

    public AdminAccessLevel() {
        super(UserAccessLevelTier.ADMINISTRATOR.getName());
    }
}
