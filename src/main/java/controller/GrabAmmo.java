package controller;

import exceptions.NotYourTurnException;
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
    public void grabAmmo(Match match, String userID) throws MaxNumberofCardsException, CardAlreadyCollectedException, NotYourTurnException {
        if(!super.isValid(match,userID)){
            throw new NotYourTurnException();
        }
        int cardType=match.getActivePlayer().getCel().inmap(match.getDashboard(),match.getActivePlayer().getCel().getX(),match.getActivePlayer().getCel().getY()).getType();
        match.assignAmmo(match.getActivePlayer());
        if(1==cardType){        //cardType==1 if Card is AmmoPowTile
            match.assignPowCard(match.getActivePlayer());
        }
        match.getActivePlayer().setAction();
    }

    public boolean isValid(Match match, String userID){
        //TODO
        return true;
    }
}
