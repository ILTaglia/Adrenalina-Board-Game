import java.util.ArrayList;

public class Ammo_Pow_Tile extends Ammo_Card {

    private ArrayList<Ammo> Refill_Ammo_Pow;

    public Ammo_Pow_Tile(int color1, int color2){
        Refill_Ammo_Pow=new ArrayList<Ammo>();

        Refill_Ammo_Pow.add(new Ammo(color1));
        Refill_Ammo_Pow.add(new Ammo(color2));

        Avaible=true;
    }

    @Override
    public void Collect_Card(){
        //TODO
        //Oltre a restituire le munizioni chiama il metodo per pescare una carta
    }


}
