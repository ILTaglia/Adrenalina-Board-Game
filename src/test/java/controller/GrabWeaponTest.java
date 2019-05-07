package controller;

import exceptions.*;
import model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GrabWeaponTest {

    Match match;
    Player player1;
    Player player2;
    Player player3;
    GrabWeapon grabweapon;

    @Before
    public void setUp() throws Exception {
        match = new Match();
        player1 = new Player("Sirius", "blue", "10583741");
        player2 = new Player("Calypso", "pink", "14253954");
        player3 = new Player("Hermione", "green", "18263100");
        try {
            match.addPlayer(player1);
            match.addPlayer(player2);
            match.addPlayer(player3);
        } catch(MaxNumberPlayerException e){System.out.println("Too many players in the game.");}

        match.createDashboard(3);
    }

    @Test
    public void weapon(){
        grabweapon = new GrabWeapon();
        ArrayList<String> destination = new ArrayList<>();
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
            System.out.println("You have too many Weapon Cards, please remove one.");
        }
        SpawnPointCell c = (SpawnPointCell)match.getDashboard().getmap(0, 2);
        try{
            c.Add_Weapon_Card(weapon4, 0);
            c.Add_Weapon_Card(weapon5, 1);
            c.Add_Weapon_Card(weapon6, 2);
        } catch (FullCellException e){}
        assertFalse(grabweapon.isValid(match, player1, destination, 1));
        assertFalse(grabweapon.isValidMovement(match, player1, destination));
        assertEquals(0, player1.getAction());
        //player1 has not enough damages to move before grabbing
        player1.setdamage(4, 3);
        //player1 has enough damages to move before grabbing
        player1.setCel(1,2);
        destination.add("N");
        assertTrue(grabweapon.isValidMovement(match, player1, destination));
        assertFalse(grabweapon.isValid(match, player1, destination, 1));
        try{
            player1.removeWeapon(weapon3);
        } catch (ZeroCardsOwnedException e){}
        catch (NotOwnedCardException e){}
        assertTrue(grabweapon.isValid(match, player1, destination, 1));
        grabweapon.movementBeforeGrab(match, player1, destination);
        assertEquals(0, player1.getCel().getX());
        assertEquals(2, player1.getCel().getY());
        assertEquals(2, player1.getnumberweapon());
        try{
            grabweapon.grabWeapon(match, player1, 0);
        } catch(MaxNumberofCardsException e){}
        assertEquals(3, player1.getnumberweapon());
        assertEquals(1, player1.getAction());

    }
}