package controller;

import exceptions.CardAlreadyCollectedException;
import exceptions.FullCellException;
import exceptions.MaxNumberofCardsException;
import model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GrabAmmoTest {

    Match match;
    Player player1;
    Player player2;
    Player player3;
    GrabAmmo grabammo;
    /*
    @Before
    public void setUp() throws Exception {
        match = new Match();
        match.createPlayer("Sirius", "Blue", "10583741");
        match.createPlayer("Calypso", "Pink", "14253954");
        match.createPlayer("Hermione", "Green", "18263100");

        match.createDashboard(3);
        player1 = match.getPlayerByIndex(0);
        player2 = match.getPlayerByIndex(1);
        player3 = match.getPlayerByIndex(2);
    }

    @Test
    public void ammo(){
        assertEquals(1, player1.getAmmo(0));
        assertEquals(1, player1.getAmmo(1));
        assertEquals(1, player1.getAmmo(2));
        AmmoTile ammoTile = new AmmoTile(0,0,1);
        AmmoPowTile ammoPowTile = new AmmoPowTile(2,1);
        ArrayList<String> destination = new ArrayList<>();

        player1.setCel(1,0);
        NormalCell cell1 = (NormalCell)player1.getCel().inmap(match.getDashboard(), 0,0);
        NormalCell cell2 = (NormalCell)player1.getCel().inmap(match.getDashboard(), 0,1);
        try{
            cell1.addAmmoCard(ammoTile);
            cell2.addAmmoCard(ammoPowTile);
        } catch (FullCellException e){}

        grabammo = new GrabAmmo();
        assertEquals(0, player1.getNumberPow());
        assertTrue(grabammo.isValid(match, player1, destination));
        assertFalse(grabammo.isValidMovement(match, player1, destination));
        destination.add("N");
        assertFalse(grabammo.isValid(match, player1, destination));
        assertFalse(grabammo.isValidMovement(match, player1, destination));
        assertEquals(0, player1.getAction());
        //player1 has not enough damages to move before grabbing
        player1.setDamage(4, 3);
        //player1 has enough damages to move before grabbing

        assertTrue(grabammo.isValid(match, player1, destination));
        assertTrue(grabammo.isValidMovement(match, player1, destination));
        grabammo.movementBeforeGrab(match, player1, destination);
        assertEquals(0, player1.getCel().getX());
        assertEquals(0, player1.getCel().getY());
        try {
            grabammo.grabAmmo(match, player1);
        } catch(MaxNumberofCardsException e){}
        catch(CardAlreadyCollectedException e){}
        assertEquals(1, player1.getAction());
        assertEquals(3, player1.getAmmo(0));
        assertEquals(2, player1.getAmmo(1));
        assertEquals(1, player1.getAmmo(2));

        destination.set(0, "E");
        grabammo.movementBeforeGrab(match, player1, destination);
        try {
            grabammo.grabAmmo(match, player1);
        } catch(MaxNumberofCardsException e){}
        catch(CardAlreadyCollectedException e){}
        assertEquals(2, player1.getAction());
        assertEquals(3, player1.getAmmo(0));
        assertEquals(3, player1.getAmmo(1));
        assertEquals(2, player1.getAmmo(2));
        assertEquals(2, player1.getNumberPow());
    }
    */
}