package Model;

import Model.Player;
import Model.Ammo;
import Model.Weapon;
import Model.Coordinate;
import exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
public class Pow_DeckTest {
    @Test
    public void testRead()
    {
        Pow_Deck A;
        A=new Pow_Deck("Pow");
        assertEquals(A.getPow(0).getColor(),0);
        assertEquals(A.getPow(1).getColor(),0);
        assertEquals(A.getPow(2).getColor(),1);
        assertEquals(A.getPow(3).getColor(),1);
        assertEquals(A.getPow(4).getColor(),2);
        assertEquals(A.getPow(5).getColor(),2);
        assertEquals(A.getPow(6).getColor(),0);
        assertEquals(A.getPow(7).getColor(),0);
        assertEquals(A.getPow(8).getColor(),1);
        assertEquals(A.getPow(9).getColor(),1);
        assertEquals(A.getPow(10).getColor(),2);
        assertEquals(A.getPow(11).getColor(),2);
        assertEquals(A.getPow(12).getColor(),0);
        assertEquals(A.getPow(13).getColor(),0);
        assertEquals(A.getPow(14).getColor(),1);
        assertEquals(A.getPow(15).getColor(),1);
        assertEquals(A.getPow(16).getColor(),2);
        assertEquals(A.getPow(17).getColor(),2);
        assertEquals(A.getPow(18).getColor(),0);
        assertEquals(A.getPow(19).getColor(),0);
        assertEquals(A.getPow(20).getColor(),1);
        assertEquals(A.getPow(21).getColor(),1);
        assertEquals(A.getPow(22).getColor(),2);
        assertEquals(A.getPow(23).getColor(),2);
    }
}
