package model;

public class AttackFactory {
    /**
     *
     * @param i is the identifier of the type of attack
     * @param typeplayer is an identifier that allows to define in what type of series an attack of a weapon will be performed
     * @param distance is an int that indicates the distance between the attacking player and the attacked player
     * @param moveme is an int that indicates of how many steps the attacking player can move
     * @param moveyou is an int that indicates of how many steps the attacked player can move
     * @return the type of attack corresponding to the identifier i, created with the other passed parameters
     * (typeplayer, distance, moveme, moveyou)
     */
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
        if(i==3)
        {
            return new MoreDistance(typeplayer,distance,moveme,moveyou);
        }
        if(i==4)
        {
            return new Cardinal(typeplayer,distance,moveme,moveyou);
        }
        if(i==5)
        {
            return new NotSeen(typeplayer,distance,moveme,moveyou);
        }
        if(i==6)
        {
            return new WhileMoving(typeplayer,distance,moveme,moveyou);
        }
        if(i==7)
        {
            return new AllAround(typeplayer,distance,moveme,moveyou);
        }
        if(i==8)
        {
            return new AfterMoving(typeplayer,distance,moveme,moveyou);
        }
        if(i==9)
        {
            return new AllRoom(typeplayer,distance,moveme,moveyou);
        }
        if(i==10)
        {
            return new Ricorsive(typeplayer,distance,moveme,moveyou);
        }
        if(i==11)
        {
            return new InFiniteLine(typeplayer,distance,moveme,moveyou);
        }
        if(i==12)
        {
            return new MovingToMe(typeplayer,distance,moveme,moveyou);
        }
        return null;
    }
}
