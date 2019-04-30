package Controller;

import Model.*;
import exceptions.CardAlreadyCollectedException;
import exceptions.InvalidDirectionException;
import exceptions.MaxNumberPlayerException;
import exceptions.MaxNumberofCardsException;

abstract public class Grab extends Action {
        public void Movement_Before_Grab(Match m, Player player, int n, String d1, String d2, String d3){
            //n is the number of passes the player wants to do before grabbing
            //you can grab even by moving up to 2 squares before doing the action if you have at least 3 damages
            //TODO IMPORTANTE: controllare validità azioni !
            //TODO I need to check the number of damages of the player to allow moving before grabbing
            if(player.gettotaldamage()>3 && n<=2) {
                Run r = new Run();
                if (!d1.equals("Zero")) {
                    try {
                        r.movement(m, player, d1);
                    } catch (InvalidDirectionException e) {
                    }
                }
                if (!d2.equals("Zero")) {
                    try {
                        r.movement(m, player, d2);
                    } catch (InvalidDirectionException e) {
                    }
                }
                if (!d3.equals("Zero")) {
                    try {
                        r.movement(m, player, d2);
                    } catch (InvalidDirectionException e) {
                    }
                }
            }
        }

        /*Bisognerà fare la collect card, lanciando eccezione se il player ha già troppe armi o potenziamenti e quindi facendo richiesta
        * di rimozione di una carta. Poi add della carta e aggiornamento mazzi*/

    }


