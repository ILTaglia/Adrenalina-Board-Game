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
    public void grabWeapon(Match match, Player player, int indexWeapon)throws MaxNumberofCardsException{
        SpawnPointCell cell;
        cell = (SpawnPointCell) player.getCel().inmap(match.getDashboard(), player.getCel().getX(), player.getCel().getY());
        cell.Collect_Weapon(player,indexWeapon);

        match.setWeaponCard(cell,indexWeapon);

        player.setAction();
    }
    public void isValid(Player player)throws MaxNumberofCardsException{
        if(player.getNumberWeapon()==3){
            throw new MaxNumberofCardsException();
        }
    }

    public void isValid(Match match, Player player, List<String> destination) throws MaxNumberofCardsException {
        if(!this.isValidMovement(match, player, destination) && !destination.isEmpty()){
            //TODO: lanciare eccezione per movimento errato.
        }
        isValid(player);
    }

}
