package pl.tul.zzpj.dietmaster.account.accesslevel;

import java.io.Serializable;

public class ClientAccessLevel extends AccessLevel implements Serializable {

    public ClientAccessLevel() {
        super(Level.CLIENT.getName());
    }
}
