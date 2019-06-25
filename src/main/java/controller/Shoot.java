package controller;

import exceptions.NotYourTurnException;
import model.Player;
import model.Weapon;
import model.Match;
import exceptions.InvalidDirectionException;

import java.util.List;

public class Shoot extends Action {
    public Shoot() {
        //n is the number of passes a player wants to do before shooting
        //TODO Player player, Weapon weapon can be parameters?
        //you can shoot even by moving up to 1 squares before doing the action if you have at least 6 damages

        /*Si dovrà raccogliere un'arma o un potenziamento a seconda della cella in cui ci si trova*/
        /*l'arma dovrà avere un metodo che ritorna il numero di danni che può infliggere, e il numero
         * di marchi, scegli il bersaglio e spara*/
        //TODO
        /*Necessaries steps:
        * calling the effect of the weapon, setting the fla that the weapon is used
        * recharge weapon if the player wants
        * in the part of calling the effect I mean that we must set damages and marks of other players*/
    }

    public void shoot(Match match, Player player, Player attackedplayer, int indexWeapon){
        //TODO
        player.setAction();
    }

    //To choose which weapon use, the index of weapon in the array of the player weapons will be used
    public boolean isValid(Match match,String userID, Player attackedplayer, List<String> destination, int indexWeapon) {
        if(indexWeapon<0 || indexWeapon>2) return false;
        else {
            //If the player choose a weapon but it doesn't own it
            Weapon w = match.getActivePlayer().getWeaponByIndex(indexWeapon);
            if (!match.getActivePlayer().weaponIspresent(w)) return false;
            else return true;
        }
    }
}
