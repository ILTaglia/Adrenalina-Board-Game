package Model;

public class Attack_Factory {
    public Type_attack getinstanceof(int i, int typeplayer,int distance,int moveme, int moveyou)
    {
        if(i==1)
        {
            return new Finite_Distance(typeplayer,distance,moveme,moveyou);
        }
        else
            if(1==2)
            {
                return new Undefined_distance(typeplayer,distance,moveme,moveyou);
            }
            else
                if(i==3)
                {
                    return new More_distance(typeplayer,distance,moveme,moveyou);
                }
                else
                    return new Cardinal(typeplayer,distance,moveme,moveyou);
    }
}
