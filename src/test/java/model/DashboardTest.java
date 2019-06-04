package model;

import controller.DeathAndRespawn;
import exceptions.MaxNumberPlayerException;
import org.junit.Test;


import static org.junit.Assert.*;

public class DashboardTest {
    Player player1;
    Player player2;
    Player player3;


    @Test
    public void set(){
        Dashboard d1 = new Dashboard(1);
        Dashboard d2 = new Dashboard(2);
        Dashboard d3 = new Dashboard(3);
        Match match = new Match();

        //try {
            match.createPlayer("Sirius", "Blue", "10583741");
            match.createPlayer("Calypso", "Pink", "14253954");
            match.createPlayer("Hermione", "Green", "18263100");
        //}
        //catch (MaxNumberPlayerException e){}
        match.createDashboard(1);
        player1 = match.getPlayerByIndex(0);
        player2 = match.getPlayerByIndex(1);
        player3 = match.getPlayerByIndex(2);

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

    @Test
    public void existingCells(){
        Dashboard d1 = new Dashboard(1);
        Dashboard d2 = new Dashboard(2);
        Dashboard d3 = new Dashboard(3);

        assertEquals(10, d1.getCells().size());
        assertEquals(11, d2.getCells().size());
        assertEquals(12, d3.getCells().size());

        Coordinate c = new Coordinate(0, 0);
        assertEquals(c.getX(), d1.getCells().get(0).getX());
        assertEquals(c.getY(), d1.getCells().get(0).getY());
        assertEquals(c.getX(), d2.getCells().get(0).getX());
        assertEquals(c.getY(), d2.getCells().get(0).getY());
        assertEquals(c.getX(), d3.getCells().get(0).getX());
        assertEquals(c.getY(), d3.getCells().get(0).getY());
        c.set(0, 1);
        assertEquals(c.getX(), d1.getCells().get(1).getX());
        assertEquals(c.getY(), d1.getCells().get(1).getY());
        assertEquals(c.getX(), d2.getCells().get(1).getX());
        assertEquals(c.getY(), d2.getCells().get(1).getY());
        assertEquals(c.getX(), d3.getCells().get(1).getX());
        assertEquals(c.getY(), d3.getCells().get(1).getY());
        c.set(0, 2);
        assertEquals(c.getX(), d1.getCells().get(2).getX());
        assertEquals(c.getY(), d1.getCells().get(2).getY());
        assertEquals(c.getX(), d2.getCells().get(2).getX());
        assertEquals(c.getY(), d2.getCells().get(2).getY());
        assertEquals(c.getX(), d3.getCells().get(2).getX());
        assertEquals(c.getY(), d3.getCells().get(2).getY());
        c.set(0, 3);
        assertEquals(c.getX(), d2.getCells().get(3).getX());
        assertEquals(c.getY(), d2.getCells().get(3).getY());
        assertEquals(c.getX(), d3.getCells().get(3).getX());
        assertEquals(c.getY(), d3.getCells().get(3).getY());
        c.set(1, 0);
        assertEquals(c.getX(), d1.getCells().get(3).getX());
        assertEquals(c.getY(), d1.getCells().get(3).getY());
        assertEquals(c.getX(), d2.getCells().get(4).getX());
        assertEquals(c.getY(), d2.getCells().get(4).getY());
        assertEquals(c.getX(), d3.getCells().get(4).getX());
        assertEquals(c.getY(), d3.getCells().get(4).getY());
        c.set(1, 1);
        assertEquals(c.getX(), d1.getCells().get(4).getX());
        assertEquals(c.getY(), d1.getCells().get(4).getY());
        assertEquals(c.getX(), d2.getCells().get(5).getX());
        assertEquals(c.getY(), d2.getCells().get(5).getY());
        assertEquals(c.getX(), d3.getCells().get(5).getX());
        assertEquals(c.getY(), d3.getCells().get(5).getY());
        c.set(1, 2);
        assertEquals(c.getX(), d1.getCells().get(5).getX());
        assertEquals(c.getY(), d1.getCells().get(5).getY());
        assertEquals(c.getX(), d2.getCells().get(6).getX());
        assertEquals(c.getY(), d2.getCells().get(6).getY());
        assertEquals(c.getX(), d3.getCells().get(6).getX());
        assertEquals(c.getY(), d3.getCells().get(6).getY());
        c.set(1, 3);
        assertEquals(c.getX(), d1.getCells().get(6).getX());
        assertEquals(c.getY(), d1.getCells().get(6).getY());
        assertEquals(c.getX(), d2.getCells().get(7).getX());
        assertEquals(c.getY(), d2.getCells().get(7).getY());
        assertEquals(c.getX(), d3.getCells().get(7).getX());
        assertEquals(c.getY(), d3.getCells().get(7).getY());
        c.set(2, 0);
        assertEquals(c.getX(), d3.getCells().get(8).getX());
        assertEquals(c.getY(), d3.getCells().get(8).getY());
        c.set(2, 1);
        assertEquals(c.getX(), d1.getCells().get(7).getX());
        assertEquals(c.getY(), d1.getCells().get(7).getY());
        assertEquals(c.getX(), d2.getCells().get(8).getX());
        assertEquals(c.getY(), d2.getCells().get(8).getY());
        assertEquals(c.getX(), d3.getCells().get(9).getX());
        assertEquals(c.getY(), d3.getCells().get(9).getY());
        c.set(2, 2);
        assertEquals(c.getX(), d1.getCells().get(8).getX());
        assertEquals(c.getY(), d1.getCells().get(8).getY());
        assertEquals(c.getX(), d2.getCells().get(9).getX());
        assertEquals(c.getY(), d2.getCells().get(9).getY());
        assertEquals(c.getX(), d3.getCells().get(10).getX());
        assertEquals(c.getY(), d3.getCells().get(10).getY());
        c.set(2, 3);
        assertEquals(c.getX(), d1.getCells().get(9).getX());
        assertEquals(c.getY(), d1.getCells().get(9).getY());
        assertEquals(c.getX(), d2.getCells().get(10).getX());
        assertEquals(c.getY(), d2.getCells().get(10).getY());
        assertEquals(c.getX(), d3.getCells().get(11).getX());
        assertEquals(c.getY(), d3.getCells().get(11).getY());

    }

}