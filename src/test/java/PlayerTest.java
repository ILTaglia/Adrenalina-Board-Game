import Model.Player;
import Model.Ammo;
import Model.Weapon;
import exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    Player player1;
    Player player2;
    Player player3;

    @BeforeEach
    void SetUp(){
        player1 = new Player("Sirius", "blue", "10583741");
        player2 = new Player("Calypso", "pink", "14253954");
        player3 = new Player("Hermione", "green", "18263100");
    }

    @Test
    void getcolor(){
        assertEquals(0, player1.getcolor());
        assertEquals(3, player2.getcolor());
    }

    @Test
    public void whenExceptionThrown1(){
        assertThrows(InvalidColorException.class, () -> new Player("Bellatrix", "red", "12220987"));
    }

    @Test
    void getnumberdamage() {
        for(int i=0; i<5; i++){
            if(player1.getcolor()!=i) assertEquals(0, player1.getnumberdamage(i));
            if(player2.getcolor()!=i) assertEquals(0, player2.getnumberdamage(i));
        }

        //verify that player1 has 3 damages by player2
        player1.setdamage(3, player2.getcolor());
        assertEquals(3, player1.getnumberdamage(player2.getcolor()));
        assertEquals(-1, player1.getnumberdamage(player1.getcolor()));

        //verify nothing changed
        player1.setdamage(2, player1.getcolor());
        assertEquals(3, player1.getnumberdamage(player2.getcolor()));
        assertEquals(-1, player1.getnumberdamage(player1.getcolor()));
    }

    @Test
    void winningpoints(){
        player1.setdamage(6, player2.getcolor());
        assertEquals(1, player1.setdamage(5, player3.getcolor()));
        //set_damages returns 1 with the damage number 11
        assertEquals(11, player1.gettotaldamage());

        assertEquals(2, player1.setdamage(2, player2.getcolor()));
        //set_damages returns 1 with the damage number 11
        assertEquals(12, player1.gettotaldamage());
    }

    @Test
    void gettotaldamage() {
        assertEquals(0, player1.gettotaldamage());
        assertEquals(0, player2.gettotaldamage());

        //verify that player1 has now 3 damages
        player1.setdamage(3, player2.getcolor());
        assertEquals(3, player1.gettotaldamage());
        assertEquals(0, player2.gettotaldamage());

        //verify nothing changed
        player2.setdamage(2, player2.getcolor());
        assertEquals(3, player1.gettotaldamage());
        assertEquals(0, player2.gettotaldamage());
    }

    @Test
    void setdamage() {
        assertEquals(0, player1.gettotaldamage());
        player1.setdamage(3, player2.getcolor());
        assertEquals(3, player1.gettotaldamage());
        assertEquals(3, player1.getnumberdamage(player2.getcolor()));

        //verify nothing changed
        player1.setdamage(2, player1.getcolor());
        assertEquals(3, player1.gettotaldamage());
        assertEquals(-1, player1.getnumberdamage(player1.getcolor()));
    }

    @Test
    void getmarks() {
        for(int i=0; i<5; i++){
            if(player1.getcolor()!=i) assertEquals(0, player1.getmarks(i));
            if(player2.getcolor()!=i) assertEquals(0, player2.getmarks(i));
        }
        //verify that player1 has 3 marks by player2
        player1.setmarks(3, player2.getcolor());
        assertEquals(3, player1.getmarks(player2.getcolor()));
        assertEquals(-1, player1.getmarks(player1.getcolor()));

        //verify nothing changed
        player1.setmarks(2, player1.getcolor());
        assertEquals(3, player1.getmarks(player2.getcolor()));
        assertEquals(-1, player1.getmarks(player1.getcolor()));
    }

    @Test
    void setmarks() {
        assertEquals(0, player1.getmarks(player2.getcolor()));
        assertEquals(-1, player1.getmarks(player1.getcolor()));
        player1.setmarks(3, player2.getcolor());
        assertEquals(3, player1.getmarks(player2.getcolor()));
        assertEquals(-1, player1.getmarks(player1.getcolor()));

        //verify nothing changed
        player1.setmarks(2, player1.getcolor());
        assertEquals(3, player1.getmarks(player2.getcolor()));
        assertEquals(-1, player1.getmarks(player1.getcolor()));

        player1.setmarks(1, player2.getcolor());
        assertEquals(3, player1.getmarks(player2.getcolor()));
        assertEquals(-1, player1.getmarks(player1.getcolor()));
    }

    @Test
    void get_ammo() {
        assertEquals(1, player1.get_ammo(1));
    }

    @Test
    public void whenExceptionThrown2(){
        assertThrows(InvalidColorException.class, () -> player1.get_ammo(4));
    }

    @Test
    void add_ammo() {
        Ammo ammo = new Ammo(0); //new ammo blue
        try{
            player1.add_ammo(ammo);
        }
        catch (MoreThanTreeAmmosException e){}
        assertEquals(2, player1.get_ammo(0));
        try{
            player1.add_ammo(ammo);
        }
        catch (MoreThanTreeAmmosException e){}
        assertEquals(3, player1.get_ammo(0));
        assertThrows(MoreThanTreeAmmosException.class, () -> player1.add_ammo(ammo));

    }

    @Test
    void remove_ammo() {
        Ammo ammo = new Ammo(0); //new ammo blue
        /*try {
            player1.remove_ammo(1, ammo);
        }
        catch (NotEnoughAmmosException e){}
        assertEquals(0, player1.get_ammo(0));
        assertThrows(NotEnoughAmmosException.class, () -> player1.remove_ammo(1, ammo));*/
    }


        @Test
    void add_weapon() {
        ArrayList<Integer> cost = new ArrayList();
        cost.add(1);
        cost.add(2);
        cost.add(0);
        Weapon weapon1 = new Weapon("Spada Laser", cost);
        try {
            player1.add_weapon(weapon1);
        }
        catch (MaxNumberofCardsException e){}
        Weapon weapon2 = new Weapon("Vortex", cost);
        Weapon weapon3 = new Weapon("Distruttore", cost);
        Weapon weapon4 = new Weapon("Fucile di precisione", cost);
        try {
            player1.add_weapon(weapon2);
        }
        catch (MaxNumberofCardsException e){}
        try {
            player1.add_weapon(weapon3);
        }
        catch (MaxNumberofCardsException e){}
        assertThrows(MaxNumberofCardsException.class, () -> player1.add_weapon(weapon4));
    }

    @Test
    void remove_weapon() {
        ArrayList<Integer> cost = new ArrayList();
        cost.add(1);
        cost.add(2);
        cost.add(0);
        Weapon weapon1 = new Weapon("Spada Laser", cost);
        assertThrows(ZeroCardsOwnedException.class, () -> player1.remove_weapon(weapon1));

        try {
            player1.add_weapon(weapon1);
        }
        catch (MaxNumberofCardsException e){}
        assertEquals(true, player1.weaponIspresent(weapon1));

        Weapon weapon2 = new Weapon("Vortex", cost);
        assertThrows(NotOwnedCardException.class, () -> player1.remove_weapon(weapon2));

        try{
            player1.remove_weapon(weapon1);
        }
        catch (NotOwnedCardException e1){}
        catch (ZeroCardsOwnedException e2){}
        assertEquals(false, player1.weaponIspresent(weapon1));
    }

    @Test
    void powIspresent() {
    }

    @Test
    void add_pow() {
    }

    @Test
    void remove_pow() {
    }

    @Test
    void get_cel() {
    }

    @Test
    void set_cel() {
    }
}