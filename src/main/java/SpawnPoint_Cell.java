import exceptions.CardAlreadyCollectedException;

import java.util.ArrayList;

public class SpawnPoint_Cell extends Cell {

    //TODO: correggere tipo arma quando sarà implementato
    //TODO: modificare costruttore con le porte

    private ArrayList<Arma> Armi_SpawnPoint;

    //Quando creo lascio i campi carta vuoti, il controller poi creerà mazzo e distribuirà le carti arma a inizio partita
    public SpawnPoint_Cell(int color){
        this.color=color;
        Armi_SpawnPoint=new ArrayList<>();
    }

    public void Add_Weapon_Card(Arma arma){
        Armi_SpawnPoint.add(arma);
    }

    //TODO: Metodo Collect, basato su implementazione armi

}
