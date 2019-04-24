package Controller;

import Model.Player;
import Model.Weapon;
import exceptions.InvalidDirectionException;

public class Shoot extends Action {
    public Shoot(Player player, Weapon weapon, int n, String d1, String d2) {
        //n is the number of passes a player wants to do before shooting
        //TODO Player player, Weapon weapon can be parameters?
        //you can shoot even by moving up to 1 squares before doing the action if you have at least 6 damages

        if(player.gettotaldamage()>5 && n<=1){
            Run r = new Run();
            if(!d1.equals("Zero")){
                try {
                    r.movement(player, d1);
                } catch (InvalidDirectionException e) {}
            }
            if(!d2.equals("Zero")){
                try {
                    r.movement(player, d2);
                } catch (InvalidDirectionException e) {}
            }
        }
        player.set_action();
        /*Si dovrà raccogliere un'arma o un potenziamento a seconda della cella in cui ci si trova*/
        /*l'arma dovrà avere un metodo che ritorna il numero di danni che può infliggere, e il numero
         * di marchi, scegli il bersaglio e spara*/
        //TODO
        /*Necessaries steps:
        * calling the effect of the weapon, setting the fla that the weapon is used
        * recharge weapon if the player wants
        * in the part of calling the effect I mean that we must set damages and marks of other players*/
    }
}
