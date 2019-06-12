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

    @Test
    public void markstest()
    {
        ShootManagement shoot = new ShootManagement();
        ArrayList<Player> visible = match.getVisiblePlayers(player1);
        Damage d = new DamageFactory().getinstaceof(2,3);
        int a =shoot.shoot(match, visible,player1,player2,0, d);
        assertEquals(a,0);
        Damage b = new DamageFactory().getinstaceof(2,2);
        a= shoot.shoot(match, visible,player1,player3,1, b);
        assertEquals(a,0);
        visible= match.getVisiblePlayers(player1);
        assertEquals(visible.get(0).getmarks(player1.getcolor()),3);
        assertEquals(visible.get(1).getmarks(player1.getcolor()),2);

    }

    @Test
    public void attackfirstcell()
    {
        player2.setCel(0, 1);
        player3.setCel(0, 1);
        ShootManagement shoot = new ShootManagement();
        ArrayList<Coordinate> visible = match.getVisibleCells(player1.getCel());
        Damage d = new DamageFactory().getinstaceof(1,3);
        int a =shoot.shoot(match,visible,player1,player2.getCel(),0,d);
        assertEquals(a,0);
        ArrayList<Player>visibles = match.getVisiblePlayers(player1);
        for(Player p:visibles)
        {
            assertEquals(p.gettotaldamage(),3);
        }
    }

    @Test
    public void attackwrongIDcell()
    {
        player2.setCel(0, 2);
        player3.setCel(0, 2);
        ShootManagement shoot = new ShootManagement();
        ArrayList<Coordinate> visible = match.getVisibleCells(player1.getCel());
        Damage d = new DamageFactory().getinstaceof(1,3);
        int a =shoot.shoot(match,visible,player1,player2.getCel(),0,d);
        assertEquals(a,0);
        ArrayList<Player>visibles = match.getVisiblePlayers(player1);
        for(Player p:visibles)
        {
            assertEquals(p.gettotaldamage(),3);
        }
        a =shoot.shoot(match,visible,player1,player2.getCel(),1,d);
        assertEquals(a,-1);
        for(Player p:visibles)
        {
            assertEquals(p.gettotaldamage(),3);
        }
    }

    @Test
    public void unreackblecells()
    {
        player2.setCel(1, 0);
        player3.setCel(1, 0);
        ShootManagement shoot = new ShootManagement();
        ArrayList<Coordinate> visible = match.getVisibleCells(player1.getCel());
        Damage d = new DamageFactory().getinstaceof(1,3);
        int a =shoot.shoot(match,visible,player1,player2.getCel(),0,d);
        assertEquals(a,-2);
        ArrayList<Player>visibles = match.getVisiblePlayers(player1);
        for(Player p:visibles)
        {
            assertEquals(p.gettotaldamage(),0);
        }
    }







}