package Model;

public class TargetingScope extends PowCard{
    private int color;
    private int cost;
    private int moveme;
    private int life;
    private int marks;
    public TargetingScope(int color, int cost, int moveme, int life, int marks)
    {
        this.color=color;
        this.cost=cost;
        this.moveme=moveme;
        this.life=life;
        this.marks=marks;
    }
    public TargetingScope(int color, int cost, int life)
    {
        this.color=color;
        this.cost=cost;
        this.moveme=0;
        this.life=life;
        this.marks=0;
    }
    public void setColor(int color)
    {
        this.color=color;
    }
    public int getColor()
    {
        return this.color;
    }
    public void setCost(int cost)
    {
        this.cost=cost;
    }
    public int getCost()
    {
        return this.cost;
    }
    public String getName()
    {
        return "Mirino";
    }
    public void setMoveme(int moveme)
    {
        this.moveme=moveme;
    }
    public int getMoveme()
    {
        return this.moveme;
    }
    public void setLife(int life)
    {
        this.life=life;
    }
    public int getLife()
    {
        return this.life;
    }
    public void setMarks(int marks)
    {
        this.marks=marks;
    }
    public int getMarks()
    {
        return this.marks;
    }

}