package Model;

import exceptions.FullCellException;
import exceptions.InvalidColorException;
import exceptions.MaxNumberPlayerException;
import exceptions.MaxNumberofCardsException;

import java.util.*;

public class Match {
    private int round;
    private ArrayList<Player> players;
    private Dashboard dashboard;
    private AmmoDeck ammodeck;
    private Weapon_Deck weapondeck;
    private Pow_Deck powdeck;
    private boolean checkdashboard =false;

    //i è parametro per la dashboard
    public Match(){
        this.round=1;
        this.players=new ArrayList<>();
        ammodeck =new AmmoDeck();
        weapondeck =new Weapon_Deck();
        powdeck =new Pow_Deck("Pow");
    }

    public void setround(){
        if(players.get(players.size()-1).getAction()==2) this.round++;         //TODO: lasciamo il controllo al controller e mettiamo direttamente il ++?
        //increase the number of the round just if the last player in the turn (that is the last of the array)
        //has done its second action, finished its turn
    }



    public int getround(){return this.round;}

    public void addplayer(Player player) throws MaxNumberPlayerException, InvalidColorException {
        if(players.size()==5) throw new MaxNumberPlayerException(); //max number of players in the classical mode
        for (Player p : this.players) {
            if (p.getcolor()==player.getcolor()) {
                throw new InvalidColorException();
            }
        }
        players.add(player);
    }

    //returns player by color
    public Player getplayer(int color) throws InvalidColorException {
        for (Player p : this.players) {
            if (p.getcolor()==color) {
                return p;
            }
        }
        throw new InvalidColorException();
    }

    //i is the index of the chosen map
    public int createdashboard(int i){
        if(players.size()>=3) {
            this.dashboard=new Dashboard(i);
            this.checkdashboard =true;
            return 0;
        }
        return 1;
    }

    public boolean getcheck(){
        return this.checkdashboard;
    }

    //returns player by index to check their ID
    public Player getplayerbyindex(int index){ return this.players.get(index);}

    public int getplayerssize(){ return this.players.size();}

    public Dashboard getDashboard(){return this.dashboard;}

    //TODO:Test
    //Riaggiunge la carta Ammo dopo che è stata usata
    public void addAmmoCard(NormalCell cell){//TODO:pensare a nome più efficace
        try{
            cell.Add_Ammo_Card((AmmoCard) ammodeck.drawCard());
        }catch(FullCellException e){
            //TODO
        }

    }
    public void addWeaponCard(SpawnPointCell cell, int index){//TODO:pensare a nome più efficace
        try{
            cell.Add_Weapon_Card((Weapon) weapondeck.drawCard(),index);//TODO:controllare
        }catch(FullCellException e){
            //TODO
        }

    }
    //Metodo per controller che mescola i mazzi (per esempio a inizio partita)
    public void shuffleAllDecks(){
        ammodeck.shuffleStack();
        weapondeck.shuffleStack();
        powdeck.shuffleStack();
    }
    //Method to assign powCard to player
    public void assignPowCard(Player player) throws MaxNumberofCardsException {     //TODO:Verificare se ha senso fare catch di una eccezione e poi rilanciarla
        PowCard powcard;
        powcard=(PowCard) powdeck.drawCard();
        try{
            player.addPow(powcard);
        }catch (MaxNumberofCardsException e){
            powdeck.discardCard(powcard);
            throw new MaxNumberofCardsException();
        }
    }

    //returns all the players seen by the given player
    public ArrayList<Player> getVisiblePlayers(Player player){
        int x;
        int y;
        int cellcolor;
        x = player.getCel().getX();
        y = player.getCel().getY();
        Cell cellplayer = this.getDashboard().getmap(x, y); //cell of the player
        cellcolor = cellplayer.getcolor(); //color of the cell of the player

        ArrayList<Player> visible = new ArrayList<>();

        //adds a player if it is in the same room
        for (Player p : this.players) {
            if (!p.equals(player)) {
                //color of the cell of the other player
                int otherplayercellcolor = p.getCel().inmap(this.dashboard, p.getCel().getX(), p.getCel().getY()).getcolor();
                if(otherplayercellcolor == cellcolor) visible.add(p);
            }
        }
        //adds a player if it is in a room connected to the parameter player by a port and the parameter player is across the port
        for(int i=0; i<4; i++){
            if(cellplayer.portIsPresent(i)==1){
                //north port
                if(i==0){
                    x--;
                    Cell othercell = this.getDashboard().getmap(x, y);
                    for (Player p : this.players) {
                        if (!p.equals(player) && !visible.contains(p)) {
                            //color of the cell of the other player
                            int otherplayercolor = p.getCel().inmap(this.dashboard, p.getCel().getX(), p.getCel().getY()).getcolor();
                            if(otherplayercolor==othercell.getcolor()) visible.add(p);
                        }
                    }
                    x++;
                }
                //east port
                else if(i==1){
                    y++;
                    Cell othercell = this.getDashboard().getmap(x, y);
                    for (Player p : this.players) {
                        if (!p.equals(player) && !visible.contains(p)) {
                            //color of the cell of the other player
                            int otherplayercolor = p.getCel().inmap(this.dashboard, p.getCel().getX(), p.getCel().getY()).getcolor();
                            if(otherplayercolor==othercell.getcolor()) visible.add(p);
                        }
                    }
                    y--;
                }
                //south port
                else if(i==2){
                    x++;
                    Cell othercell = this.getDashboard().getmap(x, y);
                    for (Player p : this.players) {
                        if (!p.equals(player) && !visible.contains(p)) {
                            //color of the cell of the other player
                            int otherplayercolor = p.getCel().inmap(this.dashboard, p.getCel().getX(), p.getCel().getY()).getcolor();
                            if(otherplayercolor==othercell.getcolor()) visible.add(p);
                        }
                    }
                    x--;
                }
                //west port
                else if(i==3){
                    y--;
                    Cell othercell = this.getDashboard().getmap(x, y);
                    for (Player p : this.players) {
                        if (!p.equals(player) && !visible.contains(p)) {
                            //color of the cell of the other player
                            int otherplayercolor = p.getCel().inmap(this.dashboard, p.getCel().getX(), p.getCel().getY()).getcolor();
                            if(otherplayercolor==othercell.getcolor()) visible.add(p);
                        }
                    }
                    y++;
                }
            }
        }
        return visible;
    }

    //returns the list of players in the same line of the given player
    public ArrayList<Player> getSameLinePlayers(Player player){
        ArrayList<Player> list = new ArrayList<>();
        int x = player.getCel().getX(); //player line
        for (Player p : this.players) {
            if (!p.equals(player)) {
                int otherplayerx = p.getCel().getX();
                if(otherplayerx == x) list.add(p);
            }
        }
        return list;
    }

    public ArrayList<Player> getSameColumnPlayers(Player player){
        ArrayList<Player> list = new ArrayList<>();
        int y = player.getCel().getY(); //player column
        for (Player p : this.players) {
            if (!p.equals(player)) {
                int otherplayery = p.getCel().getY();
                if(otherplayery == y) list.add(p);
            }
        }
        return list;
    }

    public int getPlayersMD(Player player1, Player player2){
        int distance=-1;
        int x1;
        int y1;
        int x2;
        int y2;
        x1 = player1.getCel().getX();
        y1 = player1.getCel().getY();
        x2 = player2.getCel().getX();
        y2 = player2.getCel().getY();
        if(x1==x2) {
            distance = Math.abs(y2-y1);
        }
        else if(y1==y2) {
            distance = Math.abs(x2-x1);
        }
        return distance;
    }

    public ArrayList<Coordinate> getVisibleCells(Coordinate cell) {
        int x;
        int y;
        int cellcolor;
        int othercellcolor;
        x = cell.getX();
        y = cell.getY();
        Cell cellplayer = this.getDashboard().getmap(x, y); //cell with that coordinates
        cellcolor = cellplayer.getcolor(); //color of the cell with that coordinates

        ArrayList<Coordinate> visible = new ArrayList<>();

        //adds a cell if it is in the same room
        for(int i=0; i<3; i++){
            for(int j=0; j<4; j++){
                othercellcolor = this.getDashboard().getmap(i, j).getcolor();
                if(othercellcolor==cellcolor){
                    Coordinate c = new Coordinate(i, j);
                    visible.add(c);
                }
            }
        }
        for(int k=0; k<visible.size(); k++){
            if(visible.get(k).getX()==x && visible.get(k).getY()==y){
                int index = visible.indexOf(visible.get(k));
                visible.remove(index);
            }
        }
        //adds cells if there is a port
        for(int i=0; i<4; i++){
            if(cellplayer.portIsPresent(i)==1){
                //north port
                if(i==0){
                    x--;
                    Cell portcell = this.getDashboard().getmap(x, y);
                    for(int h=0; h<3; h++){
                        for(int j=0; j<4; j++){
                            othercellcolor = this.getDashboard().getmap(h, j).getcolor();
                            if(othercellcolor == portcell.getcolor()){
                                Coordinate c = new Coordinate(h, j);
                                visible.add(c);
                            }
                        }
                    }
                    x++;
                }
                //east port
                else if(i==1){
                    y++;
                    Cell portcell = this.getDashboard().getmap(x, y);
                    for(int h=0; h<3; h++){
                        for(int j=0; j<4; j++){
                            othercellcolor = this.getDashboard().getmap(h, j).getcolor();
                            if(othercellcolor==portcell.getcolor()){
                                Coordinate c = new Coordinate(h, j);
                                visible.add(c);
                            }
                        }
                    }
                    y--;
                }
                //south port
                else if(i==2){
                    x++;
                    Cell portcell = this.getDashboard().getmap(x, y);
                    for(int h=0; h<3; h++){
                        for(int j=0; j<4; j++){
                            othercellcolor = this.getDashboard().getmap(h, j).getcolor();
                            if(othercellcolor==portcell.getcolor()){
                                Coordinate c = new Coordinate(h, j);
                                visible.add(c);
                            }
                        }
                    }
                    x--;
                }
                //west port
                else if(i==3){
                    y--;
                    Cell portcell = this.getDashboard().getmap(x, y);
                    for(int h=0; h<3; h++){
                        for(int j=0; j<4; j++){
                            othercellcolor = this.getDashboard().getmap(h, j).getcolor();
                            if(othercellcolor==portcell.getcolor()){
                                Coordinate c = new Coordinate(h, j);
                                visible.add(c);
                            }
                        }
                    }
                    y++;
                }
            }
        }
        return visible;
    }

    public ArrayList<Coordinate> getSameLineCells(Coordinate cell){
        ArrayList<Coordinate> list = new ArrayList<>();
        int x = cell.getX(); //cell column
        Coordinate c;
        for(int i=0; i<4; i++){
            if(i!=cell.getY()){
                c = new Coordinate(x, i);
                list.add(c);
            }
        }
        return list;
    }

    public ArrayList<Coordinate> getSameColumnCells(Coordinate cell){
        ArrayList<Coordinate> list = new ArrayList<>();
        int y = cell.getY(); //cell column
        Coordinate c;
        for(int i=0; i<3; i++){
            if(i!=cell.getX()){
                c = new Coordinate(i, y);
                list.add(c);
            }
        }
        return list;
    }

    public int getCellsMD(Coordinate cell1, Coordinate cell2){
        int distance=-1;
        int x1, y1, x2, y2;
        x1 = cell1.getX();
        y1 = cell1.getY();
        x2 = cell2.getX();
        y2 = cell2.getY();
        if(x1==x2) {
            distance = Math.abs(y2-y1);
        }
        else if(y1==y2) {
            distance = Math.abs(x2-x1);
        }
        return distance;
    }

    public ArrayList<Player> getRightPlayers(Player player){
        ArrayList<Player> list = new ArrayList<>();
        int x = player.getCel().getX(); //player line
        for (Player p : this.players) {
            if (!p.equals(player)) {
                int otherplayerX = p.getCel().getX();
                if(otherplayerX == x && p.getCel().getY()>player.getCel().getY()) list.add(p);
            }
        }
        return list;
    }

    public ArrayList<Player> getLeftPlayers(Player player){
        ArrayList<Player> list = new ArrayList<>();
        int x = player.getCel().getX(); //player line
        for (Player p : this.players) {
            if (!p.equals(player)) {
                int otherplayerX = p.getCel().getX();
                if(otherplayerX == x && p.getCel().getY()<player.getCel().getY()) list.add(p);
            }
        }
        return list;
    }

    public ArrayList<Player> getUpPlayers(Player player){
        ArrayList<Player> list = new ArrayList<>();
        int y = player.getCel().getY(); //player column
        for (Player p : this.players) {
            if (!p.equals(player)) {
                int otherplayerY = p.getCel().getY();
                if(otherplayerY == y && p.getCel().getX()<player.getCel().getX()) list.add(p);
            }
        }
        return list;
    }

    public ArrayList<Player> getDownPlayers(Player player){
        ArrayList<Player> list = new ArrayList<>();
        int y = player.getCel().getY(); //player column
        for (Player p : this.players) {
            if (!p.equals(player)) {
                int otherplayerY = p.getCel().getY();
                if(otherplayerY == y && p.getCel().getX()>player.getCel().getX()) list.add(p);
            }
        }
        return list;
    }

    public ArrayList<Player> getSameCellsPlayers(Coordinate cell){
        ArrayList<Player> list = new ArrayList<>();
        int Xcell = cell.getX();
        int Ycell = cell.getY();
        for (Player p : this.players) {
            int Xplayer = p.getCel().getX();
            int Yplayer = p.getCel().getY();
            if (Xcell == Xplayer && Ycell==Yplayer) { list.add(p); }
        }
        return list;
    }

    public ArrayList<Coordinate> getDownCells(Coordinate cell){
        ArrayList<Coordinate> list = new ArrayList<>();
        int x = cell.getX(); //cell line
        int y = cell.getY(); //cell column
        if(x<2){
            while(x<2){
                x++;
                Coordinate c = new Coordinate(x, y);
                list.add(c);
            }
        }
        return list;
    }

    public ArrayList<Coordinate> getUpCells(Coordinate cell){
        ArrayList<Coordinate> list = new ArrayList<>();
        int x = cell.getX(); //cell line
        int y = cell.getY(); //cell column
        if(x>0){
            while(x>0){
                x--;
                Coordinate c = new Coordinate(x, y);
                list.add(c);
            }
        }
        return list;
    }

    public ArrayList<Coordinate> getLeftCells(Coordinate cell){
        ArrayList<Coordinate> list = new ArrayList<>();
        int x = cell.getX(); //cell line
        int y = cell.getY(); //cell column
        if(y>0){
            while(y>0){
                y--;
                Coordinate c = new Coordinate(x, y);
                list.add(c);
            }
        }
        return list;
    }

    public ArrayList<Coordinate> getRightCells(Coordinate cell){
        ArrayList<Coordinate> list = new ArrayList<>();
        int x = cell.getX(); //cell line
        int y = cell.getY(); //cell column
        if(y<3){
            while(y<3){
                y++;
                Coordinate c = new Coordinate(x, y);
                list.add(c);
            }
        }
        return list;
    }


}
