package model;

import java.io.Serializable;

public interface Damage extends Serializable {
    /**
     *
     * @param damage is the number of toknes
     */
    void setdamage(int damage);
    /**
     *
     * @return the number of tokens
     */
    int getdamage();
    /**
     *
     * @return the type (mark or life damage)
     */
    int getType();
}
