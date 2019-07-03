package model;

public class PowFactory {
    /**
     *
     * @param type is type of PowCard
     * @param color is the color of the PowCard
     * @param cost is the price of the PowCard
     * @param moveme is an int that indicates of how many steps the attacking player can move
     * @return
     */
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
