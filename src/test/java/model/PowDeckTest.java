package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class PowDeckTest {

    private PowDeck W;
    private PowCard powcard;
    @BeforeEach
    public void start()
    {
        W= new PowDeck("Pow");
        powcard = (PowCard) W.drawCard();
    }

    @Test
    public void testRead()
    {
        assertEquals(0,powcard.getColor());
        powcard = (PowCard) W.drawCard();
        assertEquals(0,powcard.getColor());
        powcard = (PowCard) W.drawCard();
        assertEquals(1,powcard.getColor());
        powcard = (PowCard) W.drawCard();
        assertEquals(1,powcard.getColor());
        powcard = (PowCard) W.drawCard();
        assertEquals(2,powcard.getColor());
        powcard = (PowCard) W.drawCard();
        assertEquals(2,powcard.getColor());
        powcard = (PowCard) W.drawCard();
        assertEquals(0,powcard.getColor());
        powcard = (PowCard) W.drawCard();
        assertEquals(0,powcard.getColor());
        powcard = (PowCard) W.drawCard();
        assertEquals(1,powcard.getColor());
        powcard = (PowCard) W.drawCard();
        assertEquals(1,powcard.getColor());
        powcard = (PowCard) W.drawCard();
        assertEquals(2,powcard.getColor());
        powcard = (PowCard) W.drawCard();
        assertEquals(2,powcard.getColor());
        powcard = (PowCard) W.drawCard();
        assertEquals(0,powcard.getColor());
        powcard = (PowCard) W.drawCard();
        assertEquals(0,powcard.getColor());
        powcard = (PowCard) W.drawCard();
        assertEquals(1,powcard.getColor());
        powcard = (PowCard) W.drawCard();
        assertEquals(1,powcard.getColor());
        powcard = (PowCard) W.drawCard();
        assertEquals(2,powcard.getColor());
        powcard = (PowCard) W.drawCard();
        assertEquals(2,powcard.getColor());
        powcard = (PowCard) W.drawCard();
        assertEquals(0,powcard.getColor());
        powcard = (PowCard) W.drawCard();
        assertEquals(0,powcard.getColor());
        powcard = (PowCard) W.drawCard();
        assertEquals(1,powcard.getColor());
        powcard = (PowCard) W.drawCard();
        assertEquals(1,powcard.getColor());
        powcard = (PowCard) W.drawCard();
        assertEquals(2,powcard.getColor());
        powcard = (PowCard) W.drawCard();
        assertEquals(2,powcard.getColor());
    }
}
