package controller;

import model.Coordinate;
import model.Player;
import model.Match;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class DeathAndRespawnTest {
    Player player1;
    Player player2;
    Player player3;
    Player player4;
    Player player5;
    Match match;

    @BeforeEach
    public void setUp(){
        match = new Match();
        match.createPlayer("Sirius", "Blue", "10583741");
        match.createPlayer("Calypso", "Pink", "14253954");
        match.createPlayer("Hermione", "Green", "18263100");
        match.createPlayer("Aries", "Yellow", "18992302");
        match.createPlayer("Karka", "Grey", "18114320");
        match.createDashboard(1);
        player1 = match.getPlayerByIndex(0);
        player2 = match.getPlayerByIndex(1);
        player3 = match.getPlayerByIndex(2);
        player4 = match.getPlayerByIndex(3);
        player5 = match.getPlayerByIndex(4);
        player1.setConnected(true);
        player2.setConnected(true);
        player3.setConnected(true);
        player4.setConnected(true);
        player5.setConnected(true);
    }

    /*@Test
    public void calculatescore(){
        assertEquals(0, player1.getTotalDamage());
        assertEquals(0, player1.getNumberOfDeath());
        //player3 kills player1 with revenge
        assertEquals(0, player3.getMarks(0));
        player1.setDamage(3,3); //from player2
        player1.setDamage(2,2); //from player4
        player1.setDamage(3,4); //from player5

        player1.setMarks(1,2);
        player1.setMarks(2, 3);
        assertEquals(1, player1.getMarks(2));
        assertEquals(2, player1.getMarks(3));

        player1.setDamage(2,3); //from player2
        player1.setDamage(2,1); //from player3
        assertEquals(12, player1.getTotalDamage());
        assertEquals(5, player1.getNumberDamage(3)); //from player2
        assertEquals(3, player1.getNumberDamage(4)); //from player5
        assertEquals(2, player1.getNumberDamage(1)); //from player3
        assertEquals(2, player1.getNumberDamage(2)); //from player4
        DeathAndRespawn c = new DeathAndRespawn();
        assertTrue(match.getCheck());
        try{
            c.calculateScore(match, player1, player3, 2);
        } catch (NotExistingDashboardException e){}

        assertEquals(3, player1.getFirstBlood());
        assertEquals(9, player2.getScore()); //first is player2
        assertEquals(6, player5.getScore()); //second is player5
        assertEquals(4, player4.getScore()); //third is player4 (because it made damage before player3 even if they gave the same number of damages)
        assertEquals(2, player3.getScore()); //fourth is player3

        assertEquals(1, player1.getNumberOfDeath());
        //check revenge
        assertEquals(1, player3.getMarks(0));

        c.respawn(player1);
        assertEquals(-1, player1.getFirstBlood());
        assertEquals(0, player1.getAction());
        assertEquals(0, player1.getTotalDamage());
        assertEquals(1, player1.getMarks(2));
        assertEquals(2, player1.getMarks(3));
        Coordinate pos = new Coordinate(-1, -1);
        assertEquals(pos.getX(), player1.getCel().getX());
        assertEquals(pos.getY(), player1.getCel().getY());
        for(int k=0; k<5; k++){
            if(k!=player1.getColor()) assertEquals(0, player1.getNumberDamage(k));
        }
    }*/

    @Test
    public void end_game(){
        match.getDashboard().setKillShotTrack(player1,2);
        match.getDashboard().setKillShotTrack(player2,1);
        match.getDashboard().setKillShotTrack(player3,1);
        match.getDashboard().setKillShotTrack(player2,2);
        match.getDashboard().setKillShotTrack(player1,1);
        match.getDashboard().setKillShotTrack(player3,2);
        match.getDashboard().setKillShotTrack(player2,1);
        match.getDashboard().setKillShotTrack(player1,1);

        /*This test controls that when a match ends, points of the killshot track are given to players that have
         * signals in the track*/

        DeathAndRespawn c = new DeathAndRespawn();

        c.endgame(match, match.getDashboard());
        assertEquals(8, player1.getScore());
        assertEquals(6, player2.getScore());
        assertEquals(4, player3.getScore());

    }

    @Test
    public void winner(){
        player1.setScore(23);
        player2.setScore(18);
        player3.setScore(25);
        player4.setScore(28);
        player5.setScore(20);
        DeathAndRespawn c = new DeathAndRespawn();
        String winnerplayerID = player4.getID();
        assertEquals(winnerplayerID, c.winner(match));
    }

}