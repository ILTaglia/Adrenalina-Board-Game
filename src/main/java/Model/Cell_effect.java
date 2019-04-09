package Model;

import java.util.ArrayList;

public class Cell_effect implements Effect {
    private int id;
    private ArrayList<Damage> D;

    public void adddamage(int type, int damage)
    {
        Damage X;
        X=new Damage_Factory().getinstaceof(type,damage);
        this.D.add(X);
    }

    @Override
    public Damage getDamage(int i) {
        return this.D.get( i);
    }

    @Override
    public void setId(int id) {
        this.id=id;
    }

    public int getnumberdamage()
    {
        return D.size();
    }


    public Cell_effect(int id)
    {
        D= new ArrayList<Damage>();
        this.id=id;
    }


    @Override
    public int getId() {
        return this.id;
    }
}
