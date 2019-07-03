package controller;

import exceptions.InvalidColorException;
import exceptions.NotOwnedCardException;
import exceptions.ZeroCardsOwnedException;
import model.Match;
import model.Player;
import model.PowCard;

public class Spawn {
    /**
     *
     * @param match is the match
     * @param player is the player that has to spawn
     * @param x is the line of the selected SpawnPoint Cell
     * @param y is the column of the selected SpawnPoint Cell
     * @param indexPowCard is the index of the selected PowCard to spawn
     * @throws InvalidColorException if the PowCard does not allow to spawn in the selected cell
     */
    public void spawn(Match match, Player player, int x, int y, int indexPowCard) throws InvalidColorException{
        int powCardColor = player.getPowByIndex(indexPowCard).getColor();
        if(bornValidity(x, y, powCardColor)){
            match.spawnPlayer(player,indexPowCard,x,y);
        }
        else throw new InvalidColorException();
    }
    /**
     *
     * @param x is the line of the selected SpawnPoint Cell
     * @param y is the column of the selected SpawnPoint Cell
     * @param powCardColor is the color of the selected PowCard to spawn
     * @return true if the PowCard allows to spawn in the selected cell, 0 otherwise
     */
    public boolean bornValidity(int x, int y, int powCardColor){
        if(x==0 && y==2 && powCardColor==1) return true; //blue spawnpoint cell at line 0, column2
        else if(x==1 && y==0 && powCardColor==0) return true; //red spawnpoint cell at line 1, column0
        else if(x==2 && y==3 && powCardColor==2) return true; //yellow spawnpoint cell at line 2, column3
        return false;
    }

}
