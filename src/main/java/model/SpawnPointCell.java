package model;

import exceptions.*;

import java.util.ArrayList;
import java.util.List;

public class SpawnPointCell extends Cell {

    /**
     * weaponSpawnPoint is the ArrayList that contains all the Weapons in the SpawnPoint Cell
     */
    private ArrayList<Weapon> weaponsSpawnPoint;


    /**
     * SpawnPoint Cell has the type 0
     *
     * @param color is the color of the cell
     * @param nPort is the int corresponding to the northern port
     * @param ePort is the int corresponding to the eastern port
     * @param sPort is the int corresponding to the southern port
     * @param wPort is the int corresponding to the western port
     */
    public SpawnPointCell(int color, int nPort, int ePort, int sPort, int wPort){
        this.color=color;
        this.type=0;
        weaponsSpawnPoint =new ArrayList<>();


        this.port[0] = nPort;
        this.port[1] = ePort;
        this.port[2] = sPort;
        this.port[3] = wPort;
    }

    /**
     *
     * @param weapon is the weapon to be added in the SpawnPoint Cell
     * @param index is the position in the ArrayList where the weapon has to be added
     * @throws FullCellException when in the SpawnPoint cell there are already three weapons
     */
    public void addWeaponCard(Weapon weapon, int index) throws FullCellException{
        if(weaponsSpawnPoint.size()==3) throw new FullCellException();
        weaponsSpawnPoint.add(index,weapon);
    }

    /**
     * Different method from the simple addWeaponCard.
     * addWeaponCard is called at the starting of the game, when no weapon cards have been added to the cells yet.
     * This method is use to add a weapon card after the removal, following the grabbing action.
     *
     * @param weapon is the weapon to be added in the SpawnPoint Cell
     * @param index is the position in the ArrayList where the weapon has to be added
     */
    public void setWeaponCard(Weapon weapon, int index){
        weaponsSpawnPoint.set(index,weapon);
    }

    /**
     *
     * @param selectedWeapon is the index of the selected Weapon by the player
     * @return the card corresponding to the index
     */
    public Card collectWeapon(int selectedWeapon) {
        Card selectedCard= weaponsSpawnPoint.get(selectedWeapon);
        weaponsSpawnPoint.set(selectedWeapon,null);
        return selectedCard;
    }

    /**
     *
     * @return the list of the weapons in the SpawnPoint Cell to allow the player to choose one of them
     */
    public List<Weapon> getSpawnPointCellWeapons(){
        return weaponsSpawnPoint;
    }

}
