import exceptions.*;

import java.util.ArrayList;

public class Ammo_Tile extends Ammo_Card {


    public Ammo_Tile(int color1, int color2, int color3) throws InvalidColorException {
        Refill_Ammo=new ArrayList<Ammo>();
        Refill_Ammo.add(new Ammo(color1));
        Refill_Ammo.add(new Ammo(color2));
        Refill_Ammo.add(new Ammo(color3));

        Used=false;
    }



}
