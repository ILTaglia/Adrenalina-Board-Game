package model;

public class Mark implements Damage {
    /**
     * damage is the number of tokens
     */
    private int damage;

    /**
     *
     * @param damage is the number of tokens
     */
    @Override
    public void setDamage(int damage) {
        this.damage=damage;
    }

    /**
     *
     * @return the number of tokens
     */
    @Override
    public int getDamage() {
        return this.damage;
    }

    /**
     *
     * @return the type of damage (it is a mark)
     */
    public int getType(){return 1;}

    /**
     *
     * @param damage is the number of tokens
     */
    public Mark(int damage)
    {
        this.damage=damage;
    }
}
