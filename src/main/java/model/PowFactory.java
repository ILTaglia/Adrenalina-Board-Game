package model;

public class PowFactory {
    public PowCard getInstanceof(int type,int color, int cost, int moveme)
    {
        if(type==0)
            return new TargetingScope(color, cost, moveme);
        else
            if(type==1)
                return new Newton(color, cost, moveme);
            else
                if(type==2)
                    return new Granade(color, cost, moveme);
                else
                    return new Teleporter(color, cost, moveme);
    }
}
