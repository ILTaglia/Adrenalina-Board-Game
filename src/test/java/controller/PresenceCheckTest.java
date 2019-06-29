package controller;

import exceptions.*;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PresenceCheckTest {

    Match match;
    Player player1;
    Player player2;
    Player player3;
    GrabWeapon grabweapon;

    @BeforeEach
    public void setUp() throws Exception {
        match = new Match();
        match.createPlayer("Sirius", "Blue", "10583741");
        match.createPlayer("Calypso", "Pink", "14253954");
        match.createPlayer("Hermione", "Green", "18263100");
        player1 = match.getPlayerByIndex(0);
        player2 = match.getPlayerByIndex(1);
        player3 = match.getPlayerByIndex(2);

        match.createDashboard(3);

    }

    @Test
    public void testplayer() {
        grabweapon = new GrabWeapon();
        List<String> destination = new ArrayList<>();
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
            System.out.println("You have too many Weapon Cards, please discardWeapon one.");
        }
        List<Player> lista =match.getVisiblePlayers(player2);
        CheckIFPresent check= new CheckIFPresent();
        assertEquals(check.check(player1,lista),true);
        assertEquals(check.check(player2,lista),false);

    }

    @Test
    public void checkCell()
    {
        grabweapon = new GrabWeapon();
        List<String> destination = new ArrayList<>();
        player1.setCel(0, 3);
        player2.setCel(0, 2);
        player3.setCel(2, 0);

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
            System.out.println("You have too many Weapon Cards, please discardWeapon one.");
        }
        List<Coordinate> lista =match.getVisibleCells(player2.getCel());
        CheckIFPresent check= new CheckIFPresent();
        assertEquals(check.check(player1.getCel(),lista),true);
        assertEquals(check.check(player2.getCel(),lista),true);
        assertEquals(check.check(player3.getCel(),lista),false);
    }
}
