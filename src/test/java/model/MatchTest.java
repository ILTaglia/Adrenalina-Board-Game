package model;

import exceptions.InvalidColorException;
import exceptions.MaxNumberPlayerException;
import org.junit.Before;
import org.junit.Test;

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
            match.addPlayer(player1);
            match.addPlayer(player2);
            match.addPlayer(player3);
            match.addPlayer(player4);
        }
        catch (MaxNumberPlayerException e){}
    }

    @Test
    public void set_round() {
        assertEquals(1, match.getRound());
        Player player5 = new Player("Karka", "grey", "18114320");
        try {
            match.addPlayer(player5);
        }
        catch (MaxNumberPlayerException e){}
        player5.setAction();
        player5.setAction();
        match.setRound();
        assertEquals(2, match.getRound());
    }

    @Test
    public void get_round() {
        assertEquals(1, match.getRound());
    }

    @Test
    public void get_player_size(){
        assertEquals(4, match.getPlayersSize());
    }

    @Test
    public void get_check() {
        assertFalse(match.getCheck());
        match.createDashboard(2);
        assertTrue(match.getCheck());
    }

    @Test
    public void add_player() {
        Player player5 = new Player("Karka", "grey", "18114320");
        Player player6 = new Player("Gemini", "grey", "10003256");
        try {
            match.addPlayer(player5);
        }
        catch (MaxNumberPlayerException e){}
        assertThrows(MaxNumberPlayerException.class, () -> match.addPlayer(player6));
    }

    @Test
    public void get_player(){
        assertEquals("Sirius", player1.getname());
        assertEquals("Calypso", player2.getname());
        assertEquals("Hermione", player3.getname());
        assertEquals("Aries", player4.getname());
        try{
            player1 = match.getPlayer(1); //Hermione
            player2 = match.getPlayer(0); //Sirius
            player3 = match.getPlayer(2); //Aries
            player4 = match.getPlayer(3); //Calypso
        }
        catch (InvalidColorException e){}
        assertEquals("Hermione", player1.getname());
        assertEquals("Sirius", player2.getname());
        assertEquals("Aries", player3.getname());
        assertEquals("Calypso", player4.getname());

        player1.setActive();
        assertEquals(0, player1.getAction());
        assertTrue(player1.getActive());
        assertFalse(player2.getActive());
        assertFalse(player3.getActive());
        assertFalse(player4.getActive());
        assertEquals(player1, match.getActivePlayer());
        player1.setAction();
        assertTrue(player1.getActive());
        assertEquals(1, player1.getAction());
        player1.setAction();
        assertTrue(player1.getActive());
        assertEquals(2, player1.getAction());
        player1.setActive();
        player2.setActive();
        assertFalse(player1.getActive());
        assertTrue(player2.getActive());
        assertFalse(player3.getActive());
        assertFalse(player4.getActive());
        assertEquals(player2, match.getActivePlayer());
    }

    @Test
    public void dashboard(){
        match.createDashboard(1);
        Dashboard d = match.getDashboard();
    }

    @Test
    public void exception_test(){
        try {
            Player player6 = match.getPlayer(6);
        }
        catch (InvalidColorException e){}
        Player player5 = new Player("Karka", "blue", "18114320");
        Match m2 = new Match();
        try{
            m2.addPlayer(player1);
            m2.addPlayer(player2);
        }
        catch (MaxNumberPlayerException e){}
        assertThrows(InvalidColorException.class, () -> m2.addPlayer(player5));
        assertEquals(1, m2.createDashboard(1));
    }

    @Test
    public void players_list1(){
        match.createDashboard(3);

        player1.setCel(0, 3);
        player2.setCel(0, 1);
        player3.setCel(2, 2);
        player4.setCel(2, 1);

        assertTrue(match.getVisiblePlayers(player1).contains(player2));
        assertTrue(match.getVisiblePlayers(player1).contains(player3));
        assertEquals(2, match.getVisiblePlayers(player1).size());
        assertEquals(0, match.getVisiblePlayers(player2).size());
        assertTrue(match.getVisiblePlayers(player3).contains(player4));
        assertEquals(1, match.getVisiblePlayers(player3).size());
        assertTrue(match.getVisiblePlayers(player4).contains(player3));
        assertEquals(1, match.getVisiblePlayers(player4).size());


        assertTrue(match.getSameLinePlayers(player1).contains(player2));
        assertEquals(1, match.getSameLinePlayers(player1).size());
        assertTrue(match.getSameLinePlayers(player2).contains(player1));
        assertEquals(1, match.getSameLinePlayers(player2).size());
        assertTrue(match.getSameLinePlayers(player3).contains(player4));
        assertEquals(1, match.getSameLinePlayers(player3).size());
        assertTrue(match.getSameLinePlayers(player4).contains(player3));
        assertEquals(1, match.getSameLinePlayers(player4).size());


        assertEquals(0, match.getSameColumnPlayers(player1).size());
        assertTrue(match.getSameColumnPlayers(player2).contains(player4));
        assertEquals(1, match.getSameColumnPlayers(player2).size());
        assertEquals(0, match.getSameColumnPlayers(player3).size());
        assertTrue(match.getSameColumnPlayers(player4).contains(player2));
        assertEquals(1, match.getSameColumnPlayers(player4).size());

        assertEquals(2, match.getPlayersMD(player1, player2));
        assertEquals(2, match.getPlayersMD(player2, player4));
        assertEquals(1, match.getPlayersMD(player3, player4));
        assertEquals(-1, match.getPlayersMD(player1, player3));
    }

    @Test
    public void players_list2(){
        match.createDashboard(3);

        player1.setCel(0, 2);
        player2.setCel(0, 1);
        player3.setCel(2, 2);
        player4.setCel(1, 1);


        assertTrue(match.getVisiblePlayers(player1).contains(player2));
        assertTrue(match.getVisiblePlayers(player1).contains(player3));
        assertEquals(2, match.getVisiblePlayers(player1).size());
        assertTrue(match.getVisiblePlayers(player2).contains(player1));
        assertTrue(match.getVisiblePlayers(player2).contains(player4));
        assertEquals(2, match.getVisiblePlayers(player2).size());
        assertEquals(0, match.getVisiblePlayers(player3).size());
        assertTrue(match.getVisiblePlayers(player4).contains(player1));
        assertTrue(match.getVisiblePlayers(player4).contains(player2));
        assertEquals(2, match.getVisiblePlayers(player4).size());


        assertTrue(match.getSameLinePlayers(player1).contains(player2));
        assertEquals(1, match.getSameLinePlayers(player1).size());
        assertTrue(match.getSameLinePlayers(player2).contains(player1));
        assertEquals(1, match.getSameLinePlayers(player2).size());
        assertEquals(0, match.getSameLinePlayers(player3).size());
        assertEquals(0, match.getSameLinePlayers(player4).size());


        assertTrue(match.getSameColumnPlayers(player1).contains(player3));
        assertEquals(1, match.getSameColumnPlayers(player1).size());
        assertTrue(match.getSameColumnPlayers(player2).contains(player4));
        assertEquals(1, match.getSameColumnPlayers(player2).size());
        assertTrue(match.getSameColumnPlayers(player3).contains(player1));
        assertEquals(1, match.getSameColumnPlayers(player3).size());
        assertTrue(match.getSameColumnPlayers(player4).contains(player2));
        assertEquals(1, match.getSameColumnPlayers(player4).size());


        assertEquals(1, match.getPlayersMD(player1, player2));
        assertEquals(1, match.getPlayersMD(player2, player4));
        assertEquals(-1, match.getPlayersMD(player3, player4));
    }

    @Test
    public void cellsTests1() {
        match.createDashboard(3);

        player1.setCel(0, 3);
        player2.setCel(0, 1);
        player3.setCel(2, 2);
        player4.setCel(2, 1);

        int x = player1.getCel().getX();
        int y = player1.getCel().getY();
        Coordinate c = new Coordinate(x, y);

        assertEquals(0, match.getSameLineCells(c).get(0).getX());
        assertEquals(0, match.getSameLineCells(c).get(0).getY());
        assertEquals(0, match.getSameLineCells(c).get(1).getX());
        assertEquals(1, match.getSameLineCells(c).get(1).getY());
        assertEquals(0, match.getSameLineCells(c).get(2).getX());
        assertEquals(2, match.getSameLineCells(c).get(2).getY());
        assertEquals(3, match.getSameLineCells(c).size());
        assertEquals(1, match.getSameColumnCells(c).get(0).getX());
        assertEquals(3, match.getSameColumnCells(c).get(0).getY());
        assertEquals(2, match.getSameColumnCells(c).get(1).getX());
        assertEquals(3, match.getSameColumnCells(c).get(1).getY());
        assertEquals(2, match.getSameColumnCells(c).size());

        x = player3.getCel().getX();
        y = player3.getCel().getY();
        c.set(x, y);
        assertEquals(2, match.getSameLineCells(c).get(0).getX());
        assertEquals(0, match.getSameLineCells(c).get(0).getY());
        assertEquals(2, match.getSameLineCells(c).get(1).getX());
        assertEquals(1, match.getSameLineCells(c).get(1).getY());
        assertEquals(2, match.getSameLineCells(c).get(2).getX());
        assertEquals(3, match.getSameLineCells(c).get(2).getY());
        assertEquals(3, match.getSameLineCells(c).size());
        assertEquals(0, match.getSameColumnCells(c).get(0).getX());
        assertEquals(2, match.getSameColumnCells(c).get(0).getY());
        assertEquals(1, match.getSameColumnCells(c).get(1).getX());
        assertEquals(2, match.getSameColumnCells(c).get(1).getY());
        assertEquals(2, match.getSameColumnCells(c).size());
    }
    @Test
    public void cardinal() {
        match.createDashboard(3);

        player1.setCel(0, 3); //Sirius
        player2.setCel(0, 1); //Calypso
        player3.setCel(2, 2); //Hermione
        player4.setCel(2, 1); //Aries

        assertEquals(0, match.getRightPlayers(player1).size());
        assertTrue(match.getRightPlayers(player2).contains(player1));
        assertEquals(1, match.getRightPlayers(player2).size());
        assertEquals(0, match.getRightPlayers(player3).size());
        assertTrue(match.getRightPlayers(player4).contains(player3));
        assertEquals(1, match.getRightPlayers(player4).size());

        assertTrue(match.getLeftPlayers(player1).contains(player2));
        assertEquals(1, match.getLeftPlayers(player1).size());
        assertEquals(0, match.getLeftPlayers(player2).size());
        assertTrue(match.getLeftPlayers(player3).contains(player4));
        assertEquals(1, match.getLeftPlayers(player3).size());
        assertEquals(0, match.getLeftPlayers(player4).size());

        assertEquals(0, match.getUpPlayers(player1).size());
        assertEquals(0, match.getUpPlayers(player2).size());
        assertEquals(0, match.getUpPlayers(player3).size());
        assertTrue(match.getUpPlayers(player4).contains(player2));
        assertEquals(1, match.getUpPlayers(player4).size());

        assertEquals(0, match.getDownPlayers(player1).size());
        assertTrue(match.getDownPlayers(player2).contains(player4));
        assertEquals(1, match.getDownPlayers(player2).size());
        assertEquals(0, match.getDownPlayers(player3).size());
        assertEquals(0, match.getDownPlayers(player4).size());
    }

    @Test
    public void cellTest2(){
        match.createDashboard(3);

        Coordinate c1 = new Coordinate(0,3);

        assertEquals(7, match.getVisibleCells(c1).size());
        assertEquals(0, match.getVisibleCells(c1).get(0).getX());
        assertEquals(3, match.getVisibleCells(c1).get(0).getY());
        assertEquals(1, match.getVisibleCells(c1).get(1).getX());
        assertEquals(2, match.getVisibleCells(c1).get(1).getY());
        assertEquals(1, match.getVisibleCells(c1).get(2).getX());
        assertEquals(3, match.getVisibleCells(c1).get(2).getY());
        assertEquals(2, match.getVisibleCells(c1).get(3).getX());
        assertEquals(2, match.getVisibleCells(c1).get(3).getY());
        assertEquals(2, match.getVisibleCells(c1).get(4).getX());
        assertEquals(3, match.getVisibleCells(c1).get(4).getY());
        assertEquals(0, match.getVisibleCells(c1).get(5).getX());
        assertEquals(1, match.getVisibleCells(c1).get(5).getY());
        assertEquals(0, match.getVisibleCells(c1).get(6).getX());
        assertEquals(2, match.getVisibleCells(c1).get(6).getY());

        c1.set(1, 1);
        assertEquals(5, match.getVisibleCells(c1).size());
        assertEquals(1, match.getVisibleCells(c1).get(0).getX());
        assertEquals(1, match.getVisibleCells(c1).get(0).getY());
        assertEquals(0, match.getVisibleCells(c1).get(1).getX());
        assertEquals(1, match.getVisibleCells(c1).get(1).getY());
        assertEquals(0, match.getVisibleCells(c1).get(2).getX());
        assertEquals(2, match.getVisibleCells(c1).get(2).getY());
        assertEquals(2, match.getVisibleCells(c1).get(3).getX());
        assertEquals(0, match.getVisibleCells(c1).get(3).getY());
        assertEquals(2, match.getVisibleCells(c1).get(4).getX());
        assertEquals(1, match.getVisibleCells(c1).get(4).getY());

        c1.set(0, 0);
        assertEquals(4, match.getVisibleCells(c1).size());
        assertEquals(0, match.getVisibleCells(c1).get(0).getX());
        assertEquals(0, match.getVisibleCells(c1).get(0).getY());
        assertEquals(1, match.getVisibleCells(c1).get(1).getX());
        assertEquals(0, match.getVisibleCells(c1).get(1).getY());
        assertEquals(0, match.getVisibleCells(c1).get(2).getX());
        assertEquals(1, match.getVisibleCells(c1).get(2).getY());
        assertEquals(0, match.getVisibleCells(c1).get(3).getX());
        assertEquals(2, match.getVisibleCells(c1).get(3).getY());

        c1.set(2, 2);
        assertEquals(6, match.getVisibleCells(c1).size());
        assertEquals(1, match.getVisibleCells(c1).get(0).getX());
        assertEquals(2, match.getVisibleCells(c1).get(0).getY());
        assertEquals(1, match.getVisibleCells(c1).get(1).getX());
        assertEquals(3, match.getVisibleCells(c1).get(1).getY());
        assertEquals(2, match.getVisibleCells(c1).get(2).getX());
        assertEquals(2, match.getVisibleCells(c1).get(2).getY());
        assertEquals(2, match.getVisibleCells(c1).get(3).getX());
        assertEquals(3, match.getVisibleCells(c1).get(3).getY());
        assertEquals(2, match.getVisibleCells(c1).get(4).getX());
        assertEquals(0, match.getVisibleCells(c1).get(4).getY());
        assertEquals(2, match.getVisibleCells(c1).get(5).getX());
        assertEquals(1, match.getVisibleCells(c1).get(5).getY());
    }
    @Test
    public void cellTest3() {
        match.createDashboard(3);

        player1.setCel(0, 3); //Sirius
        player2.setCel(0, 3); //Calypso
        player3.setCel(2, 2); //Hermione
        player4.setCel(2, 1); //Aries

        Coordinate c1 = new Coordinate(0, 3);
        Coordinate c2 = new Coordinate(2, 2);
        Coordinate c3 = new Coordinate(2, 1);

        assertTrue(match.getSameCellsPlayers(c1).contains(player1));
        assertTrue(match.getSameCellsPlayers(c1).contains(player2));
        assertEquals(2, match.getSameCellsPlayers(c1).size());
        assertTrue(match.getSameCellsPlayers(c2).contains(player3));
        assertEquals(1, match.getSameCellsPlayers(c2).size());
        assertTrue(match.getSameCellsPlayers(c3).contains(player4));
        assertEquals(1, match.getSameCellsPlayers(c3).size());
    }

    @Test
    public void cellTest4() {
        match.createDashboard(3);

        Coordinate c1 = new Coordinate(0, 3);
        Coordinate c2 = new Coordinate(0, 1);
        Coordinate c3 = new Coordinate(2, 2);
        Coordinate c4 = new Coordinate(2, 1);

        assertEquals(3, match.getLeftCells(c1).size());
        assertEquals(0, match.getLeftCells(c1).get(0).getX());
        assertEquals(2, match.getLeftCells(c1).get(0).getY());
        assertEquals(0, match.getLeftCells(c1).get(1).getX());
        assertEquals(1, match.getLeftCells(c1).get(1).getY());
        assertEquals(0, match.getLeftCells(c1).get(2).getX());
        assertEquals(0, match.getLeftCells(c1).get(2).getY());

        assertEquals(1, match.getLeftCells(c2).size());
        assertEquals(0, match.getLeftCells(c2).get(0).getX());
        assertEquals(0, match.getLeftCells(c2).get(0).getY());

        assertEquals(2, match.getLeftCells(c3).size());
        assertEquals(2, match.getLeftCells(c3).get(0).getX());
        assertEquals(1, match.getLeftCells(c3).get(0).getY());
        assertEquals(2, match.getLeftCells(c3).get(1).getX());
        assertEquals(0, match.getLeftCells(c3).get(1).getY());

        assertEquals(1, match.getLeftCells(c4).size());
        assertEquals(2, match.getLeftCells(c4).get(0).getX());
        assertEquals(0, match.getLeftCells(c4).get(0).getY());

        assertEquals(0, match.getRightCells(c1).size());
        assertEquals(2, match.getRightCells(c2).size());
        assertEquals(0, match.getRightCells(c2).get(0).getX());
        assertEquals(2, match.getRightCells(c2).get(0).getY());
        assertEquals(0, match.getRightCells(c2).get(1).getX());
        assertEquals(3, match.getRightCells(c2).get(1).getY());

        assertEquals(0, match.getUpCells(c1).size());
        assertEquals(2, match.getDownCells(c1).size());
        assertEquals(1, match.getDownCells(c1).get(0).getX());
        assertEquals(3, match.getDownCells(c1).get(0).getY());
        assertEquals(2, match.getDownCells(c1).get(1).getX());
        assertEquals(3, match.getDownCells(c1).get(1).getY());

        assertEquals(2, match.getUpCells(c3).size());
        assertEquals(1, match.getUpCells(c3).get(0).getX());
        assertEquals(2, match.getUpCells(c3).get(0).getY());
        assertEquals(0, match.getUpCells(c3).get(1).getX());
        assertEquals(2, match.getUpCells(c3).get(1).getY());
        assertEquals(0, match.getDownCells(c3).size());

    }
}