package controller;

import exceptions.ActionNotAllowedException;
import exceptions.NotYourTurnException;
import model.Match;
import model.Coordinate;
import model.Player;
import exceptions.InvalidDirectionException;
import exceptions.MaxNumberPlayerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RunTest {
    Match match;
    Run r;
    Player player1;
    Player player2;
    Player player3;
    Player player4;

    @BeforeEach
    public void setUp() throws Exception, MaxNumberPlayerException {
        match = new Match();
        r = new Run();
        match.createPlayer("Sirius", "Blue", "10583741");
        match.createPlayer("Calypso", "Pink", "14253954");
        match.createPlayer("Hermione", "Green", "18263100");
        match.createPlayer("Aries", "Yellow", "18992302");

        match.createDashboard(3);

        player1 = match.getPlayerByIndex(0);
        player2 = match.getPlayerByIndex(1);
        player3 = match.getPlayerByIndex(2);
        player4 = match.getPlayerByIndex(3);
        player1.setActive();

        player1.setCel(0, 2);
        player2.setCel(0, 1);
        player3.setCel(2, 2);
        player4.setCel(1, 1);
    }

    @Test
    public void movement1() {
        //movements test for player1
        assertEquals(0, player1.getCel().getX());
        assertEquals(2, player1.getCel().getY());
        //ArrayList<String> destination = new ArrayList<>();
        List<String> destination = new ArrayList<>();
        destination.add("N");

        try{
            r.movement(match, player1.getID(), destination, false, false);
        }
        catch(InvalidDirectionException e){}
        catch(NotYourTurnException e){}
        catch(ActionNotAllowedException e){}
        //assertFalse(r.atomicValidity(match.getDashboard(), player1, 0, 2, 0));
        assertEquals(0, player1.getCel().getX());
        assertEquals(2, player1.getCel().getY());
        destination.set(0, "E");
        try{
            r.movement(match, player1.getID(), destination, false, false);
        }
        catch(InvalidDirectionException e){}
        catch(NotYourTurnException e){}
        catch(ActionNotAllowedException e){}
        assertEquals(0, player1.getCel().getX());
        assertEquals(3, player1.getCel().getY());
        destination.set(0, "S");
        try{
            r.movement(match, player1.getID(), destination, false, false);
        }
        catch(InvalidDirectionException e){}
        catch(NotYourTurnException e){}
        catch(ActionNotAllowedException e){}

        assertEquals(1, player1.getCel().getX());
        assertEquals(3, player1.getCel().getY());


        //movements test for player4
        player1.resetActive();
        player4.setActive();
        destination.set(0, "W");

        assertThrows(InvalidDirectionException.class, () -> r.movement(match, player4.getID(), destination, false, false));
        assertEquals(1, player4.getCel().getX());
        assertEquals(1, player4.getCel().getY());
        destination.set(0, "N");
        try{
            r.movement(match, player4.getID(), destination, false, false);
        }
        catch(InvalidDirectionException e){}
        catch(NotYourTurnException e){}
        catch(ActionNotAllowedException e){}
        assertEquals(0, player4.getCel().getX());
        assertEquals(1, player4.getCel().getY());
        destination.set(0, "W");
        try{
            r.movement(match, player4.getID(), destination, false, false);
        }
        catch(InvalidDirectionException e){}
        catch(NotYourTurnException e){}
        catch(ActionNotAllowedException e){}
        assertEquals(0, player4.getCel().getX());
        assertEquals(0, player4.getCel().getY());


        //movements test for player3
        player4.resetActive();
        player3.setActive();
        try{
            r.movement(match, player3.getID(), destination, false, false);
        }
        catch(InvalidDirectionException e){}
        catch(NotYourTurnException e){}
        catch(ActionNotAllowedException e){}
        assertEquals(2, player3.getCel().getX());
        assertEquals(1, player3.getCel().getY());
        destination.set(0, "N");
        try{
            r.movement(match, player3.getID(), destination, false, false);
        }
        catch(InvalidDirectionException e){}
        catch(NotYourTurnException e){}
        catch(ActionNotAllowedException e){}
        assertEquals(1, player3.getCel().getX());
        assertEquals(1, player3.getCel().getY());
        destination.set(0, "S");
        try{
            r.movement(match, player3.getID(), destination, false, false);
        }
        catch(InvalidDirectionException e){}
        catch(NotYourTurnException e){}
        catch(ActionNotAllowedException e){}
        assertEquals(2, player3.getCel().getX());
        assertEquals(1, player3.getCel().getY());
    }

    @Test
    public void registerMovement(){
        assertEquals(0, player1.getAction());
        player1.setActive();
        Run run = new Run();
        run.registerMovementAction(match);
        assertEquals(1, player1.getAction());
    }

    @Test
    public void resetPosition(){
        player1.setCel(0,2);
        assertEquals(0, player1.getCel().getX());
        assertEquals(2, player1.getCel().getY());
        Coordinate c = new Coordinate(1, 1);
        Run run = new Run();
        run.resetPosition(player1, c);
        assertEquals(1, player1.getCel().getX());
        assertEquals(1, player1.getCel().getY());
    }

    @Test
    public void validity(){
        int x=player1.getCel().getX();
        int y=player1.getCel().getY();
        ArrayList<String> destination = new ArrayList<>();
        destination.add("S");
        destination.add("E");
        destination.add("N");
        assertTrue(r.isValid(match, destination));
        try{
            r.movement(match, player1.getID(), destination, false, false);
        }
        catch(InvalidDirectionException e){}
        catch(NotYourTurnException e){}
        catch(ActionNotAllowedException e){}
        assertEquals(0, player1.getCel().getX());
        assertEquals(3, player1.getCel().getY());

        x=player4.getCel().getX();
        y=player4.getCel().getY();
        destination.set(0, "N");
        destination.set(1, "E");
        destination.set(2, "E");
        player1.resetActive();
        player4.setActive();
        assertTrue(r.isValid(match, destination));
        try{
            r.movement(match, player4.getID(), destination, false, false);
        }
        catch(InvalidDirectionException e){}
        catch(NotYourTurnException e){}
        catch(ActionNotAllowedException e){}
        assertEquals(0, player4.getCel().getX());
        assertEquals(3, player4.getCel().getY());

        destination.add("D");
        assertThrows(InvalidDirectionException.class, () -> r.movement(match, player4.getID(), destination, false, false));
        destination.remove("D");

        player4.resetActive();
        player3.setActive();
        destination.set(2, "N");
        destination.add("W");
        assertThrows(InvalidDirectionException.class, () -> r.movement(match, player3.getID(), destination, false, false));
        destination.remove("W");
        try{
            r.movement(match, player3.getID(), destination, false, false);
        }
        catch(InvalidDirectionException e){}
        catch(NotYourTurnException e){}
        catch(ActionNotAllowedException e){}
        assertEquals(0, player3.getCel().getX());
        assertEquals(3, player3.getCel().getY());

        destination.set(0, "W");
        destination.set(1, "S");
        destination.set(2, "E");
        player3.resetActive();
        player2.setActive();
        assertFalse(r.isValid(match, destination));
        try{
            r.movement(match, player2.getID(), destination, false, false);
        }
        catch(InvalidDirectionException e){}
        catch(NotYourTurnException e){}
        catch(ActionNotAllowedException e){}
        assertEquals(0, player2.getCel().getX());
        assertEquals(1, player2.getCel().getY());
        destination.set(2, "S");
        try{
            r.movement(match, player2.getID(), destination, false, false);
        }
        catch(InvalidDirectionException e){}
        catch(NotYourTurnException e){}
        catch(ActionNotAllowedException e){}
        assertEquals(2, player2.getCel().getX());
        assertEquals(0, player2.getCel().getY());
    }
}