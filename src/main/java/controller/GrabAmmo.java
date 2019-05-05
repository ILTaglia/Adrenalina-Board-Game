package controller;

import model.Match;
import model.NormalCell;
import model.Player;
import exceptions.CardAlreadyCollectedException;
import exceptions.MaxNumberofCardsException;

public class GrabAmmo extends Grab{
    public GrabAmmo(){
        //Empty Constructor, controller has to use the method grabAmmo
    }
    public void grabAmmo(Match match, Player player) throws CardAlreadyCollectedException{
        NormalCell cell;
        int cardType;
        cell = (NormalCell) player.getCel().inmap(match.getDashboard(), player.getCel().getX(), player.getCel().getY());
        cardType=cell.getCardType();
        cell.Collect_Card(player);


        if(1==cardType){        //cardType==1 if Card is AmmoPowTile
            try{
                match.assignPowCard(player);
            }catch (MaxNumberofCardsException e){
                //TODO:Stampare messaggio d'errore e ragionare su come gestire situazione
            }
        }
        player.setAction();
        match.addAmmoCard(cell);
    }

}
