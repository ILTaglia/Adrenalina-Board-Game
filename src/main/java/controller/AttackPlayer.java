package controller;

import model.Coordinate;
import model.Damage;
import model.Match;
import model.Player;

import java.util.ArrayList;

public class AttackPlayer {

    public void assigndamages(Match m, Player first, Player second, Damage damage) //Assegno i danni ad un player
    {
        if(damage.getType()==0) //Caso in cui ho un danno di tipo vita
        {
            m.getPlayer(second.getcolor()).setdamage(damage.getdamage(),first.getcolor());
        }
        else //Caso in cui ho un danno di tipo marks
        {
            m.getPlayer(second.getcolor()).setmarks(damage.getdamage(),first.getcolor());
        }
    }

    public void assigncelldamages(Match m, Coordinate c, Player first, Damage damage) //assegna i danni a tutti i player di una cella
    {
        ArrayList<Player> toattack = m.getSameCellsPlayers(c);
        for(Player p : toattack)
        {
            if(!p.equals(first)) //Controllo di non assegnare danni allo stesso giocatore attaccante
            {
                assigndamages(m,first,p,damage);
            }
        }
    }
}