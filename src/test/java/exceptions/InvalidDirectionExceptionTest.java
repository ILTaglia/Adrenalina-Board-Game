package exceptions;

import org.junit.Before;
import org.junit.Test;

import controller.Run;
import model.Player;
import model.Match;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class InvalidDirectionExceptionTest {
    Run r;
    Match match;
    Player player;

    @Before
    public void SetUp(){
        match = new Match();
        match.createDashboard(1);
        try{
            match.addPlayer(player);
        }
        catch (MaxNumberPlayerException e){}
        r = new Run();
        player = new Player("Sirius", "blue", "10583741");
        player.setCel(0,0);
    }
    @Test
    public void test(){
        assertThrows(InvalidDirectionException.class, () -> r.getDirection("H"));
    }


}