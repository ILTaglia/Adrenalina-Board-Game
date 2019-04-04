import java.util.ArrayList;

public abstract class Ammo_Card implements Card {

    //Boolean per evitare che la stessa carta rifornimento sia usata due volte nello stesso turno dallo stesso giocatore
    //Solo alla fine del turno si elimina la carta e la si sostituisce
    protected boolean Used;

    public void Set_Used(){
        Used=true;
    }
    public ArrayList<Ammo> Get_Card(){//TODO dopo aver modificato le classi di Ammo_Card
    }
}
