import java.util.ArrayList;

public class Player_effect implements Effect {
    private ArrayList <Damage> D;
    int id;
    public void setId(int id)
    {
        this.id=id;
    }
    public void adddamage(int type, int damage)
    {
        Damage X;
        X=new Damage_Factory().getinstaceof(type,damage);
        this.D.add(X);
    }

    @Override
    public Damage getDamage(int i) {
        return this.D.get( i);
    }

    public int getnumberdamage(int i)
    {
        return D.size();
    }

    public Damage getdamage(int i)
    {
        return D.get(i);
    }
    public Player_effect(int id)
    {

        this.id=id;
        this.D=new ArrayList<Damage>();
    }


    @Override
    public void shoot(int color) {

    }

    @Override
    public void shoot(Coordinate target) {

    }

    @Override
    public int getId() {
        return this.id;
    }
}
