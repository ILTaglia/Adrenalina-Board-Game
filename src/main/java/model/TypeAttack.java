package model;

import java.util.ArrayList;

public interface TypeAttack {

    public int getDistance();
    public int getTypePlayer();
    public ArrayList getExtras();
    public void setDistance(int distance);
    public void setTypePlayer(int typePlayer);
    public void addExtra(int extra);
    public void setMoveMe(int moveMe);
    public void setMoveYou(int moveYou);
    public void addEffect(Effect E);
    public Effect getEffect(int i);
    public int getNumberEffect();
    public int getNumberExtra();
    public int getMoveMe();
    public int getMoveYou();
    public int getType();
}
