package Model;

import Model.Player;
import Model.Ammo;
import Model.Weapon;
import Model.Coordinate;
import Model.Card;
import exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    Pow_Card p;
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
    void getmaxdamages(){
        Player player4 = new Player("Aries", "yellow", "18992302");
        Player player5 = new Player("Karka", "grey", "18114320");
        assertEquals(0, player1.gettotaldamage());
        assertEquals(0, player1.get_death());
        player1.setdamage(3,3); //from player2
        player1.setdamage(2,2); //from player4
        player1.setdamage(3,4); //from player5
        player1.setdamage(2,3); //from player2
        player1.setdamage(2,1); //from player3
        assertEquals(12, player1.gettotaldamage());
        assertEquals(5, player1.getnumberdamage(3)); //from player2
        assertEquals(3, player1.getnumberdamage(4)); //from player5
        assertEquals(2, player1.getnumberdamage(1)); //from player3
        assertEquals(2, player1.getnumberdamage(2)); //from player4
        assertEquals(3, player1.getmaxdamages()); //first is player2
        player1.setdamage(0,3);
        assertEquals(4, player1.getmaxdamages()); //second is player5
        player1.setdamage(0,4);
        assertEquals(2, player1.getmaxdamages()); //third is player4 (because it made damage before player3 even if they gave the same number of damages)
        player1.setdamage(0,2);
        assertEquals(1, player1.getmaxdamages()); //fourth is player3
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
        //1 mark from player2 to player1
        player1.setmarks(1, player2.getcolor());
        assertEquals(1, player1.getmarks(player2.getcolor()));
        assertEquals(-1, player1.getmarks(player1.getcolor()));
        //1 mark from player2 to player1
        player1.setmarks(1, player2.getcolor());
        assertEquals(2, player1.getmarks(player2.getcolor()));
        assertEquals(-1, player1.getmarks(player1.getcolor()));
        //2 marks from player2 to player1
        player1.setmarks(2, player2.getcolor());
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
        assertEquals(1, player1.get_ammo(0));
        try {
            player1.remove_ammo(1, ammo);
        }
        catch (NotEnoughAmmosException e){}
        assertEquals(0, player1.get_ammo(0));
        assertThrows(NotEnoughAmmosException.class, () -> player1.remove_ammo(2, ammo));
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
        //Pow_card p = new Pow_card(0, "Raggio cinetico");
    }

    @Test
    void add_pow() {
    }

    @Test
    void remove_pow() {
    }

    @Test
    void get_cel() {
        player1.set_cel(1,2);
        Coordinate c = player1.get_cel();
        assertEquals(1, c.getX());
        assertEquals(2, c.getY());
    }

    @Test
    void get_death() {
        assertEquals(0, player1.get_death());
    }

    @Test
    void set_death() {
        player1.set_death();
        assertEquals(1, player1.get_death());
    }

    @Test
    void action() {
        assertEquals(0, player1.get_action());
        player1.set_action();
        assertEquals(1, player1.get_action());
        player1.reset_action();
        assertEquals(0, player1.get_action());
    }

    @Test
    void firstblood(){
        assertEquals(-1, player1.get_firstblood());
        player1.setdamage(2, 3);
        assertEquals(3, player1.get_firstblood());
        player1.reset_firstblood();
        assertEquals(-1, player1.get_firstblood());
    }

    @Test
    void get_score() {
        assertEquals(0, player1.get_score());
    }

    @Test
    void set_score() {
        player1.set_score(5);
        assertEquals(5, player1.get_score());
    }
}