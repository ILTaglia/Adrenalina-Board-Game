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
    private ArrayList<Match> matches = new ArrayList<>();
    Random rand = new Random();

    public Game(){
        Match match = new Match();
        matches.add(match);
        //TODO communication with user, first example to be rewritten

    }

    public void select(int i){
        matches.get(0).createDashboard(i);
    }

    public String randomId(Random rand){
        int i = 10000000 + rand.nextInt(89999999);
        String id = Integer.toString(i);
        for (Match match : this.matches){
            //check to avoid different players have the same id
            for(int index = 0; index< match.getPlayersSize(); index++){
                if (match.getPlayerByIndex(index).getid().equals(id)){
                    id=id+7;
                }
            }
        }
        if(Integer.parseInt(id)>99999999) id = randomId(rand);
        return id;
    }

    public void addMatch(Match m){
        if(!this.matches.contains(m)) this.matches.add(m);
    }

    public int getMatchesSize(){return this.matches.size();}

    public Match getMatchByIndex(int index){return matches.get(index);}

    public Match getMatch (int matchID) throws IllegalArgumentException{
        for(Match m : this.matches){
            if(m.getId()== matchID) return m;
        }
        throw new IllegalArgumentException();
    }

    public Random getRand(){return this.rand;}

    public void startGame(int matchID){
        Match match;
        try{
            match = this.getMatch(matchID);
        } catch(IllegalArgumentException e) { return;}
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

    public static void main(String[] args){
        Game g = new Game();

        //Selection of the dashboard
        System.out.println("player 1: choose a dashboard, 1, 2 or 3");
        int choice=0;
        try {
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(System.in));
            String number = bufferedreader.readLine();
            choice = Integer.parseInt(number);
        }catch(IOException e)
        {e.printStackTrace();}

        g.select(choice); //method that creates a dashboard
    }
}

//TODO per la run servirà passare un'array di stringhe