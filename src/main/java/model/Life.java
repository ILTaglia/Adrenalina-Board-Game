package model;

public class Life implements Damage {
    private int damage;

    @Override
    public void setdamage(int damage) {
        this.damage=damage;
    }

    @Override
    public int getdamage() {
        return this.damage;
    }

    public Life(int damage)
    {
        this.damage=damage;
    }
}