package Model;

import java.util.ArrayList;

public interface Type_attack {

    public int getDistance();
    public int getTypeplayer();
    public ArrayList getextras();
    public void setDistance(int distance);
    public void setTypeplayer(int typeplayer);
    public void addextra(int extra);
    public void setMoveme(int moveme);
    public void setMoveyou(int moveyou);
    public void addeffect(Effect E);
    public Effect getEffect(int i);
    public int getnumbereffect();
    public int getnumberextra();
    public int getMoveme();
    public int getMoveyou();
}
