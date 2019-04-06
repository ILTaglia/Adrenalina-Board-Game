import java.util.ArrayList;

public class Cardinal implements Type_attack{
    private ArrayList<Integer> extra;
    private int typeplayer;
    private int direction;
    private int moveme;
    private int moveyou;
    public int getDistance()
    {
        return this.direction;
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
        this.direction=distance;
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
