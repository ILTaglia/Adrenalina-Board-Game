package model;

import java.util.ArrayList;

public class CellEffect implements Effect {
    private int id;
    private ArrayList<Damage> D;

    public void adddamage(int type, int damage)
    {
        Damage X;
        X=new DamageFactory().getinstaceof(type,damage);
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


    public CellEffect(int id)
    {
        D= new ArrayList<Damage>();
        this.id=id;
    }

    public int getType(){return 1;}

    @Override
    public int getId() {
        return this.id;
    }
}
