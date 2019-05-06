package controller;

import model.Match;
import model.Player;
import exceptions.InvalidColorException;
import exceptions.MaxNumberPlayerException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;

public class GameTest {
    Game game = new Game();
    Match match = new Match();
    Player p1;
    Player p2;
    Player p3;
    Player p4;
    Player p5;
    Random r;

    @Before
    public void setUp(){
        game.addMatch(match);
        r = game.getRand();
    }
    @Test
    public void random_id() {
        String s = game.randomId(r);
        p1 = new Player("Sirius", "blue", s);
        try {
            match.addPlayer(p1);
        }
        catch (MaxNumberPlayerException e){}
        catch (InvalidColorException e) {}
        System.out.println(s);

        s = game.randomId(r);
        p2 = new Player("Calypso", "pink", s);
        try {
            match.addPlayer(p2);
        }
        catch (MaxNumberPlayerException e){}
        catch (InvalidColorException e) {}
        System.out.println(s);

        s = game.randomId(r);
        p3 = new Player("Hermione", "green", s);
        try {
            match.addPlayer(p3);
        }
        catch (MaxNumberPlayerException e){}
        catch (InvalidColorException e) {}
        System.out.println(s);

        s = game.randomId(r);
        p4 = new Player("Aries", "green", s);
        assertThrows(InvalidColorException.class, () -> match.addPlayer(p4));
        p4 = new Player("Aries", "yellow", s);
        try {
            match.addPlayer(p4);
        }
        catch (MaxNumberPlayerException e){}
        catch (InvalidColorException e) {}
        System.out.println(s);

        s = game.randomId(r);
        p5 = new Player("Karka", "grey", s);
        try {
            match.addPlayer(p5);
        }
        catch (MaxNumberPlayerException e){}
        catch (InvalidColorException e) {}
        System.out.println(s);
    }

}