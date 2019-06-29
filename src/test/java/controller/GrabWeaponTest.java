package controller;

import exceptions.*;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class GrabWeaponTest {

    Match match;
    Player player1;
    Player player2;
    Player player3;
    GrabWeapon grabweapon;
    Run run;

    Boolean isMovementbeforeGrab;

    @BeforeEach
    public void setUp() {
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
    public void weapon(){
        grabweapon = new GrabWeapon();
        List<String> destination = new ArrayList<>();
        player1.setCel(0,2);

        Ammo redAmmo = new Ammo(0);
        Ammo bluAmmo = new Ammo(1);
        Ammo yellowAmmo = new Ammo(2);

        try{
            player1.addAmmo(redAmmo);
            player1.addAmmo(redAmmo);
            player1.addAmmo(bluAmmo);
            player1.addAmmo(bluAmmo);
            player1.addAmmo(yellowAmmo);
            player1.addAmmo(yellowAmmo);
        }
        catch(MoreThanTreeAmmosException e){}

        assertTrue(grabweapon.isValid(match, player1.getID()));
        try{grabweapon.grabWeapon(match, player1, 0);}
        catch(MaxNumberofCardsException e){}
        try{
            player1.addAmmo(redAmmo);
            player1.addAmmo(redAmmo);
            player1.addAmmo(bluAmmo);
            player1.addAmmo(bluAmmo);
            player1.addAmmo(yellowAmmo);
            player1.addAmmo(yellowAmmo);
        }
        catch(MoreThanTreeAmmosException e){}
        try{grabweapon.grabWeapon(match, player1, 1);}
        catch(MaxNumberofCardsException e){}
        try{
            player1.addAmmo(redAmmo);
            player1.addAmmo(redAmmo);
            player1.addAmmo(bluAmmo);
            player1.addAmmo(bluAmmo);
            player1.addAmmo(yellowAmmo);
            player1.addAmmo(yellowAmmo);
        }
        catch(MoreThanTreeAmmosException e){}
        try{grabweapon.grabWeapon(match, player1, 2);}
        catch(MaxNumberofCardsException e){}
        try{
            player1.addAmmo(redAmmo);
            player1.addAmmo(redAmmo);
            player1.addAmmo(bluAmmo);
            player1.addAmmo(bluAmmo);
            player1.addAmmo(yellowAmmo);
            player1.addAmmo(yellowAmmo);
        }
        catch(MoreThanTreeAmmosException e){}
        assertFalse(grabweapon.isValid(match, player1.getID()));
        assertThrows(MaxNumberofCardsException.class, () -> grabweapon.grabWeapon(match, player1, 0));

        assertTrue(run.isValid(match, destination));
        isMovementbeforeGrab = run.isValid(match, destination);
        player1.resetAction();
        player1.setActive();
        assertThrows(ActionNotAllowedException.class, () -> run.movement(match, player1.getID(), destination, isMovementbeforeGrab, false));
        assertEquals(0, player1.getAction());
        assertFalse(grabweapon.isValid(match, player1.getID()));
        //player1 has not enough damages to move before grabbing
        player1.setDamage(4, 3);
        //player1 has enough damages to move before grabbing
        player1.setCel(0,0);
        destination.add("S");
        assertTrue(run.isValid(match, destination));
        isMovementbeforeGrab = run.isValid(match, destination);
        try{run.movement(match, player1.getID(), destination, isMovementbeforeGrab, false);}
        catch(NotYourTurnException e){}
        catch(InvalidDirectionException e){}
        catch(ActionNotAllowedException e){}
        assertEquals(1, player1.getCel().getX());
        assertEquals(0, player1.getCel().getY());
        assertFalse(grabweapon.isValid(match, player1.getID()));
        assertEquals(3, player1.getNumberWeapon());

        player1.removeWeapon(1);
        assertTrue(grabweapon.isValid(match, player1.getID()));
        try{grabweapon.grabWeapon(match, player1, 1);}
        catch(MaxNumberofCardsException e){}
        assertThrows(MaxNumberofCardsException.class, () -> grabweapon.grabWeapon(match, player1, 0));
        assertEquals(3, player1.getNumberWeapon());
        assertEquals(1, player1.getAction());

    }
}