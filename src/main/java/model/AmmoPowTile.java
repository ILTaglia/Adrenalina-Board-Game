package model;

import java.util.ArrayList;

public class AmmoPowTile extends AmmoCard {
    /**
     * type is an int that identifies the type of card. Conventions:
     * 1 = AmmoPowTile
     * There are just two Ammos, as the third element to be collected is the PowCard.
     */


    public AmmoPowTile(int color1, int color2){
        refillAmmo =new ArrayList<>();
        refillAmmo.add(new Ammo(color1));
        refillAmmo.add(new Ammo(color2));
        type=1;
        used =false;
    }

    /**
     *
     * @return the Ammos contained in the refill as a String
     */
    @Override
    public String toString(){
        return super.toString()+"Pow";
    }

}
