package Model;

public class Newton implements PowCard{
    private int color;
    private int cost;
    private int moveme;
    private int life;
    private int marks;
    public Newton(int color, int cost, int moveme, int life, int marks)
    {
        this.color=color;
        this.cost=cost;
        this.moveme=moveme;
        this.life=life;
        this.marks=marks;
    }
    public Newton(int color, int cost, int moveme)
    {
        this.color=color;
        this.cost=cost;
        this.moveme=moveme;
        this.life=0;
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
        return "Raggio Cinetico";
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
