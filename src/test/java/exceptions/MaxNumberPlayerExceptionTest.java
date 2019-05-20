package exceptions;

import model.Match;
import model.Player;
import org.junit.Before;
import org.junit.Test;

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
        player1 = new Player("Sirius", "Blue", "10583741");
        player2 = new Player("Calypso", "Pink", "14253954");
        player3 = new Player("Hermione", "Green", "18263100");
        player4 = new Player("Aries", "Yellow", "18992302");
        player5 = new Player("Karka", "Grey", "18114320");
        player6 = new Player("Iron", "Blue", "18752344");
        try {
            match.addPlayer(player1);
            match.addPlayer(player2);
            match.addPlayer(player3);
            match.addPlayer(player4);
            match.addPlayer(player5);
        }
        catch (MaxNumberPlayerException e){}
    }

    @Test
    public void test(){
        assertThrows(MaxNumberPlayerException.class, () -> match.addPlayer(player6));
    }
}