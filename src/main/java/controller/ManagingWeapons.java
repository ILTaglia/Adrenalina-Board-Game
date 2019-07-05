package controller;

import exceptions.*;
import model.Player;
import model.PowCard;
import model.Weapon;
import model.Ammo;
import model.Match;

import java.util.List;

import static utils.Print.printOut;

public class ManagingWeapons {
    /**
     * Class to manage weapons and pows
     */
    private Match match;

    /**
     *
     * @param match is the match
     */
    public ManagingWeapons(Match match){
        this.match=match;
    }

    /**
     *
     * @param player is the active player
     * @param indexWeapon is the weapon to recharge
     * @throws WeaponAlreadyLoadedException
     */
    public void recharge(Player player, int indexWeapon)throws WeaponAlreadyLoadedException{
        Weapon weapon = player.getWeaponByIndex(indexWeapon);
        weapon.recharge();
    }
    /**
     *
     * @param player is the active player
     * @param indexWeapon is the index of weapon to discard
     */
    public void discardWeapon(Player player, int indexWeapon){
        Weapon weapon = player.getWeaponByIndex(indexWeapon);
        try {
            player.removeWeapon(weapon);
            match.discardWeaponCard(weapon);
        }
        catch(ZeroCardsOwnedException | NotOwnedCardException e){
            printOut("ZeroCardsOwnedException or NotOwnedCardException");
        }
    }

    /**
     *
     * @param player is the active player
     * @param indexPowCard is the index of PowCard to discard
     */
    public void discardPowCard(Player player, int indexPowCard){
        PowCard powcard = player.getPowByIndex(indexPowCard);
        try{
            player.removePow(powcard);
            match.discardPowCard(powcard);
        }
        catch(ZeroCardsOwnedException | NotOwnedCardException e){
        }

    }

    /**
     *
     * @param player is the active player
     * @param weaponToGrabCost is the price of the weapon to grab
     * @return true if the player has enough ammos to grab, 0 otherwise
     */
    public boolean areEnoughAmmoToGrabWeapon(Player player, List<Integer> weaponToGrabCost){
        for(int i=0; i<weaponToGrabCost.size(); i++){
            if(weaponToGrabCost.get(i)>player.getAmmo(i)) return false;
        }
        return true;
    }

    /**
     *
     * @param player is the active player
     * @param extraCost is teh price to unlock extra functions
     * @return true if the player has enough ammos to unlock extra functions, 0 otherwise
     */
    public boolean unlockExtraFunction(Player player, List<Integer> extraCost)
    {
        if(!areEnoughAmmoToGrabWeapon(player,extraCost))
        {
            return false;
        }
        for(int i=0;i<extraCost.size();i++)
        {
            try
            {
                match.removeAmmo(extraCost.get(i),new Ammo(i));
            }
            catch(NotEnoughAmmosException e)
            {

            }

        }
        return true;
    }

    //Method to convert a powcard in case you don't have enough ammos

    /**
     *
     * @param player is the active player
     * @param weaponToGrabCost is the price of the weapon to grab
     * @param indexPowCard is the index of the PowCard to use to pay
     * @throws NotEnoughAmmosException if even using the PowCard the player doesn't have enough ammos to buy
     */
    public void convertPowToGrab(Player player, List<Integer> weaponToGrabCost, int indexPowCard)throws NotEnoughAmmosException {
        int color = player.getPowByIndex(indexPowCard).getColor();      //color of the powcard
        /*The control that using the PowCard the number of Ammos is enough to but the WeaponCard is done before converting
        * the Pow in Ammo. This operation is done because the money of the player has to be enough to buy the weapon, so the addition
        * of a single Ammo is done only in the case the action of buying becomes valid with one more ammo of the color of the PowCard.
        * The number of remaining Ammos is, this way, the same number of Ammos the player would have had if he have had enough Ammos
        * since the beginning.*/

        //Verifico se con l'aggiunta di un Ammo riesco a raccogliere l'arma
        try {
            player.addAmmo(new Ammo(color));
        } catch (MoreThanTreeAmmosException e) {    //Se il Player aveva già 3 ammo di quel colore sicuramente l'aggiunta non porta a modifiche
            throw new NotEnoughAmmosException();    //quindi inutile fare ulteriori verifiche: restituisco l'eccezione
        }
        //Se l'aggiunta dell'Ammo è andata a buon fine verifico se il Player ne ha sufficienti per la raccolta dell'arma
        if(areEnoughAmmoToGrabWeapon(player,weaponToGrabCost)){
            discardPowCard(player, indexPowCard);
            //torno al metodo principale che potrà quindi effettuare effettivamente la GrabAmmo
        }
        else{
            player.removeAmmo(1,new Ammo(color));       //ripristino situazione precedente e NON rimuovo carta potenziamento
            throw new NotEnoughAmmosException();           //lancio eccezione così il Controller non procederà ulteriormente
        }
    }
}
