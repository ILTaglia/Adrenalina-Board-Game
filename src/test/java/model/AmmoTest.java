package model;

import exceptions.InvalidColorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AmmoTest {
    Ammo ammo;

    @BeforeEach
    public void setUp() {
        ammo=new Ammo(0);
    }

    @Test
    public void getTest(){
        assertEquals(0,ammo.getAmmo());
    }

    @Test
    public void ColorExceptionTest(){
        assertThrows(InvalidColorException.class,()->new Ammo(4));
        assertThrows(InvalidColorException.class,()->new Ammo(-1));
    }

}
