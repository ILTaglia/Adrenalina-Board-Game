package controller;
import model.Match;
import model.Player;
import exceptions.MaxNumberPlayerException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.Random;
//TODO: Capire se la view va istanziata nel controller oppure se va passata negli eventi che lancia al controller

public class Game{
    private ArrayList<Match> matches = new ArrayList<>();
    Random rand = new Random();

    public Game(){
        Match match = new Match();
        matches.add(match);
        //TODO communication with user, first example to be rewritten
        Player player1 = new Player("Sirius", "blue", "10583741");
        Player player2 = new Player("Calypso", "pink", "14253954");
        Player player3 = new Player("Hermione", "green", "18263100");
        Player player4 = new Player("Aries", "yellow", "18992302");
        Player player5 = new Player("Karka", "grey", "18114320");
        try {
            match.addplayer(player1);
            match.addplayer(player2);
            match.addplayer(player3);
            match.addplayer(player4);
            match.addplayer(player5);
        }
        catch (MaxNumberPlayerException e){
            //in case the players are more than five they have to wait for a new match
        }
    }

    public void select(int i){
        matches.get(0).createdashboard(i);
    }

    //TODO controllare random, meglio stringa char+int
    public String randomId(Random rand){
        int i = 10000000 + rand.nextInt(89999999);
        String id = Integer.toString(i);
        for (Match match : this.matches){
            //check to avoid different players have the same id
            for(int index = 0; index< match.getplayerssize(); index++){
                if (match.getplayerbyindex(index).getid().equals(id)){
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

    public Random getRand(){return this.rand;}

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