package controller;

import model.*;
import java.util.List;

import exceptions.InvalidDirectionException;


public abstract class Grab extends Action {
        public void movementBeforeGrab(Match m, Player player, List<String> destination){
            //n is the number of passes the player wants to do before grabbing
            //you can grab even by moving up to 2 squares before doing the action if you have at least 3 damages
            if(this.isValidMovement(m, player, destination)) {
                Run r = new Run();
                try{
                r.getMovement(m, player, destination);
                } catch(InvalidDirectionException e){}
            }
        }

    public boolean isValidMovement(Match match, Player player, List<String> destination) {
        if (!(player.gettotaldamage()>3 && destination.size()<2)) return false;
        else {
            Run r = new Run();
            int x = player.getCel().getX();
            int y = player.getCel().getY();
            if(r.isValid(match, player, x, y, destination)) return true;
            else return false;
        }
    }

        /*Bisognerà fare la collect card, lanciando eccezione se il player ha già troppe armi o potenziamenti e quindi facendo richiesta
        * di rimozione di una carta. Poi add della carta e aggiornamento mazzi*/

}