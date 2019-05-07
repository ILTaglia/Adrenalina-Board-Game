package controller;

import model.Match;

import exceptions.InvalidDirectionException;

public abstract class Action {
    private String[] cardinalPoints = {"N", "E", "S" ,"W"};
    /* Conventions are:
    * 0 = North
    * 1 = East
    * 2 = South
    * 3 = West
    * -1 is zero if the player wants to shoot or grab with less than the number of movements it has; for example in shooting
    * you can move of two passes if you have more than five damages, but the player can choose to move of just one (north for example),
    * then it will call the method of the class with (..., "N", "Zero")*/
    //TODO
    public Action(){
    }

    public int getDirection(String direction) throws InvalidDirectionException{
        int d;
        if(direction.equals("N")) d=0;
        else if(direction.equals("E")) d=1;
        else if(direction.equals("S")) d=2;
        else if(direction.equals("W")) d=3;
        else throw new InvalidDirectionException();
        return d;
    }

    public boolean isValid(Match match){
        int index=-1;
        for(int i=0; i<match.getPlayersSize(); i++){
            if(match.getPlayerByIndex(i).getActive()){
                index=i;
                for(int j=1; j<match.getPlayersSize(); j++) {
                    if (match.getPlayerByIndex(i).getActive() == match.getPlayerByIndex(j).getActive()) {
                        return false;
                    }
                }
                int action1 = match.getPlayerByIndex(i).getAction();
                if(action1>2 || action1<0) return false;
            }
        }
        for(int i=0; i<match.getPlayersSize(); i++){
            if(match.getPlayerByIndex(i)!=match.getPlayerByIndex(index)){
                if(match.getPlayerByIndex(i).getAction()!=0) return false;
            }
        }
       return true;
    }
}
