package model;

import java.io.Serializable;

public abstract class Card implements Serializable {

    /**
     * used is a boolean value to check if the card has been used or not
     */

    protected boolean used;

    /**
     *
     * @return the boolean that says if card has been used or not
     */
    public boolean getStatus(){
        return this.used;
    }

    /**
     * Method to call after the use of a card, to say it has been used
     */
    public void setUsed(){
        this.used =true;
    }

    /**
     * Method to set the boolean TRUE, to say the card can be used
     */
    public void setAvailable(){
        this.used =false;
    }

}
