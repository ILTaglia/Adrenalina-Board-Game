package controller;

import exceptions.MaxNumberofCardsException;
import exceptions.MoreThanTreeAmmosException;
import model.Ammo;
import model.Match;
import model.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ShootDoubleTest {
    Match match;
    Player player1;
    Player player2;
    Player player3;
    GrabWeapon grabweapon;
    OfficialShootVersion shoot;


    @Before
    public void setUp() throws Exception {
        match = new Match();
        match.createPlayer("Sirius", "Blue", "10583741");
        match.createPlayer("Calypso", "Pink", "14253954");
        match.createPlayer("Hermione", "Green", "18263100");
        grabweapon=new GrabWeapon();

        match.createDashboard(3);
        match.fillDashboard();

        player1 = match.getPlayerByIndex(0);
        player2 = match.getPlayerByIndex(1);
        player3 = match.getPlayerByIndex(2);
        player1.setActive();

        player1.setCel(0, 2);
        player2.setCel(0, 1);
        player3.setCel(2, 2);
    }

    @Test
    public void test1(){
        Ammo redAmmo = new Ammo(0);
        Ammo bluAmmo = new Ammo(1);
        Ammo yellowAmmo = new Ammo(2);

        try{
            player1.addAmmo(redAmmo);
            player1.addAmmo(redAmmo);
            player1.addAmmo(bluAmmo);
            player1.addAmmo(bluAmmo);
            player1.addAmmo(yellowAmmo);
            player1.addAmmo(yellowAmmo);
        }
        catch(MoreThanTreeAmmosException e){}
        try{grabweapon.grabWeapon(match, player1, 0);}
        catch(MaxNumberofCardsException e){}
        System.out.println(player1.getWeaponByIndex(0).getName());
        System.out.println(player1.getWeaponByIndex(0).getCostToRecharge());
        System.out.println(player1.getWeaponByIndex(0).getCostToGrab());
        System.out.println(player1.getWeaponByIndex(0).getNumberAttack());
        System.out.println(player1.getWeaponByIndex(0).getAttack(0).getEffect(0).getType());
        System.out.println(player1.getWeaponByIndex(0).getAttack(0).getNumberEffect());
        shoot=new OfficialShootVersion(match, player1);
        int chosenindex=0;
        match.getActivePlayer().getWeaponByIndex(chosenindex);
    }
    @Test
    public void test2(){
        Ammo redAmmo = new Ammo(0);
        Ammo bluAmmo = new Ammo(1);
        Ammo yellowAmmo = new Ammo(2);

        try{
            player1.addAmmo(redAmmo);
            player1.addAmmo(redAmmo);
            player1.addAmmo(bluAmmo);
            player1.addAmmo(bluAmmo);
            player1.addAmmo(yellowAmmo);
            player1.addAmmo(yellowAmmo);
        }
        catch(MoreThanTreeAmmosException e){}
        try{grabweapon.grabWeapon(match, player1, 0);}
        catch(MaxNumberofCardsException e){}
        System.out.println(player1.getWeaponByIndex(0).getName());
        System.out.println(player1.getWeaponByIndex(0).getCostToRecharge());
        System.out.println(player1.getWeaponByIndex(0).getCostToGrab());
        System.out.println(player1.getWeaponByIndex(0).getNumberAttack());
        System.out.println(player1.getWeaponByIndex(0).getAttack(0).getEffect(0).getType());
        System.out.println(player1.getWeaponByIndex(0).getAttack(0).getNumberEffect());
        shoot=new OfficialShootVersion(match, player1);
        int chosenindex=0;
        match.getActivePlayer().getWeaponByIndex(chosenindex);
        //assertTrue(shoot.getlistattackable(1).contains(player2));
    }
}