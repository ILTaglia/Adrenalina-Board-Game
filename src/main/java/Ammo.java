import exceptions.*;

public class Ammo {

    private int color;

    public Ammo(int color) throws InvalidColorException {
        if(color<0 || color>3) throw new InvalidColorException();
        this.color=color;
    }

    public int get_Ammo(){
        return color;
    }

    public String toString(){
        return String.valueOf(color);
    }


}
