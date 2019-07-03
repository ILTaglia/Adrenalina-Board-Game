package controller;

import model.*;

import java.util.ArrayList;
import java.util.List;

public class ShootManagement {
    /**
     * playersID: contains the association between players and the iD of the effect, just to know when we are able to shoot them
     * coordinatesID: playersID: contains the association between cellsand the iD of the effect, just to know when we are able to shoot them
     * It is the class who concretely perform the attack using the lists of attackable players and indexes
     */
    private List<Player> playersID;
    private List<Coordinate> coordinatesID;

    public ShootManagement()
    {
        playersID=new ArrayList<>();
        coordinatesID= new ArrayList<>();
    }

    /**
     *
     * @param match is the match
     * @param visiblePlayers are the visible players
     * @param playerAttacker is the active player, the one that attacks
     * @param playerAttacked is the attacked player
     * @param id is the ID
     * @param damage is the Damage
     * @return 0, -1, -2 according to the specific case
     */
    public int shoot (Match match, List<Player> visiblePlayers, Player playerAttacker, Player playerAttacked, int id, Damage damage) //0 means correct
    {
        int flag= checkID(playerAttacked,id);
        if(flag==-1)
            return -1;
        else
        {
            if(check(playerAttacked,visiblePlayers))
            {
                assignDamages(match,playerAttacker,playerAttacked,damage);
            }
            else
                return -2;
        }
        return 0;
    }

    /**
     *
     * @param match is the match
     * @param visiblePlayers are the visible players
     * @param playerAttacker is the active player, the one that attacks
     * @param cellAttacked is the attacked player
     * @param id is the ID
     * @param damage is the Damage
     * @return 0, -1, -2 according to the specific case
     */
    public int shoot(Match match, List<Coordinate> visiblePlayers, Player playerAttacker, Coordinate cellAttacked, int id, Damage damage)
    {
        int flag= checkID(cellAttacked,id);
        if(flag==-1)
            return -1;
        else
        {
            if(check(cellAttacked,visiblePlayers))
            {
                assignDamages(match,playerAttacker,cellAttacked,damage);
            }
            else
                return -2;
        }
        return 0;
    }

    /**
     *
     * @param player is the given player
     * @param id is the ID to be checked
     * @return different int to distinguish different cases
     */
    private int checkID(Player player, int id)
    {
        if(this.playersID.isEmpty())
        {
            this.playersID.add(player);
            return 1;
        }
        else
        if(this.playersID.size()==id)
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
        if(this.playersID.size()>=id+1)
        {
            if(this.playersID.get(id).equals(player))
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


    /**
     *
     * @param coordinate is the given cell
     * @param ID is the ID to be checked
     * @return different int to distinguish different cases
     */
    private int checkID(Coordinate coordinate, int ID)
    {
        if(this.coordinatesID.isEmpty())
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

    /**
     *
     * @param player is the given player
     * @param listofplayer is the given list of players
     * @return true if the list contains the player, false otherwise
     */
    private boolean check(Player player, List<Player> listofplayer)
    {
        if(listofplayer.contains(player))
        {
            return true;
        }
        else
            return false;
    }

    /**
     *
     * @param coordinate is the given cell (as a coordinate)
     * @param listofcell is the given list of cells
     * @return true if the list contains the cell, false otherwise
     */
    private boolean check (Coordinate coordinate, List<Coordinate> listofcell)
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

    /**
     *
     * @param match is the match
     * @param playerAttacker is the attacker player
     * @param playerAttacked is teh attacked player
     * @param damage is the Damage (mark or life damage)
     */
    private void assignDamages(Match match, Player playerAttacker, Player playerAttacked, Damage damage) //Assegno i danni ad un player
    {
        int outcomeOfAttack;
        int marksOfAttacker;
        if(damage.getType()==0) //Caso in cui ho un danno di tipo vita
        {
            marksOfAttacker=playerAttacked.getMarks(playerAttacker.getColor());
            playerAttacked.resetMarks(playerAttacker.getColor());
            outcomeOfAttack=match.setDamage(playerAttacker,playerAttacked,damage.getDamage()+marksOfAttacker);
            if(outcomeOfAttack==0){
                //Nothing to do, the attack was successful but no kill.
            }
            else{
                DeathAndRespawn deathAndRespawn=new DeathAndRespawn();
                deathAndRespawn.calculateScore(match,playerAttacker,playerAttacked,(outcomeOfAttack==2));
                deathAndRespawn.respawn(playerAttacked);
            }
        }
        else //Caso in cui ho un danno di tipo marks
        {
            match.setMarks(playerAttacker,playerAttacked,damage.getDamage());
        }
    }

    /**
     *
     * @param match is the match
     * @param playerAttacker is the attacker player
     * @param cellAttacked is the attacked player
     * @param damage is the Damage
     */
    private void assignDamages(Match match, Player playerAttacker, Coordinate cellAttacked, Damage damage) //assegna i danni a tutti i player di una cella
    {
        List<Player> playersToBeAttacked = match.getSameCellsPlayers(cellAttacked);
        for(Player playerAttacked : playersToBeAttacked)
        {
            if(!playerAttacked.equals(playerAttacker)) //Controllo di non assegnare danni allo stesso giocatore attaccante
            {
                assignDamages(match,playerAttacker,playerAttacked,damage);
            }
        }
    }

}
