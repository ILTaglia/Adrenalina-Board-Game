package model;

import exceptions.*;
import network.messages.InfoMatch;
import network.messages.InfoPlayer;
import network.messages.Message;

import java.io.Serializable;

import java.util.*;

import static utils.NotifyClient.notifyAllClients;
import static utils.NotifyClient.notifySpecificClient;

public class Match implements Serializable {
    private int round;
    private ArrayList<Player> players;
    private Dashboard dashboard;
    private AmmoDeck ammoDeck;
    private WeaponDeck weaponDeck;
    private PowDeck powDeck;
    private boolean checkDashboard =false;


    public Match(){
        this.round=1;
        this.players=new ArrayList<>();
        ammoDeck =new AmmoDeck();
        weaponDeck =new WeaponDeck();
        weaponDeck.setWeapons("Armi");
        powDeck =new PowDeck("Pow");
    }

    public void setRound(){
        this.round++;
        //Chiamo metodo per nuovo round.
        //if(players.get(players.size()-1).getAction()==2) this.round++;         //TODO: lasciamo il controllo al controller e mettiamo direttamente il ++?
        //increase the number of the round just if the last player in the turn (that is the last of the array)
        //has done its second action, finished its turn
    }

    public int getRound(){return this.round;}

    public void createPlayer(String name, String color, String id){
        Player player = new Player(name, color, id);
        addPlayer(player);
    }
    private void addPlayer(Player player) {
        players.add(player);
        Message message=new InfoPlayer("Assigned color: "+player.getcolor());
        notifySpecificClient(player.getid(),message);
        Message message1=new InfoPlayer("New Player in the Match, his name is"+ player.getname());
        notifyAllClients(this,message1);
    }

    //selectedDashboard is the index of the chosen map
    //TODO: Necessario che restituisca un int?
    public void createDashboard(int selectedDashboard){
        this.dashboard=new Dashboard(selectedDashboard);
        Message infoMap=new InfoMatch("La mappa selezionata è quella di indice: "+selectedDashboard+". Stampata di seguito:\n" );
        notifyAllClients(this,infoMap);
        /*if(players.size()>=3) {
            this.dashboard=new Dashboard(i);
            this.checkDashboard =true;
            //return 0;
        }
        return 1;*/
    }

    //returns player by color
    public Player getPlayer(int color) throws InvalidColorException {
        for (Player p : this.players) {
            if (p.getcolor()==color) {
                return p;
            }
        }
        throw new InvalidColorException();
    }

    //returns the active player
    public Player getActivePlayer(){
        for(Player p : this.players){
            if(p.getActive()) return p;
        }
        return null;
    }

    //Returns all the players on the match
    public ArrayList<Player> getPlayers()
    {
        return this.players;
    }

    public void fillDashboard(){
        if(this.checkDashboard) {
            int maptype = this.getDashboard().getMapType();

            weaponDeck.drawCard();
            Weapon weapon1 = (Weapon) weaponDeck.drawCard();
            Weapon weapon2 = (Weapon) weaponDeck.drawCard();
            Weapon weapon3 = (Weapon) weaponDeck.drawCard();
            Weapon weapon4 = (Weapon) weaponDeck.drawCard();
            Weapon weapon5 = (Weapon) weaponDeck.drawCard();
            Weapon weapon6 = (Weapon) weaponDeck.drawCard();
            Weapon weapon7 = (Weapon) weaponDeck.drawCard();
            Weapon weapon8 = (Weapon) weaponDeck.drawCard();
            Weapon weapon9 = (Weapon) weaponDeck.drawCard();
            SpawnPointCell c = (SpawnPointCell)this.getDashboard().getmap(0, 2);
            try{
                c.Add_Weapon_Card(weapon1, 0);
                c.Add_Weapon_Card(weapon2, 1);
                c.Add_Weapon_Card(weapon3, 2);
            } catch (FullCellException e){}
            c = (SpawnPointCell)this.getDashboard().getmap(1, 0);
            try{
                c.Add_Weapon_Card(weapon4, 0);
                c.Add_Weapon_Card(weapon5, 1);
                c.Add_Weapon_Card(weapon6, 2);
            } catch (FullCellException e){}
            c = (SpawnPointCell)this.getDashboard().getmap(2, 3);
            try{
                c.Add_Weapon_Card(weapon7, 0);
                c.Add_Weapon_Card(weapon8, 1);
                c.Add_Weapon_Card(weapon9, 2);
            } catch (FullCellException e){}

            NormalCell normalcell = (NormalCell)this.getDashboard().getmap(0, 0);
            this.addAmmoCard(normalcell);
            normalcell = (NormalCell)this.getDashboard().getmap(0, 1);
            this.addAmmoCard(normalcell);

            normalcell = (NormalCell)this.getDashboard().getmap(1, 1);
            this.addAmmoCard(normalcell);
            normalcell = (NormalCell)this.getDashboard().getmap(1, 2);
            this.addAmmoCard(normalcell);
            normalcell = (NormalCell)this.getDashboard().getmap(1, 3);
            this.addAmmoCard(normalcell);

            normalcell = (NormalCell)this.getDashboard().getmap(2, 1);
            this.addAmmoCard(normalcell);
            normalcell = (NormalCell)this.getDashboard().getmap(2, 2);
            this.addAmmoCard(normalcell);

            if(maptype!=1){
                normalcell = (NormalCell)this.getDashboard().getmap(0, 3);
                this.addAmmoCard(normalcell);
                if(maptype==3){
                    normalcell = (NormalCell)this.getDashboard().getmap(2, 0);
                    this.addAmmoCard(normalcell);
                }
            }

        }
    }

    public void firstTurnPows(){
        for(Player p:this.players){
            try{
                this.assignPowCard(p);
                this.assignPowCard(p);
            } catch(MaxNumberofCardsException e) {return;}
        }
    }



    public boolean getCheck(){
        return this.checkDashboard;
    }

    //returns player by index to check their ID
    public Player getPlayerByIndex(int index){ return this.players.get(index);}

    public int getPlayersSize(){ return this.players.size();}

    public Dashboard getDashboard(){return this.dashboard;}

    //TODO:Test
    //Riaggiunge la carta Ammo dopo che è stata usata
    public void addAmmoCard(NormalCell cell){//TODO:pensare a nome più efficace
        try{
            cell.Add_Ammo_Card((AmmoCard) ammoDeck.drawCard());
        }catch(FullCellException e){
            //TODO
        }

    }
    public void addWeaponCard(SpawnPointCell cell, int index){//TODO:pensare a nome più efficace
        try{
            Weapon weapon = (Weapon) weaponDeck.drawCard();
            cell.Add_Weapon_Card(weapon,index);//TODO:controllare
        }catch(FullCellException e){
            //TODO
        }
    }

    public void removeAmmo(int numberAmmos, Ammo ammo)throws NotEnoughAmmosException {
        Player player = this.getActivePlayer();
        player.removeAmmo(numberAmmos, ammo);
    }

    public void setWeaponCard(SpawnPointCell cell, int index){
        Weapon weapon = (Weapon) weaponDeck.drawCard();
        cell.SetWeaponCard(weapon, index);
    }


    //Metodo per controller che mescola i mazzi (per esempio a inizio partita)
    public void shuffleAllDecks(){
        ammoDeck.shuffleStack();
        weaponDeck.shuffleStack();
        powDeck.shuffleStack();
    }
    //Method to assign powCard to player
    public void assignPowCard(Player player) throws MaxNumberofCardsException {     //TODO:Verificare se ha senso fare catch di una eccezione e poi rilanciarla
        PowCard powcard;
        powcard=(PowCard) powDeck.drawCard();
        try{
            player.addPow(powcard);
        }catch (MaxNumberofCardsException e){
            powDeck.discardCard(powcard);
            throw new MaxNumberofCardsException();
        }
    }
    public void discardPowCard(PowCard powCard){
        powDeck.discardCard(powCard);
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

    //Method that returns visible players by a port
    public List<Player> getVisiblePlayersByPort(Player player, int direction) {
        List<Player> visibleplayers = new ArrayList<>();
        int x = player.getCel().getX();
        int y = player.getCel().getY();
        Cell playercell = this.getDashboard().getmap(x, y);
        //north
        if(direction==0){
            ArrayList<Coordinate> upcells = this.getUpCells(player.getCel());
            for(int i=0; i<upcells.size(); i++){
                int x1 = upcells.get(i).getX();
                int y1 = upcells.get(i).getY();
                Cell c = this.getDashboard().getmap(x1, y1);
                //you are looking up, so you need a cell that has a port in southern directon
                if(c.portIsPresent(2)==1){
                    Coordinate cell = new Coordinate(x1, y1);
                    visibleplayers = this.getRoomPlayers(cell);
                    return visibleplayers;
                }
            }
        }
        //east
        else if(direction==1) {
            ArrayList<Coordinate> rightcells = this.getRightCells(player.getCel());
            for(int i=0; i<rightcells.size(); i++){
                int x1 = rightcells.get(i).getX();
                int y1 = rightcells.get(i).getY();
                Cell c = this.getDashboard().getmap(x1, y1);
                //you are looking east, so you need a cell that has a port in western direction
                if(c.portIsPresent(3)==1){
                    Coordinate cell = new Coordinate(x1, y1);
                    visibleplayers = this.getRoomPlayers(cell);
                    return visibleplayers;
                }
            }
        }
        //south
        else if(direction==2) {
            ArrayList<Coordinate> downcells = this.getDownCells(player.getCel());
            for(int i=0; i<downcells.size(); i++){
                int x1 = downcells.get(i).getX();
                int y1 = downcells.get(i).getY();
                Cell c = this.getDashboard().getmap(x1, y1);
                //you are looking down, so you need a cell that has a port in northern direction
                if(c.portIsPresent(0)==1){
                    Coordinate cell = new Coordinate(x1, y1);
                    visibleplayers = this.getRoomPlayers(cell);
                    return visibleplayers;
                }
            }
        }
        //west
        else if(direction==3) {
            ArrayList<Coordinate> leftcells = this.getLeftCells(player.getCel());
            for(int i=0; i<leftcells.size(); i++){
                int x1 = leftcells.get(i).getX();
                int y1 = leftcells.get(i).getY();
                Cell c = this.getDashboard().getmap(x1, y1);
                //you are looking west, so you need a cell that has a port in eastern direction
                if(c.portIsPresent(1)==1){
                    Coordinate cell = new Coordinate(x1, y1);
                    visibleplayers = this.getRoomPlayers(cell);
                    return visibleplayers;
                }
            }
        }

        return visibleplayers;
    }

    //Method to have players in a room
    private List<Player> getRoomPlayers(Coordinate cell){
        List<Player> roomplayers = new ArrayList<>();
        int x = cell.getX();
        int y = cell.getY();
        Cell c = this.getDashboard().getmap(x, y); //cell
        int cellcolor = c.getcolor(); //color of the cell

        //adds a player if it is in the same room
        for (Player p : this.players) {
            int playercellcolor = p.getCel().inmap(this.dashboard, p.getCel().getX(), p.getCel().getY()).getcolor();
            if(playercellcolor==cellcolor) roomplayers.add(p);
        }
        return roomplayers;
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
        int x1;
        int y1;
        int x2;
        int y2;
        x1 = cell1.getX();
        y1 = cell1.getY();
        x2 = cell2.getX();
        y2 = cell2.getY();
        distance = Math.abs(y2-y1);
        distance = distance+ Math.abs(x2-x1);

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
        int xCell = cell.getX();
        int yCell = cell.getY();
        for (Player p : this.players) {
            int xPlayer = p.getCel().getX();
            int yPlayer = p.getCel().getY();
            if (xCell == xPlayer && yCell==yPlayer) { list.add(p); }
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
