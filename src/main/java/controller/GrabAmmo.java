package controller;

import exceptions.NotYourTurnException;
import model.Match;
import exceptions.CardAlreadyCollectedException;
import exceptions.MaxNumberofCardsException;
import model.NormalCell;

public class GrabAmmo extends Grab{
    public GrabAmmo(){
        //Empty Constructor, controller has to use the method grabAmmo
    }

    /**
     *
     * @param match is the match
     * @param userID is the ID of the player
     * @throws MaxNumberofCardsException if the player already has three PowCards
     * @throws CardAlreadyCollectedException if the card has already been collected in the previous action by the same player in the same turn
     * @throws NotYourTurnException if the player is not active
     */
    public void grabAmmo(Match match, String userID) throws MaxNumberofCardsException, CardAlreadyCollectedException, NotYourTurnException {
        if(!super.isValid(match,userID)){
            throw new NotYourTurnException();
        }
        int cardType=match.getActivePlayer().getCel().inMap(match.getDashboard(),match.getActivePlayer().getCel().getX(),match.getActivePlayer().getCel().getY()).getType();
        match.assignAmmo(match.getActivePlayer());
        if(1==cardType){        //cardType==1 if Card is AmmoPowTile
            match.assignPowCard(match.getActivePlayer());
        }
        match.getActivePlayer().setAction();
    }

    /**
     *
     * @param match is the match
     * @param userID is the ID of the player
     * @return
     */
    public boolean isValid(Match match, String userID){
        return true;
    }
}
