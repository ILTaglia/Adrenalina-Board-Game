package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
public class WeponTest {

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
        assertEquals((int)weapon.getCostToRecharge().get(0),2);
        assertEquals((int)weapon.getCostToRecharge().get(1),2);
        assertEquals((int)weapon.getCostToRecharge().get(2),3);
    }
}
