package pl.tul.zzpj.dietmaster.model.entities.accesslevels;

import pl.tul.zzpj.dietmaster.model.entities.enums.acceslevels.UserAccessLevelTier;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("level.dietitian")
public class DietitianAccessLevel extends AccessLevel implements Serializable {

    public DietitianAccessLevel() {
        super(UserAccessLevelTier.DIETITIAN.getName());
    }
}