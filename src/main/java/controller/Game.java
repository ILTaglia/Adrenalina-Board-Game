package controller;
import exceptions.InvalidColorException;
import exceptions.InvalidDirectionException;
import model.Match;
import model.Player;
import exceptions.MaxNumberPlayerException;
import model.PowCard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
//TODO: Capire se la view va istanziata nel controller oppure se va passata negli eventi che lancia al controller

public class Game{

    private Match match;

    public Game(){
        match = new Match();
    }

    public void select(int i){
        match.createDashboard(i);
    }

    public void startGame(){
        match.fillDashboard();
        match.getPlayerByIndex(0).setActive();
        for(int i=1; i<match.getPlayersSize(); i++){
            match.getPlayerByIndex(i).resetActive();
    }
        match.firstTurnPows(); //assign two powcards to each players to start
    }

    public void firstTurn(Player player, int powcardIndex, int x, int y) throws InvalidColorException {
        Spawn playerSpawn = new Spawn();
        playerSpawn.spawn(player, x, y, powcardIndex);
    }

    //TODO da fare già prima della chiamata il controllo sulla validità dell'azione
    public void setTurn(Match match){
        Player p = match.getActivePlayer();
        int index=0;
        for(int i=0; i<match.getPlayersSize(); i++){
            if(match.getPlayers().get(i).equals(p)){
                index = i;
                if(i==match.getPlayersSize()-1){
                    this.resetTurn(match);
                    return;
                }
            }
        }
        match.getPlayerByIndex(index).setActive();
        index++;
        match.getPlayerByIndex(index).setActive();
    }

    private void resetTurn(Match match){
        for(Player p:match.getPlayers()) p.resetAction();
        match.getPlayerByIndex(0).setActive();
    }
}

//TODO per la run servirà passare un'array di stringhe