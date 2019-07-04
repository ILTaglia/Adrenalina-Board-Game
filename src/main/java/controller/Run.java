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
    /**
     * Scheme of dashboard for coordinateds
     *          *    0   1   2   3
     *          * 0
     *          * 1
     *          * 2
     * lines go from 0 to 2, columns are from 0 to 3. Pay attention that to go north, the coordinate of line is decreased,
     * while to go down the coordinate of line is increased.
     *
     * Conventions for directions:
     * 0 = N (North)
     * 1 = E (East - Right)
     * 2 = S (South)
     * 3 = W (West - Left)
     */
    public Run() {

    }
    /**
     * Method to increase the number of action of the active player. It must be done because grabbing and shooting actions
     * have in their mainly methods the increase of variable Action of the active player. If setAction was in movement method,
     * as shooting and grabbing can call the method for players with a certain number of damages, action would be increased twice.
     * @param match is the match
     */
    public void registerMovementAction(Match match){
        match.getActivePlayer().setAction();
    }

    /**
     * method for complete atomicMovement, to be decomposed in atomic movements
     * @param match is the match
     * @param userID is the ID of the active player
     * @param destination is the ArrayList containing the atomic directions the player wants to go to
     * @param isMovementBeforeGrab is a boolean to say if the action of running is performed before grabbing
     * @param isMovementBeforeShoot is a boolean to say if the action of running is performed before shooting
     * @throws InvalidDirectionException if the direction is invalid
     * @throws NotYourTurnException if the player is inactive
     * @throws ActionNotAllowedException if the player has not the required data to perform the action
     */
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

    /**
     * Method to be moved by an other player, when this player is attacked
     * @param match is the match
     * @param userID is the userID
     * @param destination is the ArrayList containing the atomic movements
     */
    public void movementNotInMyTurn(Match match, String userID, List<String> destination) throws InvalidDirectionException{
        if(isValidNotInMyTurn(match, destination)){
            for (String direction : destination) {
                this.atomicMovementNotInMyTurn(match, direction, userID);
            }
        }
        else{
            throw new InvalidDirectionException();
        }
    }
    /**
     * Method to check validity of a movement when player is attacked
     * @param match is the match
     * @param destination is the ArrayList containing the atomic directions the player wants to go to
     * @return true if the movement is valid, 0 otherwise
     */
    public boolean isValidNotInMyTurn(Match match, List<String> destination) {
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
    /**
     *
     * @param match is the match
     * @param destination is the ArrayList containing the atomic directions the player wants to go to
     * @return true if the movement is valid, 0 otherwise
     */
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
    /**
     *
     * @param map is the dashboard
     * @param player is the active player
     * @param x is the line of the coordinate of the player
     * @param y is the column of the coordinate of the player
     * @param direction is the atomic character that express the direction the player wants to go to
     * @return true if the movement is valid, 0 otherwise
     */
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
    /**
     * Method for atomic movements
     * @param match is teh match
     * @param direction is the atomic character that express the direction the player wants to go to
     * @throws InvalidDirectionException if direction is invalid
     */
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

    /**
     * Method for atomic movements of a player victim of an attack
     * @param match is the match
     * @param direction is the atomic character that express the direction the player wants to go to
     * @throws InvalidDirectionException if direction is invalid
     */
    private void atomicMovementNotInMyTurn(Match match, String direction, String userID) throws InvalidDirectionException {
        int d = this.getDirection(direction);
        int x;
        int y;
        Player playervictim = match.getPlayerByID(userID);
        x = playervictim.getCel().getX();
        y = playervictim.getCel().getY();

        //player wants to go to the north
        if (d == 0) {
            x--;
            match.setPlayerCel(playervictim,x,y);

        }
        //player wants to go the the east
        else if (d == 1) {
            y++;
            match.setPlayerCel(playervictim,x,y);
        }
        //player wants to go the south
        else if (d == 2) {
            x++;
            match.setPlayerCel(playervictim,x,y);
        }
        //player wants to go to the west
        else if (d == 3) {
            y--;
            match.setPlayerCel(playervictim,x,y);
        }
    }
    /**
     *
     * @param player is the given player
     * @param cell is the given cell to reset position
     */
    public void resetPosition(Player player, Coordinate cell){
        player.setCel(cell.getX(), cell.getY());
    }
}
