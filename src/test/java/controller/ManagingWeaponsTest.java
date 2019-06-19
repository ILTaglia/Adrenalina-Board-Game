package controller;

import exceptions.MaxNumberofCardsException;
import exceptions.MoreThanTreeAmmosException;
import exceptions.NotEnoughAmmosException;
import model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ManagingWeaponsTest {

    Match match;
    Dashboard d;
    Player player1;
    Player player2;
    Player player3;
    Player player4;

    WeaponDeck WeaponDeck;
    Weapon weapon;
    PowDeck PowDeck;
    PowCard powcard;

    ManagingWeapons manage;


    @Before
    public void SetUp(){
        match = new Match();
        manage = new ManagingWeapons(match);
        match.createPlayer("Sirius", "Blue", "10583741");
        match.createPlayer("Calypso", "Pink", "14253954");
        match.createPlayer("Hermione", "Green", "18263100");
        match.createPlayer("Aries", "Yellow", "18992302");

        player1 = match.getPlayerByIndex(0);
        player2 = match.getPlayerByIndex(1);
        player3 = match.getPlayerByIndex(2);
        player4 = match.getPlayerByIndex(3);

        match.createDashboard(3);
        match.fillDashboard();
        d = match.getDashboard();

        WeaponDeck= new WeaponDeck();
        WeaponDeck.setWeapons("Test");
        weapon = (Weapon) WeaponDeck.drawCard();

        PowDeck = new PowDeck("Pow");
        powcard = (PowCard) PowDeck.drawCard();

    }

    @Test
    public void recharge() {

        //TODO
    }

    @Test
    public void discardWeapon() {
        try{player1.addWeapon(weapon);}
        catch(MaxNumberofCardsException e){}
        assertEquals(1, player1.getNumberWeapon());
        manage.discardWeapon(player1, 0);
        assertEquals(0, player1.getNumberWeapon());
    }

    @Test
    public void discardPowCard() {
        try{match.assignPowCard(player1);}
        catch(MaxNumberofCardsException e){}
        assertEquals(1, player1.getPows().size());
        manage.discardPowCard(player1, 0);
        assertEquals(0, player1.getPows().size());
    }

    @Test
    public void areEnoughAmmoToGrabWeapon() {
        Ammo redAmmo = new Ammo(0);
        Ammo blueAmmo = new Ammo(1);
        Ammo yellowAmmo = new Ammo(2);
        try{
            player1.removeAmmo(1, redAmmo);
            player1.removeAmmo(1, blueAmmo);
            player1.removeAmmo(1, yellowAmmo);
        }
        catch(NotEnoughAmmosException e){}
        player1.setCel(0, 2);
        player1.setActive();
        SpawnPointCell spawnPointCell = (SpawnPointCell)d.getMap(0, 2);
        Weapon weaponToGrab = spawnPointCell.getSpawnPointCellWeapons().get(1);
        assertFalse(manage.areEnoughAmmoToGrabWeapon(player1, weaponToGrab.returnPrice()));
        try{
            player1.addAmmo(redAmmo);
            player1.addAmmo(redAmmo);
            player1.addAmmo(redAmmo);
            player1.addAmmo(blueAmmo);
            player1.addAmmo(blueAmmo);
            player1.addAmmo(blueAmmo);
            player1.addAmmo(yellowAmmo);
            player1.addAmmo(yellowAmmo);
            player1.addAmmo(yellowAmmo);
        }
        catch(MoreThanTreeAmmosException e){}
        assertTrue(manage.areEnoughAmmoToGrabWeapon(player1, weaponToGrab.returnPrice()));
    }

    @Test
    public void convertPowToGrab() {
        Ammo redAmmo = new Ammo(0);
        Ammo blueAmmo = new Ammo(1);
        try{
            player1.removeAmmo(1, redAmmo);
        }
        catch(NotEnoughAmmosException e){}
        try{player1.addAmmo(blueAmmo); }
        catch(MoreThanTreeAmmosException e){}
        try{match.assignPowCard(player1);}
        catch(MaxNumberofCardsException e){}
        player1.setCel(0, 2);
        player1.setActive();
        SpawnPointCell spawnPointCell = (SpawnPointCell)d.getMap(0, 2);
        Weapon weapontograb = spawnPointCell.getSpawnPointCellWeapons().get(1);
        assertFalse(manage.areEnoughAmmoToGrabWeapon(player1, weapontograb.returnPrice()));
        assertEquals(0, player1.getAmmo(0));
        try{manage.convertPowToGrab(player1, weapontograb.returnPrice(), 0);}
        catch(NotEnoughAmmosException e){}
        assertEquals(1, player1.getAmmo(0));
        assertTrue(manage.areEnoughAmmoToGrabWeapon(player1, weapontograb.returnPrice()));
    }
}