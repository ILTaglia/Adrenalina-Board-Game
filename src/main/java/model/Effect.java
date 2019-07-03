package model;

import java.io.Serializable;

public interface Effect extends Serializable {
    /**
     *
     * @return ID of the attack
     */
    int getId();

    /**
     *
     * @param type of damage
     * @param damage is the number of tokens
     */
    void adddamage(int type, int damage);

    /**
     *
     * @param i is the index of the damage
     * @return the damage
     */
    Damage getDamage(int i);

    /**
     *
     * @param id is the ID of the damage
     */
    void setId(int id);

    /**
     *
     * @return the number of tokens given by the damage
     */
    int getnumberdamage();

    /**
     *
     * @return the type (player or cell effect)
     */
    int getType();
}
