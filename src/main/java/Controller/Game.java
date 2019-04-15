package Controller;
import Model.Match;
import Model.Dashboard;
import Model.Player;
import exceptions.MaxNumberPlayerException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.ArrayList;

public class Game{
    private ArrayList<Match> matches = new ArrayList<>();

    public Game(){
        Match m = new Match();
        matches.add(m);
        //TODO communication with user, first example to be rewritten
        Player player1 = new Player("Sirius", "blue", "10583741");
        Player player2 = new Player("Calypso", "pink", "14253954");
        Player player3 = new Player("Hermione", "green", "18263100");
        Player player4 = new Player("Aries", "yellow", "18992302");
        Player player5 = new Player("Karka", "grey", "18114320");
        try {
            m.add_player(player1);
            m.add_player(player2);
            m.add_player(player3);
            m.add_player(player4);
            m.add_player(player5);
        }
        catch (MaxNumberPlayerException e){}
        //in case the players are more than five they have to wait for a new match
    }

    public void select(int i){
        matches.get(0).create_dashboard(i);
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

        g.select(choice);
    }
}
