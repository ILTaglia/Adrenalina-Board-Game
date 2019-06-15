package model;

import exceptions.*;

import java.io.Serializable;

public class Ammo implements Serializable {

    private int color;

    public Ammo(int color) throws InvalidColorException {
        if(color<0 || color>2) throw new InvalidColorException();
        this.color=color;
    }

    public int getAmmo(){
        return color;
    }

    public String toString(){
        return String.valueOf(color);
    }


}
