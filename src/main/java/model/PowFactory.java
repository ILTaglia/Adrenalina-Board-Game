package model;

public class PowFactory {
    public PowCard getInstanceof(int type,int color, int cost, int moveme, int life, int marks)
    {
        if(type==0)
            return new TargetingScope(color, cost, moveme, life, marks);
        else
            if(type==1)
                return new Newton(color, cost, moveme, life, marks);
            else
                if(type==2)
                    return new Granade(color, cost, moveme, life, marks);
                else
                    return new Teleporter(color, cost, moveme, life, marks);
    }
}
