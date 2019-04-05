import exceptions.*;

import java.util.ArrayList;

public class Ammo_Tile extends Ammo_Card {


    public Ammo_Tile(int color1, int color2, int color3){
        Refill_Ammo=new ArrayList<Ammo>();

        Refill_Ammo.add(new Ammo(color1));
        Refill_Ammo.add(new Ammo(color2));
        Refill_Ammo.add(new Ammo(color3));

        Used=false;
    }

    public void Collect_Card(Player player) throws CardAlreadyCollectedException{
        if(Used=true){
            throw new CardAlreadyCollectedException();
            //Exception will be handled by Controller
        }
        for (Ammo ammo:Refill_Ammo) {
            try {
                player.add_ammo(ammo);
            }
            catch (MoreThanTreeAmmosException e){
                //TODO: pensare a catch
                //Nothing to do, just try with next ammo
            }
        }
        Used=true;
    }

}
