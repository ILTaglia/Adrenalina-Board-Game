package controller;

import model.Coordinate;
import model.Player;

import java.util.ArrayList;

public class CheckIFPresent {
    public boolean check(Player player, ArrayList<Player> listofplayer)
    {
        if(listofplayer.contains(player))
        {
            return true;
        }
        else
            return false;
    }
    public boolean check (Coordinate coordinate, ArrayList<Coordinate> listofcell)
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
}
