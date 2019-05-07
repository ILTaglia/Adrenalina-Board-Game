package model;

import exceptions.CardAlreadyCollectedException;
import exceptions.FullCellException;

public class NormalCell extends Cell {
        AmmoCard Ammo_NormalCell;

    public NormalCell(int color, int N_port, int E_port, int S_port, int W_port){
        this.color = color;
        this.port[0] = N_port;
        this.port[1] = E_port;
        this.port[2] = S_port;
        this.port[3] = W_port;
    }

    public void Add_Ammo_Card(AmmoCard ammo_card) throws FullCellException{
        if(this.Ammo_NormalCell!=null) throw new FullCellException();
        this.Ammo_NormalCell = ammo_card;
    }
    //Exception must be handled by controller
    public void Collect_Card(Player player) throws CardAlreadyCollectedException{
        Ammo_NormalCell.collectCard(player);
    }
    public int getCardType(){
        return Ammo_NormalCell.getType();
    }
}