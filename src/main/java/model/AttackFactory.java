package model;

public class AttackFactory {
    public TypeAttack getinstanceof(int i, int typeplayer, int distance, int moveme, int moveyou)
    {
        if(i==1)
        {
            return new FiniteDistance(typeplayer,distance,moveme,moveyou);
        }
        else
            if(1==2)
            {
                return new UndefinedDistance(typeplayer,distance,moveme,moveyou);
            }
            else
                if(i==3)
                {
                    return new MoreDistance(typeplayer,distance,moveme,moveyou);
                }
                else
                    return new Cardinal(typeplayer,distance,moveme,moveyou);
    }
}
