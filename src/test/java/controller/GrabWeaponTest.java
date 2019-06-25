package controller;

import exceptions.*;
import model.*;
import org.junit.Before;
import org.junit.Test;

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
    /*
    @Before
    public void setUp() {
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
    public void weapon(){
        grabweapon = new GrabWeapon();
        List<String> destination = new ArrayList<>();
        player1.setCel(0,2);
        WeaponDeck weaponDeck = new WeaponDeck();
        weaponDeck.setWeapons("Armi");
        weaponDeck.drawCard();
        Weapon weapon1 = (Weapon)weaponDeck.drawCard();
        Weapon weapon2 = (Weapon)weaponDeck.drawCard();
        Weapon weapon3 = (Weapon)weaponDeck.drawCard();
        Weapon weapon4 = (Weapon)weaponDeck.drawCard();
        Weapon weapon5 = (Weapon)weaponDeck.drawCard();
        Weapon weapon6 = (Weapon)weaponDeck.drawCard();
        try {
            player1.addWeapon(weapon1);
            player1.addWeapon(weapon2);
            player1.addWeapon(weapon3);

        }
        catch (MaxNumberofCardsException e){
            System.out.println("You have too many Weapon Cards, please discardWeapon one.");
        }
        SpawnPointCell c = (SpawnPointCell)match.getDashboard().getMap(0, 2);
        try{
            c.addWeaponCard(weapon4, 0);
            c.addWeaponCard(weapon5, 1);
            c.addWeaponCard(weapon6, 2);
        } catch (FullCellException e){}
        assertThrows(MaxNumberofCardsException.class, () -> grabweapon.isValid(match, player1, destination));
        try{grabweapon.isValid(match, player1, destination);}
        catch(MaxNumberofCardsException e){}
        //assertDoesNotThrow(MaxNumberofCardsException.class, () -> grabweapon.isValid(match, player1, destination));
        assertFalse(grabweapon.isValidMovement(match, player1, destination));
        assertEquals(0, player1.getAction());
        //player1 has not enough damages to move before grabbing
        player1.setDamage(4, 3);
        //player1 has enough damages to move before grabbing
        player1.setCel(1,2);
        destination.add("N");
        assertTrue(grabweapon.isValidMovement(match, player1, destination));
        try{grabweapon.isValid(match, player1, destination);}
        catch(MaxNumberofCardsException e){}
        try{
            player1.removeWeapon(weapon3);
        } catch (ZeroCardsOwnedException e){}
        catch (NotOwnedCardException e){}
        try{grabweapon.isValid(match, player1, destination);}
        catch(MaxNumberofCardsException e){}
        grabweapon.movementBeforeGrab(match, player1, destination);
        assertEquals(0, player1.getCel().getX());
        assertEquals(2, player1.getCel().getY());
        assertEquals(2, player1.getNumberWeapon());
        try{
            grabweapon.grabWeapon(match, player1, 0);
        } catch(MaxNumberofCardsException e){}
        assertEquals(3, player1.getNumberWeapon());
        assertEquals(1, player1.getAction());

    }
    */
}