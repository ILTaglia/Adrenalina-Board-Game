package exceptions;

import org.junit.Before;
import org.junit.Test;

import Controller.Run;
import Model.Player;
import Model.Match;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class InvalidDirectionExceptionTest {
    Run r;
    Match match;
    Player player;

    @Before
    public void SetUp(){
        match = new Match();
        match.createdashboard(1);
        try{
            match.addplayer(player);
        }
        catch (MaxNumberPlayerException e){}
        r = new Run();
        player = new Player("Sirius", "blue", "10583741");
        player.setCel(0,0);
    }
    @Test
    public void test(){
        assertThrows(InvalidDirectionException.class, () -> r.getdirection("H"));
        assertThrows(InvalidDirectionException.class, () -> r.movement(match, player,"N"));
    }


}