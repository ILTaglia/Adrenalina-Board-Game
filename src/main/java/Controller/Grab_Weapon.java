package Controller;

import Model.Match;
import Model.Player;
import Model.SpawnPoint_Cell;
import exceptions.MaxNumberofCardsException;

public class Grab_Weapon extends Grab{
    public Grab_Weapon(){

    }
    public void Grab_Weapon(Match match, Player player,int index_weapon) throws MaxNumberofCardsException{
        SpawnPoint_Cell cell;
        cell = (SpawnPoint_Cell) player.getCel().inmap(match.getDashboard(), player.getCel().getX(), player.getCel().getY());
        cell.Collect_Weapon(player,index_weapon);

        match.addWeaponCard(cell,index_weapon);

        player.setAction();
    }

}
