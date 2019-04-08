import exceptions.*;

import java.util.ArrayList;

public class SpawnPoint_Cell extends Cell {

    //TODO: modificare costruttore con le porte
    //TODO: rivedere
    private ArrayList<Weapon> Weapons_SpawnPoint;

    //Quando creo lascio i campi carta vuoti, il controller poi creerà mazzo e distribuirà le carti arma a inizio partita
    public SpawnPoint_Cell(int color){
        this.color=color;
        Weapons_SpawnPoint=new ArrayList<>();
    }

    public void Add_Weapon_Card(Weapon weapon) throws FullCellException{
        if(Weapons_SpawnPoint.size()==3) throw new FullCellException();
        Weapons_SpawnPoint.add(weapon);

    }
    //Assegna al player la carta selezionata tra le 3 disponibili
    public void Collect_Weapon(Player player,int selected_weapon) throws MaxNumberofCardsException {
        player.add_weapon(Weapons_SpawnPoint.get(selected_weapon));
    }

}
