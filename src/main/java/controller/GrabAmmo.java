package controller;

import model.Match;
import model.NormalCell;
import model.Player;
import exceptions.CardAlreadyCollectedException;
import exceptions.MaxNumberofCardsException;

import java.util.List;

public class GrabAmmo extends Grab{
    public GrabAmmo(){
        //Empty Constructor, controller has to use the method grabAmmo
    }
    public void grabAmmo(Match match, Player player) throws MaxNumberofCardsException, CardAlreadyCollectedException {
        int cardType=player.getCel().inmap(match.getDashboard(),player.getCel().getX(),player.getCel().getY()).getType();
        match.assignAmmo(player);
        if(1==cardType){        //cardType==1 if Card is AmmoPowTile
            match.assignPowCard(player);
        }
        player.setAction();
    }

    public boolean isValid(Match match, Player player, List<String> destination){
        if(!this.isValidMovement(match, player, destination) && !destination.isEmpty()) return false;
        else return true;
    }
}
