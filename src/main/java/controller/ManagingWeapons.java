package controller;

import exceptions.NotOwnedCardException;
import exceptions.WeaponAlreadyLoadedException;
import exceptions.ZeroCardsOwnedException;
import model.Player;
import model.Weapon;

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
}
