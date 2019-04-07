import exceptions.InvalidColorExeption;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void getcolor(){
        Player player1 = new Player("Sirius", "blue", "10583741");
        Player player2 = new Player("Calypso", "pink", "14253954");
        assertEquals(0, player1.getcolor());
        assertEquals(3, player2.getcolor());
    }

    @org.junit.Test(expected = InvalidColorExeption.class)
    public void whenExceptionThrown(){
        Player player3 = new Player("Bellatrix", "red", "12220987");
        assertThrows(InvalidColorExeption.class, () -> {player3.getcolor();});
    }

    @Test
    void getnumberdamage() {
        Player player1 = new Player("Sirius", "blue", "10583741");
        Player player2 = new Player("Calypso", "pink", "14253954");
        for(int i=0; i<5; i++){
            assertEquals(0, player1.getnumberdamage(i));
            assertEquals(0, player2.getnumberdamage(i));
        }

        //verify that player1 has 3 damages by player2
        player1.setdamage(3, player2.getcolor());
        assertEquals(3, player1.getnumberdamage(player2.getcolor()));
        assertEquals(0, player1.getnumberdamage(player1.getcolor()));

        //verify nothing changed
        player1.setdamage(2, player1.getcolor());
        assertEquals(0, player1.getnumberdamage(player2.getcolor()));
        assertEquals(0, player1.getnumberdamage(player1.getcolor()));
    }

    @Test
    void gettotaldamage() {
        Player player1 = new Player("Sirius", "blue", "10583741");
        Player player2 = new Player("Calypso", "pink", "14253954");
        assertEquals(0, player1.gettotaldamage());
        assertEquals(0, player2.gettotaldamage());

        //verify that player1 has now 3 damages
        player1.setdamage(3, player2.getcolor());
        assertEquals(3, player1.gettotaldamage());
        assertEquals(0, player2.gettotaldamage());

        //verify nothing changed
        player2.setdamage(2, player2.getcolor());
        assertEquals(0, player1.gettotaldamage());
        assertEquals(0, player2.gettotaldamage());
    }

    @Test
    void setdamage() {
        Player player1 = new Player("Sirius", "blue", "10583741");
        Player player2 = new Player("Calypso", "pink", "14253954");
        assertEquals(0, player1.gettotaldamage());
        player1.setdamage(3, player2.getcolor());
        assertEquals(3, player1.gettotaldamage());
        assertEquals(3, player1.getnumberdamage(player2.getcolor()));

        //verify nothing changed
        player1.setdamage(2, player1.getcolor());
        assertEquals(3, player1.gettotaldamage());
        assertEquals(0, player1.getnumberdamage(player1.getcolor()));
    }

    @Test
    void getmarks() {
        Player player1 = new Player("Sirius", "blue", "10583741");
        Player player2 = new Player("Calypso", "pink", "14253954");
        for(int i=0; i<5; i++){
            assertEquals(0, player1.getmarks(i));
            assertEquals(0, player2.getmarks(i));
        }

        //verify that player1 has 3 marks by player2
        player1.setmarks(3, player2.getcolor());
        assertEquals(3, player1.getmarks(player2.getcolor()));
        assertEquals(0, player1.getmarks(player1.getcolor()));

        //verify nothing changed
        player1.setmarks(2, player1.getcolor());
        assertEquals(0, player1.getmarks(player2.getcolor()));
        assertEquals(0, player1.getmarks(player1.getcolor()));
    }

    @Test
    void setmarks() {
        Player player1 = new Player("Sirius", "blue", "10583741");
        Player player2 = new Player("Calypso", "pink", "14253954");
        assertEquals(0, player1.getmarks(player2.getcolor()));
        assertEquals(0, player1.getmarks(player1.getcolor()));
        player1.setmarks(3, player2.getcolor());
        assertEquals(3, player1.getmarks(player2.getcolor()));
        assertEquals(0, player1.getmarks(player1.getcolor()));

        //verify nothing changed
        player1.setmarks(2, player1.getcolor());
        assertEquals(0, player1.getmarks(player2.getcolor()));
        assertEquals(0, player1.getmarks(player1.getcolor()));
    }

    @Test
    void get_ammo() {
    }

    @Test
    void add_ammo() {
    }

    @Test
    void remove_ammo() {
    }

    @Test
    void weaponIspresent() {
    }

    @Test
    void add_weapon() {
    }

    @Test
    void remove_weapon() {
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