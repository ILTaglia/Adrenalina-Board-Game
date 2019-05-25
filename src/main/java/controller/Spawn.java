package controller;

import exceptions.InvalidColorException;
import exceptions.NotOwnedCardException;
import exceptions.ZeroCardsOwnedException;
import model.Player;
import model.PowCard;

public class Spawn {
    public void spawn(Player player, int x, int y, int powcardIndex) throws InvalidColorException{
        PowCard powcard = player.getPowByIndex(powcardIndex);
        int powcardcolor = powcard.getColor();
        if(bornValidity(player, x, y, powcardcolor)){
            player.setCel(x, y);
            try{player.removePow(powcard);}
            catch(ZeroCardsOwnedException e){return;}
            catch(NotOwnedCardException e){return;}
        }
        else throw new InvalidColorException();
    }

    public boolean bornValidity(Player player, int x, int y, int powcardcolor){
        if(x==0 && y==2 && powcardcolor==0) return true; //blue spawnpoint cell at line 0, column2
        else if(x==0 && y==2 && powcardcolor==5) return true; //red spawnpoint cell at line 1, column0
        else if(x==2 && y==3 && powcardcolor==2) return true; //yellow spawnpoint cell at line 2, column3
        return false;
    }

}
