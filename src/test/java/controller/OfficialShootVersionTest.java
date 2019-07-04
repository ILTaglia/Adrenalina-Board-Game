package controller;

import exceptions.MaxNumberofCardsException;
import model.Match;
import model.Player;
import model.Weapon;
import model.WeaponDeck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
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

public class OfficialShootVersionTest {
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
        grabweapon = new GrabWeapon();
        ArrayList<String> destination = new ArrayList<>();
        player1.setCel(0, 2);
        player2.setCel(0, 1);
        player3.setCel(1, 2);

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

    }


    @Test
    public void checkeffects()
    {
        OfficialShootVersion shootElaborator= new OfficialShootVersion(match,player1);
        shootElaborator.chooseweapon(player1.getWeaponByIndex(0));
        List<Integer> listtypes = shootElaborator.gettypes();

        assertEquals(listtypes.size(),3);
        for(int i : listtypes)
        {
            assertEquals(i, 0);
        }
    }
}
