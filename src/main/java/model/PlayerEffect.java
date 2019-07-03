package model;

import java.util.ArrayList;

public class PlayerEffect implements Effect {
    /**
     *Player Effect is used to indicate that a player is being attacked with a weapon
     *
     * id is the index of the player or the player that has to be damaged
     * damages is the list of damages to assign to a player
     */
    private ArrayList <Damage> damages;
    int id;

    /**
     *
     * @param id is the ID of the damage
     */
    public void setId(int id)
    {
        this.id=id;
    }
    /**
     *
     * @param type of damage
     * @param damage is the number of tokens
     */
    public void adddamage(int type, int damage)
    {
        Damage X;
        X=new DamageFactory().getInstanceOf(type,damage);
        this.damages.add(X);
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
    public PlayerEffect(int id)
    {

        this.id=id;
        this.damages =new ArrayList<>();
    }

    /**
     *
     * @return the type (player effect)
     */
    public int getType(){return 0;}

    /**
     *
     * @return the ID of the effect
     */
    @Override
    public int getId() {
        return this.id;
    }
}
