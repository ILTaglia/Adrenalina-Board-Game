import java.util.ArrayList;

public class Undefined_distance implements Type_attack {
    private ArrayList <Integer> extra;
    private int typeplayer;
    private int moveme;
    private int moveyou;
    public int getTypeplayer()
    {
        return this.typeplayer;
    }
    public ArrayList getextras()
    {
        return this.extra;
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
