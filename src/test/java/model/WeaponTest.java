package model;

import exceptions.WeaponAlreadyLoadedException;
import exceptions.WeaponAlreadyUsedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class WeaponTest {

    @Test
    public void grabtest()
    {
        Weapon weapon = new Weapon("Prova");
        weapon.addCost(0);
        weapon.addCost(1);
        weapon.addCost(2);
        weapon.addCost(2);
        weapon.addCost(2);
        weapon.addCost(1);
        weapon.addCost(0);
        assertEquals((int)weapon.getCostToGrab().get(0),1);
        assertEquals((int)weapon.getCostToGrab().get(1),2);
        assertEquals((int)weapon.getCostToGrab().get(2),3);

    }

    @Test
    public void rechargetest()
    {
        Weapon weapon = new Weapon("Prova");
        weapon.addCost(0);
        weapon.addCost(1);
        weapon.addCost(2);
        weapon.addCost(2);
        weapon.addCost(2);
        weapon.addCost(1);
        weapon.addCost(0);
        assertEquals(2, (int)weapon.getCostToRecharge().get(0));
        assertEquals(2,(int)weapon.getCostToRecharge().get(1));
        assertEquals(3, (int)weapon.getCostToRecharge().get(2));

        Weapon weapon1 = new Weapon("Ricarica");
        weapon.addCost(0);
        weapon.addCost(1);
        weapon.addCost(2);
        weapon.addCost(2);
        assertFalse(weapon1.getStatus());
        try{weapon1.shooted();}
        catch(WeaponAlreadyUsedException e){}
        assertTrue(weapon1.getStatus());
        assertThrows(WeaponAlreadyUsedException.class,() -> weapon1.shooted());
        try{weapon1.recharge();}
        catch(WeaponAlreadyLoadedException e ){}
        assertFalse(weapon1.getStatus());
        assertThrows(WeaponAlreadyLoadedException.class,() -> weapon1.recharge());

    }
}
