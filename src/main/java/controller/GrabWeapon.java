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
    public void grabWeapon(Match match, Player player, int indexWeapon)throws MaxNumberofCardsException{
        match.assignWeaponCard(player,indexWeapon);

        player.setAction();
    }

    public boolean isValid(Match match,String userID) {
        if(match.getActivePlayer().getNumberWeapon()==3){
            return false;
        }
        return true;
    }

}
