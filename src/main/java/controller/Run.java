package controller;

import exceptions.ActionNotAllowedException;
import exceptions.NotYourTurnException;
import model.Dashboard;
import model.Player;
import model.Match;
import model.Coordinate;

import exceptions.InvalidDirectionException;

import java.util.List;

public class Run extends Action {
    public Run() {
        //parameter is just the player, because the action of running is atomic, just one step

        /*ci si sposterà a seconda dei vincoli della mappa e a seconda dei vincoli della plancia, non ho ancora messo le pareti*/
        /* Schema dashboard per le coordinate
         *    0   1   2   3
         * 0
         * 1
         * 2
         * le righe vanno da 0 a 2, le colonne da 0 a 3, ATTENZIONE all'unica convenzione non intuitiva. Se si va a nord la coordinata viene decrementata
         * mentre se si va a sud la coordinata aumenta (x vanno da 0 a 3, y vanno da 0 a 2)
         */
    }
    //Method to increase the number of action of the active player. It must be done because grabbing and shooting actions have in their mainly
    //methods the increase of variable Action of the active player. If setAction was in movement method, as shooting and grabbing can call the
    //method for players with a certain number of damages, action would be increased twice.
    public void registerMovementAction(Match match){
        match.getActivePlayer().setAction();
    }

    //method for complete atomicMovement, to be decomposed in atomic movements
    public void movement(Match match, String userID, List<String> destination,boolean isMovementBeforeGrab,boolean isMovementBeforeShoot) throws InvalidDirectionException, NotYourTurnException, ActionNotAllowedException {
        if(!super.isValid(match,userID)){
            throw new NotYourTurnException();
        }
        if(isMovementBeforeGrab){
            if(match.getActivePlayer().getTotalDamage()<3&&destination.size()!=1){
                throw new ActionNotAllowedException("Primo caso (< 3,==1) //TODO Scrivere nella Run l'eccezione");
            }
            else if(match.getActivePlayer().getTotalDamage()>3&&destination.size()>=3){
                throw new ActionNotAllowedException("Secondo caso (< 3,==1) //TODO Scrivere nella Run l'eccezione");
            }
        }
        if(isMovementBeforeShoot&&(match.getActivePlayer().getTotalDamage()<5 || destination.size()>=2)){
                throw new ActionNotAllowedException("Hai "+match.getActivePlayer().getTotalDamage()+" danni. Se non hai almeno 5 danni non puoi usare questa azione.");
        }
        if(isValid(match, destination)) {
            for (String direction : destination) {
                this.atomicMovement(match, direction);
            }
        }
        else{
            throw new InvalidDirectionException();
        }
    }

    //method to check the global validity of the action of atomicMovement
    public boolean isValid(Match match, List<String> destination) {
        if(destination.size() > 3) return false;
        Dashboard map = match.getDashboard();
        int actualXCoordinate = match.getActivePlayer().getCel().getX();
        int actualYCoordinate = match.getActivePlayer().getCel().getY();
        int atomicDirection;
        for (String direction : destination) {
            try {
                atomicDirection = this.getDirection(direction);
            } catch (InvalidDirectionException e) {
                return false;
            }
            if (!this.atomicValidity(map, match.getActivePlayer(), actualXCoordinate, actualYCoordinate, atomicDirection)) {
                return false;
            }
            //Aggiorno la posizione attuale per verificare le direzioni successive
            if (atomicDirection == 0) {
                actualXCoordinate--;
            }
            if (atomicDirection == 1) {
                actualYCoordinate++;
            }
            if (atomicDirection == 2) {
                actualXCoordinate++;
            }
            if (atomicDirection == 3) {
                actualYCoordinate--;
            }
        }
        return true;
    }

    //method to check the atomic validity of each atomicMovement
    private boolean atomicValidity(Dashboard map, Player player, int x, int y, int direction) {

        //player wants to go to the north
        if (direction == 0 && x > 0) {
            int actualcolor = player.getCel().inMap(map, x, y).getColor();
            int nextcolor = player.getCel().inMap(map, x - 1, y).getColor();

            int port = player.getCel().inMap(map, x, y).portIsPresent(0);
            if (actualcolor == nextcolor || port == 1) {
                return true;
            } else return false;
        }
        //player wants to go the the east
        else if (direction == 1 && y < 3) {
            int actualcolor = player.getCel().inMap(map, x, y).getColor();
            int nextcolor = player.getCel().inMap(map, x, y + 1).getColor();

            int port = player.getCel().inMap(map, x, y).portIsPresent(1);
            if (actualcolor == nextcolor || port == 1) {
                return true;
            } else return false;
        }
        //player wants to go the south
        else if (direction == 2 && x < 2) {
            int actualcolor = player.getCel().inMap(map, x, y).getColor();
            int nextcolor = player.getCel().inMap(map, x + 1, y).getColor();

            int port = player.getCel().inMap(map, x, y).portIsPresent(2);
            if (actualcolor == nextcolor || port == 1) {
                return true;
            } else return false;
        }
        //player wants to go to the west
        else if (direction == 3 && y > 0) {
            int actualcolor = player.getCel().inMap(map, x, y).getColor();
            int nextcolor = player.getCel().inMap(map, x, y - 1).getColor();

            int port = player.getCel().inMap(map, x, y).portIsPresent(3);
            if (actualcolor == nextcolor || port == 1) {
                return true;
            } else return false;
        } else return false;
    }

    //method for atomic movements
    private void atomicMovement(Match match, String direction) throws InvalidDirectionException {
        int d = this.getDirection(direction);
        int x;
        int y;
        x = match.getActivePlayer().getCel().getX();
        y = match.getActivePlayer().getCel().getY();

        //player wants to go to the north
        if (d == 0) {
            x--;
            match.setPlayerCel(match.getActivePlayer(),x,y);

        }
        //player wants to go the the east
        else if (d == 1) {
            y++;
            match.setPlayerCel(match.getActivePlayer(),x,y);
        }
        //player wants to go the south
        else if (d == 2) {
            x++;
            match.setPlayerCel(match.getActivePlayer(),x,y);
        }
        //player wants to go to the west
        else if (d == 3) {
            y--;
            match.setPlayerCel(match.getActivePlayer(),x,y);
        }
    }

    public void resetPosition(Player player, Coordinate cell){
        player.setCel(cell.getX(), cell.getY());
    }
}
