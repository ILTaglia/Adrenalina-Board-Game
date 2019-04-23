package Controller;

import Model.Match;
import Model.Player;
import exceptions.InvalidColorException;
import exceptions.MaxNumberPlayerException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
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
        game.add_match(match);
        r = game.get_rand();
    }
    @Test
    public void random_id() {
        String s = game.random_id(r);
        p1 = new Player("Sirius", "blue", s);
        try {
            match.add_player(p1);
        }
        catch (MaxNumberPlayerException e){}
        catch (InvalidColorException e) {}
        System.out.println(s);

        s = game.random_id(r);
        p2 = new Player("Calypso", "pink", s);
        try {
            match.add_player(p2);
        }
        catch (MaxNumberPlayerException e){}
        catch (InvalidColorException e) {}
        System.out.println(s);

        s = game.random_id(r);
        p3 = new Player("Hermione", "green", s);
        try {
            match.add_player(p3);
        }
        catch (MaxNumberPlayerException e){}
        catch (InvalidColorException e) {}
        System.out.println(s);

        s = game.random_id(r);
        p4 = new Player("Aries", "green", s);
        assertThrows(InvalidColorException.class, () -> match.add_player(p4));
        p4 = new Player("Aries", "yellow", s);
        try {
            match.add_player(p4);
        }
        catch (MaxNumberPlayerException e){}
        catch (InvalidColorException e) {}
        System.out.println(s);

        s = game.random_id(r);
        p5 = new Player("Karka", "grey", s);
        try {
            match.add_player(p5);
        }
        catch (MaxNumberPlayerException e){}
        catch (InvalidColorException e) {}
        System.out.println(s);
    }

}