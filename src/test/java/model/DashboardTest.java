package model;

import controller.DeathAndRespawn;
import exceptions.MaxNumberPlayerException;
import org.junit.Test;

import static org.junit.Assert.*;

public class DashboardTest {
    Player player1 = new Player("Sirius", "blue", "10583741"); //0
    Player player2 = new Player("Calypso", "pink", "14253954"); //3
    Player player3 = new Player("Hermione", "green", "18263100"); //1


    @Test
    public void set(){
        Dashboard d1 = new Dashboard(1);
        Dashboard d2 = new Dashboard(2);
        Dashboard d3 = new Dashboard(3);
        Match match = new Match();

        try {
            match.addPlayer(player1);
            match.addPlayer(player2);
            match.addPlayer(player3);
        }
        catch (MaxNumberPlayerException e){}
        match.createDashboard(1);

        assertEquals(0, match.getDashboard().getindex());
        match.getDashboard().setKillshottrack(player1,2);
        assertEquals(1, match.getDashboard().getindex());
        match.getDashboard().setKillshottrack(player2,1);
        match.getDashboard().setKillshottrack(player3,1);
        match.getDashboard().setKillshottrack(player2,2);
        match.getDashboard().setKillshottrack(player1,1);
        match.getDashboard().setKillshottrack(player3,2);
        match.getDashboard().setKillshottrack(player2,1);
        match.getDashboard().setKillshottrack(player1,1);

        /*This test controls that when a match ends, points of the killshot track are given to players that have
        * signals in the track*/

        DeathAndRespawn c = new DeathAndRespawn();

        c.endgame(match, match.getDashboard());
        assertEquals(8, player1.getScore());
        assertEquals(6, player2.getScore());
        assertEquals(4, player3.getScore());

        for(int i=0; i<3; i++){
            for(int j=0; j<4; j++){
                System.out.println(d1.getmap(i, j).elenco());
            }
        }

        //check print to verify ports are memorized correctly

    }

}