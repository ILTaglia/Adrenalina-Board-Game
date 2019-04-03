import java.util.ArrayList;

public class Ammo_Tile extends Ammo_Card {

    private ArrayList<Ammo> Refill_Ammo;

    public Ammo_Tile(int color1, int color2, int color3){
        Refill_Ammo=new ArrayList<Ammo>();

        Refill_Ammo.add(new Ammo(color1));
        Refill_Ammo.add(new Ammo(color2));
        Refill_Ammo.add(new Ammo(color3));

        Avaible=true;
    }

    @Override
    public void Collect_Card(){


    }

}
