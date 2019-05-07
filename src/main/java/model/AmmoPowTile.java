package model;

import java.util.ArrayList;

public class AmmoPowTile extends AmmoCard {


    public AmmoPowTile(int color1, int color2){
        refillAmmo =new ArrayList<>();
        refillAmmo.add(new Ammo(color1));
        refillAmmo.add(new Ammo(color2));
        type=1;
        used =false;
    }

    @Override
    public String toString(){
        return super.toString()+"Pow";
    }

}
