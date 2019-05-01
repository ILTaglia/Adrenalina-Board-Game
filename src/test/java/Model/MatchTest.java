package Model;

import exceptions.InvalidColorException;
import exceptions.MaxNumberPlayerException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class MatchTest {
    Match match;
    Player player1;
    Player player2;
    Player player3;
    Player player4;


    @Before
    public void SetUp(){
        match = new Match();
        player1 = new Player("Sirius", "blue", "10583741");
        player2 = new Player("Calypso", "pink", "14253954");
        player3 = new Player("Hermione", "green", "18263100");
        player4 = new Player("Aries", "yellow", "18992302");
        try {
            match.add_player(player1);
            match.add_player(player2);
            match.add_player(player3);
            match.add_player(player4);
        }
        catch (MaxNumberPlayerException e){}
    }

    @Test
    public void set_round() {
        assertEquals(1, match.get_round());
        Player player5 = new Player("Karka", "grey", "18114320");
        try {
            match.add_player(player5);
        }
        catch (MaxNumberPlayerException e){}
        player5.set_action();
        player5.set_action();
        match.set_round();
        assertEquals(2, match.get_round());
    }

    @Test
    public void get_round() {
        assertEquals(1, match.get_round());
    }

    @Test
    public void get_player_size(){
        assertEquals(4, match.get_players_size());
    }

    @Test
    public void get_check() {
        assertFalse(match.get_check());
        match.create_dashboard(2);
        assertTrue(match.get_check());
    }

    @Test
    public void add_player() {
        Player player5 = new Player("Karka", "grey", "18114320");
        Player player6 = new Player("Gemini", "grey", "10003256");
        try {
            match.add_player(player5);
        }
        catch (MaxNumberPlayerException e){}
        assertThrows(MaxNumberPlayerException.class, () -> match.add_player(player6));
    }

    @Test
    public void get_player(){
        assertEquals("Sirius", player1.getname());
        assertEquals("Calypso", player2.getname());
        assertEquals("Hermione", player3.getname());
        assertEquals("Aries", player4.getname());
        try{
            player1 = match.get_player(1); //Hermione
            player2 = match.get_player(0); //Sirius
            player3 = match.get_player(2); //Aries
            player4 = match.get_player(3); //Calypso
        }
        catch (InvalidColorException e){}
        assertEquals("Hermione", player1.getname());
        assertEquals("Sirius", player2.getname());
        assertEquals("Aries", player3.getname());
        assertEquals("Calypso", player4.getname());
    }

    @Test
    public void dashboard(){
        match.create_dashboard(1);
        Dashboard d = match.get_dashboard();
    }

    @Test
    public void exception_test(){
        try {
            Player player6 = match.get_player(6);
        }
        catch (InvalidColorException e){}
        Player player5 = new Player("Karka", "blue", "18114320");
        Match m2 = new Match();
        try{
            m2.add_player(player1);
            m2.add_player(player2);
        }
        catch (MaxNumberPlayerException e){}
        assertThrows(InvalidColorException.class, () -> m2.add_player(player5));
        assertEquals(1, m2.create_dashboard(1));
    }

    @Test
    public void players_list1(){
        match.create_dashboard(3);

        player1.set_cel(0, 3);
        player2.set_cel(0, 1);
        player3.set_cel(2, 2);
        player4.set_cel(2, 1);

        assertTrue(match.visible_players(player1).contains(player2));
        assertTrue(match.visible_players(player1).contains(player3));
        assertEquals(2, match.visible_players(player1).size());
        assertEquals(0, match.visible_players(player2).size());
        assertTrue(match.visible_players(player3).contains(player4));
        assertEquals(1, match.visible_players(player3).size());
        assertTrue(match.visible_players(player4).contains(player3));
        assertEquals(1, match.visible_players(player4).size());


        assertTrue(match.same_line_players(player1).contains(player2));
        assertEquals(1, match.same_line_players(player1).size());
        assertTrue(match.same_line_players(player2).contains(player1));
        assertEquals(1, match.same_line_players(player2).size());
        assertTrue(match.same_line_players(player3).contains(player4));
        assertEquals(1, match.same_line_players(player3).size());
        assertTrue(match.same_line_players(player4).contains(player3));
        assertEquals(1, match.same_line_players(player4).size());


        assertEquals(0, match.same_column_players(player1).size());
        assertTrue(match.same_column_players(player2).contains(player4));
        assertEquals(1, match.same_column_players(player2).size());
        assertEquals(0, match.same_column_players(player3).size());
        assertTrue(match.same_column_players(player4).contains(player2));
        assertEquals(1, match.same_column_players(player4).size());

        assertEquals(2, match.manhattan_distance(player1, player2));
        assertEquals(2, match.manhattan_distance(player2, player4));
        assertEquals(1, match.manhattan_distance(player3, player4));
        assertEquals(-1, match.manhattan_distance(player1, player3));
    }

    @Test
    public void players_list2(){
        match.create_dashboard(3);

        player1.set_cel(0, 2);
        player2.set_cel(0, 1);
        player3.set_cel(2, 2);
        player4.set_cel(1, 1);


        assertTrue(match.visible_players(player1).contains(player2));
        assertTrue(match.visible_players(player1).contains(player3));
        assertEquals(2, match.visible_players(player1).size());
        assertTrue(match.visible_players(player2).contains(player1));
        assertTrue(match.visible_players(player2).contains(player4));
        assertEquals(2, match.visible_players(player2).size());
        assertEquals(0, match.visible_players(player3).size());
        assertTrue(match.visible_players(player4).contains(player1));
        assertTrue(match.visible_players(player4).contains(player2));
        assertEquals(2, match.visible_players(player4).size());


        assertTrue(match.same_line_players(player1).contains(player2));
        assertEquals(1, match.same_line_players(player1).size());
        assertTrue(match.same_line_players(player2).contains(player1));
        assertEquals(1, match.same_line_players(player2).size());
        assertEquals(0, match.same_line_players(player3).size());
        assertEquals(0, match.same_line_players(player4).size());


        assertTrue(match.same_column_players(player1).contains(player3));
        assertEquals(1, match.same_column_players(player1).size());
        assertTrue(match.same_column_players(player2).contains(player4));
        assertEquals(1, match.same_column_players(player2).size());
        assertTrue(match.same_column_players(player3).contains(player1));
        assertEquals(1, match.same_column_players(player3).size());
        assertTrue(match.same_column_players(player4).contains(player2));
        assertEquals(1, match.same_column_players(player4).size());


        assertEquals(1, match.manhattan_distance(player1, player2));
        assertEquals(1, match.manhattan_distance(player2, player4));
        assertEquals(-1, match.manhattan_distance(player3, player4));
    }
}