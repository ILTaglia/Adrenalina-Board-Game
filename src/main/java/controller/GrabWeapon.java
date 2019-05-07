package controller;

import model.Match;
import model.Player;
import model.SpawnPointCell;
import exceptions.MaxNumberofCardsException;
import java.util.List;

public class GrabWeapon extends Grab{
    public GrabWeapon(){
        //Empty Constructor, controller has to use the method grabWeapon
    }
    public void grabWeapon(Match match, Player player, int indexWeapon) throws MaxNumberofCardsException{
        SpawnPointCell cell;
        cell = (SpawnPointCell) player.getCel().inmap(match.getDashboard(), player.getCel().getX(), player.getCel().getY());
        cell.Collect_Weapon(player,indexWeapon);

        match.addWeaponCard(cell,indexWeapon);

        player.setAction();
    }

    public boolean isValid(Match match, Player player, List<String> destination, int indexWeapon){
        if(indexWeapon<0 || indexWeapon>2) return false;
        if(!this.isValidMovement(match, player, destination)) return false;
        else {
            if (player.getnumberweapon() == 3) return false;
            else return true;
        }
    }

}
