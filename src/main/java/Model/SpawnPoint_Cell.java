package Model;

import exceptions.*;

import java.util.ArrayList;

public class SpawnPoint_Cell extends Cell {

    private ArrayList<Weapon> Weapons_SpawnPoint;

    //Quando creo lascio i campi carta vuoti, il controller poi creerà mazzo e distribuirà le carti arma a inizio partita
    public SpawnPoint_Cell(int color, int N_port, int E_port, int S_port, int W_port){
        this.color=color;
        Weapons_SpawnPoint=new ArrayList<>();

        this.port[0] = N_port;
        this.port[1] = E_port;
        this.port[2] = S_port;
        this.port[3] = W_port;
    }
    public void Add_Weapon_Card(Weapon weapon,int index) throws FullCellException{
        if(Weapons_SpawnPoint.size()==3) throw new FullCellException();
        Weapons_SpawnPoint.set(index,weapon);
    }
    //Assegna al player la carta selezionata tra le 3 disponibili
    public void Collect_Weapon(Player player,int selected_weapon) throws MaxNumberofCardsException {
        player.addWeapon(Weapons_SpawnPoint.get(selected_weapon));

    }

}
