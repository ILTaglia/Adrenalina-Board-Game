import java.util.*;

public abstract class Cell {

    protected int color;    //colore della cella
    protected int[] port;   //Presenza delle porte espresse in base ai punti cardinali, se la porta è assente e il colore delle celle è ≠ si assume che ci sia un muro

    //TODO: costruttore come deve gestire le porte?
    //TODO: il metodo per sapere quale carta è presente? Va ragionato con la view?
}
