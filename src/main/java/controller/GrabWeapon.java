package controller;

import exceptions.NotYourTurnException;
import model.Match;
import model.Player;
import model.SpawnPointCell;
import exceptions.MaxNumberofCardsException;
import java.util.List;

public class GrabWeapon extends Grab{
    public GrabWeapon(){
        //Empty Constructor, controller has to use the method grabWeapon
    }

    /**
     *
     * @param match is the match
     * @param player is the active player
     * @param indexWeapon is the index of weapon to grab
     * @throws MaxNumberofCardsException if the player already has three weapon cards
     */
    public void grabWeapon(Match match, Player player, int indexWeapon)throws MaxNumberofCardsException{
        match.assignWeaponCard(player,indexWeapon);

        player.setAction();
    }
    /**
     *
     * @param match is the match
     * @param userID is the ID of the player
     * @return false if the player already has three weapon cards
     */
    public boolean isValid(Match match,String userID) {
        if(match.getActivePlayer().getNumberWeapon()==3){
            return false;
        }
        return true;
    }

}
