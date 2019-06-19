package controller;

import exceptions.InvalidColorException;
import exceptions.NotOwnedCardException;
import exceptions.ZeroCardsOwnedException;
import model.Match;
import model.Player;
import model.PowCard;

public class Spawn {
    public void spawn(Match match, Player player, int x, int y, int indexPowCard) throws InvalidColorException{
        int powCardColor = player.getPowByIndex(indexPowCard).getColor();
        if(bornValidity(x, y, powCardColor)){
            match.spawnPlayer(player,indexPowCard,x,y);
        }
        else throw new InvalidColorException();
    }

    public boolean bornValidity(int x, int y, int powcardcolor){
        if(x==0 && y==2 && powcardcolor==1) return true; //blue spawnpoint cell at line 0, column2
        else if(x==1 && y==0 && powcardcolor==0) return true; //red spawnpoint cell at line 1, column0
        else if(x==2 && y==3 && powcardcolor==2) return true; //yellow spawnpoint cell at line 2, column3
        return false;
    }

}
