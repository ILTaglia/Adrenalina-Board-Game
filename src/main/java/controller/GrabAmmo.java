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
    public void grabAmmo(Match match, Player player) throws MaxNumberofCardsException{
        NormalCell cell;
        int cardType;
        cell = (NormalCell) player.getCel().inmap(match.getDashboard(), player.getCel().getX(), player.getCel().getY());
        cardType=cell.getCardType();
        try{
            cell.Collect_Card(player);
        } catch (CardAlreadyCollectedException e){
            return; //TODO avvisare utente
        }


        if(1==cardType){        //cardType==1 if Card is AmmoPowTile
            match.assignPowCard(player);
            //TODO:Stampare messaggio d'errore e ragionare su come gestire situazione
        }
        player.setAction();
        match.addAmmoCard(cell);
    }

    public boolean isValid(Match match, Player player, List<String> destination){
        if(!this.isValidMovement(match, player, destination) && !destination.isEmpty()) return false;
        else return true;
    }
}
