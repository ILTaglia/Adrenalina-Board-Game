package model;

import java.io.Serializable;

public class Coordinate implements Serializable {

    /**
     * x is the line of the Coordinate
     * y is the column of the Coordinate
     */
    private int x;
    private int y;

    /**
     * Coordinate indicates the position of a cel in the dashboard
     * @param i is number of line
     * @param i1 is number of column
     */
    public Coordinate(int i, int i1) {
        this.x=i;
        this.y=i1;
    }

    /**
     * Method to change line and column of the coordinate
     * @param x is number of line
     * @param y is number of column
     */
    public void set(int x, int y)
    {
        this.x=x;
        this.y=y;
    }

    /**
     *
     * @return number of line of the coordinate
     */
    public int getX()
    {
        return this.x;
    }

    /**
     *
     * @return number of column of the coordinate
     */
    public int getY()
    {
        return this.y;
    }

    /**
     * Method to have a Cell (object) knowing the position, the cell's coordinates
     * @param d is dashboard, the map
     * @param x is number of line
     * @param y number of column
     * @return the Cell (object) in the dashboard at line x, column y
     */
    public Cell inMap(Dashboard d, int x, int y){
        return d.getMap(x, y);
    }
}
