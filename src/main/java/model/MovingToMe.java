package model;

import java.util.ArrayList;
import java.util.List;

public class MovingToMe implements TypeAttack {
    private List<Integer> extra;
    private int typeplayer;
    private int distance;
    private int moveme;
    private int moveyou;
    private List<Effect> E;
    public int getDistance()
    {
        return this.distance;
    }
    public int getTypePlayer()
    {
        return this.typeplayer;
    }
    public List getExtras()
    {
        return this.extra;
    }
    public void setDistance(int distance)
    {
        this.distance=distance;
    }
    public void setTypePlayer(int typePlayer)
    {
        this.typeplayer= typePlayer;
    }
    public void addExtra(int extra)
    {
        this.extra.add(extra);
    }
    public void setMoveMe(int moveMe)
    {
        this.moveme= moveMe;
    }
    public void setMoveYou(int moveYou)
    {
        this.moveyou= moveYou;
    }
    public void addEffect(Effect E)
    {
        this.E.add(E);
    }
    public Effect getEffect(int i)
    {
        return this.E.get(i);
    }
    public int getNumberEffect()
    {
        return this.E.size();
    }
    public int getNumberExtra()
    {
        return this.extra.size();
    }
    public int getMoveMe()
    {
        return this.moveme;
    }
    public int getMoveYou()
    {
        return this.moveyou;
    }
    public int getType(){return 12;}

    public MovingToMe(int typeplayer, int distance, int moveme, int moveyou)
    {
        this.extra=new ArrayList<Integer>();
        this.E= new ArrayList<Effect>();
        this.distance=distance;
        this.moveme=moveme;
        this.moveyou=moveyou;
        this.typeplayer=typeplayer;
    }
}
