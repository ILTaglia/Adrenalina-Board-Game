package controller;

import exceptions.InvalidColorException;
import exceptions.MaxNumberofCardsException;
import model.Match;
import model.Player;
import model.PowCard;
import model.PowDeck;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SpawnTest {
    private Match match;
    private Player player1;
    private Player player2;
    private Player player3;

    private PowDeck W;
    private PowCard powcard;

    private Spawn spawn;

    @Before
    public void setUp() throws Exception {
        W= new PowDeck("Pow");
        powcard = (PowCard) W.drawCard();
        spawn=new Spawn();

        match = new Match();
        match.createPlayer("Sirius", "Blue", "10583741");
        match.createPlayer("Calypso", "Pink", "14253954");
        match.createPlayer("Hermione", "Green", "18263100");

        match.createDashboard(3);

        player1 = match.getPlayerByIndex(0);
        player2 = match.getPlayerByIndex(1);
        player3 = match.getPlayerByIndex(2);
        player1.setActive();
    }

    @Test
    public void spawn() {
        assertEquals(-1, player1.getCel().getX());
        assertEquals(-1, player1.getCel().getY());
        try{player1.addPow(powcard);}
        catch(MaxNumberofCardsException e){}
        assertTrue(spawn.bornValidity(1, 0, 0));
        try{spawn.spawn(match, player1, 1, 0, 0);}
        catch(InvalidColorException e){}
        assertEquals(1, player1.getCel().getX());
        assertEquals(0, player1.getCel().getY());
        assertEquals(0,powcard.getColor());
        powcard = (PowCard) W.drawCard();
        assertEquals(0,powcard.getColor());

        powcard = (PowCard) W.drawCard();
        assertEquals(1,powcard.getColor());
        assertEquals(-1, player2.getCel().getX());
        assertEquals(-1, player2.getCel().getY());
        try{player2.addPow(powcard);}
        catch(MaxNumberofCardsException e){}
        assertTrue(spawn.bornValidity(0, 2, 1));
        try{spawn.spawn(match, player1, 0, 1, 0);}
        catch(InvalidColorException e){}
        //assertEquals(1, player1.getCel().getX());
        //assertEquals(0, player1.getCel().getY());


        powcard = (PowCard) W.drawCard();
        assertEquals(1,powcard.getColor());
        powcard = (PowCard) W.drawCard();
        assertEquals(2,powcard.getColor());
        assertEquals(-1, player3.getCel().getX());
        assertEquals(-1, player3.getCel().getY());
        try{player3.addPow(powcard);}
        catch(MaxNumberofCardsException e){}
        assertTrue(spawn.bornValidity(2, 3, 2));
        try{spawn.spawn(match, player1, 2, 3, 0);}
        catch(InvalidColorException e){}
        //assertEquals(1, player1.getCel().getX());
        //assertEquals(0, player1.getCel().getY());
    }
}