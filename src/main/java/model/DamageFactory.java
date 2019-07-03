package model;

public class DamageFactory {
    /**
     *
     * @param type is 1 for life damage, 0 for mark
     * @param damage is number of tokens
     * @return the Damage
     */
    public Damage getinstaceof(int type, int damage)
    {
        if(type==1)
        {
            return new Life(damage);
        }
        else
            return new Mark(damage);
    }
}
