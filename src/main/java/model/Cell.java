package model;

import java.io.Serializable;
import java.util.*;

public abstract class Cell implements Serializable {

    protected int color;    //colore della cella
    int type;
    //0 spawnpoint, 1 normal
    protected int[] port;   //Presenza delle porte espresse in base ai punti cardinali, se la porta è assente e il colore delle celle è ≠ si assume che ci sia un muro

    //TODO: costruttore come deve gestire le porte?
    //TODO: il metodo per sapere quale carta è presente? Va ragionato con la view?

    public Cell(){
        port = new int[4];
    }

    public int getType(){return this.type;}

    @Override
    public String toString(){
        return Arrays.toString(this.port);
    }

    public int getColor(){return this.color;}

    public int portIsPresent(int d){return this.port[d];}

}
