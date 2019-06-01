package controller;

import exceptions.*;
import model.Player;
import model.PowCard;
import model.SpawnPointCell;
import model.Weapon;
import model.Ammo;
import model.Match;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class ManagingWeapons {

    public void Recharge(Player player, int indexweapon)throws WeaponAlreadyLoadedException{
        Weapon weapon = player.getWeaponByIndex(indexweapon);
        weapon.recharge();
    }

    public void Remove(Player player, int indexweapon){
        Weapon weapon = player.getWeaponByIndex(indexweapon);
        try {
            player.removeWeapon(weapon);
        }
        catch(ZeroCardsOwnedException e){return;}
        catch(NotOwnedCardException e){return;}
    }

    public void RemovePow(Player player, int indexpow){
        PowCard powcard = player.getPowByIndex(indexpow);
        try{
            player.removePow(powcard);
        }
        catch(ZeroCardsOwnedException e){return;}
        catch(NotOwnedCardException e){return;}
    }

    public boolean EnoughMoneytoBuy(Player player, int weapontograb){
        List<Integer> weaponcost = player.getWeaponByIndex(weapontograb).returnPrice();
        for(int i=0; i<weaponcost.size(); i++){
            if(weaponcost.get(i)>player.getAmmo(i)) return false;
        }
        return true;
    }

    //Method to convert a powcard in case you don't have enough ammos
    public void ConvertPowToBuy(Match match, Player player, int weapontograb, int indexofpow)throws NotEnoughAmmosException {
        //color of the powcard
        int color = player.getPowByIndex(indexofpow).getColor();
        SpawnPointCell cell;
        Ammo ammo;
        ManagingWeapons removing = new ManagingWeapons();
        cell = (SpawnPointCell) player.getCel().inmap(match.getDashboard(), player.getCel().getX(), player.getCel().getY());

        List<Integer> weaponcost = cell.getSpawnPointCellWeapons().get(weapontograb).returnPrice();
        /*The control that using the PowCard the number of Ammos is enough to but the WeaponCard is done before converting
        * the Pow in Ammo. This operation is done because the money of the player has to be enough to buy the weapon, so the addition
        * of a single Ammo is done only in the case the action of buying becomes valid with one more ammo of the color of the PowCard.
        * The number of remaining Ammos is, this way, the same number of Ammos the player would have had if he have had enough Ammos
        * since the beginning.*/
        if(color==0){
            if(player.getAmmo(0)+1==weaponcost.get(0)){
                removing.Remove(player, indexofpow);
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
            if(player.getAmmo(1)+1==weaponcost.get(1)){
                removing.Remove(player, indexofpow);
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
            if(player.getAmmo(2)+1==weaponcost.get(2)){
                removing.Remove(player, indexofpow);
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
    }
}
