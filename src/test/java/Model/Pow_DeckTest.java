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

    private Pow_Deck W;
    private PowCard powcard;
    @BeforeEach
    public void start()
    {
        W= new Pow_Deck("Pow");
        powcard = (PowCard) W.Draw_Card();
    }

    @Test
    public void testRead()
    {
        assertEquals(powcard.getColor(),0);
        powcard = (PowCard) W.Draw_Card();
        assertEquals(powcard.getColor(),0);
        powcard = (PowCard) W.Draw_Card();
        assertEquals(powcard.getColor(),1);
        powcard = (PowCard) W.Draw_Card();
        assertEquals(powcard.getColor(),1);
        powcard = (PowCard) W.Draw_Card();
        assertEquals(powcard.getColor(),2);
        powcard = (PowCard) W.Draw_Card();
        assertEquals(powcard.getColor(),2);
        powcard = (PowCard) W.Draw_Card();
        assertEquals(powcard.getColor(),0);
        powcard = (PowCard) W.Draw_Card();
        assertEquals(powcard.getColor(),0);
        powcard = (PowCard) W.Draw_Card();
        assertEquals(powcard.getColor(),1);
        powcard = (PowCard) W.Draw_Card();
        assertEquals(powcard.getColor(),1);
        powcard = (PowCard) W.Draw_Card();
        assertEquals(powcard.getColor(),2);
        powcard = (PowCard) W.Draw_Card();
        assertEquals(powcard.getColor(),2);
        powcard = (PowCard) W.Draw_Card();
        assertEquals(powcard.getColor(),0);
        powcard = (PowCard) W.Draw_Card();
        assertEquals(powcard.getColor(),0);
        powcard = (PowCard) W.Draw_Card();
        assertEquals(powcard.getColor(),1);
        powcard = (PowCard) W.Draw_Card();
        assertEquals(powcard.getColor(),1);
        powcard = (PowCard) W.Draw_Card();
        assertEquals(powcard.getColor(),2);
        powcard = (PowCard) W.Draw_Card();
        assertEquals(powcard.getColor(),2);
        powcard = (PowCard) W.Draw_Card();
        assertEquals(powcard.getColor(),0);
        powcard = (PowCard) W.Draw_Card();
        assertEquals(powcard.getColor(),0);
        powcard = (PowCard) W.Draw_Card();
        assertEquals(powcard.getColor(),1);
        powcard = (PowCard) W.Draw_Card();
        assertEquals(powcard.getColor(),1);
        powcard = (PowCard) W.Draw_Card();
        assertEquals(powcard.getColor(),2);
        powcard = (PowCard) W.Draw_Card();
        assertEquals(powcard.getColor(),2);
    }
}
