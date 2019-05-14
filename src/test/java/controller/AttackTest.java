package controller;

import exceptions.*;
import model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class AttackTest {

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
        } catch (MaxNumberPlayerException e) {
            System.out.println("Too many players in the game.");
        }

        match.createDashboard(3);

    }

    @Test
    public void damage() {
        grabweapon = new GrabWeapon();
        ArrayList<String> destination = new ArrayList<>();
        player1.setCel(0, 2);
        player2.setCel(0, 2);
        player3.setCel(0, 2);

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
        Coordinate coordinate = new Coordinate(0,2);
        Damage damage=new DamageFactory().getinstaceof(1,5);
        Damage damage2=new DamageFactory().getinstaceof(1,2);
        //System.out.println(player1.gettotaldamage());
        AttackPlayer attackPlayer=new AttackPlayer();
        attackPlayer.assigndamages(match,player2,player1,damage);
        assertEquals(player1.gettotaldamage(),5);
        //System.out.println(player1.gettotaldamage());
        attackPlayer.assigncelldamages(match,coordinate,player2,damage2);
        assertEquals(player1.gettotaldamage(),7);
        assertEquals(player3.gettotaldamage(),2);
    }

    @Test
    public void marks() {
        grabweapon = new GrabWeapon();
        ArrayList<String> destination = new ArrayList<>();
        player1.setCel(0, 2);
        player2.setCel(0, 2);
        player3.setCel(0, 2);

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
        Coordinate coordinate = new Coordinate(0,2);
        Damage damage=new DamageFactory().getinstaceof(2,2);
        Damage damage2=new DamageFactory().getinstaceof(2,2);
        //System.out.println(player1.gettotaldamage());
        AttackPlayer attackPlayer=new AttackPlayer();
        attackPlayer.assigndamages(match,player2,player1,damage);
        System.out.println(player1.getmarks(0));
        System.out.println(player1.getmarks(1));
        System.out.println(player1.getmarks(2));
        System.out.println(player1.getmarks(3));
        //assertEquals(player1.getmarks(0),5);
        System.out.println(player1.gettotaldamage());
        attackPlayer.assigncelldamages(match,coordinate,player2,damage2);
        //assertEquals(player1.gettotaldamage(),7);
        //assertEquals(player3.gettotaldamage(),2);
    }
}