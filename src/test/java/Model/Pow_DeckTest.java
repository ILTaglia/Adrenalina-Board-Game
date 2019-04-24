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
        assertEquals(A.getPow(0).getColor(),2);
    }
}
