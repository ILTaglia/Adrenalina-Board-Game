package model;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;


public class EffectTest {
    @Test
    public void CreationTest()
    {
        Effect E;
        E=new EffectFactory().getinstanceof(1,5);
        assertEquals(E.getId(),5);
        E=new EffectFactory().getinstanceof(2,2);
        assertEquals(E.getId(),2);
    }

    @Test
        public void Testlist()
        {
            ArrayList<Effect>E;
            E=new ArrayList<Effect>();
            E.add(new EffectFactory().getinstanceof(1,5));
            E.add(new EffectFactory().getinstanceof(2,5));
            E.add(new EffectFactory().getinstanceof(2,2));
            E.add(new EffectFactory().getinstanceof(1,9));
            E.add(new EffectFactory().getinstanceof(1,5));
            assertEquals(E.size(),5);
            assertEquals(E.get(0).getId(),5);
            assertEquals(E.get(3).getId(),9);
            assertEquals(E.get(4).getId(),5);
            assertEquals(E.get(2).getId(),2);
        }
    @Test
    public void subclasses1()
    {
        Effect E;
        E=new EffectFactory().getinstanceof(1,2);
        E.adddamage(1,2);
        assertEquals(E.getDamage(0).getdamage(),2);
    }

    @Test
    public void subclasses2()
    {
        Effect E;
        E=new EffectFactory().getinstanceof(1,2);
        E.adddamage(1,2);
        E.getDamage(0).setdamage(6);
        assertEquals(E.getDamage(0).getdamage(),6);
    }

    @Test
    public void multipledamages()
    {
        Effect E;
        E=new EffectFactory().getinstanceof(1,1);
        E.adddamage(1,4);
        E.adddamage(2,8);
        E.adddamage(2,1);
        assertEquals(E.getDamage(0).getdamage(),4);
        assertEquals(E.getDamage(1).getdamage(),8);
        assertEquals(E.getDamage(2).getdamage(),1);
    }

    @Test
    public void PlayerEffectId()
    {
        Effect E;
        E=new EffectFactory().getinstanceof(1,5);
        assertEquals(E.getId(),5);
        E.setId(8);
        assertEquals(E.getId(),8);
    }

    @Test
    public void CellEffectId()
    {
        Effect E;
        E=new EffectFactory().getinstanceof(2,5);
        assertEquals(E.getId(),5);
        E.setId(8);
        assertEquals(E.getId(),8);
    }


    @Test
    public void multiplesetdamages()
    {
        Effect E;
        E=new EffectFactory().getinstanceof(1,1);
        E.adddamage(1,4);
        E.adddamage(2,8);
        E.adddamage(2,1);
        E.getDamage(0).setdamage(1);
        E.getDamage(1).setdamage(2);
        E.getDamage(2).setdamage(3);
        assertEquals(E.getDamage(0).getdamage(),1);
        assertEquals(E.getDamage(1).getdamage(),2);
        assertEquals(E.getDamage(2).getdamage(),3);
    }


    @Test
    public void numberdamageTest()
    {
        Effect E;
        E=new EffectFactory().getinstanceof(1,1);
        E.adddamage(1,1);
        E.adddamage(1,1);
        E.adddamage(2,1);
        E.adddamage(2,1);
        E.adddamage(1,1);
        assertEquals(E.getnumberdamage(),5);
    }

    @Test
    public void damagesTest()
    {
        Effect E;
        E=new EffectFactory().getinstanceof(1,1);
        E.adddamage(1,2);
        E.adddamage(2,4);
        assertEquals(E.getDamage(0).getdamage(),2);
        assertEquals(E.getDamage(1).getdamage(),4);
        assertEquals(E.getnumberdamage(),2);
        E=new EffectFactory().getinstanceof(2,1);
        E.adddamage(1,2);
        E.adddamage(2,4);
        assertEquals(E.getDamage(0).getdamage(),2);
        assertEquals(E.getDamage(1).getdamage(),4);
        assertEquals(E.getnumberdamage(),2);
    }



}
