package model;

import exceptions.*;

import java.util.ArrayList;

public abstract class AmmoCard extends Card {

    /**
     * type is an int that identifies the type of card. Conventions:
     * 0 = AmmoTile
     * 1 = AmmoPowTile
     */
    int type;
    /**
     * The boolean value USED is aimed to avoid that the use of the same card in a single turn from the same player.
     * Just at the end of the turn the card is eliminated and substituted.
     *
     * refillAmmo is an ArrayList that contains all the Ammos that an AmmoCard allows to collect
     */
    protected ArrayList<Ammo> refillAmmo;
    /**
     *
     * @param player is the active player in the turn
     * @throws CardAlreadyCollectedException if the card has already been collected in the player previous action
     * @throws MoreThanTreeAmmosException if the player already has three Ammos, that is the maximum allowed value of Ammos for each color
     */
    public void collectCard(Player player) throws CardAlreadyCollectedException, MoreThanTreeAmmosException {
        if(used){
            throw new CardAlreadyCollectedException();
            //Exception will be handled by controller
        }
        for (Ammo ammo: refillAmmo) {

            player.addAmmo(ammo);

        }
        used =true;
    }

    /**
     *
     * @return the type of the card, the can be
     * 0 = AmmoTile
     * 1 = AmmoPowTile
     */
    public int getType(){
        return this.type;
    }

    /**
     *
     * @return the Ammos contained in the refill as a String
     */
    @Override
    public String toString(){
        return refillAmmo.stream().map(Ammo::toString).reduce("",(a, b)->a + b);
    }
}
