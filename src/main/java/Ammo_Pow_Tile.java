import java.util.ArrayList;

public class Ammo_Pow_Tile extends Ammo_Card {


    public Ammo_Pow_Tile(int color1, int color2){
        Refill_Ammo=new ArrayList<Ammo>();

        Refill_Ammo.add(new Ammo(color1));
        Refill_Ammo.add(new Ammo(color2));

        Used=false;
    }

    @Override
    public ArrayList<Ammo> Get_Card(){
        //TODO
        //Oltre a restituire le munizioni chiama il metodo per pescare una carta
    }


}
