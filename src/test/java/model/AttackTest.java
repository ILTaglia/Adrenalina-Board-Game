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
        assertEquals(A.getMoveMe(),4);
        assertEquals(A.getMoveYou(),5);
        assertEquals(A.getTypePlayer(),2);
    }

    @Test
    public void MultipleAttack()
    {
        ArrayList<TypeAttack> A;
        A=new ArrayList<TypeAttack>();
        A.add(new AttackFactory().getinstanceof(1,2,3,4,5));
        A.add(new AttackFactory().getinstanceof(6,7,8,9,10));
        A.add(new AttackFactory().getinstanceof(7,6,5,4,3));
        assertEquals(A.get(0).getTypePlayer(),2);
        assertEquals(A.get(0).getMoveYou(),5);
        assertEquals(A.get(0).getMoveMe(),4);
        assertEquals(A.get(0).getDistance(),3);
        assertEquals(A.get(1).getTypePlayer(),7);
        assertEquals(A.get(1).getMoveYou(),10);
        assertEquals(A.get(1).getMoveMe(),9);
        assertEquals(A.get(1).getDistance(),8);
        assertEquals(A.get(2).getTypePlayer(),6);
        assertEquals(A.get(2).getMoveYou(),3);
        assertEquals(A.get(2).getMoveMe(),4);
        assertEquals(A.get(2).getDistance(),5);
    }

    @Test
    public void TestSet()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(1,8,8,8,8);
        A.setDistance(3);
        A.setMoveMe(4);
        A.setMoveYou(5);
        A.setTypePlayer(2);
        assertEquals(A.getDistance(),3);
        assertEquals(A.getMoveMe(),4);
        assertEquals(A.getMoveYou(),5);
        assertEquals(A.getTypePlayer(),2);
    }

    @Test
    public void TestzeroNumberelement()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(1,8,8,8,8);
        assertEquals(A.getNumberEffect(),0);
        assertEquals(A.getNumberExtra(),0);
    }

    @Test
    public void deeptest1()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(1,8,8,8,8);
        A.addExtra(1);
        A.addExtra(2);
        A.addExtra(44);
        assertEquals(A.getNumberExtra(),3);
    }


    @Test
    public void deeptest2()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(1,8,8,8,8);
        A.addEffect(new EffectFactory().getinstanceof(1,2));
        A.addEffect(new EffectFactory().getinstanceof(2,2));
        A.addEffect(new EffectFactory().getinstanceof(1,3));
        A.addEffect(new EffectFactory().getinstanceof(2,8));
        assertEquals(A.getNumberEffect(),4);
    }


    @Test
    public void EffectTest()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(1,8,8,8,8);
        A.addEffect(new EffectFactory().getinstanceof(1,1));
        assertEquals(A.getEffect(0).getId(),1);
        A=new AttackFactory().getinstanceof(2,8,8,8,8);
        A.addEffect(new EffectFactory().getinstanceof(1,1));
        assertEquals(A.getEffect(0).getId(),1);
        A=new AttackFactory().getinstanceof(3,8,8,8,8);
        A.addEffect(new EffectFactory().getinstanceof(1,1));
        assertEquals(A.getEffect(0).getId(),1);
        A=new AttackFactory().getinstanceof(4,8,8,8,8);
        A.addEffect(new EffectFactory().getinstanceof(1,1));
        assertEquals(A.getEffect(0).getId(),1);
    }

    @Test
    public void ExtraTest()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(1,8,8,8,8);
        A.addExtra(1);
        A.addExtra(2);
        A.addExtra(3);
        assertEquals(A.getExtras().get(0),1);
        assertEquals(A.getExtras().get(1),2);
        assertEquals(A.getExtras().get(2),3);
        A=new AttackFactory().getinstanceof(2,8,8,8,8);
        A.addExtra(1);
        A.addExtra(2);
        A.addExtra(3);
        assertEquals(A.getExtras().get(0),1);
        assertEquals(A.getExtras().get(1),2);
        assertEquals(A.getExtras().get(2),3);
        A=new AttackFactory().getinstanceof(3,8,8,8,8);
        A.addExtra(1);
        A.addExtra(2);
        A.addExtra(3);
        assertEquals(A.getExtras().get(0),1);
        assertEquals(A.getExtras().get(1),2);
        assertEquals(A.getExtras().get(2),3);
        A=new AttackFactory().getinstanceof(4,8,8,8,8);
        A.addExtra(1);
        A.addExtra(2);
        A.addExtra(3);
        assertEquals(A.getExtras().get(0),1);
        assertEquals(A.getExtras().get(1),2);
        assertEquals(A.getExtras().get(2),3);
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
        assertEquals(A.getTypePlayer(),1);
        A.setTypePlayer(2);
        assertEquals(A.getTypePlayer(),2);
    }

    @Test
    public void TypeplayerTest2()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(2,1,1,1,1);
        assertEquals(A.getTypePlayer(),1);
        A.setTypePlayer(2);
        assertEquals(A.getTypePlayer(),2);
    }

    @Test
    public void TypeplayerTest3()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(3,1,1,1,1);
        assertEquals(A.getTypePlayer(),1);
        A.setTypePlayer(2);
        assertEquals(A.getTypePlayer(),2);
    }

    @Test
    public void TypeplayerTest4()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(4,1,1,1,1);
        assertEquals(A.getTypePlayer(),1);
        A.setTypePlayer(2);
        assertEquals(A.getTypePlayer(),2);
    }

    @Test
    public void MovemeTest1()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(1,1,1,1,1);
        assertEquals(A.getMoveMe(),1);
        A.setMoveMe(2);
        assertEquals(A.getMoveMe(),2);
    }

    @Test
    public void MovemeTest2()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(2,1,1,1,1);
        assertEquals(A.getMoveMe(),1);
        A.setMoveMe(2);
        assertEquals(A.getMoveMe(),2);
    }

    @Test
    public void MovemeTest3()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(3,1,1,1,1);
        assertEquals(A.getMoveMe(),1);
        A.setMoveMe(2);
        assertEquals(A.getMoveMe(),2);
    }

    @Test
    public void MovemeTest4()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(4,1,1,1,1);
        assertEquals(A.getMoveMe(),1);
        A.setMoveMe(2);
        assertEquals(A.getMoveMe(),2);
    }

    @Test
    public void MoveouTest1()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(1,1,1,1,1);
        assertEquals(A.getMoveYou(),1);
        A.setMoveYou(2);
        assertEquals(A.getMoveYou(),2);
    }

    @Test
    public void MoveouTest2()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(2,1,1,1,1);
        assertEquals(A.getMoveYou(),1);
        A.setMoveYou(2);
        assertEquals(A.getMoveYou(),2);
    }

    @Test
    public void MoveouTest3()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(3,1,1,1,1);
        assertEquals(A.getMoveYou(),1);
        A.setMoveYou(2);
        assertEquals(A.getMoveYou(),2);
    }

    @Test
    public void MoveouTest4()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(4,1,1,1,1);
        assertEquals(A.getMoveYou(),1);
        A.setMoveYou(2);
        assertEquals(A.getMoveYou(),2);
    }

    @Test
    public void EffectTest1()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(1,1,1,1,1);
        A.addEffect(new EffectFactory().getinstanceof(1,1));
        A.addEffect(new EffectFactory().getinstanceof(2,2));
        assertEquals(A.getNumberEffect(),2);
        assertEquals(A.getEffect(1).getId(),2);
    }

    @Test
    public void EffectTest2()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(2,1,1,1,1);
        A.addEffect(new EffectFactory().getinstanceof(1,1));
        A.addEffect(new EffectFactory().getinstanceof(2,2));
        assertEquals(A.getNumberEffect(),2);
        assertEquals(A.getEffect(1).getId(),2);
    }

    @Test
    public void EffectTest3()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(3,1,1,1,1);
        A.addEffect(new EffectFactory().getinstanceof(1,1));
        A.addEffect(new EffectFactory().getinstanceof(2,2));
        assertEquals(A.getNumberEffect(),2);
        assertEquals(A.getEffect(1).getId(),2);
    }

    @Test
    public void EffectTest4()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(4,1,1,1,1);
        A.addEffect(new EffectFactory().getinstanceof(1,1));
        A.addEffect(new EffectFactory().getinstanceof(2,2));
        assertEquals(A.getNumberEffect(),2);
        assertEquals(A.getEffect(1).getId(),2);
    }

    @Test
    public void ExtraTest1()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(1,1,1,1,1);
        A.addExtra(1);
        A.addExtra(2);
        assertEquals(A.getNumberExtra(),2);
        assertEquals(A.getExtras().get(1),2);
    }

    @Test
    public void ExtraTest2()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(2,1,1,1,1);
        A.addExtra(1);
        A.addExtra(2);
        assertEquals(A.getNumberExtra(),2);
        assertEquals(A.getExtras().get(1),2);
    }

    @Test
    public void ExtraTest3()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(3,1,1,1,1);
        A.addExtra(1);
        A.addExtra(2);
        assertEquals(A.getNumberExtra(),2);
        assertEquals(A.getExtras().get(1),2);
    }

    @Test
    public void ExtraTest4()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(4,1,1,1,1);
        A.addExtra(1);
        A.addExtra(2);
        assertEquals(A.getNumberExtra(),2);
        assertEquals(A.getExtras().get(1),2);
    }


}
