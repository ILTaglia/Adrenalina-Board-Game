package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class Pow_DeckTest {

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
        assertEquals(powcard.getColor(),0);
        powcard = (PowCard) W.drawCard();
        assertEquals(powcard.getColor(),0);
        powcard = (PowCard) W.drawCard();
        assertEquals(powcard.getColor(),1);
        powcard = (PowCard) W.drawCard();
        assertEquals(powcard.getColor(),1);
        powcard = (PowCard) W.drawCard();
        assertEquals(powcard.getColor(),2);
        powcard = (PowCard) W.drawCard();
        assertEquals(powcard.getColor(),2);
        powcard = (PowCard) W.drawCard();
        assertEquals(powcard.getColor(),0);
        powcard = (PowCard) W.drawCard();
        assertEquals(powcard.getColor(),0);
        powcard = (PowCard) W.drawCard();
        assertEquals(powcard.getColor(),1);
        powcard = (PowCard) W.drawCard();
        assertEquals(powcard.getColor(),1);
        powcard = (PowCard) W.drawCard();
        assertEquals(powcard.getColor(),2);
        powcard = (PowCard) W.drawCard();
        assertEquals(powcard.getColor(),2);
        powcard = (PowCard) W.drawCard();
        assertEquals(powcard.getColor(),0);
        powcard = (PowCard) W.drawCard();
        assertEquals(powcard.getColor(),0);
        powcard = (PowCard) W.drawCard();
        assertEquals(powcard.getColor(),1);
        powcard = (PowCard) W.drawCard();
        assertEquals(powcard.getColor(),1);
        powcard = (PowCard) W.drawCard();
        assertEquals(powcard.getColor(),2);
        powcard = (PowCard) W.drawCard();
        assertEquals(powcard.getColor(),2);
        powcard = (PowCard) W.drawCard();
        assertEquals(powcard.getColor(),0);
        powcard = (PowCard) W.drawCard();
        assertEquals(powcard.getColor(),0);
        powcard = (PowCard) W.drawCard();
        assertEquals(powcard.getColor(),1);
        powcard = (PowCard) W.drawCard();
        assertEquals(powcard.getColor(),1);
        powcard = (PowCard) W.drawCard();
        assertEquals(powcard.getColor(),2);
        powcard = (PowCard) W.drawCard();
        assertEquals(powcard.getColor(),2);
    }
}
