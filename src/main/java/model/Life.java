package model;

public class Life implements Damage {
    /**
     * damage is the number of tokens
     */
    private int damage;

    /**
     *
     * @param damage is the number of tokens
     */
    @Override
    public void setdamage(int damage) {
        this.damage=damage;
    }

    /**
     *
     * @return the number of tokens
     */
    @Override
    public int getdamage() {
        return this.damage;
    }

    /**
     *
     * @return the type of damage (it is a damage for life)
     */
    public int getType(){return 0;}

    /**
     *
     * @param damage is the number of tokens
     */
    public Life(int damage)
    {
        this.damage=damage;
    }
}
