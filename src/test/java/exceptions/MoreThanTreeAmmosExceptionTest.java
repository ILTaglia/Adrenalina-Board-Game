package exceptions;

import model.Ammo;
import model.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MoreThanTreeAmmosExceptionTest {
    Player player;
    Ammo ammo;

    @Before
    public void setUp() throws Exception {
        player = new Player("Sirius", "Blue", "10583741");
        ammo = new Ammo(0); //new ammo blue
        try{
            player.addAmmo(ammo);
            player.addAmmo(ammo);
        }
        catch (MoreThanTreeAmmosException e){}
    }

    @Test
    public void test(){
        assertThrows(MoreThanTreeAmmosException.class, () -> player.addAmmo(ammo));
    }
}