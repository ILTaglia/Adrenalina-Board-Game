package model;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class DamageTest {
    @Test
    public void creationtest()
    {
        ArrayList<Damage> D;
        D=new ArrayList<>();
        D.add(new DamageFactory().getInstanceOf(1,5));
        assertEquals(5,D.get(0).getDamage());
    }

    @Test
    public void LifeTest()
    {
        Damage D;
        D=new DamageFactory().getInstanceOf(1,1);
        assertEquals(D.getDamage(),1);
        D.setDamage(5);
        assertEquals(D.getDamage(),5);
        assertEquals(0, D.getType());
    }

    @Test
    public void MarkTest()
    {
        Damage D;
        D=new DamageFactory().getInstanceOf(2,1);
        assertEquals(D.getDamage(),1);
        D.setDamage(5);
        assertEquals(D.getDamage(),5);
        assertEquals(1, D.getType());
    }


    @Test
    public void multipledamages()
    {
        ArrayList<Damage> D;
        D=new ArrayList<>();
        Damage y,x;
        x=new DamageFactory().getInstanceOf(1,6);
        y=new DamageFactory().getInstanceOf(2,7);

        D.add(new DamageFactory().getInstanceOf(1,5));
        D.add(new DamageFactory().getInstanceOf(2,4));
        D.add(new DamageFactory().getInstanceOf(1,6));
        D.add(new DamageFactory().getInstanceOf(2,7));
        D.add(new DamageFactory().getInstanceOf(1,9));

        assertEquals(y.getDamage(),D.get(3).getDamage());
        assertEquals(x.getDamage(),D.get(2).getDamage());

    }
}
