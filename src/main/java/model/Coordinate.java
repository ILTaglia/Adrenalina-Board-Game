package model;

import java.io.Serializable;

public class Coordinate implements Serializable {
    private int x;
    private int y;

    public Coordinate(int i, int i1) {
        this.x=i;
        this.y=i1;
    }

    public void set(int x, int y)
    {
        this.x=x;
        this.y=y;
    }
    public int getX()
    {
        return this.x;
    }
    public int getY()
    {
        return this.y;
    }

    public Cell inmap(Dashboard d, int x, int y){
        return d.getmap(x, y);
    }
}
