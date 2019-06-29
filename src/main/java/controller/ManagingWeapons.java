package controller;

import exceptions.*;
import model.Player;
import model.PowCard;
import model.Weapon;
import model.Ammo;
import model.Match;

import java.util.List;

public class ManagingWeapons {

    private Match match;

    public ManagingWeapons(Match match){
        this.match=match;
    }

    public void recharge(Player player, int indexWeapon)throws WeaponAlreadyLoadedException{
        Weapon weapon = player.getWeaponByIndex(indexWeapon);
        weapon.recharge();
    }

    public void discardWeapon(Player player, int indexWeapon){
        Weapon weapon = player.getWeaponByIndex(indexWeapon);
        try {
            player.removeWeapon(weapon);
            match.discardWeaponCard(weapon);
        }
        catch(ZeroCardsOwnedException | NotOwnedCardException e){
            //TODO: ragionare su eventuale modifica alle eccezioni!
            //Potrei rilanciare eccezioni verso l'alto e mandare un messaggio all'utente con un errore.
        }
    }

    public void discardPowCard(Player player, int indexPowCard){
        PowCard powcard = player.getPowByIndex(indexPowCard);
        try{
            player.removePow(powcard);
            match.discardPowCard(powcard);
        }
        catch(ZeroCardsOwnedException | NotOwnedCardException e){
            //TODO: ragionare su eventuale modifica alle eccezioni!
            //Potrei rilanciare eccezioni verso l'alto e mandare un messaggio all'utente con un errore.
        }

    }

    public boolean areEnoughAmmoToGrabWeapon(Player player, List<Integer> weaponToGrabCost){
        for(int i=0; i<weaponToGrabCost.size(); i++){
            if(weaponToGrabCost.get(i)>player.getAmmo(i)) return false;
        }
        return true;
    }

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



    /*
        if(color==0){
            if(player.getAmmo(0)+1==weaponCost.get(0)){
                removing.Remove(player, indexPowCard);
                ammo = new Ammo(0);
                try{
                    player.addAmmo(ammo);
                } catch(MoreThanTreeAmmosException e){return;} //Exception never verified because of the control of validity
                try{
                    GrabWeapon grabWeapon = new GrabWeapon();
                    grabWeapon.grabWeapon(match, match.getActivePlayer(), weapontograb);
                    return;
                } catch(MaxNumberofCardsException ex){return;}
            }
        }
        //TODO finire il codice, devi aggiungere un ammo per rendere lecito l'acquisto, altrimenti la grab non ti compra l'arma
        if(color==1){
            if(player.getAmmo(1)+1==weaponCost.get(1)){
                removing.Remove(player, indexPowCard);
                ammo = new Ammo(1);
                try{
                    player.addAmmo(ammo);
                } catch(MoreThanTreeAmmosException e){return;} //Exception never verified because of the control of validity
                try{

                    GrabWeapon grabWeapon = new GrabWeapon();
                    grabWeapon.grabWeapon(match, match.getActivePlayer(), weapontograb);
                    return;
                } catch(MaxNumberofCardsException ex){return;}
            }
        }
        if(color==2){
            if(player.getAmmo(2)+1==weaponCost.get(2)){
                removing.Remove(player, indexPowCard);
                ammo = new Ammo(2);
                try{
                    player.addAmmo(ammo);
                } catch(MoreThanTreeAmmosException e){return;} //Exception never verified because of the control of validity
                try{
                    GrabWeapon grabWeapon = new GrabWeapon();
                    grabWeapon.grabWeapon(match, match.getActivePlayer(), weapontograb);
                    return;
                } catch(MaxNumberofCardsException ex){return;}
            }
        }
        throw new NotEnoughAmmosException();
        */

}
