package model;

public class Newton extends PowCard{
    private int color;
    private int cost;
    private int moveme;
    public Newton(int color, int cost, int moveme)
    {
        this.color=color;
        this.cost=cost;
        this.moveme=moveme;

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

    @Override
    public int getType() {
        return 0;
    }
}
