package network.server;

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
import static utils.printStream.printOut;


//la classe unisce sia il server Socket che RMI, in questo modo ho il vantaggio di poter gestire contemporaneamente
//entrambe le tipologie di connessione da parte dei connectionHandler

public class GameServer {

    private static final int MIN_PLAYER_NUMBER = 3;
    private static final int MAX_PLAYER_NUMBER = 5;
    private static final int TIMER = 30000;
    private static final int SOCKET_SERVER_PORT =7218;
    private static final int RMI_SERVER_PORT =1099;

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
        GameServer gameServer = new GameServer();
        gameServer.launchServer();
        //TODO: parametri, lettura da riga di comando dei parametri (TIMER PRINCIPALMENTE)
    }

    private GameServer(){
        this.gameSocketSvr=new GameSocketSvr(this);
        this.gameRMISvr=new GameRMISvr(this);
        this.waitingRoom= new WaitingRoom(this,TIMER,MIN_PLAYER_NUMBER,MAX_PLAYER_NUMBER);
        this.usernameToUserID =new HashMap<>();
        this.userIDToClientInterface =new HashMap<>();
        this.userIDToGameRoom =new HashMap<>();
        this.userIDInGameToStatusConnection =new HashMap<>();
    }

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
    //TODO: synchronized? A che livello?
    //------------------------Metodi usati dalle ClientInterface------------------------------------//

    public boolean isAlreadyInQueue(String requestedUsername) {
        return waitingRoom.isAlreadyInQueue(requestedUsername);
    }
    public boolean isPlayerDisconnected(String username){
        if(usernameToUserID.containsKey(username)&& !userIDInGameToStatusConnection.get(usernameToUserID.get(username))){
            return true;
        }
        else{
            return false;
        }

    }

    public boolean checkUserID(String userIDToReconnect) {
        if(usernameToUserID.containsValue(userIDToReconnect)&& !userIDInGameToStatusConnection.get(userIDToReconnect)){
            return true;
        }
        else{
            return false;
        }

    }

    public synchronized void addClientToWR(String playerUsername, ClientInterface clientInterface){
        assignIDToUsername(playerUsername);
        assignClientHandlerToID(playerUsername,clientInterface);
        Message infoIDMessage=new InfoID( usernameToUserID.get(playerUsername));
        try {
            clientInterface.sendMessage(infoIDMessage);
        }catch (RemoteException e) {
            //TODO: disconnect nel caso che il Player non sia ancora connesso praticamente
        }
        //Imposto a connesso il Player, a termine del timer verifico che siano ancora tutti connessi
        waitingRoom.addUserToRoom(playerUsername);
    }

    public synchronized void handleDisconnect(ClientInterface clientInterface){
        //Imposto il ClientHandler come Disconnesso, comunico inoltre alla singola GameRoom o WR che il singolo giocatore è disconnesso
        String userID;
        try {
            userID=clientInterface.getPlayerID();
            clientInterface.setConnection(false);
        } catch (RemoteException e) {
            userID=null;
            //TODO
        }
        userIDInGameToStatusConnection.replace(userID,false);
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
        closeConnection(userID);
    }

    public synchronized void handleReConnect(String userID,ClientInterface clientInterface){
        try {
            clientInterface.setConnection(false);
        } catch (RemoteException e) {
            //TODO
        }
        userIDToClientInterface.replace(userID,clientInterface);
        userIDInGameToStatusConnection.replace(userID,true);
        updatePlayerStatus(userID,true);
        userIDToGameRoom.get(userID).reConnectPlayer(userID);
        Message confirmationMessage=new InfoMatch("Bentornato nella partita!\n");       //TODO: modificare tipo e contenuto messaggio.
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
        Message confirmationMessage=new InfoMatch("Sei stato riaggiunto nella WR!\n");       //TODO: modificare tipo e contenuto messaggio.
        sendMessageToID(userID,confirmationMessage);
    }

    //Metodo probabilmente utile solo per i Client connessi con Socket, nel caso di RMI diventa "inutile", serve solo a marchiare il client disconnesso
    //Sui socket chiude forzatamente la connessione.
    public synchronized void closeConnection(String userID){
        try {
            userIDToClientInterface.get(userID).closeConnection();
        } catch (RemoteException e) {
            //TODO
        }
    }

    private void  assignIDToUsername(String playerUsername) {
        UUID uid=UUID.randomUUID();
        String playerID=uid.toString();
        usernameToUserID.put(playerUsername,playerID);
    }
    private void assignClientHandlerToID(String playerUsername, ClientInterface clientHandler){
        String playerID=usernameToUserID.get(playerUsername);
        userIDToClientInterface.put(playerID,clientHandler);
        try {
            clientHandler.setPlayerID(playerID);
        } catch (RemoteException e) {
            //TODO
        }
    }
    //Metodo in cui viene lanciato effettivamente il gioco, si crea una stanza per i giocatori che hanno effettuato il login e
    //si chiama il metodo per runnare la partita.
    public void newGameRoom(List<String> usernameList) {
        HashMap<String, String> userList=new HashMap<>();
        for(String username:usernameList){
            userList.put(username,usernameToUserID.get(username));
            userIDInGameToStatusConnection.put(usernameToUserID.get(username),true);
        }
        GameRoom gameRoom=new GameRoom(userList,this);
        for(String playerUsername:usernameList){
            String playerID=usernameToUserID.get(playerUsername);
            userIDToGameRoom.put(playerID,gameRoom);
        }
        gameRoom.setUpGame();
    }

    //------------------------Metodi usati per la gestione dei messaggi di rete------------------------------------//
    //Metodo per inviare un messaggio a tutti i giocatori di una partita (si richiede in ingresso una collection di userID)
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
    public synchronized void sendMessageToID(String userID, Message message) {
        try {
            userIDToClientInterface.get(userID).sendMessage(message);
        } catch (RemoteException e) {
            handleDisconnect(userIDToClientInterface.get(userID));
        }
    }

    //------------------------Metodi usati per la gestione dei messaggi in ingresso-----------------------------------//

    public synchronized void handleMessage(Message message) {
        switch(message.getType()) {
            case "clientRequest":
                ClientRequestMessage requestMessage=(ClientRequestMessage) message;
                handleRequest(requestMessage);
                break;
        }
    }

    private void handleRequest(ClientRequestMessage requestMessage) {
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
            {
                int intero = Integer.parseInt(requestMessage.getInfo());
                userIDToGameRoom.get(requestMessage.getUserID()).getIndexShoot(requestMessage.getUserID(),intero);
            }
        }
    }


    //-------------------------------Metodi da completare----------------------------//

    private void closeServer(){         //TODO: le connessioni vanno chiuse (capire dove)
        gameSocketSvr.close();
    }


}










