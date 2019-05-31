package controller;

import exceptions.*;
import model.Player;
import model.PowCard;
import model.SpawnPointCell;
import model.Weapon;
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
        cell = (SpawnPointCell) player.getCel().inmap(match.getDashboard(), player.getCel().getX(), player.getCel().getY());

        List<Integer> weaponcost = cell.getSpawnPointCellWeapons().get(weapontograb).returnPrice();
        if(color==0){
            if(player.getAmmo(0)+1==weaponcost.get(0)){
                ManagingWeapons removing = new ManagingWeapons();
                removing.Remove(player, indexofpow);
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
                ManagingWeapons removing = new ManagingWeapons();
                removing.Remove(player, indexofpow);
                try{

                    GrabWeapon grabWeapon = new GrabWeapon();
                    grabWeapon.grabWeapon(match, match.getActivePlayer(), weapontograb);
                    return;
                } catch(MaxNumberofCardsException ex){return;}
            }
        }
        if(color==2){
            if(player.getAmmo(2)+1==weaponcost.get(2)){
                ManagingWeapons removing = new ManagingWeapons();
                removing.Remove(player, indexofpow);
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
