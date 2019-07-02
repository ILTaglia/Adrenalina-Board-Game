package model;

import java.io.Serializable;

public abstract class PowCard extends Card {
    public abstract void setColor(int color);
    public abstract int getColor();
    public abstract void setCost(int cost);
    public abstract int getCost();
    public abstract String getName();
    public abstract void setMoveme(int moveme);
    public abstract int getMoveme();
    public abstract int getType();

}
