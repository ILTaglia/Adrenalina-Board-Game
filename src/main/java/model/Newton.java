package model;

public class Newton extends PowCard{
    /**
     * color is the int corresponding to the color. Conventions of Ammos:
     * 0 = Red
     * 1 = Blue
     * 2 = Yellow
     * cost is an int representing the price
     * moveme is an int that indicates of how many steps the attacking player can move
     */
    private int color;
    private int cost;
    private int moveme;
    /**
     *
     * @param color is the int representing the color
     * @param cost is the int representing the price
     * @param moveme is the int that indicates of how many steps the attacking player can move
     */
    public Newton(int color, int cost, int moveme)
    {
        this.color=color;
        this.cost=cost;
        this.moveme=moveme;

    }
    /**
     *
     * @param color is the int corresponding color associated to the PowCard
     */
    public void setColor(int color)
    {
        this.color=color;
    }
    /**
     *
     * @return the int representing the color
     */
    public int getColor()
    {
        return this.color;
    }
    /**
     *
     * @return the the color of the PowCard
     */
    public String getColorAsString(){
        String s="";
        if(color==0) return "red";
        if(color==1) return "blue";
        if(color==2) return "yellow";
        else return s;
    }
    /**
     *
     * @param cost is the price of the PowCard
     */
    public void setCost(int cost)
    {
        this.cost=cost;
    }
    /**
     *
     * @return the int representing the price of the PowCard
     */
    public int getCost()
    {
        return this.cost;
    }
    /**
     *
     * @return the name of the PowCard
     */
    public String getName()
    {
        return "Raggio Cinetico";
    }
    /**
     *
     * @param moveme is the int that indicates of how many steps the attacking player can move
     */
    public void setMoveme(int moveme)
    {
        this.moveme=moveme;
    }
    /**
     *
     * @return the int that indicates of how many steps the attacking player can move
     */
    public int getMoveme()
    {
        return this.moveme;
    }
    /**
     *
     * @return the type of the PowCard
     */
    @Override
    public int getType() {
        return 0;
    }
}
