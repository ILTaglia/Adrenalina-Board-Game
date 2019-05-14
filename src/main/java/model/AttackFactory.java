package model;

public class AttackFactory {
    public TypeAttack getinstanceof(int i, int typeplayer, int distance, int moveme, int moveyou)
    {
        if(i==1)
        {
            return new FiniteDistance(typeplayer,distance,moveme,moveyou);
        }
        if(i==2)
        {
            return new UndefinedDistance(typeplayer,distance,moveme,moveyou);
        }
        if(i==2)
        {
            return new MoreDistance(typeplayer,distance,moveme,moveyou);
        }
        if(i==2)
        {
            return new Cardinal(typeplayer,distance,moveme,moveyou);
        }
        if(i==2)
        {
            return new NotSeen(typeplayer,distance,moveme,moveyou);
        }
        if(i==2)
        {
            return new WhileMoving(typeplayer,distance,moveme,moveyou);
        }
        if(i==2)
        {
            return new AllAround(typeplayer,distance,moveme,moveyou);
        }
        if(i==2)
        {
            return new AfterMoving(typeplayer,distance,moveme,moveyou);
        }
        if(i==2)
        {
            return new AllRoom(typeplayer,distance,moveme,moveyou);
        }
        if(i==2)
        {
            return new Ricorsive(typeplayer,distance,moveme,moveyou);
        }
        if(i==2)
        {
            return new InFiniteLine(typeplayer,distance,moveme,moveyou);
        }
        if(i==2)
        {
            return new MovingToMe(typeplayer,distance,moveme,moveyou);
        }
        return null;
    }
}
