package pl.tul.zzpj.dietmaster.account.accesslevel;

import java.io.Serializable;

public class AdminAccessLevel extends AccessLevel implements Serializable {

    public AdminAccessLevel() {
        super(Level.ADMINISTRATOR.getName());
    }
}
