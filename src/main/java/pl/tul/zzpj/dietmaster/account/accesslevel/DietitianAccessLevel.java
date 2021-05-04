package pl.tul.zzpj.dietmaster.account.accesslevel;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("level.dietitian")
public class DietitianAccessLevel extends AccessLevel implements Serializable {

    public DietitianAccessLevel() {
        super(Level.DIETITIAN.getName());
    }
}