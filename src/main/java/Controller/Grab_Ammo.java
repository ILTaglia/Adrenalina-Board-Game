package Controller;

import Model.Cell;
import Model.Match;
import Model.Normal_Cell;
import Model.Player;
import exceptions.CardAlreadyCollectedException;
import exceptions.MaxNumberofCardsException;

public class Grab_Ammo extends Grab{
    public Grab_Ammo(){
    }
    public void Grab_Ammo(Match match, Player player) throws CardAlreadyCollectedException{
        Normal_Cell cell;
        int CardType;
        cell = (Normal_Cell) player.get_cel().inmap(match.get_dashboard(), player.get_cel().getX(), player.get_cel().getY());
        cell.Collect_Card(player);
        match.Add_AmmoCard(cell);
        CardType=cell.getCardType();
        if(1==CardType){        //CardType==1 if Card is Ammo_Pow_Tile
            try{
                match.assignPowCard(player);
            }catch (MaxNumberofCardsException e){
                //TODO:Stampare messaggio d'errore e ragionare su come gestire situazione
            }
        }
        player.set_action();
    }

}
