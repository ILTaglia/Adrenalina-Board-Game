package model;

import exceptions.CardAlreadyCollectedException;
import exceptions.FullCellException;
import exceptions.MoreThanTreeAmmosException;

public class NormalCell extends Cell {
    private AmmoCard ammoNormalCell;

    public NormalCell(int color, int N_port, int E_port, int S_port, int W_port){
        this.color = color;
        this.type=1; //normal
        this.port[0] = N_port;
        this.port[1] = E_port;
        this.port[2] = S_port;
        this.port[3] = W_port;
    }

    public void addAmmoCard(AmmoCard ammoCard) throws FullCellException{
        if(this.ammoNormalCell !=null) throw new FullCellException();
        this.ammoNormalCell = ammoCard;
    }
    //Exception must be handled by controller
    public void collectCard(Player player) throws CardAlreadyCollectedException, MoreThanTreeAmmosException {
        ammoNormalCell.collectCard(player);
    }
    public int getCardType(){
        return ammoNormalCell.getType();
    }
}