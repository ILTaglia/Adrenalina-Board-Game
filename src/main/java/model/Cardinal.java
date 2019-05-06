package model;

import java.util.ArrayList;

public class Cardinal implements TypeAttack {
    private ArrayList<Integer> extra;
    private int typePlayer;
    private int distance;
    private int moveMe;
    private int moveYou;
    private ArrayList<Effect> E;
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
    public ArrayList getExtras(){
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
}
