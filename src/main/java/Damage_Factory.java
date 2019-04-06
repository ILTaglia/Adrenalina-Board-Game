public class Damage_Factory {
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
