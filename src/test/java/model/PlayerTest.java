package model;

import exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    Player player1;
    Player player2;
    Player player3;

    @BeforeEach
    void SetUp(){
        player1 = new Player("Sirius", "Blue", "10583741");
        player2 = new Player("Calypso", "Pink", "14253954");
        player3 = new Player("Hermione", "Green", "18263100");
    }

    @Test
    void get(){
        assertEquals(0, player1.getColor());
        assertEquals(3, player2.getColor());
        assertEquals("10583741", player1.getID());
        assertEquals(0, player1.getAction());
        assertFalse(player1.getActive());
        player1.setActive();
        player1.setAction();
        assertEquals(1, player1.getAction());
        player1.setActive();
        assertTrue(player1.getActive());
        player1.setAction();
        assertEquals(2, player1.getAction());
        player1.setActive();
        assertFalse(player1.getActive());
        player1.resetAction();
        assertEquals(0, player1.getAction());
        player1.setActive();
        assertTrue(player1.getActive());
        player1.resetActive();
        assertFalse(player1.getActive());
    }

    @Test
    public void whenExceptionThrown1(){
        assertThrows(InvalidColorException.class, () -> new Player("Bellatrix", "Red", "12220987"));
    }

    @Test
    void getnumberdamage() {
        for(int i=0; i<5; i++){
            if(player1.getColor()!=i) assertEquals(0, player1.getNumberDamage(i));
            if(player2.getColor()!=i) assertEquals(0, player2.getNumberDamage(i));
        }

        //verify that player1 has 3 damages by player2
        player1.setDamage(3, player2.getColor());
        assertEquals(3, player1.getNumberDamage(player2.getColor()));
        assertEquals(-1, player1.getNumberDamage(player1.getColor()));

        //verify nothing changed
        player1.setDamage(2, player1.getColor());
        assertEquals(3, player1.getNumberDamage(player2.getColor()));
        assertEquals(-1, player1.getNumberDamage(player1.getColor()));
    }

    @Test
    void winningpoints(){
        player1.setDamage(6, player2.getColor());
        assertEquals(1, player1.setDamage(5, player3.getColor()));
        //set_damages returns 1 with the damage number 11
        assertEquals(11, player1.getTotalDamage());

        assertEquals(2, player1.setDamage(2, player2.getColor()));
        //set_damages returns 1 with the damage number 11
        assertEquals(12, player1.getTotalDamage());
    }

    @Test
    void gettotaldamage() {
        assertEquals(0, player1.getTotalDamage());
        assertEquals(0, player2.getTotalDamage());

        //verify that player1 has now 3 damages
        player1.setDamage(3, player2.getColor());
        assertEquals(3, player1.getTotalDamage());
        assertEquals(0, player2.getTotalDamage());

        //verify nothing changed
        player2.setDamage(2, player2.getColor());
        assertEquals(3, player1.getTotalDamage());
        assertEquals(0, player2.getTotalDamage());
    }

    @Test
    void getmaxdamages(){
        Player player4 = new Player("Aries", "Yellow", "18992302");
        Player player5 = new Player("Karka", "Grey", "18114320");
        assertEquals(0, player1.getTotalDamage());
        assertEquals(0, player1.getNumberOfDeath());
        player1.setDamage(3,3); //from player2
        player1.setDamage(2,2); //from player4
        player1.setDamage(3,4); //from player5
        player1.setDamage(2,3); //from player2
        player1.setDamage(2,1); //from player3
        assertEquals(12, player1.getTotalDamage());
        assertEquals(5, player1.getNumberDamage(3)); //from player2
        assertEquals(3, player1.getNumberDamage(4)); //from player5
        assertEquals(2, player1.getNumberDamage(1)); //from player3
        assertEquals(2, player1.getNumberDamage(2)); //from player4
        assertEquals(3, player1.getMaxDamages()); //first is player2
        player1.setDamage(0,3);
        assertEquals(4, player1.getMaxDamages()); //second is player5
        player1.setDamage(0,4);
        assertEquals(2, player1.getMaxDamages()); //third is player4 (because it made damage before player3 even if they gave the same number of damages)
        player1.setDamage(0,2);
        assertEquals(1, player1.getMaxDamages()); //fourth is player3
    }

    @Test
    void setdamage() {
        assertEquals(0, player1.getTotalDamage());
        player1.setDamage(3, player2.getColor());
        assertEquals(3, player1.getTotalDamage());
        assertEquals(3, player1.getNumberDamage(player2.getColor()));

        //verify nothing changed
        player1.setDamage(2, player1.getColor());
        assertEquals(3, player1.getTotalDamage());
        assertEquals(-1, player1.getNumberDamage(player1.getColor()));
    }

    @Test
    void getmarks() {
        for(int i=0; i<5; i++){
            if(player1.getColor()!=i) assertEquals(0, player1.getMarks(i));
            if(player2.getColor()!=i) assertEquals(0, player2.getMarks(i));
        }
        //verify that player1 has 3 marks by player2
        player1.setMarks(3, player2.getColor());
        assertEquals(3, player1.getMarks(player2.getColor()));
        assertEquals(-1, player1.getMarks(player1.getColor()));

        //verify nothing changed
        assertThrows(InvalidColorException.class, () -> player1.setMarks(2, player1.getColor()));
        assertEquals(3, player1.getMarks(player2.getColor()));
        assertEquals(-1, player1.getMarks(player1.getColor()));
    }

    @Test
    void setmarks() {
        assertEquals(0, player1.getMarks(player2.getColor()));
        assertEquals(-1, player1.getMarks(player1.getColor()));
        //1 mark from player2 to player1
        player1.setMarks(1, player2.getColor());
        assertEquals(1, player1.getMarks(player2.getColor()));
        assertEquals(-1, player1.getMarks(player1.getColor()));
        //1 mark from player2 to player1
        player1.setMarks(1, player2.getColor());
        assertEquals(2, player1.getMarks(player2.getColor()));
        assertEquals(-1, player1.getMarks(player1.getColor()));
        //2 marks from player2 to player1
        player1.setMarks(2, player2.getColor());
        assertEquals(3, player1.getMarks(player2.getColor()));
        assertEquals(-1, player1.getMarks(player1.getColor()));

        //verify nothing changed
        assertThrows(InvalidColorException.class, () -> player1.setMarks(2, player1.getColor()));
        assertEquals(3, player1.getMarks(player2.getColor()));
        assertEquals(-1, player1.getMarks(player1.getColor()));

        player1.setMarks(1, player2.getColor());
        assertEquals(3, player1.getMarks(player2.getColor()));
        assertEquals(-1, player1.getMarks(player1.getColor()));
        player1.resetMarks(player2.getColor());
        assertEquals(0, player1.getMarks(player2.getColor()));
        assertEquals(-1, player1.getMarks(player1.getColor()));
    }

    @Test
    void getAmmo() {
        assertEquals(1, player1.getAmmo(1));
        assertThrows(InvalidColorException.class, () -> player1.getAmmo(4));

    }


    @Test
    void addAmmo() {
        Ammo ammo = new Ammo(0); //new ammo blue
        try{
            player1.addAmmo(ammo);
        }
        catch (MoreThanTreeAmmosException e){
            System.out.println("You have too many Ammos, please discardWeapon one.");
        }
        assertEquals(2, player1.getAmmo(0));
        try{
            player1.addAmmo(ammo);
        }
        catch (MoreThanTreeAmmosException e){
            System.out.println("You have too many Ammos, please discardWeapon one.");
        }
        assertEquals(3, player1.getAmmo(0));
        assertThrows(MoreThanTreeAmmosException.class, () -> player1.addAmmo(ammo));

    }

    @Test
    void removeAmmo() {
        Ammo ammo = new Ammo(0); //new ammo blue
        assertEquals(1, player1.getAmmo(0));
        try {
            player1.removeAmmo(1, ammo);
        }
        catch (NotEnoughAmmosException e){
            System.out.println("You don't have enough Ammos.");
        }
        assertEquals(0, player1.getAmmo(0));
        assertThrows(NotEnoughAmmosException.class, () -> player1.removeAmmo(2, ammo));
    }


    @Test
    void testingWeapon() {
        WeaponDeck weaponDeck = new WeaponDeck();
        weaponDeck.setWeapons("Armi");
        weaponDeck.drawCard();
        Weapon weapon1 = (Weapon)weaponDeck.drawCard();
        Weapon weapon2 = (Weapon)weaponDeck.drawCard();
        Weapon weapon3 = (Weapon)weaponDeck.drawCard();
        Weapon weapon4 = (Weapon)weaponDeck.drawCard();
        assertEquals(0, player1.getNumberWeapon());
        assertThrows(ZeroCardsOwnedException.class, () -> player1.removeWeapon(weapon1));
        try { player1.addWeapon(weapon1); }
        catch (MaxNumberofCardsException e){ System.out.println("You have too many Weapon Cards, please discardWeapon one."); }
        assertEquals(1, player1.getNumberWeapon());
        assertTrue(player1.getWeapons().contains(weapon1));
        assertTrue(player1.weaponIspresent(weapon1));
        assertThrows(NotOwnedCardException.class, () -> player1.removeWeapon(weapon2));
        try {
            player1.addWeapon(weapon2);
            player1.addWeapon(weapon3);
        }
        catch (MaxNumberofCardsException e){ System.out.println("You have too many Weapon Cards, please discardWeapon one."); }
        assertEquals(3, player1.getNumberWeapon());
        assertThrows(MaxNumberofCardsException.class, () -> player1.addWeapon(weapon4));

        try{
            player1.removeWeapon(weapon1);
        }
        catch (NotOwnedCardException e1){ System.out.println("You don't have this Weapon Card."); }
        catch (ZeroCardsOwnedException e2){ System.out.println("You have zero Weapon Cards."); }
        assertFalse(player1.weaponIspresent(weapon1));
        Weapon weapon5 = (Weapon)weaponDeck.drawCard();
        try{
            player1.addWeapon(weapon5);
        }
        catch (MaxNumberofCardsException e){
            System.out.println("You have too many Weapons, please discardWeapon one.");
        }
        assertTrue(player1.weaponIspresent(weapon5));
        assertEquals(weapon2, player1.getWeaponByIndex(0));
        assertEquals(weapon3, player1.getWeaponByIndex(1));
        assertEquals(weapon5, player1.getWeaponByIndex(2));
        assertEquals(3, player1.getWeapons().size());
        player1.removeWeapon(2);
        assertFalse(player1.weaponIspresent(weapon5));
        assertEquals(2, player1.getWeapons().size());
    }

    @Test
    void testingPow() {
        PowDeck deck = new PowDeck("Pow");
        PowCard powcard1 = (PowCard) deck.drawCard();
        assertEquals(0, player1.getNumberPow());
        try{
            player1.addPow(powcard1);
        }
        catch (MaxNumberofCardsException e){
            System.out.println("You have too many Pow Cards, please discardWeapon one.");
        }
        assertEquals(1, player1.getNumberPow());
        assertTrue(player1.isPowPresent(powcard1));
        assertEquals(powcard1, player1.getPowByIndex(0));
        PowCard powcard2 = (PowCard) deck.drawCard();
        assertThrows(NotOwnedCardException.class, () -> player1.removePow(powcard2));

        try{
            player1.removePow(powcard1);
        }
        catch (NotOwnedCardException e){
            System.out.println("You don't have this Pow Card.");
        }
        catch (ZeroCardsOwnedException e){
            System.out.println("You have zero Pow Cards.");
        }
        assertTrue(!player1.isPowPresent(powcard1));
        assertThrows(ZeroCardsOwnedException.class, () -> player1.removePow(powcard2));
        PowCard powcard3 = (PowCard) deck.drawCard();
        try{
            player1.addPow(powcard3);
        }
        catch (MaxNumberofCardsException e){
            System.out.println("You have too many Pow Cards, please discardWeapon one.");
        }
        assertTrue(player1.isPowPresent(powcard3));
        assertEquals(powcard3, player1.getPowByIndex(0));
        player1.removePow(0);
        assertFalse(player1.isPowPresent(powcard3));

        List<PowCard> powList = player1.getPows();
        assertEquals(0, powList.size());
        PowCard powcard4 = (PowCard) deck.drawCard();
        PowCard powcard5 = (PowCard) deck.drawCard();
        try{
            player1.addPow(powcard4);
            player1.addPow(powcard5);
        }
        catch (MaxNumberofCardsException e){
            System.out.println("You have too many Pow Cards, please discardWeapon one.");
        }
        powList = player1.getPows();
        assertEquals(2, powList.size());
    }


    @Test
    void getCel() {
        player1.setCel(1,2);
        Coordinate c = player1.getCel();
        assertEquals(1, c.getX());
        assertEquals(2, c.getY());
    }

    @Test
    void getDeath() {
        assertEquals(0, player1.getNumberOfDeath());
    }

    @Test
    void setDeath() {
        player1.setDeath();
        assertEquals(1, player1.getNumberOfDeath());
        player1.setDeathFrienzy(4);
        assertEquals(4, player1.getNumberOfDeath());
    }

    @Test
    void action() {
        assertEquals(0, player1.getAction());
        player1.setAction();
        assertEquals(1, player1.getAction());
        player1.resetAction();
        assertEquals(0, player1.getAction());
    }

    @Test
    void firstblood(){
        assertEquals(-1, player1.getFirstBlood());
        player1.setDamage(2, 3);
        assertEquals(3, player1.getFirstBlood());
        player1.resetFirstBlood();
        assertEquals(-1, player1.getFirstBlood());
    }

    @Test
    void getScore() {
        assertEquals(0, player1.getScore());
    }

    @Test
    void setScore() {
        player1.setScore(5);
        assertEquals(5, player1.getScore());
    }

    @Test
    void booleanConnection(){
        player1.setConnected(false);
        assertFalse(player1.isConnected());
        player1.setConnected(true);
        assertTrue(player1.isConnected());
    }

    @Test
    void booleanDeath(){
        player1.setDead(false);
        assertFalse(player1.isDead());
        player1.setDead(true);
        assertTrue(player1.isDead());
    }
}