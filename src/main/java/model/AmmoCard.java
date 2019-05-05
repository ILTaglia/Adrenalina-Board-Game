package model;

import exceptions.*;

import java.util.ArrayList;

public abstract class AmmoCard extends Card {

    int type;   //type convention: AmmoTile 0 AmmoPowTile 1

    //boolean per evitare che la stessa carta rifornimento sia usata due volte nello stesso turno dallo stesso giocatore
    //Solo alla fine del turno si elimina la carta e la si sostituisce
    protected ArrayList<Ammo> refillAmmo;

    //collectCard throws Exception if player try to collect card already collected in his precedent action
    public void collectCard(Player player) throws CardAlreadyCollectedException{
        if(used){
            throw new CardAlreadyCollectedException();
            //Exception will be handled by controller
        }
        for (Ammo ammo: refillAmmo) {
            try {
                player.addAmmo(ammo);
            }
            catch (MoreThanTreeAmmosException e){
                //Nothing to do, just try with next ammo
            }
        }
        used =true;
    }

    public int getType(){
        return this.type;
    }
    @Override
    public String toString(){
        return refillAmmo.stream().map(ammo->ammo.toString()).reduce("",(a, b)->a + b);
    }
}
