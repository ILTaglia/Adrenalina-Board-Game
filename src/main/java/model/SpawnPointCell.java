package model;

import exceptions.*;

import java.util.ArrayList;
import java.util.List;

public class SpawnPointCell extends Cell {

    private ArrayList<Weapon> weaponsSpawnPoint;

    //Quando creo lascio i campi carta vuoti, il controller poi creerà mazzo e distribuirà le carti arma a inizio partita
    public SpawnPointCell(int color, int N_port, int E_port, int S_port, int W_port){
        this.color=color;
        this.type=0; //spawnpoint
        weaponsSpawnPoint =new ArrayList<>();


        this.port[0] = N_port;
        this.port[1] = E_port;
        this.port[2] = S_port;
        this.port[3] = W_port;
    }

    public void addWeaponCard(Weapon weapon, int index) throws FullCellException{
        if(weaponsSpawnPoint.size()==3) throw new FullCellException();
        weaponsSpawnPoint.add(index,weapon);
    }

    public void setWeaponCard(Weapon weapon, int index){
        weaponsSpawnPoint.set(index,weapon);
    }

    //Assegna al player la carta selezionata tra le 3 disponibili
    public Card collectWeapon(int selectedWeapon) {
        Card selectedCard= weaponsSpawnPoint.get(selectedWeapon);
        weaponsSpawnPoint.set(selectedWeapon,null);
        return selectedCard;
    }

    //Returns the list of the weapons in the SpawnPoint Cell to allow the player to choose one of them
    public List<Weapon> getSpawnPointCellWeapons(){
        return new ArrayList<>(weaponsSpawnPoint);
    }

}
