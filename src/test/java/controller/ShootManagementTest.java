package controller;

import exceptions.*;
import model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ShootManagementTest {

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
            System.out.println("You have too many Weapon Cards, please remove one.");
        }

    }

    @Test
    public void firstattackvisibletest()
    {
        ShootManagement shoot = new ShootManagement();
        ArrayList<Player> visible = match.getVisiblePlayers(player1);
        Damage d = new DamageFactory().getinstaceof(1,3);
        int a =shoot.shoot(match, visible,player1,player2,0, d);
        assertEquals(a,0);
        Damage b = new DamageFactory().getinstaceof(1,4);
        a= shoot.shoot(match, visible,player1,player3,1, b);
        assertEquals(a,0);
        visible= match.getVisiblePlayers(player1);
        assertEquals(visible.get(0).gettotaldamage(),3);
        assertEquals(visible.get(1).gettotaldamage(),4);

    }

    @Test
    public void secondattackincorrectID()
    {
        ShootManagement shoot = new ShootManagement();
        ArrayList<Player> visible = match.getVisiblePlayers(player1);
        Damage d = new DamageFactory().getinstaceof(1,3);
        int a =shoot.shoot(match, visible,player1,player2,0, d);
        assertEquals(a,0);
        a= shoot.shoot(match, visible,player1,player2,1, d);
        assertEquals(a,-1);
        visible= match.getVisiblePlayers(player1);
        assertEquals(visible.get(0).gettotaldamage(),3);
        assertEquals(visible.get(1).gettotaldamage(),0);
    }

    @Test
    public void attacknotvisible()
    {
        player1.setCel(2, 3);
        ArrayList<Player> visible = match.getVisiblePlayers(player1);
        ShootManagement shoot = new ShootManagement();
        Damage d = new DamageFactory().getinstaceof(1,3);
        int a =shoot.shoot(match, visible,player1,player2,0, d);
        assertEquals(a,-2);
        ArrayList<Player> all = match.getPlayers();
        for(Player p : all)
        {
            assertEquals(p.gettotaldamage(),0);
        }
    }









}