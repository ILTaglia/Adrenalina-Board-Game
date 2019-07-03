package model;

import java.io.Serializable;

public interface Damage extends Serializable {
    /**
     *
     * @param damage is the number of toknes
     */
    void setDamage(int damage);
    /**
     *
     * @return the number of tokens
     */
    int getDamage();
    /**
     *
     * @return the type (mark or life damage)
     */
    int getType();
}
