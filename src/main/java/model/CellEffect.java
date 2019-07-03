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
     * @param type of damage
     * @param damage is the number of tokens
     */
    public void adddamage(int type, int damage)
    {
        Damage damage1;
        damage1=new DamageFactory().getinstaceof(type,damage);
        this.damages.add(damage1);
    }
    /**
     *
     * @param i is the index of the damage
     * @return the damage
     */
    @Override
    public Damage getDamage(int i) {
        return this.damages.get( i);
    }

    /**
     *
     * @param id is the ID of the damage
     */
    @Override
    public void setId(int id) {
        this.id=id;
    }
    /**
     *
     * @return the number of tokens given by the damage
     */
    public int getnumberdamage()
    {
        return damages.size();
    }
    /**
     *
     * @param id of the effect
     */
    public CellEffect(int id)
    {
        damages = new ArrayList<>();
        this.id=id;
    }
    /**
     *
     * @return the type (cell effect)
     */
    public int getType(){return 1;}
    /**
     *
     * @return the ID of the effect
     */
    @Override
    public int getId() {
        return this.id;
    }
}
