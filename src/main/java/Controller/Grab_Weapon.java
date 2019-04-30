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
        cell = (SpawnPoint_Cell) player.get_cel().inmap(match.get_dashboard(), player.get_cel().getX(), player.get_cel().getY());
        cell.Collect_Weapon(player,index_weapon);

        match.Add_WeaponCard(cell,index_weapon);

        player.set_action();
    }

}
