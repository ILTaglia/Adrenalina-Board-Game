package controller;

import model.Match;

import exceptions.InvalidDirectionException;

public abstract class Action {
    /**
     *
     * @param direction is the direction in which to go
     * @return the corresponding int
     * Conventions are:
     * 0 = North
     * 1 = East (Right)
     * 2 = South
     * 3 = West (Left)
     * @throws InvalidDirectionException is the direction is not allowed (for example moving in diagonal way
     */
    public int getDirection(String direction) throws InvalidDirectionException{
        int d;
        if(direction.equals("N")) d=0;
        else if(direction.equals("E")) d=1;
        else if(direction.equals("S")) d=2;
        else if(direction.equals("W")) d=3;
        else throw new InvalidDirectionException();
        return d;
    }
    /**
     * Method to check validity of actions
     * @param match is the match
     * @param userID is the ID of the player
     * @return true is the player with the given ID is active, false otherwise
     */
    public boolean isValid(Match match,String userID) {
        if(!match.getActivePlayer().getID().equals(userID)) {
            return false;
        }
        else if(match.getActivePlayer().getAction()>2){
            return false;
        }
        else{
            return true;
        }
    }
}
