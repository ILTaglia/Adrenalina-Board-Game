package model;

import java.util.ArrayList;

public class CellEffect implements Effect {
    /**
     *Cell Effect is used to indicate that a Cell is being attacked with a weapon
     *
     * id is the index of the player or the cell that has to be damaged
     * damages is the list of damages to assign to a player
     */
    private int id;
    private ArrayList<Damage> damages;

    /**
     *
     * @param type indicates the type of damage
     * @param damage
     */
    public void adddamage(int type, int damage)
    {
        Damage damage1;
        damage1=new DamageFactory().getinstaceof(type,damage);
        this.damages.add(damage1);
    }

    /**
     *
     * @param i is
     * @return
     */
    @Override
    public Damage getDamage(int i) {
        return this.damages.get( i);
    }

    @Override
    public void setId(int id) {
        this.id=id;
    }

    public int getnumberdamage()
    {
        return damages.size();
    }


    public CellEffect(int id)
    {
        damages = new ArrayList<>();
        this.id=id;
    }

    public int getType(){return 1;}

    @Override
    public int getId() {
        return this.id;
    }
}
