package Model;

import exceptions.CardAlreadyCollectedException;
import exceptions.FullCellException;

public class Normal_Cell extends Cell {
        Ammo_Card Ammo_NormalCell;

    public Normal_Cell(int color, int N_port, int E_port, int S_port, int W_port){
        this.color = color;
        this.port[0] = N_port;
        this.port[1] = E_port;
        this.port[2] = S_port;
        this.port[3] = W_port;
    }

    public void Add_Ammo_Card(Ammo_Card ammo_card) throws FullCellException{
        if(ammo_card!=null) throw new FullCellException();
        this.Ammo_NormalCell =ammo_card;
        //TODO: Aggiungere costruzione porte
    }
    //Exception must be handled by Controller
    public void Collect_Card(Player player) throws CardAlreadyCollectedException{
        Ammo_NormalCell.Collect_Card(player);
        //TODO: Aggiungere carta dove appena rimossa
    }
}