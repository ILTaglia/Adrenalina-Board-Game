package model;

import java.io.Serializable;

public interface Damage extends Serializable {
    public void setdamage(int damage);
    public int getdamage();
    public int getType();
}
