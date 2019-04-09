import java.util.ArrayList;

public class Cardinal implements Type_attack{
    private ArrayList<Integer> extra;
    private int typeplayer;
    private int distance;
    private int moveme;
    private int moveyou;
    private ArrayList<Effect> E;
    public int getDistance()
    {
        return this.distance;
    }


    public int getTypeplayer()
    {
        return this.typeplayer;
    }
    public ArrayList getextras()
    {
        return this.extra;
    }
    public void setDistance(int distance)
    {
        this.distance =distance;
    }
    public void setTypeplayer(int typeplayer)
    {
        this.typeplayer=typeplayer;
    }
    public void addextra(int extra)
    {
        this.extra.add(extra);
    }
    public void setMoveme(int moveme)
    {
        this.moveme=moveme;
    }
    public void setMoveyou(int moveyou)
    {
        this.moveyou=moveyou;
    }
    public void addeffect(Effect E)
    {
        this.E.add(E);
    }
    public Effect getEffect(int i)
    {
        return this.E.get(i);
    }
    public int getnumbereffect()
    {
        return this.E.size();
    }
    public int getnumberextra()
    {
        return this.extra.size();
    }
    public Cardinal(int typeplayer,int direction,int moveme, int moveyou)
    {
        this.extra=new ArrayList<Integer>();
        this.E= new ArrayList<Effect>();
        this.distance =direction;
        this.moveme=moveme;
        this.moveyou=moveyou;
        this.typeplayer=typeplayer;
    }
}
