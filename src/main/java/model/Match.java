package model;

import exceptions.*;
import network.messages.playerDataMessage.*;
import network.messages.Message;

import java.io.Serializable;

import java.util.*;

import static utils.NotifyClient.*;

public class Match implements Serializable {
    /**
     * round is an int representing the number of round that is being played
     * players is an ArrayList containing all the players in the game
     * dashboard is the dashboard associated to the match
     * ammoDeck, weaponDeck, powDeck are the decks associated to the match
     * checkDashboard is a boolean value to check whether the dashboard has been correctly created or not
     */
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
    /**
     * Method to increment the number of round
     */
    public void setRound() {
        this.round++;
        //increase the number of the round just if the last player in the turn (that is the last of the array)
        //has done its second action, finished its turn
    }
    /**
     *
     * @return the number of round
     */
    public int getRound() {
        return this.round;
    }

    //---------------------------------Metodi inizializzazione Player-------------------------------------------------//

    //Alla creazione del Player istanzio la relativa PlayerData che mando al Client
    //Ciascun Client quindi avrà la classe con la classe Player già inizializzata.
    //In seguito la classe va aggiornata con il nome dei Player avversari
    /**
     * Method to create the player, with the given Strings:
     * @param name is the name chosen by the player
     * @param color is the color chosen by the player
     * @param id is the id for the player
     */
    public void createPlayer(String name, String color, String id) {
        Player player = new Player(name, color, id);
        addPlayer(player);
    }
    /**
     * Method to add the player in a match
     * @param player to be added in the match
     */
    private void addPlayer(Player player) {
        players.add(player);
        Message message = new NewPlayerData(new PlayerVisibleData(player));
        notifySpecificClient(player.getID(), message);
    }
    /**
     * Method to notify all the other players
     */
    public void notifyOfOtherPlayers() {
        for (Player player : players) {
            notifyAllExceptOneClient(player.getID(), new OtherPlayerData(player.getName(), player.getColorAsString()));
        }
    }
    //________________________________________________________________________________________________________________//

    //---------------------------------Metodi gestione connessione ---------------------------------------------------//
    /**
     * Method to disconnect a player
     * @param userID is the ID of the player to disconnect
     */
    public void setPlayerDisconnected(String userID) {
        getPlayerByID(userID).setConnected(false);
        Message infoPlayerDisconnected = new InfoMatch("Player " + getPlayerByID(userID).getName() + " is disconnected from game.");
        notifyAllExceptOneClient(userID, infoPlayerDisconnected);
    }
    /**
     * Method to re-connect a player
     * @param userID is the ID of the player to re-connect
     */
    public void setPlayerReConnected(String userID) {
        getPlayerByID(userID).setConnected(true);
        Message infoPlayerDisconnected = new InfoMatch("Player " + getPlayerByID(userID).getName() + " re joined game.");
        notifyAllExceptOneClient(userID, infoPlayerDisconnected);
    }

    //---------------------------------Metodi inizializzazione Dashboard----------------------------------------------//
    /**
     * Method to create the dashboard. This is not made in the constructor because the first player in the match chooses
     * the type of dashboard to use
     * @param selectedDashboard is the index representing the chosen map
     */
    public void createDashboard(int selectedDashboard) {
        this.dashboard = new Dashboard(selectedDashboard,8 );               //TODO
        checkDashboard = true;
    }
    /**
     * Method to fill the dashboard with all weapons in the SpawnPoint Cells and AmmoCards in the Normal Cells
     */
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
    /**
     * Method to notify the new map has been created
     */
    public void notifyNewMap() {
        InfoMatch message = new InfoMatch("Selected map " + dashboard.getMapType() + " . Stampo di seguito:");
        notifyAllClients(this, message);
    }
    /**
     * Method to fill the SpawnPoint Cell
     * @param cell to be filled with weapons (as it is a SpawnPoint Cell)
     */
    private void fillSpawnPoint(SpawnPointCell cell) {
        for (int index = 0; index < 3; index++) {
            try {
                if (cell.getSpawnPointCellWeapons().get(index) == null) {
                    cell.setWeaponCard((Weapon) weaponDeck.drawCard(), index);
                }
            } catch (IndexOutOfBoundsException e) {
                try {
                    cell.addWeaponCard((Weapon) weaponDeck.drawCard(), index);
                } catch (FullCellException e1) {
                    //Nothing to do
                }
            }
        }
        //This method fill the dashboard for the first time, this Exception is impossible
    }
    /**
     * Method to fill the Normal Cell
     * @param cell to be filled with AmmoCard (as it is a Normal Cell)
     */
    private void fillNormal(NormalCell cell) {
        try {
            if (cell.getAmmoCard() == null) {
                cell.addAmmoCard((AmmoCard) ammoDeck.drawCard());
            } else if (cell.getAmmoCard().getStatus()) {
                ammoDeck.discardCard(cell.getAmmoCard());
                cell.addAmmoCard((AmmoCard) ammoDeck.drawCard());
            }
        } catch (FullCellException e) {
            //This method fill the dashboard for the first time, this Exception is impossible
        }
    }
    /**
     * Method to notify all players the dashboard has been updated
     */
    public void updateClientDashboard() {
        Message infoMap = new DashboardData(this.dashboard);
        notifyAllClients(this, infoMap);
    }
    /**
     * Method to notify all players the end of the action
     */
    public void updateEndAction() {
        this.updateClientDashboard();
    }
    /**
     * Method to notify all players the end of the turn
     */
    public void updateEndTurn() {
        this.fillDashboard();
        // updateClientDashboard chiamato direttamente dalla fill!
    }
    //TODO: messaggi per Match!!!!
    /**
     * Method to assign the score calculated in controller to the players
     * @param score is the List containg the score to be added to players. Index represents the color so
     * score.get(0) represents the score to be given to the player with color blue
     * score.get(1) represents the score to be given to the player with color green
     * and so on, according to the usual conventions for player colors
     * @param deadPlayer is the int representing the color of the dead player
     */
    public void assignScore(List<Integer> score,Player deadPlayer) {
        for (int i = 0; i < score.size(); i++) {
            try {
                getPlayer(i).setScore(score.get(i));
            } catch (InvalidColorException e) {
            }
        }
        //TODO: messaggi per Player che ricevono punti. ExceptOne (player Dead)
    }
    //________________________________________________________________________________________________________________//
    /**
     * Method to initialize in the first turn the PowCards to the players
     */
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
    /**
     * Method to make a player spawn
     * @param player is the player that has to spawn
     * @param indexPowCard is the int representing the index of the PowCard to use to spawn
     * @param x is the number of line of the SpawnPoint where to spawn
     * @param y is the number of column of the SpawnPoint where to spawn
     */
    public void spawnPlayer(Player player, int indexPowCard, int x, int y) {
        player.setCel(x, y);
        player.setDead(false);
        PowCard powCard = player.getPowByIndex(indexPowCard);
        try {
            player.removePow(powCard);
            powDeck.discardCard(powCard);
        } catch (ZeroCardsOwnedException | NotOwnedCardException e) {
            //Impossible Exception
        }
        Message infoUsedCard = new NewCardUsed("PowCard", indexPowCard);
        notifySpecificClient(player.getID(), infoUsedCard);
        Message infoSpawnPoint = new NewPosition(x, y);
        notifySpecificClient(player.getID(), infoSpawnPoint);
    }
    /**
     * Method to make a dead player spawn
     * @param player is the dead player
     */
    public void spawnDeadPlayer(Player player){
        PowCard powCard=player.getPowByIndex(player.getNumberPow()-1);
        int colorPowCard=powCard.getColor();
        try {
            player.removePow(powCard);
        } catch (ZeroCardsOwnedException | NotOwnedCardException e) {
            //Impossible Exception
        }
        int indexR;
        int indexC;
        for (indexR = 0; indexR < dashboard.getRowDim(); indexR++) {
            for (indexC = 0; indexC < dashboard.getColDim(); indexC++) {
                Cell cell = this.getDashboard().getMap(indexR, indexC);
                if ((cell.getType() == 0) && (cell.getColor() != colorPowCard)){
                    player.setCel(indexR,indexC);
                }
            }
        }
        player.setDead(false);
    }
    /**
     * Method to assign a PowCard to a player
     * @param player is the player to whom assign the PowCard
     * @throws MaxNumberofCardsException if the player already has three PowCards, the maximum number
     */
    public void assignPowCard(Player player) throws MaxNumberofCardsException {
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
    /**
     * Method to assign a Weapon to a player
     * @param player is the player to whom assign the Weapon
     * @param indexWeapon is the index of weapon to choose in the SpawnPoint Cell
     * @throws MaxNumberofCardsException if the player already has three weapons, the maximum number
     */
    public void assignWeaponCard(Player player, int indexWeapon) throws MaxNumberofCardsException {
        SpawnPointCell cell;
        cell = (SpawnPointCell) player.getCel().inMap(this.getDashboard(), player.getCel().getX(), player.getCel().getY());
        Weapon weaponCard = (Weapon) cell.collectWeapon(indexWeapon);
        player.addWeapon(weaponCard);
        Message infoWeaponCard = new NewWeaponCard(weaponCard);
        notifySpecificClient(player.getID(), infoWeaponCard);
    }
    /**
     * Method to assign Ammos to a player
     * @param player is the player to whom assign the Ammos
     * @throws CardAlreadyCollectedException if the card has already been collected by the player in the same turn at the previous action
     */
    public void assignAmmo(Player player) throws CardAlreadyCollectedException {
        NormalCell cell;
        cell = (NormalCell) player.getCel().inMap(this.getDashboard(), player.getCel().getX(), player.getCel().getY());
        try {
            cell.collectCard(player);
        } catch (MoreThanTreeAmmosException e) {
            //TODO: avviso il player
        }
        List<Integer> ammo = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ammo.add(i, player.getAmmo(i));
        }
        Message infoAmmo = new NewAmmo(ammo);
        notifySpecificClient(player.getID(), infoAmmo);
    }
    /**
     *
     * @param player is the player to be set
     * @param x is number of line of the cel
     * @param y is number of column of the cel
     */
    public void setPlayerCel(Player player, int x, int y) {
        player.setCel(x, y);
        Message infoSpawnPoint = new NewPosition(x, y);
        notifySpecificClient(player.getID(), infoSpawnPoint);
    }
    //TODO: @Angelica controlla questi tre metodi plis
    /**
     * Method to set the number of damages to a player
     * @param playerAttacker is the player that attacks
     * @param playerAttacked is the attacked player
     * @param damage is the number of given damages
     * @return
     */
    //TODO @DA Angelica: metodo è ok!
    public int setDamage(Player playerAttacker, Player playerAttacked, int damage) {
        int outcomeOfAttack;
        outcomeOfAttack=playerAttacked.setDamage(damage,playerAttacker.getColor());
        //TODO: segnalo attacco ricevuto al Player e gli do l'esito del suo stato.
        return outcomeOfAttack;
    }

    /**
     * Method to set the number of marks to a player
     * @param playerAttacker is the player that attacks
     * @param playerAttacked is the attacked player
     * @param damage is the number of given damages
     */
    //TODO @DA Angelica: metodo è ok!
    public void setMarks(Player playerAttacker, Player playerAttacked, int damage) {
        playerAttacked.setMarks(damage,playerAttacker.getColor());
        //TODO: segnalo attacco ricevuto al Player e gli do l'esito dei marchi.
    }
    /**
     * Method to manage the death of a player
     * @param playerKiller is the player that kills
     * @param playerKilled is the killed player
     * @param withRevenge is a boolean to say if there is the revenge mark or not
     */
    //TODO @DA Angelica: metodo è ok!
    public void playerDeath(Player playerKiller,Player playerKilled,boolean withRevenge){
        //update KillShot Track
        if(withRevenge){
            getDashboard().setKillShotTrack(playerKiller, 2);
            playerKiller.setMarks(1,playerKilled.getColor());
        }
        else{
            getDashboard().setKillShotTrack(playerKiller, 1);
        }
        //Sign dead player
        playerKilled.setDead(true);

        //TODO Segnalo tutto al Player

    }
    /**
     * Method to return player by color
     * @param color is the int representing the color of the player
     * @return the player
     * @throws InvalidColorException if the int passed is not allowed
     */
    public Player getPlayer(int color) throws InvalidColorException {
        for (Player p : this.players) {
            if (p.getColor() == color) {
                return p;
            }
        }
        throw new InvalidColorException();
    }
    /**
     * Method to return the active player
     * @return the active player
     */
    public Player getActivePlayer() {
        for (Player p : this.players) {
            if (p.getActive()) return p;
        }
        return null;
    }
    /**
     *
     * @param userID is the ID of the player requested
     * @return the player with ID passed as a parameter
     */
    public Player getPlayerByID(String userID) {
        for (Player player : this.players) {
            if (player.getID().equals(userID)) {
                return player;
            }
        }
        return null;
    }
    /**
     *
     * @return all the players in the match
     */
    public List<Player> getPlayers() {
        return this.players;
    }
    /**
     *
     * @return all the players in the match that has no damages
     */
    public List<Player> getNoDamagedPlayers() {
        List<Player> list = new ArrayList<>();
        for (Player p : this.players) {
            if (p.getTotalDamage() == 0) list.add(p);
        }
        return list;
    }
    /**
     *
     * @return the boolean checkDashboard to verify correct creation of the dashboard
     */
    public boolean getCheck() {
        return this.checkDashboard;
    }
    /**
     * Method that returns player by index to check their ID
     * @param index is the index of player
     * @return player corresponding to the index
     */
    public Player getPlayerByIndex(int index) {
        return this.players.get(index);
    }
    /**
     *
     * @return number of players in the match (the size of the ArrayList containing the players)
     */
    public int getPlayersSize() {
        return this.players.size();
    }
    /**
     * @return the dashboard of the match
     */
    public Dashboard getDashboard() {
        return this.dashboard;
    }
    /**
     * Method to add again the card after it has been used
     * @param cell is the normal cell in which to add the card
     */
    public void addAmmoCard(NormalCell cell) {
        try {
            cell.addAmmoCard((AmmoCard) ammoDeck.drawCard());
        } catch (FullCellException e) {
            //TODO
        }
    }
    /**
     * Method to add a weapon in the SpawnPoint Cell
     * @param cell is the SpawnPoint Cell where to add the weapon
     * @param index is the index in which to add the weapon
     */
    public void addWeaponCard(SpawnPointCell cell, int index) {
        try {
            Weapon weapon = (Weapon) weaponDeck.drawCard();
            cell.addWeaponCard(weapon, index);
        } catch (FullCellException e) {
            //TODO
        }
    }
    /**
     *
     * @param numberAmmo is the number of Ammos to remove to the active player
     * @param ammo is the ammo color to remove
     * @throws NotEnoughAmmosException
     */
    public void removeAmmo(int numberAmmo, Ammo ammo) throws NotEnoughAmmosException {
        Player player = this.getActivePlayer();
        player.removeAmmo(numberAmmo, ammo);
    }
    /**
     * Method to set a weapon card
     * @param cell is the SpawnPoint Cell in which to set the weapon
     * @param index is the index in which to set the weapon
     */
    public void setWeaponCard(SpawnPointCell cell, int index) {
        Weapon weapon = (Weapon) weaponDeck.drawCard();
        cell.setWeaponCard(weapon, index);
    }
    /**
     * Method for controller, to shuffle all decks at the start of the game
     */
    public void shuffleAllDecks() {
        ammoDeck.shuffleStack();
        weaponDeck.shuffleStack();
        powDeck.shuffleStack();
    }
    /**
     * Method to discard a PowCard
     * @param powCard to be discarded
     */
    public void discardPowCard(PowCard powCard) {
        powDeck.discardCard(powCard);
    }
    /**
     * Method to discard a weapon
     * @param weapon to be discarded
     */
    public void discardWeaponCard(Weapon weapon) {
        weaponDeck.discardCard(weapon);
    }

    //________________________________________________________________________________________________________________//

    //---------------------------------Metodi utili per la Shoot------------------------------------------------------//
    /**
     *
     * @param player is the given player
     * @return all the players seen by the given player
     */
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
                int otherplayercellcolor = p.getCel().inMap(this.dashboard, p.getCel().getX(), p.getCel().getY()).getColor();
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
                            int otherplayercolor = p.getCel().inMap(this.dashboard, p.getCel().getX(), p.getCel().getY()).getColor();
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
                            int otherplayercolor = p.getCel().inMap(this.dashboard, p.getCel().getX(), p.getCel().getY()).getColor();
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
                            int otherplayercolor = p.getCel().inMap(this.dashboard, p.getCel().getX(), p.getCel().getY()).getColor();
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
                            int otherplayercolor = p.getCel().inMap(this.dashboard, p.getCel().getX(), p.getCel().getY()).getColor();
                            if (otherplayercolor == othercell.getColor()) visible.add(p);
                        }
                    }
                    y++;
                }
            }
        }
        return visible;
    }
    /**
     *
     * @param player is the given player
     * @param direction is the direction in which the player wants to watch
     * @return visible players by the port in the given direction
     */
    public List<Player> getVisiblePlayersByPort(Player player, int direction) {
        List<Player> visibleplayers = new ArrayList<>();
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
    /**
     *
     * @param cell is the considered cell
     * @return all the players that are in the same room of the given cell
     */
    private List<Player> getRoomPlayers(Coordinate cell) {
        List<Player> roomplayers = new ArrayList<>();
        int x = cell.getX();
        int y = cell.getY();
        Cell c = this.getDashboard().getMap(x, y); //cell
        int cellcolor = c.getColor(); //color of the cell

        //adds a player if it is in the same room
        for (Player p : this.players) {
            int playercellcolor = p.getCel().inMap(this.dashboard, p.getCel().getX(), p.getCel().getY()).getColor();
            if (playercellcolor == cellcolor) roomplayers.add(p);
        }
        return roomplayers;
    }
    /**
     *
     * @param player is the given player
     * @return the list of players in the same line of the given player
     */
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
    /**
     *
     * @param player is the given player
     * @return the list of players in the same column of the given player
     */
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
    /**
     *
     * @param player1 is the first considered player
     * @param player2 is the second considered player
     * @return the distance between the two players
     */
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
            distance = Math.abs(x2 - x1); }
        return distance;
    }
    /**
     *
     * @param cell is the given cell (given as a coordinate)
     * @return all the visible cells by the given cell
     */
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
                    visible.add(c); } } }

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
    /**
     *
     * @param cell is the given cell (as a coordinate)
     * @return the cells on the same line of the given cell
     */
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
    /**
     *
     * @param cell is the given cell (as a coordinate)
     * @return the cells on the same column of the given cell
     */
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
    /**
     *
     * @param cell1 is the first considered cell
     * @param cell2 is the second considered cell
     * @return is the distance between the two cells
     */
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
    /**
     *
     * @param player is the given player
     * @return all the players at the right side of the given player
     */
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
    /**
     *
     * @param player is the given player
     * @return all the players at the left side of the given player
     */
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
    /**
     *
     * @param player is the given player
     * @return all the players over the given player
     */
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
    /**
     *
     * @param player is the given player
     * @return all the players under the given player
     */
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
    /**
     *
     * @param cell is the given cell(as a coordinate)
     * @return all the players in the given cell
     */
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
    /**
     *
     * @param cell is the given cell(as a coordinate)
     * @return all the cells under the given cell, as coordinates
     */
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
    /**
     *
     * @param cell is the given cell(as a coordinate)
     * @return all the cells over the given cell, as coordinates
     */
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
    /**
     *
     * @param cell is the given cell(as a coordinate)
     * @return all the cells at the left side of the given cell, as coordinates
     */
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

    /**
     *
     * @param cell is the given cell(as a coordinate)
     * @return all the cells at the right side of the given cell, as coordinates
     */
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




