package pl.tul.zzpj.dietmaster.account.accesslevel;

import java.io.Serializable;

public class DietitianAccessLevel extends AccessLevel implements Serializable {

    public DietitianAccessLevel() {
        super(Level.DIETICIAN.getName());
    }
}