package model;

import exceptions.*;

import java.util.ArrayList;

public class AmmoTile extends AmmoCard {


    public AmmoTile(int color1, int color2, int color3) throws InvalidColorException {
        refillAmmo =new ArrayList<>();
        refillAmmo.add(new Ammo(color1));
        refillAmmo.add(new Ammo(color2));
        refillAmmo.add(new Ammo(color3));
        type=0;
        used =false;
    }



}
