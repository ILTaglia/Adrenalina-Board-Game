package model;

import java.io.Serializable;

public interface Effect extends Serializable {
    public int getId();
    public void adddamage(int type, int damage);
    public Damage getDamage(int i);
    public void setId(int id);
    public int getnumberdamage();
    public int getType();
}
