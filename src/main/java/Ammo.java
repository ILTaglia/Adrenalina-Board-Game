import exceptions.*;

public class Ammo {

    private int color;

    public Ammo(int color) throws InvalidColorExeption{
        if(color<0 || color>3) throw new InvalidColorExeption();
        this.color=color;
    }

    public int get_Ammo(){
        return color;
    }

    /*public String toString(){
        return String.valueOf(color);
    }
    */

}
