package Model;

import Controller.DeathAndRespawn;
import exceptions.MaxNumberPlayerException;
import exceptions.NotExistingDashboardException;
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
            match.add_player(player1);
            match.add_player(player2);
            match.add_player(player3);
        }
        catch (MaxNumberPlayerException e){}
        match.create_dashboard(1);

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