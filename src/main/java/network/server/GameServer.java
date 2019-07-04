package network.server;

import network.client.Client;
import network.messages.*;
import network.messages.clientRequest.*;
import network.messages.clientRequest.CardToWeaponGrabClientRequest;
import network.messages.clientRequest.RunClientRequest;
import network.messages.clientRequest.SpawnPointClientRequest;
import network.messages.playerDataMessage.InfoID;
import network.messages.playerDataMessage.InfoMatch;
import network.server.rmi.GameRMISvr;
import network.server.socket.GameSocketSvr;

import java.rmi.RemoteException;
import java.util.*;

import static utils.NotifyClient.registerServer;
import static utils.NotifyClient.updatePlayerStatus;
import static utils.Print.printOut;


//la classe unisce sia il server Socket che RMI, in questo modo ho il vantaggio di poter gestire contemporaneamente
//entrambe le tipologie di connessione da parte dei connectionHandler

public class GameServer {

    private static final int MIN_PLAYER_NUMBER = 3;
    private static final int MAX_PLAYER_NUMBER = 5;
    private static final int TIMER_PING = 3000;
    private static final int SOCKET_SERVER_PORT =7218;
    private static final int RMI_SERVER_PORT =1099;

    private static int queueTimer;
    private static int inGameTimer;

    //----------------HashMap per Username e Client/ID----------------------//
    private HashMap<String,String> usernameToUserID;                            //Collega Username e IdPlayer
    private HashMap<String, ClientInterface> userIDToClientInterface;           //Non per forza utile
    private HashMap<String,GameRoom> userIDToGameRoom;                        //Collega IdPlayer e Partita in cui è inserito
    private HashMap<String,Boolean> userIDInGameToStatusConnection;                   //PlayerID to his Connection Status
    //Se non è ancora in GameRoom si potrebbe mettere il campo String a "WaitingRoom"
    //Così facendo eviterei di dovermi inventare altro per i WaitingPlayers

    //----------------------------------------------------------------------//

    private WaitingRoom waitingRoom;                                        //Stanza per i giocatori in attesa

    // Implementazione vera e propria dei Server
    private GameSocketSvr gameSocketSvr;
    private GameRMISvr gameRMISvr;


    public static void main(String[] args) {
        if(args.length==2){
            //leggo queue Timer
            queueTimer=Integer.parseInt(args[0]);
            //leggo inGame Timer
            inGameTimer=Integer.parseInt(args[1]);
        }
        else{
            queueTimer=30000;
            inGameTimer=45000;
        }
        GameServer gameServer = new GameServer();
        gameServer.launchServer();
    }

    /**
     * Method for the gameserver
     */
    private GameServer(){
        this.gameSocketSvr=new GameSocketSvr(this);
        this.gameRMISvr=new GameRMISvr(this);
        this.waitingRoom= new WaitingRoom(this,queueTimer,MIN_PLAYER_NUMBER,MAX_PLAYER_NUMBER);
        this.usernameToUserID =new HashMap<>();
        this.userIDToClientInterface =new HashMap<>();
        this.userIDToGameRoom =new HashMap<>();
        this.userIDInGameToStatusConnection =new HashMap<>();
    }

    /**
     * Method to launch server
     */
    private void launchServer(){
        gameSocketSvr.start(SOCKET_SERVER_PORT);
        gameSocketSvr.start();
        try {
            gameRMISvr.start(RMI_SERVER_PORT);
        }catch (RemoteException e){
            printOut(e.getMessage());
        }
        registerServer(this);
    }

    //------------------------Metodi usati dalle ClientInterface------------------------------------//

    /**
     *
     * @param requestedUsername is the username to be checked in the queue
     * @return true if the client is already in the queue, false otherwise
     */
    public boolean isAlreadyInQueue(String requestedUsername) {
        return waitingRoom.isAlreadyInQueue(requestedUsername);
    }

    /**
     *
     * @param username is the username to check for disconnection
     * @return true if the player has disconnected, false otherwise
     */
    public boolean hasPlayerDisconnected(String username){
        return usernameToUserID.containsKey(username) && !userIDInGameToStatusConnection.get(usernameToUserID.get(username));
    }

    /**
     *
     * @param userIDToReconnect is the username to reconnect
     * @return a boolean that says if the client has reconnected or not
     */
    public boolean checkUserID(String userIDToReconnect) {
        return usernameToUserID.containsValue(userIDToReconnect) && !userIDInGameToStatusConnection.get(userIDToReconnect);

    }

    /**
     *
     * @param playerUsername is the player user
     * @param clientInterface is the client interface for communication
     */
    public synchronized void addClientToWR(String playerUsername, ClientInterface clientInterface){
        assignIDToUsername(playerUsername);
        assignClientHandlerToID(playerUsername,clientInterface);
        Message infoIDMessage=new InfoID( usernameToUserID.get(playerUsername));
        try {
            clientInterface.sendMessage(infoIDMessage);
        }catch (RemoteException e) {

        }
        //Imposto a connesso il Player, a termine del timer verifico che siano ancora tutti connessi
        userIDInGameToStatusConnection.put(usernameToUserID.get(playerUsername),true);
        startCheckConnection(usernameToUserID.get(playerUsername));
        waitingRoom.addUserToRoom(playerUsername);
    }

    /**
     *
     * @param userID is the user to check if the connection has started
     */
    private void startCheckConnection(String userID){
        (new Timer()).schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    if((userIDInGameToStatusConnection.get(userID))) {
                        userIDToClientInterface.get(userID).setClientConnected();
                        startCheckConnection(userID);
                    }else{
                        printOut("Close Connection of Player. ID: "+ userID);
                        handleDisconnect(userIDToClientInterface.get(userID));
                        cancel();
                    }
                } catch (RemoteException e) {
                    //SIGNIFICA CHE IL PLAYER SI È DISCONNESSO.
                    printOut("Disconnected Player. ID: "+ userID);
                    handleDisconnect(userIDToClientInterface.get(userID));
                    cancel();
                }
            }
        },TIMER_PING);
    }

    /**
     *
     * @param clientInterface to manage disconnection
     */
    public synchronized void handleDisconnect(ClientInterface clientInterface){
        //Imposto il ClientHandler come Disconnesso, comunico inoltre alla singola GameRoom o WR che il singolo giocatore è disconnesso
        String userID;
        try {
            userID=clientInterface.getPlayerID();
            clientInterface.setConnection(false);
        } catch (RemoteException e) {
            userID = null;
            for (Map.Entry<String, ClientInterface> entry : userIDToClientInterface.entrySet()) {
                String id = entry.getKey();
                ClientInterface interfaceClient = entry.getValue();
                if (clientInterface.equals(interfaceClient)) {
                    userID = id;
                }
            }
        }
        updatePlayerStatus(userID,false);
        if(userIDToGameRoom.containsKey(userID)) {
            userIDToGameRoom.get(userID).disconnectPlayer(userID);
        }
        else{
            for (Map.Entry<String, String> entry : usernameToUserID.entrySet()) {
                String username = entry.getKey();
                String id = entry.getValue();
                if (id.equals(userID)) {
                    waitingRoom.removePlayerInQueue(username);
                    usernameToUserID.remove(username, id);
                    userIDInGameToStatusConnection.remove(userID);
                }
            }
        }
        if(userIDInGameToStatusConnection.get(userID)) {
            closeConnection(userID);
        }
    }

    /**
     *
     * @param userID is the userID to connect
     * @param clientInterface to manage re connection
     */
    public synchronized void handleReConnect(String userID,ClientInterface clientInterface){
        try {
            clientInterface.setConnection(false);
        } catch (RemoteException e) {
            //TODO
        }
        userIDToClientInterface.replace(userID,clientInterface);
        startCheckConnection(userID);
        userIDInGameToStatusConnection.replace(userID,true);

        updatePlayerStatus(userID,true);
        userIDToGameRoom.get(userID).reConnectPlayer(userID);
        Message confirmationMessage=new InfoMatch("Bentornato nella partita!\n");
        sendMessageToID(userID,confirmationMessage);
    }
    public void reAddClientToWR(String userID, ClientInterface clientInterface) {
        userIDToClientInterface.replace(userID,clientInterface);
        String name=null;
        for (Map.Entry<String, String> entry : usernameToUserID.entrySet()) {
            String username = entry.getKey();
            String id = entry.getValue();
            if (id.equals(userID)) {
                name = username;
            }
        }
        waitingRoom.addUserToRoom(name);
        Message confirmationMessage=new InfoMatch("Sei stato riaggiunto nella WR!\n");
        sendMessageToID(userID,confirmationMessage);
    }

    //Metodo probabilmente utile solo per i Client connessi con Socket, nel caso di RMI diventa "inutile", serve solo a marchiare il client disconnesso
    //Sui socket chiude forzatamente la connessione.

    /**
     * Method just for socket clients
     * @param userID is teh client to be marked disconnected
     */
    public synchronized void closeConnection(String userID){
        try {
            userIDInGameToStatusConnection.replace(userID,false);
            userIDToClientInterface.get(userID).closeConnection();
        } catch (RemoteException e) {
            //Nothing to do.
        }
    }

    /**
     *
     * @param playerUsername is the username to be assigned
     */
    private void  assignIDToUsername(String playerUsername) {
        UUID uid=UUID.randomUUID();
        String playerID=uid.toString();
        usernameToUserID.put(playerUsername,playerID);
    }

    /**
     *
     * @param playerUsername is the username to be assigned
     * @param clientHandler to manage connection
     */
    private void assignClientHandlerToID(String playerUsername, ClientInterface clientHandler){
        String playerID=usernameToUserID.get(playerUsername);
        userIDToClientInterface.put(playerID,clientHandler);
        try {
            clientHandler.setPlayerID(playerID);
        } catch (RemoteException e) {
            printOut("Problem, closing connection");
        }
    }
    //Metodo in cui viene lanciato effettivamente il gioco, si crea una stanza per i giocatori che hanno effettuato il login e
    //si chiama il metodo per runnare la partita.

    /**
     * Method to effectively launch the game
     * @param usernameList is the list of players that has done login
     */
    public void newGameRoom(List<String> usernameList) {
        HashMap<String, String> userList=new HashMap<>();
        for(String username:usernameList){
            userList.put(username,usernameToUserID.get(username));

        }
        GameRoom gameRoom=new GameRoom(userList,this,inGameTimer);
        for(String playerUsername:usernameList){
            String playerID=usernameToUserID.get(playerUsername);
            userIDToGameRoom.put(playerID,gameRoom);
        }
        gameRoom.setUpGame();
    }

    //------------------------Metodi usati per la gestione dei messaggi di rete------------------------------------//
    //Metodo per inviare un messaggio a tutti i giocatori di una partita (si richiede in ingresso una collection di userID)

    /**
     * Method to send message to all players in a match
     * @param userID  is the collection of userID in the match
     * @param message is the message to be sent to the all the users
     */
    public synchronized void sendMessageToAll(Collection<String> userID, Message message){
        userIDToClientInterface.forEach((id,clientInterface)-> {
            if(userID.contains(id)) {
                try {
                    clientInterface.sendMessage(message);
                } catch (RemoteException e) {
                    handleDisconnect(clientInterface);
                }
            }
        });
    }
    //Metodo per inviare un messaggio a un giocatore specifico.

    /**
     *
     * @param userID is the specific ID to whom send the message
     * @param message is the message to be sent
     */
    public synchronized void sendMessageToID(String userID, Message message) {
        try {
            userIDToClientInterface.get(userID).sendMessage(message);
        } catch (RemoteException e) {
            handleDisconnect(userIDToClientInterface.get(userID));
        }
    }

    //------------------------Metodi usati per la gestione dei messaggi in ingresso-----------------------------------//

    /**
     *
     * @param message is the message to be handled
     */
    public synchronized void handleMessage(Message message) {
        if ("clientRequest".equals(message.getType())) {
            ClientRequestMessage requestMessage = (ClientRequestMessage) message;
            handleRequest(requestMessage);
        }
    }

    /**
     *
     * @param requestMessage is the request message to be handled
     */
    private void handleRequest(ClientRequestMessage requestMessage) {
        int intero;
        switch(requestMessage.getContent()){
            case "ColorRequest":
                userIDToGameRoom.get(requestMessage.getUserID()).registerPlayerColor(requestMessage.getUserID(),requestMessage.getInfo());
                break;
            case "MapRequest":
                userIDToGameRoom.get(requestMessage.getUserID()).setMapChoice(Integer.parseInt(requestMessage.getInfo()));
                break;
            case "SpawnPointRequest":
                SpawnPointClientRequest spawnPointRequest=(SpawnPointClientRequest) requestMessage;
                userIDToGameRoom.get(spawnPointRequest.getUserID()).setSpawnPoint(spawnPointRequest.getUserID(),spawnPointRequest.getCoordinate(),Integer.parseInt(spawnPointRequest.getInfo()));
                break;
            case "ActionRequest":
                userIDToGameRoom.get(requestMessage.getUserID()).performAction(requestMessage.getUserID(), Integer.parseInt(requestMessage.getInfo()));
                break;
            case "RunRequest":
                RunClientRequest runRequest=(RunClientRequest) requestMessage;
                userIDToGameRoom.get(runRequest.getUserID()).performRun(requestMessage.getUserID(),runRequest.getDirection());
                break;
            case "WeaponGrabRequest":
                userIDToGameRoom.get(requestMessage.getUserID()).performWeaponGrab(requestMessage.getUserID(),Integer.parseInt(requestMessage.getInfo()));
                break;
            case "WeaponDiscardToGrabRequest":
                CardToWeaponGrabClientRequest discardWeaponMessage=(CardToWeaponGrabClientRequest) requestMessage;
                userIDToGameRoom.get(requestMessage.getUserID()).discardWeaponCardToGrab(discardWeaponMessage.getUserID(),Integer.parseInt(discardWeaponMessage.getInfo()),Integer.parseInt(discardWeaponMessage.getIndexCardToDiscard()));
                break;
            case "PowToWeaponGrabRequest":
                CardToWeaponGrabClientRequest powToConvertMessage=(CardToWeaponGrabClientRequest) requestMessage;
                userIDToGameRoom.get(powToConvertMessage.getUserID()).performWeaponGrabWithPowCard(powToConvertMessage.getUserID(),Integer.parseInt(powToConvertMessage.getInfo()),Integer.parseInt(powToConvertMessage.getIndexCardToDiscard()));
                break;
            case "PowCardDiscardRequest":
                userIDToGameRoom.get(requestMessage.getUserID()).discardPowCard(requestMessage.getUserID(),Integer.parseInt(requestMessage.getInfo()));
                break;
            case "shootingIndexRequest":
                intero = Integer.parseInt(requestMessage.getInfo());
                userIDToGameRoom.get(requestMessage.getUserID()).getIndexShoot(requestMessage.getUserID(),intero);
                break;
            case "muxIndex":
                intero = Integer.parseInt(requestMessage.getInfo());
                userIDToGameRoom.get(requestMessage.getUserID()).getIndexShoot(requestMessage.getUserID(),intero);
                break;
            case "idScope":
                intero = Integer.parseInt(requestMessage.getInfo());
                userIDToGameRoom.get(requestMessage.getUserID()).getScopeIndex(intero,requestMessage.getUserID());
                break;
            case "idGranade":
                intero = Integer.parseInt(requestMessage.getInfo());
                userIDToGameRoom.get(requestMessage.getUserID()).getGranadeIndex(intero,requestMessage.getUserID());
                break;

            //TODO @Daniele, non lasciare "intero", scrivi cosa fa.

        }
    }


    //-------------------------------Metodi da completare----------------------------//

    /**
     * Method to close connection
     */
    private void closeServer(){         //TODO: le connessioni vanno chiuse (capire dove)
        gameSocketSvr.close();
    }


}










