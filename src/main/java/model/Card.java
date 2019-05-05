package model;

public abstract class Card {

    protected boolean used;

    public boolean getStatus(){
        return this.used;
    }

    public void setUsed(){
        this.used =true;
    }

    public void setAvailable(){
        this.used =false;
    }

}
