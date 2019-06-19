package model;

import java.io.Serializable;

public abstract class Card implements Serializable {

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
