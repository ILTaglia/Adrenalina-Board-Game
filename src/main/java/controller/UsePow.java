package controller;
import exceptions.*;
import model.*;

import java.util.ArrayList;

public class UsePow {
    public int usepowcard(Match m, PowCard p, Player first, Player second, String move,int n, Coordinate c)
    {
        if(m.getPlayer(first.getColor()).equals(first)&&first.isPowPresent(p)) //Controllo che il giocatore esista e abbia quel potenziamento
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
                m.getPlayer(first.getColor()).removePow(p);
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
        m.getPlayer(first.getColor()).setCel(c.getX(),c.getY());
    }


    private void usegranade(Match m, Player first, Player second)
    {
        m.getPlayer(second.getColor()).setmarks(1,first.getColor());
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
            r.movement(m,second.getID(),moves,false,false);             //TODO
        }
        catch (InvalidDirectionException e)
        {

        } catch (NotYourTurnException e) {
            e.printStackTrace();
        } catch (ActionNotAllowedException e) {
            e.printStackTrace();
        }
    }


    private void usescope(Match m, Player first, Player second)
    {
        m.getPlayer(second.getColor()).setDamage(1,first.getColor());
    }
}
