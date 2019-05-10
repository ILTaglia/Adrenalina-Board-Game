package model;

public class Mark implements Damage {
    private int damage;

    @Override
    public void setdamage(int damage) {
        this.damage=damage;
    }

    @Override
    public int getdamage() {
        return this.damage;
    }

    public int getType(){return 1;}

    public Mark(int damage)
    {
        this.damage=damage;
    }
}
