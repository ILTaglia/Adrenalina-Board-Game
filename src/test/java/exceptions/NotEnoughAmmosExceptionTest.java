package exceptions;

import Model.Ammo;
import Model.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NotEnoughAmmosExceptionTest {
    Player player;
    Ammo ammo;

    @Before
    public void setUp() throws Exception {
        player = new Player("Sirius", "blue", "10583741");
        ammo = new Ammo(0); //new ammo blue
    }

    @Test
    public void test(){
        //assertThrows(NotEnoughAmmosExceptionTest.class, () -> player.remove_ammo(2, ammo));
    }
}