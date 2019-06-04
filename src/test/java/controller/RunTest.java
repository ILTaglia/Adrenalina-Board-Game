package controller;

import model.Match;
import model.Player;
import exceptions.InvalidDirectionException;
import exceptions.MaxNumberPlayerException;
import org.junit.Before;
import org.junit.Test;

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

    @Before
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
            r.getMovement(match, player1, destination);
        }
        catch(InvalidDirectionException e){ System.out.println("Invalid direction."); }
        assertFalse(r.atomicValidity(match.getDashboard(), player1, 0, 2, 0));
        assertEquals(0, player1.getCel().getX());
        assertEquals(2, player1.getCel().getY());
        destination.set(0, "E");
        try{
            r.getMovement(match, player1, destination);
        }
        catch(InvalidDirectionException e){ System.out.println("Invalid direction."); }
        assertEquals(0, player1.getCel().getX());
        assertEquals(3, player1.getCel().getY());
        destination.set(0, "S");
        try{
            r.getMovement(match, player1, destination);
        }
        catch(InvalidDirectionException e){ System.out.println("Invalid direction."); }

        assertEquals(1, player1.getCel().getX());
        assertEquals(3, player1.getCel().getY());


        //movements test for player4
        destination.set(0, "W");
        try{
            r.getMovement(match, player4, destination);
        }
        catch(InvalidDirectionException e){ System.out.println("Invalid direction."); }
        assertEquals(1, player4.getCel().getX());
        assertEquals(1, player4.getCel().getY());
        destination.set(0, "W");
        try{
            r.getMovement(match, player4, destination);
        }
        catch(InvalidDirectionException e){ System.out.println("Invalid direction."); }
        assertEquals(1, player4.getCel().getX());
        assertEquals(1, player4.getCel().getY());
        destination.set(0, "N");
        try{
            r.getMovement(match, player4, destination);
        }
        catch(InvalidDirectionException e){ System.out.println("Invalid direction."); }
        assertEquals(0, player4.getCel().getX());
        assertEquals(1, player4.getCel().getY());
        destination.set(0, "W");
        try{
            r.getMovement(match, player4, destination);
        }
        catch(InvalidDirectionException e){ System.out.println("Invalid direction."); }
        assertEquals(0, player4.getCel().getX());
        assertEquals(0, player4.getCel().getY());


        //movements test for player3
        try{
            r.getMovement(match, player3, destination);
        }
        catch(InvalidDirectionException e){ System.out.println("Invalid direction."); }
        assertEquals(2, player3.getCel().getX());
        assertEquals(1, player3.getCel().getY());
        destination.set(0, "N");
        try{
            r.getMovement(match, player3, destination);
        }
        catch(InvalidDirectionException e){ System.out.println("Invalid direction."); }
        assertEquals(1, player3.getCel().getX());
        assertEquals(1, player3.getCel().getY());
        destination.set(0, "S");
        try{
            r.getMovement(match, player3, destination);
        }
        catch(InvalidDirectionException e){ System.out.println("Invalid direction."); }
        assertEquals(2, player3.getCel().getX());
        assertEquals(1, player3.getCel().getY());
    }

    @Test
    public void validity(){
        int x=player1.getCel().getX();
        int y=player1.getCel().getY();
        ArrayList<String> destination = new ArrayList<>();
        destination.add("S");
        destination.add("E");
        destination.add("N");
        assertTrue(r.isValid(match, player1, x, y, destination));
        try{
            r.getMovement(match, player1, destination);
        } catch(InvalidDirectionException e){}
        assertEquals(0, player1.getCel().getX());
        assertEquals(3, player1.getCel().getY());

        x=player4.getCel().getX();
        y=player4.getCel().getY();
        destination.set(0, "N");
        destination.set(1, "E");
        destination.set(2, "E");
        assertTrue(r.isValid(match, player4, x, y, destination));
        try{
            r.getMovement(match, player4, destination);
        } catch(InvalidDirectionException e){}
        assertEquals(0, player4.getCel().getX());
        assertEquals(3, player4.getCel().getY());

        destination.add("D");
        assertThrows(InvalidDirectionException.class, () -> r.getMovement(match, player4, destination));
        destination.remove("D");

        destination.set(2, "N");
        destination.add("W");
        assertThrows(InvalidDirectionException.class, () -> r.getMovement(match, player3, destination));
        destination.remove("W");
        try{
            r.getMovement(match, player3, destination);
        } catch(InvalidDirectionException e){}
        assertEquals(0, player3.getCel().getX());
        assertEquals(3, player3.getCel().getY());

        destination.set(0, "W");
        destination.set(1, "S");
        destination.set(2, "E");
        assertFalse(r.isValid(match, player2, player2.getCel().getX(), player2.getCel().getY(), destination));
        try{
            r.getMovement(match, player2, destination);
        } catch(InvalidDirectionException e){}
        assertEquals(0, player2.getCel().getX());
        assertEquals(1, player2.getCel().getY());
        destination.set(2, "S");
        try{
            r.getMovement(match, player2, destination);
        } catch(InvalidDirectionException e){}
        assertEquals(2, player2.getCel().getX());
        assertEquals(0, player2.getCel().getY());
    }
}