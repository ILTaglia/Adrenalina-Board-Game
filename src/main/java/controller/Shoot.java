package controller;

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

    public boolean movementBeforeShoot(Match m, Player player, List<String> destination){
        if(this.isValidMovement(m, player, destination)) {
            Run r = new Run();
            try{
                r.getMovement(m, player, destination);
                return true;
            } catch(InvalidDirectionException e){}
        }
        return false;
    }

    public boolean isValidMovement(Match match, Player player, List<String> destination) {
        if (!(player.gettotaldamage()>5 && destination.size()<2)) return false;
        else {
            Run r = new Run();
            int x = player.getCel().getX();
            int y = player.getCel().getY();
            if(r.isValid(match, player, x, y, destination)) return true;
            else return false;
        }
    }

    //To choose which weapon use, the index of weapon in the array of the player weapons will be used
    public boolean isValid(Match match, Player player, Player attackedplayer, List<String> destination, int indexWeapon) {
        if(indexWeapon<0 || indexWeapon>2) return false;
        if(!this.isValidMovement(match, player, destination) && !destination.isEmpty()) return false;
        else {
            //If the player choose a weapon but it doesn't own it
            Weapon w = player.getWeaponByIndex(indexWeapon);
            if (!player.weaponIspresent(w)) return false;
            else return true;
        }
    }
}
