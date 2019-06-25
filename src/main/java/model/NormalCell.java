package model;

import exceptions.CardAlreadyCollectedException;
import exceptions.FullCellException;
import exceptions.MoreThanTreeAmmosException;

public class NormalCell extends Cell {
    private AmmoCard ammoNormalCell;

    public NormalCell(int color, int nPort, int ePort, int sPort, int wPort){
        this.color = color;
        this.type=1; //normal
        this.port[0] = nPort;
        this.port[1] = ePort;
        this.port[2] = sPort;
        this.port[3] = wPort;
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