package Model;

import java.util.ArrayList;

public class Weapon_Deck {
    public ArrayList<Weapon> D;

    public Weapon_Deck(){
        D=new ArrayList<Weapon>();
        ArrayList <Integer> A;
        A=new ArrayList<Integer>();
        A.add(1);
        A.add(1);
        D.add(new Weapon("Distruttore",A ));
        D.get(0).addAttack(2,0,0,0,0);
        D.get(0).getAttack(0).addeffect(new Effect_factory().getinstanceof(1,0));

    }

}