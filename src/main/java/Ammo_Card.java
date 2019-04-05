import java.util.ArrayList;

public abstract class Ammo_Card extends Card {

    //Boolean per evitare che la stessa carta rifornimento sia usata due volte nello stesso turno dallo stesso giocatore
    //Solo alla fine del turno si elimina la carta e la si sostituisce
    protected ArrayList<Ammo> Refill_Ammo;

    public abstract void Collect_Card(Player player);

}
