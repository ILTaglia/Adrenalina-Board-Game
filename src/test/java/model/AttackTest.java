package model;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;

public class AttackTest {
    @Test
    public void CreateAttack()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(1,2,3,4,5);
        assertEquals(A.getDistance(),3);
        assertEquals(A.getMoveme(),4);
        assertEquals(A.getMoveyou(),5);
        assertEquals(A.getTypeplayer(),2);
    }

    @Test
    public void MultipleAttack()
    {
        ArrayList<TypeAttack> A;
        A=new ArrayList<TypeAttack>();
        A.add(new AttackFactory().getinstanceof(1,2,3,4,5));
        A.add(new AttackFactory().getinstanceof(6,7,8,9,10));
        A.add(new AttackFactory().getinstanceof(7,6,5,4,3));
        assertEquals(A.get(0).getTypeplayer(),2);
        assertEquals(A.get(0).getMoveyou(),5);
        assertEquals(A.get(0).getMoveme(),4);
        assertEquals(A.get(0).getDistance(),3);
        assertEquals(A.get(1).getTypeplayer(),7);
        assertEquals(A.get(1).getMoveyou(),10);
        assertEquals(A.get(1).getMoveme(),9);
        assertEquals(A.get(1).getDistance(),8);
        assertEquals(A.get(2).getTypeplayer(),6);
        assertEquals(A.get(2).getMoveyou(),3);
        assertEquals(A.get(2).getMoveme(),4);
        assertEquals(A.get(2).getDistance(),5);
    }

    @Test
    public void TestSet()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(1,8,8,8,8);
        A.setDistance(3);
        A.setMoveme(4);
        A.setMoveyou(5);
        A.setTypeplayer(2);
        assertEquals(A.getDistance(),3);
        assertEquals(A.getMoveme(),4);
        assertEquals(A.getMoveyou(),5);
        assertEquals(A.getTypeplayer(),2);
    }

    @Test
    public void TestzeroNumberelement()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(1,8,8,8,8);
        assertEquals(A.getnumbereffect(),0);
        assertEquals(A.getnumberextra(),0);
    }

    @Test
    public void deeptest1()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(1,8,8,8,8);
        A.addextra(1);
        A.addextra(2);
        A.addextra(44);
        assertEquals(A.getnumberextra(),3);
    }


    @Test
    public void deeptest2()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(1,8,8,8,8);
        A.addeffect(new EffectFactory().getinstanceof(1,2));
        A.addeffect(new EffectFactory().getinstanceof(2,2));
        A.addeffect(new EffectFactory().getinstanceof(1,3));
        A.addeffect(new EffectFactory().getinstanceof(2,8));
        assertEquals(A.getnumbereffect(),4);
    }


    @Test
    public void EffectTest()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(1,8,8,8,8);
        A.addeffect(new EffectFactory().getinstanceof(1,1));
        assertEquals(A.getEffect(0).getId(),1);
        A=new AttackFactory().getinstanceof(2,8,8,8,8);
        A.addeffect(new EffectFactory().getinstanceof(1,1));
        assertEquals(A.getEffect(0).getId(),1);
        A=new AttackFactory().getinstanceof(3,8,8,8,8);
        A.addeffect(new EffectFactory().getinstanceof(1,1));
        assertEquals(A.getEffect(0).getId(),1);
        A=new AttackFactory().getinstanceof(4,8,8,8,8);
        A.addeffect(new EffectFactory().getinstanceof(1,1));
        assertEquals(A.getEffect(0).getId(),1);
    }

    @Test
    public void ExtraTest()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(1,8,8,8,8);
        A.addextra(1);
        A.addextra(2);
        A.addextra(3);
        assertEquals(A.getextras().get(0),1);
        assertEquals(A.getextras().get(1),2);
        assertEquals(A.getextras().get(2),3);
        A=new AttackFactory().getinstanceof(2,8,8,8,8);
        A.addextra(1);
        A.addextra(2);
        A.addextra(3);
        assertEquals(A.getextras().get(0),1);
        assertEquals(A.getextras().get(1),2);
        assertEquals(A.getextras().get(2),3);
        A=new AttackFactory().getinstanceof(3,8,8,8,8);
        A.addextra(1);
        A.addextra(2);
        A.addextra(3);
        assertEquals(A.getextras().get(0),1);
        assertEquals(A.getextras().get(1),2);
        assertEquals(A.getextras().get(2),3);
        A=new AttackFactory().getinstanceof(4,8,8,8,8);
        A.addextra(1);
        A.addextra(2);
        A.addextra(3);
        assertEquals(A.getextras().get(0),1);
        assertEquals(A.getextras().get(1),2);
        assertEquals(A.getextras().get(2),3);
    }

    @Test
    public void DistanceTest1()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(1,1,1,1,1);
        assertEquals(A.getDistance(),1);
        A.setDistance(2);
        assertEquals(A.getDistance(),2);
    }

    @Test
    public void DistanceTest2()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(2,1,1,1,1);
        assertEquals(A.getDistance(),1);
        A.setDistance(2);
        assertEquals(A.getDistance(),2);
    }

    @Test
    public void DistanceTest3()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(3,1,1,1,1);
        assertEquals(A.getDistance(),1);
        A.setDistance(2);
        assertEquals(A.getDistance(),2);
    }

    @Test
    public void DistanceTest4()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(4,1,1,1,1);
        assertEquals(A.getDistance(),1);
        A.setDistance(2);
        assertEquals(A.getDistance(),2);
    }

    @Test
    public void TypeplayerTest1()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(1,1,1,1,1);
        assertEquals(A.getTypeplayer(),1);
        A.setTypeplayer(2);
        assertEquals(A.getTypeplayer(),2);
    }

    @Test
    public void TypeplayerTest2()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(2,1,1,1,1);
        assertEquals(A.getTypeplayer(),1);
        A.setTypeplayer(2);
        assertEquals(A.getTypeplayer(),2);
    }

    @Test
    public void TypeplayerTest3()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(3,1,1,1,1);
        assertEquals(A.getTypeplayer(),1);
        A.setTypeplayer(2);
        assertEquals(A.getTypeplayer(),2);
    }

    @Test
    public void TypeplayerTest4()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(4,1,1,1,1);
        assertEquals(A.getTypeplayer(),1);
        A.setTypeplayer(2);
        assertEquals(A.getTypeplayer(),2);
    }

    @Test
    public void MovemeTest1()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(1,1,1,1,1);
        assertEquals(A.getMoveme(),1);
        A.setMoveme(2);
        assertEquals(A.getMoveme(),2);
    }

    @Test
    public void MovemeTest2()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(2,1,1,1,1);
        assertEquals(A.getMoveme(),1);
        A.setMoveme(2);
        assertEquals(A.getMoveme(),2);
    }

    @Test
    public void MovemeTest3()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(3,1,1,1,1);
        assertEquals(A.getMoveme(),1);
        A.setMoveme(2);
        assertEquals(A.getMoveme(),2);
    }

    @Test
    public void MovemeTest4()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(4,1,1,1,1);
        assertEquals(A.getMoveme(),1);
        A.setMoveme(2);
        assertEquals(A.getMoveme(),2);
    }

    @Test
    public void MoveouTest1()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(1,1,1,1,1);
        assertEquals(A.getMoveyou(),1);
        A.setMoveyou(2);
        assertEquals(A.getMoveyou(),2);
    }

    @Test
    public void MoveouTest2()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(2,1,1,1,1);
        assertEquals(A.getMoveyou(),1);
        A.setMoveyou(2);
        assertEquals(A.getMoveyou(),2);
    }

    @Test
    public void MoveouTest3()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(3,1,1,1,1);
        assertEquals(A.getMoveyou(),1);
        A.setMoveyou(2);
        assertEquals(A.getMoveyou(),2);
    }

    @Test
    public void MoveouTest4()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(4,1,1,1,1);
        assertEquals(A.getMoveyou(),1);
        A.setMoveyou(2);
        assertEquals(A.getMoveyou(),2);
    }

    @Test
    public void EffectTest1()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(1,1,1,1,1);
        A.addeffect(new EffectFactory().getinstanceof(1,1));
        A.addeffect(new EffectFactory().getinstanceof(2,2));
        assertEquals(A.getnumbereffect(),2);
        assertEquals(A.getEffect(1).getId(),2);
    }

    @Test
    public void EffectTest2()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(2,1,1,1,1);
        A.addeffect(new EffectFactory().getinstanceof(1,1));
        A.addeffect(new EffectFactory().getinstanceof(2,2));
        assertEquals(A.getnumbereffect(),2);
        assertEquals(A.getEffect(1).getId(),2);
    }

    @Test
    public void EffectTest3()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(3,1,1,1,1);
        A.addeffect(new EffectFactory().getinstanceof(1,1));
        A.addeffect(new EffectFactory().getinstanceof(2,2));
        assertEquals(A.getnumbereffect(),2);
        assertEquals(A.getEffect(1).getId(),2);
    }

    @Test
    public void EffectTest4()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(4,1,1,1,1);
        A.addeffect(new EffectFactory().getinstanceof(1,1));
        A.addeffect(new EffectFactory().getinstanceof(2,2));
        assertEquals(A.getnumbereffect(),2);
        assertEquals(A.getEffect(1).getId(),2);
    }

    @Test
    public void ExtraTest1()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(1,1,1,1,1);
        A.addextra(1);
        A.addextra(2);
        assertEquals(A.getnumberextra(),2);
        assertEquals(A.getextras().get(1),2);
    }

    @Test
    public void ExtraTest2()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(2,1,1,1,1);
        A.addextra(1);
        A.addextra(2);
        assertEquals(A.getnumberextra(),2);
        assertEquals(A.getextras().get(1),2);
    }

    @Test
    public void ExtraTest3()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(3,1,1,1,1);
        A.addextra(1);
        A.addextra(2);
        assertEquals(A.getnumberextra(),2);
        assertEquals(A.getextras().get(1),2);
    }

    @Test
    public void ExtraTest4()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(4,1,1,1,1);
        A.addextra(1);
        A.addextra(2);
        assertEquals(A.getnumberextra(),2);
        assertEquals(A.getextras().get(1),2);
    }


}
