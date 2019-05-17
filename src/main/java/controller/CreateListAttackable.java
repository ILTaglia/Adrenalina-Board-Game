package controller;

import model.*;

import java.util.ArrayList;

public class CreateListAttackable {
    private ArrayList<Player> attackableplayers;
    private ArrayList<Coordinate> attackablecells;

    public CreateListAttackable()
    {
        attackablecells= new ArrayList<Coordinate>();
        attackableplayers= new ArrayList<Player>();
    }

    public void createlist(Match match, TypeAttack attack, Player player1)
    {
        if(attack.getType()==1) //Caso in cui ho un finite distance
        {
            ArrayList<Player> players =new ArrayList<Player>();
            ArrayList<Coordinate> cells = new ArrayList<Coordinate>();
            players=match.getVisiblePlayers(player1);
            for(Player p : players)
            {
                if(match.getPlayersMD(player1,p)!=attack.getDistance())
                {
                    players.remove(p);
                }
            }
            this.attackableplayers=players;
            cells=match.getVisibleCells(player1.getCel());
            for(Coordinate c : cells)
            {
                if(match.getCellsMD(c,player1.getCel())!=attack.getDistance())
                {
                    cells.remove(c);
                }
            }
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
                if(match.getPlayersMD(player1,p)<attack.getDistance())
                {
                    players.remove(p);
                }
            }
            this.attackableplayers=players;
            cells=match.getVisibleCells(player1.getCel());
            for(Coordinate c : cells)
            {
                if(match.getCellsMD(c,player1.getCel())<attack.getDistance())
                {
                    cells.remove(c);
                }
            }
            this.attackablecells=cells;
        }
        if(attack.getType()==4) //Caso in cui ho un cardinal
        {
            //TODO Creare metodo con stesso nome ma paramentro lista in più, si fa poi il confronto con la lista precedente
        }
        if(attack.getType()==5) //Caso in cui ho un notseen
        {
            ArrayList<Player> players =new ArrayList<Player>();
            ArrayList<Coordinate> cells = new ArrayList<Coordinate>();
            ArrayList<Player> playersnotseen =new ArrayList<Player>();
            ArrayList<Coordinate> cellsnotseen = new ArrayList<Coordinate>();
            playersnotseen=match.getPlayers();
            //TODO aspetto metodo per tutte le celle possibili
            players=match.getVisiblePlayers(player1);
            cells=match.getVisibleCells(player1.getCel());
            for(Player p: playersnotseen)
            {
                if(players.contains(p))
                {
                    playersnotseen.remove(p);
                }
            }
            for(Coordinate c:cellsnotseen)
            {
                if(cells.contains(c))
                {
                    cellsnotseen.remove(c);
                }
            }

            this.attackablecells=cellsnotseen;
            this.attackableplayers=playersnotseen;
        }

        if(attack.getType()==6) //Caso in cui ho un while moving
        {
            //TODO BISOGNA RAGIONARE SU EVENTI PRECEDENTI COME CON CARDINAL
        }
        if(attack.getType()==7) //Caso in cui ho un all around
        {

        }
        if(attack.getType()==8) //Caso in cui ho un after moving
        {

        }
        if(attack.getType()==9) //Caso in cui ho un all room
        {

        }
        if(attack.getType()==10) //Caso in cui ho un ricorsive
        {

        }
        if(attack.getType()==11) //Caso in cui ho un in finite line
        {

        }
        if(attack.getType()==12) //Caso in cui ho un moving to me
        {


        }

    }


}
