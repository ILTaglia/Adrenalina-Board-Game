package Model;

public class Granade implements PowCard{
    private int color;
    private int cost;
    private int moveme;
    private int life;
    private int marks;
    public Granade(int color, int cost, int moveme, int life, int marks)
    {
        this.color=color;
        this.cost=cost;
        this.moveme=moveme;
        this.life=life;
        this.marks=marks;
    }
    public Granade(int color, int cost, int marks)
    {
        this.color=color;
        this.cost=cost;
        this.moveme=0;
        this.life=0;
        this.marks=marks;
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
        return "Granata Venom";
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
