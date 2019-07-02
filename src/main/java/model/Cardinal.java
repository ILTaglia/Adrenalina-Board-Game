package model;

import java.util.ArrayList;
import java.util.List;

public class Cardinal implements TypeAttack {
    /**
     * type player is an identifier that allows to define in what type of series an attack of a weapon will be performed
     * extra are the money required to unlock extra functions when a player shoots
     * distance is an int that indicates the distance between the attacking player and the attacked player
     * moveme is an int that indicates of how many steps the attacking player can move
     * moveyou is an int that indicates of how many steps the attacked player can move
     * E is the list containing all the effects that a weapon with the given type attack will own
     */
    private List<Integer> extra;
    private int typePlayer;
    private int distance;
    private int moveMe;
    private int moveYou;
    private List<Effect> E;
    //TODO Verificare @Override
    @Override
    public int getDistance(){
        return this.distance;
    }
    @Override
    public int getTypePlayer(){
        return this.typePlayer;
    }
    @Override
    public List getExtras(){
        return this.extra;
    }
    @Override
    public void setDistance(int distance){
        this.distance =distance;
    }
    @Override
    public void setTypePlayer(int typePlayer){
        this.typePlayer = typePlayer;
    }
    @Override
    public void addExtra(int extra){
        this.extra.add(extra);
    }
    @Override
    public void setMoveMe(int moveMe){
        this.moveMe = moveMe;
    }
    @Override
    public void setMoveYou(int moveYou){
        this.moveYou = moveYou;
    }
    @Override
    public void addEffect(Effect E){
        this.E.add(E);
    }
    @Override
    public Effect getEffect(int i){
        return this.E.get(i);
    }
    @Override
    public int getNumberEffect(){
        return this.E.size();
    }
    @Override
    public int getNumberExtra(){
        return this.extra.size();
    }
    @Override
    public int getMoveMe(){
        return this.moveMe;
    }
    @Override
    public int getMoveYou(){
        return this.moveYou;
    }
    public Cardinal(int typePlayer, int direction, int moveMe, int moveYou){
        this.extra=new ArrayList<>();
        this.E= new ArrayList<>();
        this.distance =direction;
        this.moveMe = moveMe;
        this.moveYou = moveYou;
        this.typePlayer = typePlayer;
    }
    public int getType(){return 4;}
}
