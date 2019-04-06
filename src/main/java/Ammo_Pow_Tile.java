import exceptions.*;

import java.util.ArrayList;

public class Ammo_Pow_Tile extends Ammo_Card {


    public Ammo_Pow_Tile(int color1, int color2){
        Refill_Ammo=new ArrayList<Ammo>();

        Refill_Ammo.add(new Ammo(color1));
        Refill_Ammo.add(new Ammo(color2));

        Used=false;
    }

    @Override
    public void Collect_Card(Player player) throws CardAlreadyCollectedException {
        super.Collect_Card(player);
        //TODO capire come implementare la carta da pescare (Eventi ??)
    }


}
