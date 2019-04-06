public class Attack_Factory {
    public Type_attack getinstanceof(int i)
    {
        if(i==1)
        {
            return new Finite_Distance();
        }
        else
            if(1==2)
            {
                return new Undefined_distance();
            }
            else
                if(i==3)
                {
                    return new More_distance();
                }
                else
                    return new Cardinal();
    }
}
