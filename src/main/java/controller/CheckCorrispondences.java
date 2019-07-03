package controller;

import model.Coordinate;
import model.Player;

import java.util.List;
import java.util.ArrayList;

public class CheckCorrispondences {
    /**
     *
     * @param player is the given player
     * @param ID
     * @param lista
     * @return
     */
    public List<List> checkID(Player player, int ID, List<Player> lista)
    {
        List<List> risultato= new ArrayList<List>();
        List<Integer> flag=new ArrayList<Integer>();
        if(lista.isEmpty())
        {
            lista.add(player);
            risultato.add(0,lista);
            flag.add(1);
            risultato.add(1,flag);
        }
        else
            if(lista.size()==ID)
            {
                if(!lista.contains(player))
                {
                    lista.add(player);
                    risultato.add(0,lista);
                    flag.add(1);
                    risultato.add(1,flag);
                }
                else
                {
                    flag.add(-1);
                    risultato.add(0,lista);
                    risultato.add(1,flag);

                }
            }
            else
                if(lista.size()>=ID+1)
                {
                    if(lista.get(ID).equals(player))
                    {
                        flag.add(1);
                        risultato.add(0,lista);
                        risultato.add(1,flag);
                    }
                    else
                    {
                        flag.add(-1);
                        risultato.add(0,lista);
                        risultato.add(1,flag);

                    }
                }
                else {
                    flag.add(-1);
                    risultato.add(0, lista);
                    risultato.add(1, flag);
                }
                return risultato;
    }


    public List<List> checkID(Coordinate cell, int ID, List<Coordinate> lista)
    {
        List<List> risultato= new ArrayList<List>();
        List<Integer> flag=new ArrayList<Integer>();
        if(lista.isEmpty())
        {
            lista.add(cell);
            risultato.add(0,lista);
            flag.add(1);
            risultato.add(1,flag);
        }
        else
        if(lista.size()==ID)
        {
            if(!lista.contains(cell))
            {
                lista.add(cell);
                risultato.add(0,lista);
                flag.add(1);
                risultato.add(1,flag);
            }
            else
            {
                flag.add(-1);
                risultato.add(0,lista);
                risultato.add(1,flag);

            }
        }
        else
        if(lista.size()>=ID+1)
        {
            if(lista.get(ID).equals(cell))
            {
                flag.add(1);
                risultato.add(0,lista);
                risultato.add(1,flag);
            }
            else
            {
                flag.add(-1);
                risultato.add(0,lista);
                risultato.add(1,flag);

            }
        }
        else {
            flag.add(-1);
            risultato.add(0, lista);
            risultato.add(1, flag);
        }
        return risultato;
    }

}
