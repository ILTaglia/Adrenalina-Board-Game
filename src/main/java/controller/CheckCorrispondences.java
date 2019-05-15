package controller;

import model.Player;

import java.util.ArrayList;

public class CheckCorrispondences {
    public ArrayList<ArrayList> checkID(Player player, int ID, ArrayList<Player> lista)
    {
        ArrayList<ArrayList> risultato= new ArrayList<ArrayList>();
        ArrayList<Integer> flag=new ArrayList<Integer>();
        if(lista.size()==0)
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
}
