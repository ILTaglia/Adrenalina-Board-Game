import exceptions.CardAlreadyCollectedException;

import java.util.ArrayList;

public class Normal_Cell extends Cell {

    //TODO: modificare costruttore con le porte

    Ammo_Card ammo_card;

    public Normal_Cell(int color){
        this.color=color;
    }

    public void Add_Ammo_Card(Ammo_Card ammo_card){
        this.ammo_card=ammo_card;
    }
    public void Collect_Card(Player player) throws CardAlreadyCollectedException{
        ammo_card.Collect_Card(player);
    }
}