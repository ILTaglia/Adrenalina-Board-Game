package exceptions;

import model.Ammo;
import model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class NotEnoughAmmosExceptionTest {
    Player player;
    Ammo ammo;

    @BeforeEach
    public void setUp() throws Exception {
        player = new Player("Sirius", "Blue", "10583741");
        ammo = new Ammo(0); //new ammo blue
    }

    @Test
    public void test(){
        //assertThrows(NotEnoughAmmosExceptionTest.class, () -> player.removeAmmo(2, ammo));
    }
}