package Model;

public abstract class Card {

    protected boolean used;

    public void setUsed(){
        this.used =true;
    }

    public void setAvailable(){
        this.used =false;
    }

}
