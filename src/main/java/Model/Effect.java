package Model;

public interface Effect {
    public void shoot(int color);
    public void shoot(Coordinate target);
    public int getId();
    public void adddamage(int type, int damage);
    public Damage getDamage(int i);

}
