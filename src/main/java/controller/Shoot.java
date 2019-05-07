package controller;

import model.Player;
import model.Weapon;
import model.Match;
import exceptions.InvalidDirectionException;

import java.util.List;

public class Shoot extends Action {
    public Shoot(Match m, Player player, Weapon weapon, List<String> destination) {
        //n is the number of passes a player wants to do before shooting
        //TODO Player player, Weapon weapon can be parameters?
        //you can shoot even by moving up to 1 squares before doing the action if you have at least 6 damages

        if(isValid(player, destination)){
            Run r = new Run();
            try{
                r.getMovement(m, player, destination);
            } catch(InvalidDirectionException e){}

        }
        player.setAction();
        /*Si dovrà raccogliere un'arma o un potenziamento a seconda della cella in cui ci si trova*/
        /*l'arma dovrà avere un metodo che ritorna il numero di danni che può infliggere, e il numero
         * di marchi, scegli il bersaglio e spara*/
        //TODO
        /*Necessaries steps:
        * calling the effect of the weapon, setting the fla that the weapon is used
        * recharge weapon if the player wants
        * in the part of calling the effect I mean that we must set damages and marks of other players*/
    }
    public boolean isValid(Player player, List<String> destination) {
        if (player.gettotaldamage()>5 && destination.size()<=1) return true;
        else return false;
    }
}
