import static org.junit.Assert.*;

import Model.Attack_Factory;
import Model.Effect_factory;
import Model.Type_attack;
import org.junit.Test;

import java.util.ArrayList;

public class AttackTest {
    @Test
    public void CreateAttack()
    {
        Type_attack A;
        A=new Attack_Factory().getinstanceof(1,2,3,4,5);
        assertEquals(A.getDistance(),3);
        assertEquals(A.getMoveme(),4);
        assertEquals(A.getMoveyou(),5);
        assertEquals(A.getTypeplayer(),2);
    }

    @Test
    public void MultipleAttack()
    {
        ArrayList<Type_attack> A;
        A=new ArrayList<Type_attack>();
        A.add(new Attack_Factory().getinstanceof(1,2,3,4,5));
        A.add(new Attack_Factory().getinstanceof(6,7,8,9,10));
        A.add(new Attack_Factory().getinstanceof(7,6,5,4,3));
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
        Type_attack A;
        A=new Attack_Factory().getinstanceof(1,8,8,8,8);
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
        Type_attack A;
        A=new Attack_Factory().getinstanceof(1,8,8,8,8);
        assertEquals(A.getnumbereffect(),0);
        assertEquals(A.getnumberextra(),0);
    }

    @Test
    public void deeptest1()
    {
        Type_attack A;
        A=new Attack_Factory().getinstanceof(1,8,8,8,8);
        A.addextra(1);
        A.addextra(2);
        A.addextra(44);
        assertEquals(A.getnumberextra(),3);
    }

    @Test
    public void deeptest2()
    {
        Type_attack A;
        A=new Attack_Factory().getinstanceof(1,8,8,8,8);
        A.addeffect(new Effect_factory().getinstanceof(1,2));
        A.addeffect(new Effect_factory().getinstanceof(2,2));
        A.addeffect(new Effect_factory().getinstanceof(1,3));
        A.addeffect(new Effect_factory().getinstanceof(2,8));
        assertEquals(A.getnumbereffect(),4);
    }


}
