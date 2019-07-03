package controller;

import model.*;

import java.util.List;
import java.util.ArrayList;

public class CreateListAttackable {
    /**
     * attackableplayers is the list of players that I can attack using the tipeattack given
     * attackablecells is the list of cells that I can attack using the tipeattack given
     * direction is the direction chosen by the player in case I can attack only in one direction
     * residualmovement is the number of movement I have, starting from the total number given by the card
     * viewer is the player ftom who I have to start elaborating the lists of attackables, it indicates where the attacks starts from
     */
    private List<Player> attackableplayers;
    private List<Coordinate> attackablecells;
    private int direction;
    private int residualmovement;
    private Player viewer;
    public CreateListAttackable()
    {
        attackablecells= new ArrayList<Coordinate>();
        attackableplayers= new ArrayList<Player>();
        direction=0;
        residualmovement=0;
        viewer=null;
    }
    /**
     *
     * @param viewer to be set
     */
    public void setViewer(Player viewer)
    {
        this.viewer=viewer;
    }
    /**
     *
     * @param direction to be set
     */
    public void setDirection(int direction)
    {
        this.direction=direction;
    }
    /**
     *
     * @param direction is the required direction
     * @param movement is the number of movements
     */
    public CreateListAttackable(int direction, int movement)
    {
        attackablecells= new ArrayList<Coordinate>();
        attackableplayers= new ArrayList<Player>();
        this.direction=direction;
        this.residualmovement=movement;
        this.viewer=null;
    }
    /**
     *
     * @return the list of attackable players
     */
    public List<Player> getAttackableplayers()
    {
        return this.attackableplayers;
    }

    /**
     *
     * @return the list of attackable cells
     */
    public List<Coordinate> getAttackablecells()
    {
        return this.attackablecells;
    }
    /**
     *
     * @param match is the match
     * @param attack is the type of attack
     * @param player1 is the given player
     */
    public void createlist(Match match, TypeAttack attack, Player player1)
    {
        this.attackableplayers= new ArrayList<Player>();
        this.attackablecells= new ArrayList<Coordinate>();
        List<Player> tocancelplayer = new ArrayList<Player>();
        List<Coordinate> tocancelcel = new ArrayList<Coordinate>();
        if(attack.getType()==1) //Caso in cui ho un finite distance
        {
            List<Player> players =new ArrayList<Player>();
            List<Coordinate> cells = new ArrayList<Coordinate>();
            players=match.getVisiblePlayers(player1);
            for(Player p : players)
            {
                if(match.getPlayersMD(player1,p)!=attack.getDistance())
                {
                    //players.discardWeapon(p);
                    tocancelplayer.add(p);
                }
            }
            reciclebeenp(players,tocancelplayer);
            this.attackableplayers=players;
            cells=match.getVisibleCells(player1.getCel());
            for(Coordinate c : cells)
            {
                if(match.getCellsMD(c,player1.getCel())!=attack.getDistance())
                {
                    //cells.discardWeapon(c);
                    tocancelcel.add(c);
                }
            }
            reciclebeenc(cells,tocancelcel);
            this.attackablecells=cells;
        }
        if(attack.getType()==2 || attack.getType()==10) //Caso in cui ho un undefined distance
        {
            List<Player> players =new ArrayList<Player>();
            List<Coordinate> cells = new ArrayList<Coordinate>();
            players=match.getVisiblePlayers(player1);
            cells=match.getVisibleCells(player1.getCel());
            this.attackablecells=cells;
            this.attackableplayers=players;
        }
        if(attack.getType()==3) //Caso in cui ho un more distance
        {
            List<Player> players =new ArrayList<Player>();
            List<Coordinate> cells = new ArrayList<Coordinate>();
            players=match.getVisiblePlayers(player1);
            for(Player p : players)
            {
                if(match.getPlayersMD(player1,p)<=attack.getDistance())
                {
                    //players.discardWeapon(p);
                    tocancelplayer.add(p);
                }
            }
            reciclebeenp(players,tocancelplayer);
            this.attackableplayers=players;
            cells=match.getVisibleCells(player1.getCel());
            for(Coordinate c : cells)
            {
                if(match.getCellsMD(c,player1.getCel())<attack.getDistance())
                {
                    //cells.discardWeapon(c);
                    tocancelcel.add(c);
                }
            }
            reciclebeenc(cells,tocancelcel);
            this.attackablecells=cells;
        }
        if(attack.getType()==4) //Caso in cui ho un cardinal
        {

            if(this.direction==0)
            {
                this.attackableplayers.addAll(match.getUpPlayers(player1));
                this.attackablecells.addAll(match.getUpCells(player1.getCel()));
            }
            if(this.direction==1)
            {
                this.attackableplayers.addAll(match.getRightPlayers(player1));
                this.attackablecells.addAll(match.getRightCells(player1.getCel()));
            }
            if(this.direction==2)
            {
                this.attackableplayers.addAll(match.getDownPlayers(player1));
                this.attackablecells.addAll(match.getDownCells(player1.getCel()));
            }
            if(this.direction==3)
            {
                this.attackableplayers.addAll(match.getLeftPlayers(player1));
                this.attackablecells.addAll(match.getLeftCells(player1.getCel()));
            }

        }
        if(attack.getType()==5) //Caso in cui ho un notseen
        {
            List<Player> players =new ArrayList<Player>();
            List<Coordinate> cells = new ArrayList<Coordinate>();
            List<Player> playersnotseen =new ArrayList<Player>();
            List<Coordinate> cellsnotseen = new ArrayList<Coordinate>();
            playersnotseen=match.getPlayers();
            for(Player p:playersnotseen)
            {
                if(!cellsnotseen.contains(p.getCel()))
                {
                    cellsnotseen.add(p.getCel());
                }
            }
            players=match.getVisiblePlayers(player1);
            cells=match.getVisibleCells(player1.getCel());
            for(Player p: playersnotseen)
            {
                if(players.contains(p))
                {
                    //playersnotseen.discardWeapon(p);
                    tocancelplayer.add(p);
                }
            }
            reciclebeenp(playersnotseen,tocancelplayer);
            for(Coordinate c:cellsnotseen)
            {
                if(cells.contains(c))
                {
                    //cellsnotseen.discardWeapon(c);
                    tocancelcel.add(c);
                }
            }
            reciclebeenc(cellsnotseen,tocancelcel);
            playersnotseen.remove(player1);
            cellsnotseen.remove(player1.getCel());

            this.attackablecells=cellsnotseen;
            this.attackableplayers=playersnotseen;
        }

        if(attack.getType()==6) //Caso in cui ho un while moving, considero tale azione prima di muovermi effettivamente
        {
            List<Player> players = new ArrayList<Player>();
            List<Coordinate> coordinates = new ArrayList<Coordinate>();
            if(this.direction==0)
            {
                players.addAll(match.getUpPlayers(player1));
                coordinates.addAll(match.getUpCells(player1.getCel()));
            }
            if(this.direction==1)
            {
                players.addAll(match.getRightPlayers(player1));
                coordinates.addAll(match.getRightCells(player1.getCel()));
            }
            if(this.direction==2)
            {
                players.addAll(match.getDownPlayers(player1));
                coordinates.addAll(match.getDownCells(player1.getCel()));
            }
            if(this.direction==3)
            {
                players.addAll(match.getLeftPlayers(player1));
                coordinates.addAll(match.getLeftCells(player1.getCel()));
            }
            for(Player p : players)
            {
                if(match.getPlayersMD(p,player1)!=1)
                {
                    //players.discardWeapon(p);
                    tocancelplayer.add(p);
                }
            }
            reciclebeenp(players,tocancelplayer);
            for(Coordinate c : coordinates)
            {
                if(match.getCellsMD(player1.getCel(),c)!=1)
                {
                    //coordinates.discardWeapon(c);
                    tocancelcel.add(c);
                }
            }
            reciclebeenc(coordinates,tocancelcel);
            this.attackableplayers=players;
            this.attackablecells=coordinates;
        }
        if(attack.getType()==7) //Caso in cui ho un all around
        {
            List<Player> players = match.getVisiblePlayers(player1);
            List<Coordinate> coordinates= match.getVisibleCells((player1.getCel()));
            List<Player> directed = new ArrayList<Player>();
            /*
            if(this.direction==0)
            {
                directed=match.getUpPlayers(player1);
            }
            if(direction==1)
            {
                directed=match.getRightPlayers(player1);
            }
            if(direction==2)
            {
                directed=match.getDownPlayers(player1);
            }
            if(direction==3)
            {
                directed=match.getLeftPlayers(player1);
            }
            */
            directed=players;
            for(Player p: players)
            {
                if(match.getPlayersMD(p,player1)!=1||!directed.contains(p))
                {
                    //players.discardWeapon(p);
                    tocancelplayer.add(p);
                }
            }

            reciclebeenp(players,tocancelplayer);
            for(Coordinate c : coordinates)
            {
                if(match.getCellsMD(player1.getCel(),c)!=1)
                {
                    //coordinates.discardWeapon(c);
                    tocancelcel.add(c);
                }
            }
            reciclebeenc(coordinates,tocancelcel);
            this.attackableplayers=players;
            this.attackablecells=coordinates;
        }
        if(attack.getType()==8) //Caso in cui ho un after moving
        {

            //TODO METODO CHE MI PERMETTE DI STABILIRE DOVE MI TROVERO' PER POI TROVARE I GIOCATORI VISIBILI
        }
        if(attack.getType()==9) //Caso in cui ho un all room
        {
            this.attackableplayers=(ArrayList<Player>) match.getVisiblePlayersByPort(player1,this.direction);
            for(Player p : this.attackableplayers)
            {
                this.attackablecells.add(p.getCel());
            }
            //TODO ASPETTARE METODO PER GIOCATORI IN STANZE SE PRESENZA DI PORTA
        }
        if(attack.getType()==10) //Caso in cui ho un ricorsive
        {
            if(this.viewer==null)
            {
                this.viewer=player1;
            }
            this.attackableplayers=match.getVisiblePlayers(this.viewer);
            this.attackablecells= match.getVisibleCells(this.viewer.getCel());
        }
        if(attack.getType()==11) //Caso in cui ho un in finite line
        {
            if(this.direction==0)
            {
                this.attackablecells=match.getUpCells(player1.getCel());
                this.attackableplayers= match.getUpPlayers(player1);
            }
            if(this.direction==1)
            {
                this.attackablecells=match.getRightCells(player1.getCel());
                this.attackableplayers= match.getRightPlayers(player1);
            }
            if(this.direction==2)
            {
                this.attackablecells=match.getDownCells(player1.getCel());
                this.attackableplayers= match.getDownPlayers(player1);
            }
            if(this.direction==3)
            {
                this.attackablecells=match.getLeftCells(player1.getCel());
                this.attackableplayers= match.getLeftPlayers(player1);
            }
            for(Player p : this.attackableplayers)
            {
                if(match.getPlayersMD(p,player1)>this.residualmovement)
                {
                    //this.attackableplayers.discardWeapon(p);
                    tocancelplayer.add(p);
                }
            }
            reciclebeenp(this.attackableplayers,tocancelplayer);
            for(Coordinate c : this.attackablecells)
            {
                if(match.getCellsMD(c,player1.getCel())>this.residualmovement)
                {
                    //this.attackablecells.discardWeapon(c);
                    tocancelcel.add(c);
                }
            }
            reciclebeenc(this.attackablecells,tocancelcel);

        }
        if(attack.getType()==12) //Caso in cui ho un moving to me
        {
            List<Player> players = match.getPlayers();
            List<Coordinate> visiblecoordinates = match.getVisibleCells(player1.getCel());
            this.attackableplayers= match.getVisiblePlayers(player1);
            for(Player p : players)
            {
                if(!this.attackableplayers.contains(p))
                {
                    for(Coordinate c : visiblecoordinates)
                    {
                        if(match.getCellsMD(c,p.getCel())<=attack.getMoveYou()&&!this.attackableplayers.contains(p)&&p!=player1)
                        {
                            this.attackableplayers.add(p);
                        }
                    }
                }
            }
        }

    }


    /**
     *
     * @param effective is the list of players
     * @param tocancel is the list of players to remove from the effective list
     */
    private void reciclebeenp (List<Player> effective, List<Player> tocancel)
    {
        for(Player p : tocancel)
        {
            effective.remove(p);
        }
        tocancel= new ArrayList<Player>();
    }

    /**
     *
     * @param effective is the list of coordinates
     * @param tocancel is the list of coordinates to remove from the effective list
     */
    private void reciclebeenc(List<Coordinate> effective, List<Coordinate> tocancel)
    {
        for(Coordinate c : tocancel)
        {
            effective.remove(c);
        }
        tocancel=new ArrayList<Coordinate>();
    }

}
