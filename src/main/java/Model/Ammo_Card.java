package Model;

import exceptions.*;

import java.util.ArrayList;

public abstract class Ammo_Card extends Card {

    //boolean per evitare che la stessa carta rifornimento sia usata due volte nello stesso turno dallo stesso giocatore
    //Solo alla fine del turno si elimina la carta e la si sostituisce
    protected ArrayList<Ammo> Refill_Ammo;

    //Collect_Card throws Exception if player try to collect card already collected in his precedent action
    public void Collect_Card(Player player) throws CardAlreadyCollectedException{
        if(Used){
            throw new CardAlreadyCollectedException();
            //Exception will be handled by Controller
        }
        for (Ammo ammo:Refill_Ammo) {
            try {
                player.add_ammo(ammo);
            }
            catch (MoreThanTreeAmmosException e){
                //Nothing to do, just try with next ammo
            }
        }
        Used=true;
    }
    @Override
    public String toString(){
        return Refill_Ammo.stream().map(ammo->ammo.toString()).reduce("",(a,b)->a + b);
    }



}
