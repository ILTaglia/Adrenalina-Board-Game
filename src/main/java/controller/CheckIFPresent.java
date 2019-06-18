package controller;

import model.Coordinate;
import model.Player;

import java.util.List;

public class CheckIFPresent {
    public boolean check(Player player, List<Player> listofplayer)
    {
        if(listofplayer.contains(player))
        {
            return true;
        }
        else
            return false;
    }
    public boolean check (Coordinate coordinate, List<Coordinate> listofcell)
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
