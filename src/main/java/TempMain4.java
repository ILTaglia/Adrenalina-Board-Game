import java.util.ArrayList;

public class TempMain4 {
    public static void main(String [] args)
    {
        Weapoin W;
        ArrayList <Integer> cost;
        cost=new ArrayList<Integer>();
        cost.add(0);
        cost.add(0);
        cost.add(2);
        W=new Weapoin("Distruttore",cost);
        System.out.println("Arma "+ W.getName());
        ArrayList<Integer> costi;
        costi=new ArrayList<Integer>();
        costi=W.getCost();
        for(int i : cost)
        {
            System.out.println("Il costo sarebbe "+i);
        }
        W.addAttack(2,1,0,0,0);
        System.out.println("Il numero di azioni contenute in questa arma e'"+ W.getnumberattack());
        Effect E=new Effect_factory().getinstanceof(1, 1);
        E.adddamage(1,2);
        E.adddamage(2, 1);
        W.getAttack(0).addeffect(E);
        Effect F=new Effect_factory().getinstanceof(1, 2);
        F.adddamage(2, 1);
        W.getAttack(0).addeffect(F);
        System.out.println("Il numero di Effetti di quest'arma sarebbe "+W.getAttack(0).getnumbereffect());
        System.out.println("Con il primo attacco hai dato un numero di danni apri a "+W.getAttack(0).getEffect(0).getDamage(0).getdamage()+ " al giocatore numero "+W.getAttack(0).getEffect(0).getId());
        System.out.println("Con il secondo attacco hai dato un numero di marchi apri a "+W.getAttack(0).getEffect(0).getDamage(1).getdamage()+ " al giocatore numero "+W.getAttack(0).getEffect(0).getId());
        System.out.println("Con il secondo attacco hai dato un numero di marchi apri a "+W.getAttack(0).getEffect(1).getDamage(0).getdamage()+ " al giocatore numero "+W.getAttack(0).getEffect(1).getId());
    }
}
