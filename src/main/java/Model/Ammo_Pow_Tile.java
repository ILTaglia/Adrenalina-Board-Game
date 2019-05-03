package Model;

import exceptions.*;

import java.util.ArrayList;

public class Ammo_Pow_Tile extends Ammo_Card {


    public Ammo_Pow_Tile(int color1, int color2){
        Refill_Ammo=new ArrayList<>();
        Refill_Ammo.add(new Ammo(color1));
        Refill_Ammo.add(new Ammo(color2));
        type=0;
        Used=false;
    }

    @Override
    public String toString(){
        return super.toString()+"Pow";
    }

}
