package controller;
import exceptions.*;
import model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import static org.junit.Assert.*;
public class ListAttackCreatorTest {

    @Test
    public void testcard()
    {

        Weapon weapon = new Weapon("Piergiorgio");
        TypeAttack t1 = new AttackFactory().getinstanceof(1,0,3,1,8);
        TypeAttack t2 = new AttackFactory().getinstanceof(2,0,2,2,7);
        TypeAttack t3 = new AttackFactory().getinstanceof(1,1,1,3,6);
        TypeAttack t4 = new AttackFactory().getinstanceof(3,2,3,4,5);
        TypeAttack t5 = new AttackFactory().getinstanceof(5,1,2,5,4);
        TypeAttack t6 = new AttackFactory().getinstanceof(7,3,5,6,3);
        TypeAttack t7 = new AttackFactory().getinstanceof(1,1,6,7,2);
        TypeAttack t8 = new AttackFactory().getinstanceof(3,0,8,8,1);
        weapon.addAttack(1,0,3,1,8);
        weapon.addAttack(2,0,2,2,7);
        weapon.addAttack(1,1,1,3,6);
        weapon.addAttack(3,2,3,4,5);
        weapon.addAttack(5,1,2,5,4);
        weapon.addAttack(7,3,5,6,3);
        weapon.addAttack(1,1,6,7,2);
        weapon.addAttack(3,0,8,8,1);
        ListAttackCreator lister = new ListAttackCreator();
        ArrayList<TypeAttack> list = new ArrayList<TypeAttack>();
        list=lister.elaboratelist(weapon,1);
        assertEquals(list.get(0).getTypePlayer(),1);
        assertEquals(list.get(1).getTypePlayer(),1);
        assertEquals(list.get(2).getTypePlayer(),1);
        assertEquals(list.size(),3);
        assertEquals(list.get(0).getDistance(),t3.getDistance());
        assertEquals(list.get(1).getMoveMe(),t5.getMoveMe());
        assertEquals(list.get(2).getMoveYou(),t7.getMoveYou());
        list=lister.elaboratelist(weapon,0);
        assertEquals(list.get(0).getTypePlayer(),0);
        assertEquals(list.get(1).getTypePlayer(),0);
        assertEquals(list.get(2).getTypePlayer(),0);
        assertEquals(list.size(),3);
        assertEquals(list.get(0).getDistance(),t1.getDistance());
        assertEquals(list.get(1).getMoveMe(),t2.getMoveMe());
        assertEquals(list.get(2).getMoveYou(),t8.getMoveYou());
    }

}
