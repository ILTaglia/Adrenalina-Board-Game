package model;

import exceptions.*;

import java.io.Serializable;

public class Ammo implements Serializable {
    /**
     * color is the int corresponding to the color of the Ammo
     * Conventions used:
     * 0 = red
     * 1 = blue
     * 2 = yellow
     */
    private int color;
    /**
     *
     * @param color is the int corresponding to the color of the Ammo
     * @throws InvalidColorException if the passed color (parameter) is not one of the allowed colors
     */
    public Ammo(int color) throws InvalidColorException {
        if(color<0 || color>2) throw new InvalidColorException();
        this.color=color;
    }
    /**
     *
     * @return the color as an int of the object Ammo
     */
    public int getAmmo(){
        return color;
    }
    /**
     *
     * @return the int representing the color as a String
     */
    public String toString(){
        return String.valueOf(color);
    }
}
