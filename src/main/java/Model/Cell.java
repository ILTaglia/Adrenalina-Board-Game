package Model;

import java.util.*;

public abstract class Cell {

    protected int color;    //colore della cella
    protected int type;     //tipo della porta (spawn point o normal)
    protected int[] port;   //Presenza delle porte espresse in base ai punti cardinali, se la porta è assente e il colore delle celle è ≠ si assume che ci sia un muro

    //TODO: costruttore come deve gestire le porte?
    //TODO: il metodo per sapere quale carta è presente? Va ragionato con la view?

    public Cell(){
        port = new int[4];
    }

    public String elenco(){
        return Arrays.toString(this.port);
    }

    public int getcolor(){return this.color;}

    public int gettype(){return this.type;}

    public int portIsPresent(int d){return this.port[d];}

}
