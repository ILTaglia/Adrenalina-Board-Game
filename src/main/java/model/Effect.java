package model;

public interface Effect {
    public int getId();
    public void adddamage(int type, int damage);
    public Damage getDamage(int i);
    public void setId(int id);
    public int getnumberdamage();

}
