package controller;

import exceptions.*;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class GrabAmmoTest {

    Match match;
    Player player1;
    Player player2;
    Player player3;
    GrabAmmo grabammo;
    Run run;

    Boolean isMovementbeforeGrab;

    @BeforeEach
    public void setUp() throws Exception {
        match = new Match();
        run = new Run();
        match.createPlayer("Sirius", "Blue", "10583741");
        match.createPlayer("Calypso", "Pink", "14253954");
        match.createPlayer("Hermione", "Green", "18263100");

        match.createDashboard(3);
        match.fillDashboard();
        player1 = match.getPlayerByIndex(0);
        player2 = match.getPlayerByIndex(1);
        player3 = match.getPlayerByIndex(2);
        player1.setActive();
    }

    @Test
    public void ammo(){
        assertEquals(1, player1.getAmmo(0));
        assertEquals(1, player1.getAmmo(1));
        assertEquals(1, player1.getAmmo(2));
        ArrayList<String> destination = new ArrayList<>();

        player1.setCel(1,0);

        grabammo = new GrabAmmo();
        assertEquals(0, player1.getNumberPow());
        assertTrue(run.isValid(match, destination));
        isMovementbeforeGrab = run.isValid(match, destination);
        try{run.movement(match, player1.getID(), destination, isMovementbeforeGrab, false);}
        catch(NotYourTurnException e){}
        catch(InvalidDirectionException e){}
        catch(ActionNotAllowedException e){}
        assertTrue(grabammo.isValid(match, player1.getID()));
        destination.add("N");
        destination.add("E");
        assertTrue(run.isValid(match, destination));
        assertThrows(ActionNotAllowedException.class, () -> run.movement(match, player1.getID(), destination, isMovementbeforeGrab, false));
        assertEquals(0, player1.getAction());
        //player1 has not enough damages to move before grabbing
        player1.setDamage(4, 3);
        destination.remove("E");
        //player1 has enough damages to move before grabbing

        assertTrue(run.isValid(match, destination));
        isMovementbeforeGrab = run.isValid(match, destination);
        try{run.movement(match, player1.getID(), destination, isMovementbeforeGrab, false);}
        catch(NotYourTurnException e){}
        catch(InvalidDirectionException e){}
        catch(ActionNotAllowedException e){}
        assertTrue(grabammo.isValid(match, player1.getID()));
        assertEquals(0, player1.getCel().getX());
        assertEquals(0, player1.getCel().getY());
        try {
            grabammo.grabAmmo(match, player1.getID()); }
        catch(MaxNumberofCardsException e){}
        catch(CardAlreadyCollectedException e){}
        catch(NotYourTurnException e){}
        assertEquals(1, player1.getAction());
        assertEquals(2, player1.getAmmo(0));
        assertEquals(3, player1.getAmmo(1));
        assertEquals(1, player1.getAmmo(2));
        assertEquals(0, player1.getNumberPow());

        destination.set(0, "E");
        try{run.movement(match, player1.getID(), destination, isMovementbeforeGrab, false);}
        catch(NotYourTurnException e){}
        catch(InvalidDirectionException e){}
        catch(ActionNotAllowedException e){}
        try { grabammo.grabAmmo(match, player1.getID()); }
        catch(MaxNumberofCardsException e){}
        catch(CardAlreadyCollectedException e){}
        catch(NotYourTurnException e){}
        player1.setCel(0, 0);
        NormalCell cell = (NormalCell)match.getDashboard().getMap(0, 0);
        System.out.println(cell.getAmmoCard().toString());
        assertEquals(2, player1.getAction());
        assertEquals(3, player1.getAmmo(0));
        assertEquals(3, player1.getAmmo(1));
        assertEquals(1, player1.getAmmo(2));
    }

    @Test
    public void validityOFActive(){
        assertTrue(run.isValid(match, player1.getID()));
        assertFalse(run.isValid(match, player2.getID()));
        player1.setAction();
        player1.setAction();
        player1.setAction();
        assertFalse(run.isValid(match, player1.getID()));
    }
}