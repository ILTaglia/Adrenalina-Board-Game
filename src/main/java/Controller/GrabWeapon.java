package Controller;

import Model.Match;
import Model.Player;
import Model.SpawnPointCell;
import exceptions.MaxNumberofCardsException;

public class GrabWeapon extends Grab{
    public GrabWeapon(){

    }
    public void Grab_Weapon(Match match, Player player,int index_weapon) throws MaxNumberofCardsException{
        SpawnPointCell cell;
        cell = (SpawnPointCell) player.getCel().inmap(match.getDashboard(), player.getCel().getX(), player.getCel().getY());
        cell.Collect_Weapon(player,index_weapon);

        match.addWeaponCard(cell,index_weapon);

        player.setAction();
    }

}
