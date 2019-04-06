import java.util.ArrayList;

public class TempMain {
    public static void main(String Args[])
    {
        Weapon A;
        ArrayList <Integer> E;
        E=new ArrayList<Integer>();
        E.add(4);
        E.add(3);
        E.add(2);
        System.out.println("Ciao A tutti, Il main temporaneo funziona");
        A=new Weapon("Raggio Fotonico",E);
        System.out.println("Il nome dell'arma e' "+A.getName());
        for(int i:A.getCost())
        {
            System.out.println("Costo: "+i);
        }
        if(A.getUsed()==1)
        {
            System.out.println("Arma utilizzata");
        }
        else
        {
            System.out.println("Arma carica!");
        }
        if(A.recharge()==1)
        {
            System.out.println("Arma gia' carica! non ci stanno altri colpi");
        }
        else
        {
            System.out.println("Il nuovo caricatore e' stato inserito!");
        }
        if(A.shooted()==0)
        {
            System.out.println("L'arma ha sparato, bisogna ricaricarla!");
        }
        else
        {
            System.out.println("L'arma e' scarica, non sta sparando nulla!");
        }
        if(A.shooted()==0)
        {
            System.out.println("L'arma ha sparato, bisogna ricaricarla!");
        }
        else
        {
            System.out.println("L'arma e' scarica, non sta sparando nulla!");
        }
        if(A.recharge()==1)
        {
            System.out.println("Arma gia' carica! non ci stanno altri colpi");
        }
        else
        {
            System.out.println("Il nuovo caricatore e' stato inserito!");
        }

    }
}
