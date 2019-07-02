package model;

import exceptions.CardAlreadyCollectedException;
import exceptions.FullCellException;
import exceptions.MoreThanTreeAmmosException;

import java.util.Arrays;
import java.util.List;

public class NormalCell extends Cell {
    /**
     * ammoNormalCell is the AmmoCard associated to the Normal Cell
     */
    private AmmoCard ammoNormalCell;

    /**
     * Normal Cell has the type 1
     *
     * @param color is the color of the cell
     * @param nPort is the int corresponding to the northern port
     * @param ePort is the int corresponding to the eastern port
     * @param sPort is the int corresponding to the southern port
     * @param wPort is the int corresponding to the western port
     */
    public NormalCell(int color, int nPort, int ePort, int sPort, int wPort){
        this.color = color;
        this.type=1; //normal
        this.port[0] = nPort;
        this.port[1] = ePort;
        this.port[2] = sPort;
        this.port[3] = wPort;
    }

    /**
     *
     * @param ammoCard is the ammoCard to be added in the cell
     * @throws FullCellException if the cell already contains an ammocard
     */
    public void addAmmoCard(AmmoCard ammoCard) throws FullCellException{
        if(this.ammoNormalCell !=null) throw new FullCellException();
        this.ammoNormalCell = ammoCard;
    }

    /**
     *
     * @param player is the player that collects the ammocard
     * @throws CardAlreadyCollectedException if the card has been collected by the same player in the same turn in the previous action
     * @throws MoreThanTreeAmmosException if the player already owns three ammos
     *
     * exceptoins are handled by controller
     */
    public void collectCard(Player player) throws CardAlreadyCollectedException, MoreThanTreeAmmosException {
        ammoNormalCell.collectCard(player);
    }

    /**
     *
     * @return the type of card (1 for normal cell)
     */
    public int getCardType(){
        return ammoNormalCell.getType();
    }

    /**
     *
     * @return the AmmoCard contained in the cell
     */
    public AmmoCard getAmmoCard(){ return ammoNormalCell;}
}