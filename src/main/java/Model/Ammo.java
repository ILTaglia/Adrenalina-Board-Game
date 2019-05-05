package Model;

import exceptions.*;

public class Ammo {

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
