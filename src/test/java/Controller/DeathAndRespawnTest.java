package Controller;

import Model.Coordinate;
import Model.Player;
import Model.Match;
import exceptions.MaxNumberPlayerException;
import exceptions.NotExistingDashboardException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DeathAndRespawnTest {
    Player player1;
    Player player2;
    Player player3;
    Player player4;
    Player player5;
    Match match;

    @Before
    public void setUp(){
        player1 = new Player("Sirius", "blue", "10583741");
        player2 = new Player("Calypso", "pink", "14253954");
        player3 = new Player("Hermione", "green", "18263100");
        player4 = new Player("Aries", "yellow", "18992302");
        player5 = new Player("Karka", "grey", "18114320");
        match = new Match();
        try {
            match.add_player(player1);
            match.add_player(player2);
            match.add_player(player3);
            match.add_player(player4);
            match.add_player(player5);
        }
        catch (MaxNumberPlayerException e){}
        match.create_dashboard(1);
    }

    @Test
    public void calculatescore(){
        assertEquals(0, player1.gettotaldamage());
        assertEquals(0, player1.get_death());
        //player3 kills player1 with revenge
        assertEquals(0, player3.getmarks(0));
        player1.setdamage(3,3); //from player2
        player1.setdamage(2,2); //from player4
        player1.setdamage(3,4); //from player5

        player1.setmarks(1,2);
        player1.setmarks(2, 3);
        assertEquals(1, player1.getmarks(2));
        assertEquals(2, player1.getmarks(3));

        player1.setdamage(2,3); //from player2
        player1.setdamage(2,1); //from player3
        assertEquals(12, player1.gettotaldamage());
        assertEquals(5, player1.getnumberdamage(3)); //from player2
        assertEquals(3, player1.getnumberdamage(4)); //from player5
        assertEquals(2, player1.getnumberdamage(1)); //from player3
        assertEquals(2, player1.getnumberdamage(2)); //from player4
        DeathAndRespawn c = new DeathAndRespawn();
        assertTrue(match.get_check());
        try{
            c.calculatescore(match, player1, player3, 2);
        } catch (NotExistingDashboardException e){}

        assertEquals(3, player1.get_firstblood());
        assertEquals(9, player2.get_score()); //first is player2
        assertEquals(6, player5.get_score()); //second is player5
        assertEquals(4, player4.get_score()); //third is player4 (because it made damage before player3 even if they gave the same number of damages)
        assertEquals(2, player3.get_score()); //fourth is player3

        assertEquals(1, player1.get_death());
        //check revenge
        assertEquals(1, player3.getmarks(0));

        c.respawn(player1);
        assertEquals(-1, player1.get_firstblood());
        assertEquals(0, player1.get_action());
        assertEquals(0, player1.gettotaldamage());
        assertEquals(1, player1.getmarks(2));
        assertEquals(2, player1.getmarks(3));
        Coordinate pos = new Coordinate(-1, -1);
        assertEquals(pos.getX(), player1.get_cel().getX());
        assertEquals(pos.getY(), player1.get_cel().getY());
        for(int k=0; k<5; k++){
            if(k!=player1.getcolor()) assertEquals(0, player1.getnumberdamage(k));
        }
    }

    @Test
    public void end_game(){
        match.get_dashboard().setKillshot_track(player1,2);
        match.get_dashboard().setKillshot_track(player2,1);
        match.get_dashboard().setKillshot_track(player3,1);
        match.get_dashboard().setKillshot_track(player2,2);
        match.get_dashboard().setKillshot_track(player1,1);
        match.get_dashboard().setKillshot_track(player3,2);
        match.get_dashboard().setKillshot_track(player2,1);
        match.get_dashboard().setKillshot_track(player1,1);

        /*This test controls that when a match ends, points of the killshot track are given to players that have
         * signals in the track*/

        DeathAndRespawn c = new DeathAndRespawn();

        c.end_game(match, match.get_dashboard());
        assertEquals(8, player1.get_score());
        assertEquals(6, player2.get_score());
        assertEquals(4, player3.get_score());

    }

}