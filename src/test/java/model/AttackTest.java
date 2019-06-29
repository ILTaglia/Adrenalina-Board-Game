package model;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;

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

    @Test
    public void EffectTest5()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(5,1,2,5,4);

        A.addEffect(new EffectFactory().getinstanceof(1,1));
        A.addEffect(new EffectFactory().getinstanceof(2,2));
        assertEquals(2, A.getNumberEffect());
        assertEquals(2, A.getEffect(1).getId());

    }

    @Test
    public void ExtraTest5(){
        TypeAttack A;
        A=new AttackFactory().getinstanceof(5,1,2,5,4);
        A.addExtra(1);
        A.addExtra(2);
        assertEquals(2, A.getNumberExtra());
        assertEquals(2, A.getExtras().get(1));
    }

    @Test
    public void DistanceTest5(){
        TypeAttack A;
        A=new AttackFactory().getinstanceof(5,1,2,5,4);
        assertEquals(2, A.getDistance());
        A.setDistance(1);
        assertEquals(1, A.getDistance());
    }

    @Test
    public void TypeplayerTest5(){
        TypeAttack A;
        A=new AttackFactory().getinstanceof(5,1,2,5,4);
        assertEquals(1, A.getTypePlayer());
        A.setTypePlayer(2);
        assertEquals(2, A.getTypePlayer());
    }

    @Test
    public void MovemeTest5(){
        TypeAttack A;
        A=new AttackFactory().getinstanceof(5,1,2,5,4);
        assertEquals(5, A.getMoveMe());
        A.setMoveMe(2);
        assertEquals(2, A.getMoveMe());
    }

    @Test
    public void MoveyouTest5(){
        TypeAttack A;
        A=new AttackFactory().getinstanceof(5,1,2,5,4);
        assertEquals(4, A.getMoveYou());
        A.setMoveYou(2);
        assertEquals(2, A.getMoveYou());
    }

    @Test
    public void EffectTest6()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(6,7,8,9,10);

        A.addEffect(new EffectFactory().getinstanceof(1,1));
        A.addEffect(new EffectFactory().getinstanceof(2,2));
        assertEquals(2, A.getNumberEffect());
        assertEquals(2, A.getEffect(1).getId());

    }

    @Test
    public void ExtraTest6(){
        TypeAttack A;
        A=new AttackFactory().getinstanceof(6,7,8,9,10);
        A.addExtra(1);
        A.addExtra(2);
        assertEquals(2, A.getNumberExtra());
        assertEquals(2, A.getExtras().get(1));
    }

    @Test
    public void DistanceTest6(){
        TypeAttack A;
        A=new AttackFactory().getinstanceof(6,7,8,9,10);
        assertEquals(8, A.getDistance());
        A.setDistance(1);
        assertEquals(1, A.getDistance());
    }

    @Test
    public void TypeplayerTest6(){
        TypeAttack A;
        A=new AttackFactory().getinstanceof(6,7,8,9,10);
        assertEquals(7, A.getTypePlayer());
        A.setTypePlayer(2);
        assertEquals(2, A.getTypePlayer());
    }

    @Test
    public void MovemeTest6(){
        TypeAttack A;
        A=new AttackFactory().getinstanceof(6,7,8,9,10);
        assertEquals(9, A.getMoveMe());
        A.setMoveMe(2);
        assertEquals(2, A.getMoveMe());
    }

    @Test
    public void MoveyouTest6(){
        TypeAttack A;
        A=new AttackFactory().getinstanceof(6,7,8,9,10);
        assertEquals(10, A.getMoveYou());
        A.setMoveYou(2);
        assertEquals(2, A.getMoveYou());
    }

    @Test
    public void EffectTest7()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(7,6,5,4,3);

        A.addEffect(new EffectFactory().getinstanceof(1,1));
        A.addEffect(new EffectFactory().getinstanceof(2,2));
        assertEquals(2, A.getNumberEffect());
        assertEquals(2, A.getEffect(1).getId());

    }

    @Test
    public void ExtraTest7(){
        TypeAttack A;
        A=new AttackFactory().getinstanceof(7,6,5,4,3);
        A.addExtra(1);
        A.addExtra(2);
        assertEquals(2, A.getNumberExtra());
        assertEquals(2, A.getExtras().get(1));
    }

    @Test
    public void DistanceTest7(){
        TypeAttack A;
        A=new AttackFactory().getinstanceof(7,6,5,4,3);
        assertEquals(5, A.getDistance());
        A.setDistance(1);
        assertEquals(1, A.getDistance());
    }

    @Test
    public void TypeplayerTest7(){
        TypeAttack A;
        A=new AttackFactory().getinstanceof(7,6,5,4,3);
        assertEquals(6, A.getTypePlayer());
        A.setTypePlayer(2);
        assertEquals(2, A.getTypePlayer());
    }

    @Test
    public void MovemeTest7(){
        TypeAttack A;
        A=new AttackFactory().getinstanceof(7,6,5,4,3);
        assertEquals(4, A.getMoveMe());
        A.setMoveMe(2);
        assertEquals(2, A.getMoveMe());
    }

    @Test
    public void MoveyouTest7(){
        TypeAttack A;
        A=new AttackFactory().getinstanceof(7,6,5,4,3);
        assertEquals(3, A.getMoveYou());
        A.setMoveYou(2);
        assertEquals(2, A.getMoveYou());
    }

    @Test
    public void EffectTest8()
    {
        TypeAttack A;
        A = new AttackFactory().getinstanceof(8,0,1,0,0);

        A.addEffect(new EffectFactory().getinstanceof(1,1));
        A.addEffect(new EffectFactory().getinstanceof(2,2));
        assertEquals(2, A.getNumberEffect());
        assertEquals(2, A.getEffect(1).getId());

    }

    @Test
    public void ExtraTest8(){
        TypeAttack A;
        A = new AttackFactory().getinstanceof(8,0,1,0,0);
        A.addExtra(1);
        A.addExtra(2);
        assertEquals(2, A.getNumberExtra());
        assertEquals(2, A.getExtras().get(1));
    }

    @Test
    public void DistanceTest8(){
        TypeAttack A;
        A = new AttackFactory().getinstanceof(8,0,1,0,0);
        assertEquals(1, A.getDistance());
        A.setDistance(2);
        assertEquals(2, A.getDistance());
    }

    @Test
    public void TypeplayerTest8(){
        TypeAttack A;
        A = new AttackFactory().getinstanceof(8,0,1,0,0);
        assertEquals(0, A.getTypePlayer());
        A.setTypePlayer(2);
        assertEquals(2, A.getTypePlayer());
    }

    @Test
    public void MovemeTest8(){
        TypeAttack A;
        A = new AttackFactory().getinstanceof(8,0,1,0,0);
        assertEquals(0, A.getMoveMe());
        A.setMoveMe(2);
        assertEquals(2, A.getMoveMe());
    }

    @Test
    public void MoveyouTest8(){
        TypeAttack A;
        A = new AttackFactory().getinstanceof(8,0,1,0,0);
        assertEquals(0, A.getMoveYou());
        A.setMoveYou(2);
        assertEquals(2, A.getMoveYou());
    }

    @Test
    public void EffectTest9()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(9,0,1,0,0);

        A.addEffect(new EffectFactory().getinstanceof(1,1));
        A.addEffect(new EffectFactory().getinstanceof(2,2));
        assertEquals(2, A.getNumberEffect());
        assertEquals(2, A.getEffect(1).getId());

    }

    @Test
    public void ExtraTest9(){
        TypeAttack A;
        A=new AttackFactory().getinstanceof(9,0,1,0,0);
        A.addExtra(1);
        A.addExtra(2);
        assertEquals(2, A.getNumberExtra());
        assertEquals(2, A.getExtras().get(1));
    }

    @Test
    public void DistanceTest9(){
        TypeAttack A;
        A=new AttackFactory().getinstanceof(9,0,1,0,0);
        assertEquals(1, A.getDistance());
        A.setDistance(1);
        assertEquals(1, A.getDistance());
    }

    @Test
    public void TypeplayerTest9(){
        TypeAttack A;
        A=new AttackFactory().getinstanceof(9,0,1,0,0);
        assertEquals(0, A.getTypePlayer());
        A.setTypePlayer(2);
        assertEquals(2, A.getTypePlayer());
    }

    @Test
    public void MovemeTest9(){
        TypeAttack A;
        A=new AttackFactory().getinstanceof(9,0,1,0,0);
        assertEquals(0, A.getMoveMe());
        A.setMoveMe(2);
        assertEquals(2, A.getMoveMe());
    }

    @Test
    public void MoveyouTest9(){
        TypeAttack A;
        A=new AttackFactory().getinstanceof(9,0,1,0,0);
        assertEquals(0, A.getMoveYou());
        A.setMoveYou(2);
        assertEquals(2, A.getMoveYou());
    }

    @Test
    public void EffectTest10()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(10,0,1,0,0);

        A.addEffect(new EffectFactory().getinstanceof(1,1));
        A.addEffect(new EffectFactory().getinstanceof(2,2));
        assertEquals(2, A.getNumberEffect());
        assertEquals(2, A.getEffect(1).getId());

    }

    @Test
    public void ExtraTest10(){
        TypeAttack A;
        A=new AttackFactory().getinstanceof(10,0,1,0,0);
        A.addExtra(1);
        A.addExtra(2);
        assertEquals(2, A.getNumberExtra());
        assertEquals(2, A.getExtras().get(1));
    }

    @Test
    public void DistanceTest10(){
        TypeAttack A;
        A=new AttackFactory().getinstanceof(10,0,1,0,0);
        assertEquals(1, A.getDistance());
        A.setDistance(1);
        assertEquals(1, A.getDistance());
    }

    @Test
    public void TypeplayerTest10(){
        TypeAttack A;
        A=new AttackFactory().getinstanceof(10,0,1,0,0);
        assertEquals(0, A.getTypePlayer());
        A.setTypePlayer(2);
        assertEquals(2, A.getTypePlayer());
    }

    @Test
    public void MovemeTest10(){
        TypeAttack A;
        A=new AttackFactory().getinstanceof(10,0,1,0,0);
        assertEquals(0, A.getMoveMe());
        A.setMoveMe(2);
        assertEquals(2, A.getMoveMe());
    }

    @Test
    public void MoveyouTest10(){
        TypeAttack A;
        A=new AttackFactory().getinstanceof(10,0,1,0,0);
        assertEquals(0, A.getMoveYou());
        A.setMoveYou(2);
        assertEquals(2, A.getMoveYou());
    }

    @Test
    public void EffectTest11()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(11,0,1,0,0);

        A.addEffect(new EffectFactory().getinstanceof(1,1));
        A.addEffect(new EffectFactory().getinstanceof(2,2));
        assertEquals(2, A.getNumberEffect());
        assertEquals(2, A.getEffect(1).getId());

    }

    @Test
    public void ExtraTest11(){
        TypeAttack A;
        A=new AttackFactory().getinstanceof(11,0,1,0,0);
        A.addExtra(1);
        A.addExtra(2);
        assertEquals(2, A.getNumberExtra());
        assertEquals(2, A.getExtras().get(1));
    }

    @Test
    public void DistanceTest11(){
        TypeAttack A;
        A=new AttackFactory().getinstanceof(11,0,1,0,0);
        assertEquals(1, A.getDistance());
        A.setDistance(1);
        assertEquals(1, A.getDistance());
    }

    @Test
    public void TypeplayerTest11(){
        TypeAttack A;
        A=new AttackFactory().getinstanceof(11,0,1,0,0);
        assertEquals(0, A.getTypePlayer());
        A.setTypePlayer(2);
        assertEquals(2, A.getTypePlayer());
    }

    @Test
    public void MovemeTest11(){
        TypeAttack A;
        A=new AttackFactory().getinstanceof(11,0,1,0,0);
        assertEquals(0, A.getMoveMe());
        A.setMoveMe(2);
        assertEquals(2, A.getMoveMe());
    }

    @Test
    public void MoveyouTest11(){
        TypeAttack A;
        A=new AttackFactory().getinstanceof(11,0,1,0,0);
        assertEquals(0, A.getMoveYou());
        A.setMoveYou(2);
        assertEquals(2, A.getMoveYou());
    }

    @Test
    public void EffectTest12()
    {
        TypeAttack A;
        A=new AttackFactory().getinstanceof(12,0,1,0,0);

        A.addEffect(new EffectFactory().getinstanceof(1,1));
        A.addEffect(new EffectFactory().getinstanceof(2,2));
        assertEquals(2, A.getNumberEffect());
        assertEquals(2, A.getEffect(1).getId());

    }

    @Test
    public void ExtraTest12(){
        TypeAttack A;
        A=new AttackFactory().getinstanceof(12,0,1,0,0);
        A.addExtra(1);
        A.addExtra(2);
        assertEquals(2, A.getNumberExtra());
        assertEquals(2, A.getExtras().get(1));
    }

    @Test
    public void DistanceTest12(){
        TypeAttack A;
        A=new AttackFactory().getinstanceof(12,0,1,0,0);
        assertEquals(1, A.getDistance());
        A.setDistance(1);
        assertEquals(1, A.getDistance());
    }

    @Test
    public void TypeplayerTest12(){
        TypeAttack A;
        A=new AttackFactory().getinstanceof(12,0,1,0,0);
        assertEquals(0, A.getTypePlayer());
        A.setTypePlayer(2);
        assertEquals(2, A.getTypePlayer());
    }

    @Test
    public void MovemeTest12(){
        TypeAttack A;
        A=new AttackFactory().getinstanceof(12,0,1,0,0);
        assertEquals(0, A.getMoveMe());
        A.setMoveMe(2);
        assertEquals(2, A.getMoveMe());
    }

    @Test
    public void MoveyouTest12(){
        TypeAttack A;
        A=new AttackFactory().getinstanceof(12,0,1,0,0);
        assertEquals(0, A.getMoveYou());
        A.setMoveYou(2);
        assertEquals(2, A.getMoveYou());
    }

}
