package model;

import java.io.Serializable;
import java.util.*;

public abstract class Cell implements Serializable {

    /**
     * color is an int representing the color of the Cell. Conventions:
     * 0 - Blue
     * 1 - Green
     * 2 - Yellow
     * 3 - Pink
     * 4 - Grey
     * 5 - Red
     *
     * type is an identifier for the type of cells. Conventions:
     * 0 - SpawnPoint Cell
     * 1 - Normal Cell
     *
     * port is an array of four elements (int) representing the cardinal points, according to the index of the array:
     * 0 is north
     * 1 is east (right)
     * 2 is south
     * 3 is west (left)
     * if port[0]=0 it means that there is no port for the given cell in that direction,
     * if port[0]=1 it means that there is a port for the given cell in that direction
     */
    protected int color;
    int type;
    protected int[] port;

    public Cell(){
        port = new int[4];
    }

    /**
     *
     * @return the type of the cell
     * 0 - SpawnPoint Cell
     * 1 - Normal Cell
     */
    public int getType(){return this.type;}

    /**
     *
     * @return a String of length four, containing 1 or 0 if the port in the corresponding direction is present or not
     */
    @Override
    public String toString(){
        return Arrays.toString(this.port);
    }

    /**
     *
     * @return an int that is the color of the cell
     */
    public int getColor(){return this.color;}

    /**
     *
     * @param d is the direction in which you want to check if the port is present or not
     * 0 is north
     * 1 is east (right)
     * 2 is south
     * 3 is west (left)
     *
     *        N
     *      _____
     *  W  |     |  E
     *     |_____|
     *        S
     *
     * @return 1 or 0 according to the conventions used and previously explained.
     */
    public int portIsPresent(int d){return this.port[d];}

}
