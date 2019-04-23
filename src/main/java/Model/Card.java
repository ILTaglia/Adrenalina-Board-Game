package Model;

import exceptions.*;

abstract public class Card {

    protected boolean Used;

    public void Set_Used(){
        this.Used=true;
    }

    public void Set_Available(){
        this.Used=false;
    }

}
