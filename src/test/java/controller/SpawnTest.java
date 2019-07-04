package controller;

import exceptions.InvalidColorException;
import exceptions.MaxNumberofCardsException;
import model.Match;
import model.Player;
import model.PowCard;
import model.PowDeck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class SpawnTest {
    private Match match;
    private Player player1;
    private Player player2;
    private Player player3;

    private PowDeck powDeck;
    private PowCard powcard;

    private Spawn spawn;

    @BeforeEach
    public void setUp() throws Exception {
        spawn=new Spawn();
        match = new Match();
        match.createPlayer("Sirius", "Blue", "10583741");
        match.createPlayer("Calypso", "Pink", "14253954");
        match.createPlayer("Hermione", "Green", "18263100");
        player1 = match.getPlayerByIndex(0);
        player2 = match.getPlayerByIndex(1);
        player3 = match.getPlayerByIndex(2);
        match.assignPowCard(player1);
        match.assignPowCard(player2);
        match.getPlayer(3).removePow(0);
        match.assignPowCard(player2);
        match.assignPowCard(player3);
        match.getPlayer(1).removePow(0);
        match.assignPowCard(player3);
        match.createDashboard(3);
        player1.setActive();
    }

    @Test
    public void spawn() {
        assertEquals(0,player1.getPowByIndex(0).getColor());
        assertEquals(-1, player1.getCel().getX());
        assertEquals(-1, player1.getCel().getY());
        assertTrue(spawn.bornValidity(1, 0, player1.getPowByIndex(0).getColor()));
        try{spawn.spawn(match, player1, 1, 0, 0);}
        catch(InvalidColorException e){}
        assertEquals(1, player1.getCel().getX());
        assertEquals(0, player1.getCel().getY());
        assertEquals(1,player2.getPowByIndex(0).getColor());
        assertEquals(-1, player2.getCel().getX());
        assertEquals(-1, player2.getCel().getY());
        assertTrue(spawn.bornValidity(0, 2, player2.getPowByIndex(0).getColor()));
        try{spawn.spawn(match, player2, 0, 2, 0);}
        catch(InvalidColorException e){}
        assertEquals(0, player2.getCel().getX());
        assertEquals(2, player2.getCel().getY());
        assertEquals(2,player3.getPowByIndex(0).getColor());
        assertEquals(-1, player3.getCel().getX());
        assertEquals(-1, player3.getCel().getY());
        assertTrue(spawn.bornValidity(2, 3, 2));
        try{spawn.spawn(match, player3, 2, 3, 0);}
        catch(InvalidColorException e){}
        assertEquals(2, player3.getCel().getX());
        assertEquals(3, player3.getCel().getY());
    }
}