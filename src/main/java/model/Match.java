package model;

import exceptions.*;
import network.messages.playerDataMessage.*;
import network.messages.Message;

import java.io.Serializable;

import java.util.*;

import static utils.NotifyClient.*;

public class Match implements Serializable {
    private int round;
    private ArrayList<Player> players;
    private Dashboard dashboard;
    private AmmoDeck ammoDeck;
    private WeaponDeck weaponDeck;
    private PowDeck powDeck;
    private boolean checkDashboard = false;


    public Match() {
        this.round = 1;
        this.players = new ArrayList<>();
        ammoDeck = new AmmoDeck();
        weaponDeck = new WeaponDeck();
        weaponDeck.setWeapons("Armi");
        powDeck = new PowDeck("Pow");
    }

    public void setRound() {
        this.round++;
        //increase the number of the round just if the last player in the turn (that is the last of the array)
        //has done its second action, finished its turn
    }

    public int getRound() {
        return this.round;
    }

    //---------------------------------Metodi inizializzazione Player-------------------------------------------------//

    //Alla creazione del Player istanzio la relativa PlayerData che mando al Client
    //Ciascun Client quindi avrà la classe con la classe Player già inizializzata.
    //In seguito la classe va aggiornata con il nome dei Player avversari
    public void createPlayer(String name, String color, String id) {
        Player player = new Player(name, color, id);
        addPlayer(player);
    }

    private void addPlayer(Player player) {
        players.add(player);
        Message message = new NewPlayerData(new PlayerVisibleData(player));
        notifySpecificClient(player.getID(), message);
    }

    public void notifyOfOtherPlayers() {
        for (Player player : players) {
            notifyAllExceptOneClient(player.getID(), new OtherPlayerData(player.getName(), player.getColor()));
        }
    }
    //________________________________________________________________________________________________________________//

    //---------------------------------Metodi inizializzazione Dashboard----------------------------------------------//

    //selectedDashboard is the index of the chosen map
    public void createDashboard(int selectedDashboard) {
        this.dashboard = new Dashboard(selectedDashboard);
        checkDashboard = true;
    }

    public void fillDashboard() {
        if (this.checkDashboard) {
            int indexR;
            int indexC;
            for (indexR = 0; indexR < dashboard.getRowDim(); indexR++) {
                for (indexC = 0; indexC < dashboard.getColDim(); indexC++) {
                    Cell cell = this.getDashboard().getMap(indexR, indexC);
                    if ((cell.getType() == 0) && (cell.getColor() != -1)) {             //spawn Cell & exists
                        fillSpawnPoint((SpawnPointCell) cell);
                    } else if ((cell.getType() == 1) && (cell.getColor() != -1)) {      //normal Cell & exists
                        fillNormal((NormalCell) cell);
                    }
                }
            }
        }
        updateClientDashboard();
    }
    public void notifyNewMap() {
        InfoMatch message=new InfoMatch("Selected map "+ dashboard.getMapType() + " . Stampo di seguito:");
        notifyAllClients(this,message);
    }
    private void fillSpawnPoint(SpawnPointCell cell) {
            for (int index = 0; index < 3; index++) {
                try {
                    if (cell.getSpawnPointCellWeapons().get(index) == null) {
                        cell.setWeaponCard((Weapon) weaponDeck.drawCard(), index);
                    }
                }catch (IndexOutOfBoundsException e){
                    try {
                        cell.addWeaponCard((Weapon) weaponDeck.drawCard(), index);
                    } catch (FullCellException e1) {
                        //Nothing to do
                    }
                }
            }
            //This method fill the dashboard for the first time, this Exception is impossible
    }


    private void fillNormal(NormalCell cell) {
        try {
            if(cell.getAmmoCard()==null){
                cell.addAmmoCard((AmmoCard) ammoDeck.drawCard());
            }
            else if(cell.getAmmoCard().getStatus()) {
                ammoDeck.discardCard(cell.getAmmoCard());
                cell.addAmmoCard((AmmoCard) ammoDeck.drawCard());
            }
        } catch (FullCellException e) {
            //This method fill the dashboard for the first time, this Exception is impossible
        }
    }

    public void updateClientDashboard(){
        Message infoMap = new DashboardData(this.dashboard);
        notifyAllClients(this, infoMap);
    }
    public void updateEndAction() {
        this.updateClientDashboard();

    }
    public void updateEndTurn() {
        this.fillDashboard();
        this.updateClientDashboard();
        this.round++;
    }



    //TODO: messaggi per Match!!!!
    public void assignScore(List<Integer> score){
        for(int i=0; i<score.size(); i++){getPlayer(i).setScore(score.get(i));}
    }

    //________________________________________________________________________________________________________________//
    //metodo inizializzazione PowCard Player
    public void firstTurnPow() {
        for (Player player : this.players) {
            try {
                this.assignPowCard(player);
                this.assignPowCard(player);
            } catch (MaxNumberofCardsException e) {
                //Nothing to do in this case, this method is called at the beginning of the match.
            }
        }
    }

    public void spawnPlayer(Player player,int indexPowCard, int x, int y) {
        player.setCel(x, y);
        PowCard powCard=player.getPowByIndex(indexPowCard);
        try{
            player.removePow(powCard);       //TODO: perchè non una removeByIndex per i Pow che restituisce la carta rimossa?
            powDeck.discardCard(powCard);
        }
        catch(ZeroCardsOwnedException | NotOwnedCardException e){}
        Message infoUsedCard=new NewCardUsed("PowCard",indexPowCard);
        notifySpecificClient(player.getID(),infoUsedCard);
        Message infoSpawnPoint=new NewPosition(x,y);
        notifySpecificClient(player.getID(),infoSpawnPoint);
    }

    //Method to assign powCard to player
    public void assignPowCard(Player player) throws MaxNumberofCardsException {     //TODO:Verificare se ha senso fare catch di una eccezione e poi rilanciarla
        PowCard powcard;
        powcard = (PowCard) powDeck.drawCard();
        try {
            player.addPow(powcard);
        } catch (MaxNumberofCardsException e) {
            powDeck.discardCard(powcard);
            throw new MaxNumberofCardsException();
        }
        Message infoPowCard = new NewPowCard(powcard);
        notifySpecificClient(player.getID(), infoPowCard);
    }

    public void assignWeaponCard(Player player, int indexWeapon) throws MaxNumberofCardsException {
        SpawnPointCell cell;
        cell = (SpawnPointCell) player.getCel().inmap(this.getDashboard(), player.getCel().getX(), player.getCel().getY());
        Weapon weaponCard = (Weapon) cell.collectWeapon(indexWeapon);
        player.addWeapon(weaponCard);
        Message infoWeaponCard = new NewWeaponCard(weaponCard);
        notifySpecificClient(player.getID(), infoWeaponCard);
    }

    public void assignAmmo(Player player) throws CardAlreadyCollectedException {
        NormalCell cell;
        cell = (NormalCell) player.getCel().inmap(this.getDashboard(), player.getCel().getX(), player.getCel().getY());
        try {
            cell.collectCard(player);
        } catch (MoreThanTreeAmmosException e) {
            //TODO: avviso il player
        }
        List<Integer> ammo=new ArrayList<>();
        for(int i=0;i<3;i++){
            ammo.add(i,player.getAmmo(i));
        }
        Message infoAmmo=new NewAmmo(ammo);
        notifySpecificClient(player.getID(),infoAmmo);
    }

    public void setPlayerCel(Player player, int x, int y) {
        player.setCel(x, y);
        Message infoSpawnPoint=new NewPosition(x,y);
        notifySpecificClient(player.getID(),infoSpawnPoint);
    }


    //returns player by color
    public Player getPlayer(int color) throws InvalidColorException {
        for (Player p : this.players) {
            if (p.getColor() == color) {
                return p;
            }
        }
        throw new InvalidColorException();
    }

    //returns the active player
    public Player getActivePlayer() {
        for (Player p : this.players) {
            if (p.getActive()) return p;
        }
        return null;
    }

    public Player getPlayerByID(String userID) {
        for (Player player : this.players) {
            if (player.getID().equals(userID)) {
                return player;
            }
        }
        return null;
    }

    //Returns all the players on the match
    public List<Player> getPlayers() {
        return this.players;
    }

    public List<Player> getNoDamagedPlayers() {
        List<Player> list = new ArrayList<>();
        for (Player p : this.players) {
            if (p.getTotalDamage() == 0) list.add(p);
        }
        return list;
    }
    
    public boolean getCheck() {
        return this.checkDashboard;
    }

    //returns player by index to check their ID
    public Player getPlayerByIndex(int index) {
        return this.players.get(index);
    }

    public int getPlayersSize() {
        return this.players.size();
    }

    public Dashboard getDashboard() {
        return this.dashboard;
    }

    //TODO:Test
    //Riaggiunge la carta Ammo dopo che è stata usata
    public void addAmmoCard(NormalCell cell) {
        try {
            cell.addAmmoCard((AmmoCard) ammoDeck.drawCard());
        } catch (FullCellException e) {
            //TODO
        }

    }

    public void addWeaponCard(SpawnPointCell cell, int index) {
        try {
            Weapon weapon = (Weapon) weaponDeck.drawCard();
            cell.addWeaponCard(weapon, index);              //TODO:controllare
        } catch (FullCellException e) {
            //TODO
        }
    }

    public void removeAmmo(int numberAmmo, Ammo ammo) throws NotEnoughAmmosException {
        Player player = this.getActivePlayer();
        player.removeAmmo(numberAmmo, ammo);
    }

    public void setWeaponCard(SpawnPointCell cell, int index) {
        Weapon weapon = (Weapon) weaponDeck.drawCard();
        cell.setWeaponCard(weapon, index);
    }

    //Metodo per controller che mescola i mazzi (per esempio a inizio partita)
    public void shuffleAllDecks() {
        ammoDeck.shuffleStack();
        weaponDeck.shuffleStack();
        powDeck.shuffleStack();
    }


    public void discardPowCard(PowCard powCard) {
        powDeck.discardCard(powCard);
    }

    public void discardWeaponCard(Weapon weapon) {
        weaponDeck.discardCard(weapon);
    }

    //________________________________________________________________________________________________________________//

    //---------------------------------Metodi utili per la Shoot------------------------------------------------------//

    //returns all the players seen by the given player
    public List<Player> getVisiblePlayers(Player player) {
        int x;
        int y;
        int cellColor;
        x = player.getCel().getX();
        y = player.getCel().getY();
        Cell cellplayer = this.getDashboard().getMap(x, y); //cell of the player
        cellColor = cellplayer.getColor(); //color of the cell of the player

        List<Player> visible = new ArrayList<>();

        //adds a player if it is in the same room
        for (Player p : this.players) {
            if (!p.equals(player)) {
                //color of the cell of the other player
                int otherplayercellcolor = p.getCel().inmap(this.dashboard, p.getCel().getX(), p.getCel().getY()).getColor();
                if (otherplayercellcolor == cellColor) visible.add(p);
            }
        }
        //adds a player if it is in a room connected to the parameter player by a port and the parameter player is across the port
        for (int i = 0; i < 4; i++) {
            if (cellplayer.portIsPresent(i) == 1) {
                //north port
                if (i == 0) {
                    x--;
                    Cell othercell = this.getDashboard().getMap(x, y);
                    for (Player p : this.players) {
                        if (!p.equals(player) && !visible.contains(p)) {
                            //color of the cell of the other player
                            int otherplayercolor = p.getCel().inmap(this.dashboard, p.getCel().getX(), p.getCel().getY()).getColor();
                            if (otherplayercolor == othercell.getColor()) visible.add(p);
                        }
                    }
                    x++;
                }
                //east port
                else if (i == 1) {
                    y++;
                    Cell othercell = this.getDashboard().getMap(x, y);
                    for (Player p : this.players) {
                        if (!p.equals(player) && !visible.contains(p)) {
                            //color of the cell of the other player
                            int otherplayercolor = p.getCel().inmap(this.dashboard, p.getCel().getX(), p.getCel().getY()).getColor();
                            if (otherplayercolor == othercell.getColor()) visible.add(p);
                        }
                    }
                    y--;
                }
                //south port
                else if (i == 2) {
                    x++;
                    Cell othercell = this.getDashboard().getMap(x, y);
                    for (Player p : this.players) {
                        if (!p.equals(player) && !visible.contains(p)) {
                            //color of the cell of the other player
                            int otherplayercolor = p.getCel().inmap(this.dashboard, p.getCel().getX(), p.getCel().getY()).getColor();
                            if (otherplayercolor == othercell.getColor()) visible.add(p);
                        }
                    }
                    x--;
                }
                //west port
                else if (i == 3) {
                    y--;
                    Cell othercell = this.getDashboard().getMap(x, y);
                    for (Player p : this.players) {
                        if (!p.equals(player) && !visible.contains(p)) {
                            //color of the cell of the other player
                            int otherplayercolor = p.getCel().inmap(this.dashboard, p.getCel().getX(), p.getCel().getY()).getColor();
                            if (otherplayercolor == othercell.getColor()) visible.add(p);
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
        //north
        if (direction == 0) {
            List<Coordinate> upcells = this.getUpCells(player.getCel());
            for (int i = 0; i < upcells.size(); i++) {
                int x1 = upcells.get(i).getX();
                int y1 = upcells.get(i).getY();
                Cell c = this.getDashboard().getMap(x1, y1);
                //you are looking up, so you need a cell that has a port in southern directon
                if (c.portIsPresent(2) == 1) {
                    Coordinate cell = new Coordinate(x1, y1);
                    visibleplayers = this.getRoomPlayers(cell);
                    return visibleplayers;
                }
            }
        }
        //east
        else if (direction == 1) {
            List<Coordinate> rightcells = this.getRightCells(player.getCel());
            for (int i = 0; i < rightcells.size(); i++) {
                int x1 = rightcells.get(i).getX();
                int y1 = rightcells.get(i).getY();
                Cell c = this.getDashboard().getMap(x1, y1);
                //you are looking east, so you need a cell that has a port in western direction
                if (c.portIsPresent(3) == 1) {
                    Coordinate cell = new Coordinate(x1, y1);
                    visibleplayers = this.getRoomPlayers(cell);
                    return visibleplayers;
                }
            }
        }
        //south
        else if (direction == 2) {
            List<Coordinate> downcells = this.getDownCells(player.getCel());
            for (int i = 0; i < downcells.size(); i++) {
                int x1 = downcells.get(i).getX();
                int y1 = downcells.get(i).getY();
                Cell c = this.getDashboard().getMap(x1, y1);
                //you are looking down, so you need a cell that has a port in northern direction
                if (c.portIsPresent(0) == 1) {
                    Coordinate cell = new Coordinate(x1, y1);
                    visibleplayers = this.getRoomPlayers(cell);
                    return visibleplayers;
                }
            }
        }
        //west
        else if (direction == 3) {
            List<Coordinate> leftcells = this.getLeftCells(player.getCel());
            for (int i = 0; i < leftcells.size(); i++) {
                int x1 = leftcells.get(i).getX();
                int y1 = leftcells.get(i).getY();
                Cell c = this.getDashboard().getMap(x1, y1);
                //you are looking west, so you need a cell that has a port in eastern direction
                if (c.portIsPresent(1) == 1) {
                    Coordinate cell = new Coordinate(x1, y1);
                    visibleplayers = this.getRoomPlayers(cell);
                    return visibleplayers;
                }
            }
        }

        return visibleplayers;
    }

    //Method to have players in a room
    private List<Player> getRoomPlayers(Coordinate cell) {
        List<Player> roomplayers = new ArrayList<>();
        int x = cell.getX();
        int y = cell.getY();
        Cell c = this.getDashboard().getMap(x, y); //cell
        int cellcolor = c.getColor(); //color of the cell

        //adds a player if it is in the same room
        for (Player p : this.players) {
            int playercellcolor = p.getCel().inmap(this.dashboard, p.getCel().getX(), p.getCel().getY()).getColor();
            if (playercellcolor == cellcolor) roomplayers.add(p);
        }
        return roomplayers;
    }

    //returns the list of players in the same line of the given player
    public List<Player> getSameLinePlayers(Player player) {
        List<Player> list = new ArrayList<>();
        int x = player.getCel().getX(); //player line
        for (Player p : this.players) {
            if (!p.equals(player)) {
                int otherplayerx = p.getCel().getX();
                if (otherplayerx == x) list.add(p);
            }
        }
        return list;
    }

    public List<Player> getSameColumnPlayers(Player player) {
        List<Player> list = new ArrayList<>();
        int y = player.getCel().getY(); //player column
        for (Player p : this.players) {
            if (!p.equals(player)) {
                int otherplayery = p.getCel().getY();
                if (otherplayery == y) list.add(p);
            }
        }
        return list;
    }

    public int getPlayersMD(Player player1, Player player2) {
        int distance = -1;
        int x1;
        int y1;
        int x2;
        int y2;
        x1 = player1.getCel().getX();
        y1 = player1.getCel().getY();
        x2 = player2.getCel().getX();
        y2 = player2.getCel().getY();
        if (x1 == x2) {
            distance = Math.abs(y2 - y1);
        } else if (y1 == y2) {
            distance = Math.abs(x2 - x1);
        }
        return distance;
    }

    public List<Coordinate> getVisibleCells(Coordinate cell) {
        int x;
        int y;
        int cellcolor;
        int othercellcolor;
        x = cell.getX();
        y = cell.getY();
        Cell cellplayer = this.getDashboard().getMap(x, y); //cell with that coordinates
        cellcolor = cellplayer.getColor(); //color of the cell with that coordinates

        List<Coordinate> visible = new ArrayList<>();

        //adds a cell if it is in the same room
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                othercellcolor = this.getDashboard().getMap(i, j).getColor();
                if (othercellcolor == cellcolor) {
                    Coordinate c = new Coordinate(i, j);
                    visible.add(c);
                }
            }
        }

        //adds cells if there is a port
        for (int i = 0; i < 4; i++) {
            if (cellplayer.portIsPresent(i) == 1) {
                //north port
                if (i == 0) {
                    x--;
                    Cell portcell = this.getDashboard().getMap(x, y);
                    for (int h = 0; h < 3; h++) {
                        for (int j = 0; j < 4; j++) {
                            othercellcolor = this.getDashboard().getMap(h, j).getColor();
                            if (othercellcolor == portcell.getColor()) {
                                Coordinate c = new Coordinate(h, j);
                                visible.add(c);
                            }
                        }
                    }
                    x++;
                }
                //east port
                else if (i == 1) {
                    y++;
                    Cell portcell = this.getDashboard().getMap(x, y);
                    for (int h = 0; h < 3; h++) {
                        for (int j = 0; j < 4; j++) {
                            othercellcolor = this.getDashboard().getMap(h, j).getColor();
                            if (othercellcolor == portcell.getColor()) {
                                Coordinate c = new Coordinate(h, j);
                                visible.add(c);
                            }
                        }
                    }
                    y--;
                }
                //south port
                else if (i == 2) {
                    x++;
                    Cell portcell = this.getDashboard().getMap(x, y);
                    for (int h = 0; h < 3; h++) {
                        for (int j = 0; j < 4; j++) {
                            othercellcolor = this.getDashboard().getMap(h, j).getColor();
                            if (othercellcolor == portcell.getColor()) {
                                Coordinate c = new Coordinate(h, j);
                                visible.add(c);
                            }
                        }
                    }
                    x--;
                }
                //west port
                else if (i == 3) {
                    y--;
                    Cell portcell = this.getDashboard().getMap(x, y);
                    for (int h = 0; h < 3; h++) {
                        for (int j = 0; j < 4; j++) {
                            othercellcolor = this.getDashboard().getMap(h, j).getColor();
                            if (othercellcolor == portcell.getColor()) {
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

    public List<Coordinate> getSameLineCells(Coordinate cell) {
        List<Coordinate> list = new ArrayList<>();
        int x = cell.getX(); //cell column
        Coordinate c;
        for (int i = 0; i < 4; i++) {
            if (i != cell.getY()) {
                c = new Coordinate(x, i);
                list.add(c);
            }
        }
        return list;
    }

    public List<Coordinate> getSameColumnCells(Coordinate cell) {
        List<Coordinate> list = new ArrayList<>();
        int y = cell.getY(); //cell column
        Coordinate c;
        for (int i = 0; i < 3; i++) {
            if (i != cell.getX()) {
                c = new Coordinate(i, y);
                list.add(c);
            }
        }
        return list;
    }

    public int getCellsMD(Coordinate cell1, Coordinate cell2) {
        int distance;
        int x1;
        int y1;
        int x2;
        int y2;
        x1 = cell1.getX();
        y1 = cell1.getY();
        x2 = cell2.getX();
        y2 = cell2.getY();
        distance = Math.abs(y2 - y1);
        distance = distance + Math.abs(x2 - x1);

        return distance;
    }

    public List<Player> getRightPlayers(Player player) {
        List<Player> list = new ArrayList<>();
        int x = player.getCel().getX(); //player line
        for (Player p : this.players) {
            if (!p.equals(player)) {
                int otherplayerX = p.getCel().getX();
                if (otherplayerX == x && p.getCel().getY() > player.getCel().getY()) list.add(p);
            }
        }
        return list;
    }

    public List<Player> getLeftPlayers(Player player) {
        List<Player> list = new ArrayList<>();
        int x = player.getCel().getX(); //player line
        for (Player p : this.players) {
            if (!p.equals(player)) {
                int otherplayerX = p.getCel().getX();
                if (otherplayerX == x && p.getCel().getY() < player.getCel().getY()) list.add(p);
            }
        }
        return list;
    }

    public List<Player> getUpPlayers(Player player) {
        List<Player> list = new ArrayList<>();
        int y = player.getCel().getY(); //player column
        for (Player p : this.players) {
            if (!p.equals(player)) {
                int otherplayerY = p.getCel().getY();
                if (otherplayerY == y && p.getCel().getX() < player.getCel().getX()) list.add(p);
            }
        }
        return list;
    }

    public List<Player> getDownPlayers(Player player) {
        List<Player> list = new ArrayList<>();
        int y = player.getCel().getY(); //player column
        for (Player p : this.players) {
            if (!p.equals(player)) {
                int otherplayerY = p.getCel().getY();
                if (otherplayerY == y && p.getCel().getX() > player.getCel().getX()) list.add(p);
            }
        }
        return list;
    }

    public List<Player> getSameCellsPlayers(Coordinate cell) {
        List<Player> list = new ArrayList<>();
        int xCell = cell.getX();
        int yCell = cell.getY();
        for (Player p : this.players) {
            int xPlayer = p.getCel().getX();
            int yPlayer = p.getCel().getY();
            if (xCell == xPlayer && yCell == yPlayer) {
                list.add(p);
            }
        }
        return list;
    }

    public List<Coordinate> getDownCells(Coordinate cell) {
        List<Coordinate> list = new ArrayList<>();
        int x = cell.getX(); //cell line
        int y = cell.getY(); //cell column
        if (x < 2) {
            while (x < 2) {
                x++;
                Coordinate c = new Coordinate(x, y);
                list.add(c);
            }
        }
        return list;
    }

    public List<Coordinate> getUpCells(Coordinate cell) {
        List<Coordinate> list = new ArrayList<>();
        int x = cell.getX(); //cell line
        int y = cell.getY(); //cell column
        if (x > 0) {
            while (x > 0) {
                x--;
                Coordinate c = new Coordinate(x, y);
                list.add(c);
            }
        }
        return list;
    }

    public List<Coordinate> getLeftCells(Coordinate cell) {
        List<Coordinate> list = new ArrayList<>();
        int x = cell.getX(); //cell line
        int y = cell.getY(); //cell column
        if (y > 0) {
            while (y > 0) {
                y--;
                Coordinate c = new Coordinate(x, y);
                list.add(c);
            }
        }
        return list;
    }

    public List<Coordinate> getRightCells(Coordinate cell) {
        List<Coordinate> list = new ArrayList<>();
        int x = cell.getX(); //cell line
        int y = cell.getY(); //cell column
        if (y < 3) {
            while (y < 3) {
                y++;
                Coordinate c = new Coordinate(x, y);
                list.add(c);
            }
        }
        return list;
    }



}
