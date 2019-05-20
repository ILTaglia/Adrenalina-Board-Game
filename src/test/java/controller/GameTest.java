package controller;

import model.Match;
import model.Player;
import exceptions.InvalidColorException;
import exceptions.MaxNumberPlayerException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.Assert.*;

import java.util.Random;

public class GameTest {
    Game game = new Game();
    Match match;
    Player player1;
    Player player2;
    Player player3;
    Player player4;
    Player player5;
    Random r;

    @Before
    public void setUp(){
        match = game.getMatchByIndex(game.getMatchesSize()-1);
        r = game.getRand();
    }
    @Test
    public void random_id() {
        String s = game.randomId(r);
        player1 = new Player("Sirius", "Blue", s);
        try {
            match.addPlayer(player1);
        }
        catch (MaxNumberPlayerException e){}
        catch (InvalidColorException e) {}
        System.out.println(s);

        s = game.randomId(r);
        player2 = new Player("Calypso", "Pink", s);
        try {
            match.addPlayer(player2);
        }
        catch (MaxNumberPlayerException e){}
        catch (InvalidColorException e) {}
        System.out.println(s);

        s = game.randomId(r);
        player3 = new Player("Hermione", "Green", s);
        try {
            match.addPlayer(player3);
        }
        catch (MaxNumberPlayerException e){}
        catch (InvalidColorException e) {}
        System.out.println(s);

        s = game.randomId(r);
        player4 = new Player("Aries", "Green", s);
        assertThrows(InvalidColorException.class, () -> match.addPlayer(player4));
        player4 = new Player("Aries", "Yellow", s);
        try {
            match.addPlayer(player4);
        }
        catch (MaxNumberPlayerException e){}
        catch (InvalidColorException e) {}
        System.out.println(s);

        s = game.randomId(r);
        player5 = new Player("Karka", "Grey", s);
        try {
            match.addPlayer(player5);
        }
        catch (MaxNumberPlayerException e){}
        catch (InvalidColorException e) {}
        System.out.println(s);
    }

    @Test
    public void startingGame(){
        String s = game.randomId(r);
        player1 = new Player("Sirius", "Blue", s);
        s = game.randomId(r);
        player2 = new Player("Calypso", "Pink", s);
        s = game.randomId(r);
        player3 = new Player("Hermione", "Green", s);
        try {
            match.addPlayer(player1);
            match.addPlayer(player2);
            match.addPlayer(player3);
        }
        catch (MaxNumberPlayerException e){}
        catch (InvalidColorException e) {}

        assertEquals(0, player1.getAction());
        assertFalse(player1.getActive());
        assertFalse(player2.getActive());
        assertFalse(player3.getActive());
        game.startGame(match.getId());
        assertEquals(0, player1.getAction());
        assertTrue(player1.getActive());
        assertFalse(player2.getActive());
        assertFalse(player3.getActive());
        player1.setAction();
        player1.setAction();
        //After having done two actions, player1 has ended its turn
        game.setTurn(match);
        assertFalse(player1.getActive());
        assertTrue(player2.getActive());
        assertFalse(player3.getActive());
    }

}