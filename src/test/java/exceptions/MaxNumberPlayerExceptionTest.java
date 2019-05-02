package exceptions;

import Model.Match;
import Model.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MaxNumberPlayerExceptionTest {
    Match match;
    Player player1;
    Player player2;
    Player player3;
    Player player4;
    Player player5;
    Player player6;

    @Before
    public void setUp() throws Exception {
        match = new Match();
        player1 = new Player("Sirius", "blue", "10583741");
        player2 = new Player("Calypso", "pink", "14253954");
        player3 = new Player("Hermione", "green", "18263100");
        player4 = new Player("Aries", "yellow", "18992302");
        player5 = new Player("Karka", "grey", "18114320");
        player6 = new Player("Iron", "blue", "18752344");
        try {
            match.add_player(player1);
            match.add_player(player2);
            match.add_player(player3);
            match.add_player(player4);
            match.add_player(player5);
        }
        catch (MaxNumberPlayerException e){}
    }

    @Test
    public void test(){
        assertThrows(MaxNumberPlayerException.class, () -> match.add_player(player6));
    }
}