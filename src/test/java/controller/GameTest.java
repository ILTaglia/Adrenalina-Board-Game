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

public class GameTest {/*
    Game game = new Game();
    Player player1;
    Player player2;
    Player player3;
    Player player4;
    Player player5;
    Random r;

    @Before
    public void SetUp(){
       game.select(3);
    }
    @Test
    public void startingGame(){

        player1 = new Player("Sirius", "Blue", "12345678");
        player2 = new Player("Calypso", "Pink", "12345679");
        player3 = new Player("Hermione", "Green", "12345688");
        try {
            game.addPlayer(player1);
            game.addPlayer(player2);
            game.addPlayer(player3);
        }
        catch (MaxNumberPlayerException e){}
        catch (InvalidColorException e) {}
        game.startGame();
        assertEquals(0, player1.getAction());
        assertTrue(player1.getActive());
        assertFalse(player2.getActive());
        assertFalse(player3.getActive());
        assertEquals(0, player1.getAction());

        player1.setAction();
        player1.setAction();
        //After having done two actions, player1 has ended its turn
        game.setTurn();
        assertFalse(player1.getActive());
        assertTrue(player2.getActive());
        assertFalse(player3.getActive());
    }*/

}