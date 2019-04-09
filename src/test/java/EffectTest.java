import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;


public class EffectTest {
    @Test
    public void CreationTest()
    {
        Effect E;
        E=new Effect_factory().getinstanceof(1,5);
        assertEquals(E.getId(),5);
        E=new Effect_factory().getinstanceof(2,2);
        assertEquals(E.getId(),2);
    }

    @Test
        public void Testlist()
        {
            ArrayList<Effect>E;
            E=new ArrayList<Effect>();
            E.add(new Effect_factory().getinstanceof(1,5));
            E.add(new Effect_factory().getinstanceof(2,5));
            E.add(new Effect_factory().getinstanceof(2,2));
            E.add(new Effect_factory().getinstanceof(1,9));
            E.add(new Effect_factory().getinstanceof(1,5));
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
        E=new Effect_factory().getinstanceof(1,2);
        E.adddamage(1,2);
        assertEquals(E.getDamage(0).getdamage(),2);
    }

    @Test
    public void subclasses2()
    {
        Effect E;
        E=new Effect_factory().getinstanceof(1,2);
        E.adddamage(1,2);
        E.getDamage(0).setdamage(6);
        assertEquals(E.getDamage(0).getdamage(),6);
    }
}
