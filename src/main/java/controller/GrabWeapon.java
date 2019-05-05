package controller;

import model.Match;
import model.Player;
import model.SpawnPointCell;
import exceptions.MaxNumberofCardsException;

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

}
