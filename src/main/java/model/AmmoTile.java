package model;

import exceptions.*;

import java.io.Serializable;
import java.util.ArrayList;

public class AmmoTile extends AmmoCard {
    /**
     * type is an int that identifies the type of card. Conventions:
     * 0 = AmmoTile
     * There are three Ammos.
     */

    public AmmoTile(int color1, int color2, int color3) throws InvalidColorException {
        refillAmmo =new ArrayList<>();
        refillAmmo.add(new Ammo(color1));
        refillAmmo.add(new Ammo(color2));
        refillAmmo.add(new Ammo(color3));
        type=0;
        used =false;
    }



}
