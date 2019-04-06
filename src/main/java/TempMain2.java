import java.lang.reflect.Array;
import java.util.ArrayList;

public class TempMain2 {
    public static void main(String []args)
    {
        ArrayList <Damage> D;
        D=new ArrayList<Damage>();
        D.add(new Damage_Factory().getinstaceof(1,3));
        System.out.println("La dimenstione della lista di danni e' "+D.size());
        System.out.println("Il danno del primo attacco e' "+ D.get(0).getdamage());
        D.add(new Damage_Factory().getinstaceof(2,10));
        System.out.println("La dimenstione della lista di danni e' "+D.size());
        for (int i=0;i<D.size();i++)
        {
            System.out.println("Il danno dell'azione "+(i+1)+" e' "+ D.get(i).getdamage());
        }
        D.get(1).setdamage(5);
        for (int i=0;i<D.size();i++)
        {
            System.out.println("Il danno dell'azione "+(i+1)+" e' "+ D.get(i).getdamage());
        }
        ArrayList<Effect> E;
        E= new ArrayList<Effect>();
        E.add(new Effect_factory().getinstanceof(1,5));
        System.out.println("Il giocatore puntato sarebbe il numero "+ E.get(0).getId());
        //E.get(0).adddamage(1, 10);
        //System.out.println("Il giocatore"+ E.get(0).getId()+" Subisce un danno di "+ E.get(0).getDamage(0));
    }
}
