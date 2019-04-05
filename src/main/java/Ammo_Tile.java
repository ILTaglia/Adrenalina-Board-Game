import java.util.ArrayList;

public class Ammo_Tile extends Ammo_Card {

    private ArrayList<Ammo> Refill_Ammo;

    public Ammo_Tile(int color1, int color2, int color3){
        Refill_Ammo=new ArrayList<Ammo>();

        Refill_Ammo.add(new Ammo(color1));
        Refill_Ammo.add(new Ammo(color2));
        Refill_Ammo.add(new Ammo(color3));

        Used=false;
    }

    @Override
    public ArrayList<Ammo> Get_Card(){
        //TODO
    }
    /*
    @Override
    public Ammo get(int i){
        return Refill_Ammo.get(i);
    }
    */
}
