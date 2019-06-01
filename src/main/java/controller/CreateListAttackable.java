package controller;

import model.*;

import java.util.ArrayList;

public class CreateListAttackable {
    private ArrayList<Player> attackableplayers;
    private ArrayList<Coordinate> attackablecells;
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

    public void setViewer(Player viewer)
    {
        this.viewer=viewer;
    }

    public void setDirection(int direction)
    {
        this.direction=direction;
    }

    public CreateListAttackable(int direction, int movement)
    {
        attackablecells= new ArrayList<Coordinate>();
        attackableplayers= new ArrayList<Player>();
        this.direction=direction;
        this.residualmovement=movement;
        this.viewer=null;
    }


    public ArrayList<Player> getAttackableplayers()
    {
        return this.attackableplayers;
    }

    public ArrayList<Coordinate> getAttackablecells()
    {
        return this.attackablecells;
    }


    public void createlist(Match match, TypeAttack attack, Player player1)
    {
        this.attackableplayers= new ArrayList<Player>();
        this.attackablecells= new ArrayList<Coordinate>();
        ArrayList<Player> tocancelplayer = new ArrayList<Player>();
        ArrayList<Coordinate> tocancelcel = new ArrayList<Coordinate>();
        if(attack.getType()==1) //Caso in cui ho un finite distance
        {
            ArrayList<Player> players =new ArrayList<Player>();
            ArrayList<Coordinate> cells = new ArrayList<Coordinate>();
            players=match.getVisiblePlayers(player1);
            for(Player p : players)
            {
                if(match.getPlayersMD(player1,p)!=attack.getDistance())
                {
                    //players.remove(p);
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
                    //cells.remove(c);
                    tocancelcel.add(c);
                }
            }
            reciclebeenc(cells,tocancelcel);
            this.attackablecells=cells;
        }
        if(attack.getType()==2 || attack.getType()==10) //Caso in cui ho un undefined distance
        {
            ArrayList<Player> players =new ArrayList<Player>();
            ArrayList<Coordinate> cells = new ArrayList<Coordinate>();
            players=match.getVisiblePlayers(player1);
            cells=match.getVisibleCells(player1.getCel());
            this.attackablecells=cells;
            this.attackableplayers=players;
        }
        if(attack.getType()==3) //Caso in cui ho un more distance
        {
            ArrayList<Player> players =new ArrayList<Player>();
            ArrayList<Coordinate> cells = new ArrayList<Coordinate>();
            players=match.getVisiblePlayers(player1);
            for(Player p : players)
            {
                if(match.getPlayersMD(player1,p)<=attack.getDistance())
                {
                    //players.remove(p);
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
                    //cells.remove(c);
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
            ArrayList<Player> players =new ArrayList<Player>();
            ArrayList<Coordinate> cells = new ArrayList<Coordinate>();
            ArrayList<Player> playersnotseen =new ArrayList<Player>();
            ArrayList<Coordinate> cellsnotseen = new ArrayList<Coordinate>();
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
                    //playersnotseen.remove(p);
                    tocancelplayer.add(p);
                }
            }
            reciclebeenp(playersnotseen,tocancelplayer);
            for(Coordinate c:cellsnotseen)
            {
                if(cells.contains(c))
                {
                    //cellsnotseen.remove(c);
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
            ArrayList<Player> players = new ArrayList<Player>();
            ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
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
                    //players.remove(p);
                    tocancelplayer.add(p);
                }
            }
            reciclebeenp(players,tocancelplayer);
            for(Coordinate c : coordinates)
            {
                if(match.getCellsMD(player1.getCel(),c)!=1)
                {
                    //coordinates.remove(c);
                    tocancelcel.add(c);
                }
            }
            reciclebeenc(coordinates,tocancelcel);
            this.attackableplayers=players;
            this.attackablecells=coordinates;
        }
        if(attack.getType()==7) //Caso in cui ho un all around
        {
            ArrayList<Player> players = match.getVisiblePlayers(player1);
            ArrayList<Coordinate> coordinates= match.getVisibleCells((player1.getCel()));
            ArrayList<Player> directed = new ArrayList<Player>();
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
            for(Player p: players)
            {
                if(match.getPlayersMD(p,player1)!=1||!directed.contains(p))
                {
                    //players.remove(p);
                    tocancelplayer.add(p);
                }
            }
            reciclebeenp(players,tocancelplayer);
            for(Coordinate c : coordinates)
            {
                if(match.getCellsMD(player1.getCel(),c)!=1)
                {
                    //coordinates.remove(c);
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
                    //this.attackableplayers.remove(p);
                    tocancelplayer.add(p);
                }
            }
            reciclebeenp(this.attackableplayers,tocancelplayer);
            for(Coordinate c : this.attackablecells)
            {
                if(match.getCellsMD(c,player1.getCel())>this.residualmovement)
                {
                    //this.attackablecells.remove(c);
                    tocancelcel.add(c);
                }
            }
            reciclebeenc(this.attackablecells,tocancelcel);

        }
        if(attack.getType()==12) //Caso in cui ho un moving to me
        {
            ArrayList<Player> players = match.getPlayers();
            ArrayList<Coordinate> visiblecoordinates = match.getVisibleCells(player1.getCel());
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


    private void reciclebeenp (ArrayList<Player> effective, ArrayList<Player> tocancel)
    {
        for(Player p : tocancel)
        {
            effective.remove(p);
        }
        tocancel= new ArrayList<Player>();
    }

    private void reciclebeenc(ArrayList<Coordinate> effective, ArrayList<Coordinate> tocancel)
    {
        for(Coordinate c : tocancel)
        {
            effective.remove(c);
        }
        tocancel=new ArrayList<Coordinate>();
    }

}
