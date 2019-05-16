package controller;

import model.*;

import java.util.ArrayList;

public class ShootManagement {
    private ArrayList<Player> playersID;
    private ArrayList<Coordinate> coordinatesID;

    public ShootManagement()
    {
        playersID=new ArrayList<Player>();
        coordinatesID= new ArrayList<Coordinate>();
    }

    public int shoot (Match match, ArrayList<Player> visibles, Player first, Player second, int ID, Damage damage) //0 means correct
    {
        int flag= checkID(second,ID);
        if(flag==-1)
            return -1;
        else
        {
            if(check(second,visibles))
            {
                assigndamages(match,first,second,damage);
            }
            else
                return -2;
        }
        return 0;
    }

    public int shoot(Match match, ArrayList<Coordinate> visibles, Player first, Coordinate second, int ID, Damage damage)
    {
        int flag= checkID(second,ID);
        if(flag==-1)
            return -1;
        else
        {
            if(check(second,visibles))
            {
                assigndamages(match,second,first,damage);
            }
            else
                return -2;
        }
        return 0;
    }












    private int checkID(Player player, int ID)
    {
        ArrayList<Integer> flag=new ArrayList<Integer>();
        if(this.playersID.size()==0)
        {
            this.playersID.add(player);
            return 1;
        }
        else
        if(this.playersID.size()==ID)
        {
            if(!this.playersID.contains(player))
            {
                this.playersID.add(player);
                return 1;
            }
            else
            {
                return -1;

            }
        }
        else
        if(this.playersID.size()>=ID+1)
        {
            if(this.playersID.get(ID).equals(player))
            {
                return 1;
            }
            else
            {
                return -1;

            }
        }
        else {
            return -1;
        }
    }

    private int checkID(Coordinate coordinate, int ID)
    {
        ArrayList<Integer> flag=new ArrayList<Integer>();
        if(this.coordinatesID.size()==0)
        {
            this.coordinatesID.add(coordinate);
            return 1;
        }
        else
        if(this.coordinatesID.size()==ID)
        {
            if(!this.coordinatesID.contains(coordinate))
            {
                this.coordinatesID.add(coordinate);
                return 1;
            }
            else
            {
                return -1;

            }
        }
        else
        if(this.coordinatesID.size()>=ID+1)
        {
            if(this.coordinatesID.get(ID).equals(coordinate))
            {
                return 1;
            }
            else
            {
                return -1;

            }
        }
        else {
            return -1;
        }
    }

    private boolean check(Player player, ArrayList<Player> listofplayer)
    {
        if(listofplayer.contains(player))
        {
            return true;
        }
        else
            return false;
    }
    private boolean check (Coordinate coordinate, ArrayList<Coordinate> listofcell)
    {
        //if(listofcell.contains(coordinate))
        //{
        //    return true;
        //}
        //else
        for(Coordinate c : listofcell)
        {
            if(c.getY()==coordinate.getY()&&c.getX()==coordinate.getX())
                return true;
        }
        return false;
    }

    private void assigndamages(Match m, Player first, Player second, Damage damage) //Assegno i danni ad un player
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

    private void assigndamages(Match m, Coordinate c, Player first, Damage damage) //assegna i danni a tutti i player di una cella
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
