package model;

public class Teleporter extends PowCard{
    private int color;
    private int cost;
    private int moveme;

    public Teleporter(int color, int cost, int moveme)
    {
        this.color=color;
        this.cost=cost;
        this.moveme=0;

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
        return "Teletrasporto";
    }
    public void setMoveme(int moveme)
    {
        this.moveme=moveme;
    }
    public int getMoveme()
    {
        return this.moveme;
    }

    @Override
    public int getType() {
        return 2;
    }
}
