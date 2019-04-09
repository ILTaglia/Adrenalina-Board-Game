import static org.junit.Assert.*;

import Model.Damage;
import Model.Damage_Factory;
import org.junit.Test;

import java.util.ArrayList;

public class DamageTest {
    @Test
    public void creationtest()
    {
        ArrayList<Damage> D;
        D=new ArrayList<Damage>();
        D.add(new Damage_Factory().getinstaceof(1,5));
        assertEquals(5,D.get(0).getdamage());
    }


    @Test
    public void multipledamages()
    {
        ArrayList<Damage> D;
        D=new ArrayList<Damage>();
        Damage y,x;
        x=new Damage_Factory().getinstaceof(1,6);
        y=new Damage_Factory().getinstaceof(2,7);

        D.add(new Damage_Factory().getinstaceof(1,5));
        D.add(new Damage_Factory().getinstaceof(2,4));
        D.add(new Damage_Factory().getinstaceof(1,6));
        D.add(new Damage_Factory().getinstaceof(2,7));
        D.add(new Damage_Factory().getinstaceof(1,9));

        assertEquals(y.getdamage(),D.get(3).getdamage());
        assertEquals(x.getdamage(),D.get(2).getdamage());

    }
}
