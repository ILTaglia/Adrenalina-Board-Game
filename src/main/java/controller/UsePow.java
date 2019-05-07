package controller;
import exceptions.InvalidDirectionException;
import exceptions.NotOwnedCardException;
import exceptions.ZeroCardsOwnedException;
import model.*;

import java.util.ArrayList;
import java.util.List;

public class UsePow {
    public int usepowcard(Match m, PowCard p, Player first, Player second, String move,int n, Coordinate c)
    {
        if(m.getPlayer(first.getcolor()).equals(first)&&first.powIspresent(p)) //Controllo che il giocatore esista e abbia quel potenziamento
        {
            if(p instanceof model.Teleporter) //Nel caso in cui avessi un teleporter
            {
                useteleporter(m,first,c);

            }
            else
                if(p instanceof  model.Granade) //Nel caso in cui avessi una granata
                {
                    usegranade(m,first,second);
                }
                else
                    if(p instanceof  model.Newton) //Nel caso in cui avessi un newton
                    {
                        usenewton(m,second,move,n);
                    }
                    else //Caso in cui avessi un mirino
                    {
                        usescope(m,first,second);
                    }
            try
            {
                m.getPlayer(first.getcolor()).removePow(p);
            }
            catch (ZeroCardsOwnedException e)
            {

            }
            catch (NotOwnedCardException e)
            {

            }

            m.discardPowCard(p);
        }
        else
        {
            // caso in cui stia barando
            return -1;
        }
        return 0;
    }


    private void useteleporter(Match m, Player first, Coordinate c)
    {
        m.getPlayer(first.getcolor()).setCel(c.getX(),c.getY());
    }


    private void usegranade(Match m, Player first, Player second)
    {
        m.getPlayer(second.getcolor()).setmarks(1,first.getcolor());
    }


    private void usenewton(Match m, Player second, String move, int n )
    {
        ArrayList<String> moves = new ArrayList<String>(); //I create the list of single movements
        for(int i=0;i<n;i++)
        {
            moves.add(move);
        }
        Run r = new Run();
        try
        {
            r.getMovement(m,second,moves);
        }
        catch (InvalidDirectionException e)
        {

        }
    }


    private void usescope(Match m, Player first, Player second)
    {
        m.getPlayer(second.getcolor()).setdamage(1,first.getcolor());
    }
}
