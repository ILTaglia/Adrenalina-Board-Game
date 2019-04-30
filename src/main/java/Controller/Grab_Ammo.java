package Controller;

import Model.Cell;
import Model.Match;
import Model.Normal_Cell;
import Model.Player;
import exceptions.CardAlreadyCollectedException;

public class Grab_Ammo extends Grab{
    public Grab_Ammo(){

    }
    public void Grab_Ammo(Match match, Player player) throws CardAlreadyCollectedException{
        Normal_Cell cell;
        cell = (Normal_Cell) player.get_cel().inmap(match.get_dashboard(), player.get_cel().getX(), player.get_cel().getY());
        cell.Collect_Card(player);
        //TODO questione potenziamento
        match.Add_AmmoCard(cell);

        player.set_action();
    }

}
