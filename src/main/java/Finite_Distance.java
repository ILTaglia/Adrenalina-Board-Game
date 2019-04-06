import java.util.ArrayList;

public class Finite_Distance implements Type_attack{
    private ArrayList <Integer> extra;
    private int typeplayer;
    private int distance;
    private int moveme;
    private int moveyou;
    public int getMoveme()
    {
        return this.moveme;
    }
    public int getMoveyou()
    {
        return this.moveyou;
    }
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
        this.distance=distance;
    }
    public void setTypeplayer(int typeplayer)
    {
        this.typeplayer=typeplayer;
    }
    public void setMoveme(int moveme)
    {
        this.moveme=moveme;
    }
    public void setMoveyou(int moveyou)
    {
        this.moveyou=moveyou;
    }

}
