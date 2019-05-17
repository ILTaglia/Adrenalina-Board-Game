package controller;

import exceptions.*;
import model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PresenceCheckTest {

    Match match;
    Player player1;
    Player player2;
    Player player3;
    GrabWeapon grabweapon;

    @Before
    public void setUp() throws Exception {
        match = new Match();
        player1 = new Player("Sirius", "Blue", "10583741");
        player2 = new Player("Calypso", "Pink", "14253954");
        player3 = new Player("Hermione", "Green", "18263100");
        try {
            match.addPlayer(player1);
            match.addPlayer(player2);
            match.addPlayer(player3);
        } catch (MaxNumberPlayerException e) {
            System.out.println("Too many players in the game.");
        }

        match.createDashboard(3);

    }

    @Test
    public void testplayer() {
        grabweapon = new GrabWeapon();
        ArrayList<String> destination = new ArrayList<>();
        player1.setCel(0, 3);
        player2.setCel(0, 2);
        player3.setCel(0, 1);

        WeaponDeck weaponDeck = new WeaponDeck();
        weaponDeck.setWeapons("Armi");
        weaponDeck.drawCard();
        Weapon weapon1 = (Weapon) weaponDeck.drawCard();
        Weapon weapon2 = (Weapon) weaponDeck.drawCard();
        Weapon weapon3 = (Weapon) weaponDeck.drawCard();
        Weapon weapon4 = (Weapon) weaponDeck.drawCard();
        Weapon weapon5 = (Weapon) weaponDeck.drawCard();
        Weapon weapon6 = (Weapon) weaponDeck.drawCard();
        try {
            player1.addWeapon(weapon1);
            player1.addWeapon(weapon2);
            player1.addWeapon(weapon3);

        } catch (MaxNumberofCardsException e) {
            System.out.println("You have too many Weapon Cards, please remove one.");
        }
        ArrayList<Player> lista =match.getVisiblePlayers(player2);
        CheckIFPresent check= new CheckIFPresent();
        assertEquals(check.check(player1,lista),true);
        assertEquals(check.check(player2,lista),false);

    }

    @Test
    public void checkCell()
    {
        grabweapon = new GrabWeapon();
        ArrayList<String> destination = new ArrayList<>();
        player1.setCel(0, 3);
        player2.setCel(0, 2);
        player3.setCel(0, 1);

        WeaponDeck weaponDeck = new WeaponDeck();
        weaponDeck.setWeapons("Armi");
        weaponDeck.drawCard();
        Weapon weapon1 = (Weapon) weaponDeck.drawCard();
        Weapon weapon2 = (Weapon) weaponDeck.drawCard();
        Weapon weapon3 = (Weapon) weaponDeck.drawCard();
        Weapon weapon4 = (Weapon) weaponDeck.drawCard();
        Weapon weapon5 = (Weapon) weaponDeck.drawCard();
        Weapon weapon6 = (Weapon) weaponDeck.drawCard();
        try {
            player1.addWeapon(weapon1);
            player1.addWeapon(weapon2);
            player1.addWeapon(weapon3);

        } catch (MaxNumberofCardsException e) {
            System.out.println("You have too many Weapon Cards, please remove one.");
        }
        ArrayList<Coordinate> lista =match.getVisibleCells(player2.getCel());
        CheckIFPresent check= new CheckIFPresent();
        assertEquals(check.check(player1.getCel(),lista),true);
        assertEquals(check.check(player2.getCel(),lista),false);
    }
}
