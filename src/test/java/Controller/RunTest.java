package Controller;

import Model.Match;
import Model.Player;
import exceptions.InvalidDirectionException;
import exceptions.MaxNumberPlayerException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

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
    public void setUp() throws Exception {
        match = new Match();
        r = new Run();
        player1 = new Player("Sirius", "blue", "10583741");
        player2 = new Player("Calypso", "pink", "14253954");
        player3 = new Player("Hermione", "green", "18263100");
        player4 = new Player("Aries", "yellow", "18992302");
        try {
            match.addplayer(player1);
            match.addplayer(player2);
            match.addplayer(player3);
            match.addplayer(player4);
        }
        catch (MaxNumberPlayerException e){
            System.out.println("Too many players in the game.");
        }

        match.createdashboard(3);

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

        assertThrows(InvalidDirectionException.class, () -> r.movement(match, player1, "N"));
        try{
            r.movement(match, player1, "E");
        }
        catch(InvalidDirectionException e){ System.out.println("Invalid direction."); }
        assertEquals(0, player1.getCel().getX());
        assertEquals(3, player1.getCel().getY());
        try{
            r.movement(match, player1, "S");
        }
        catch(InvalidDirectionException e){ System.out.println("Invalid direction."); }

        assertEquals(1, player1.getCel().getX());
        assertEquals(3, player1.getCel().getY());


        //movements test for player4
        assertThrows(InvalidDirectionException.class, () -> r.movement(match, player4, "W"));
        assertThrows(InvalidDirectionException.class, () -> r.movement(match, player4, "E"));
        try{
            r.movement(match, player4, "N");
        }
        catch(InvalidDirectionException e){ System.out.println("Invalid direction."); }
        assertEquals(0, player4.getCel().getX());
        assertEquals(1, player4.getCel().getY());
        try{
            r.movement(match, player4, "W");
        }
        catch(InvalidDirectionException e){ System.out.println("Invalid direction."); }
        assertEquals(0, player4.getCel().getX());
        assertEquals(0, player4.getCel().getY());


        //movements test for player3
        try{
            r.movement(match, player3, "W");
        }
        catch(InvalidDirectionException e){ System.out.println("Invalid direction."); }
        assertEquals(2, player3.getCel().getX());
        assertEquals(1, player3.getCel().getY());
        //assertThrows(InvalidDirectionException.class, () -> r.movement(match, player3, "N"));
        try{
            r.movement(match, player3, "N");
        }
        catch(InvalidDirectionException e){ System.out.println("Invalid direction."); }
        assertEquals(1, player3.getCel().getX());
        assertEquals(1, player3.getCel().getY());
        try{
            r.movement(match, player3, "S");
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