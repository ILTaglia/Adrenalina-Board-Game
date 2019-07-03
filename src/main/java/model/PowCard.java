package model;

import java.io.Serializable;

public abstract class PowCard extends Card {
    /**
     * Method to set the color of the PowCard
     * Conventions used are the same used for the Ammos:
     * 0 = red
     * 1 = blue
     * 2 = yellow
     *
     * @param color is the int corresponding color associated to the PowCard
     */
    public abstract void setColor(int color);

    /**
     *
     * @return the int corresponding to the color of the PowCard
     */
    public abstract int getColor();

    /**
     *
     * @param cost is the price of the PowCard
     */
    public abstract void setCost(int cost);

    /**
     *
     * @return the price of the PowCard
     */
    public abstract int getCost();

    /**
     *
     * @return the name of the PowCard
     */
    public abstract String getName();

    /**
     * @param moveme is the int that indicates of how many steps the attacking player can move
     *
     */
    public abstract void setMoveme(int moveme);

    /**
     *
     * @return the int that indicates of how many steps the attacking player can move
     */
    public abstract int getMoveme();


    /**
     *
     * @return the type of the PowCard
     */
    public abstract int getType();



}
