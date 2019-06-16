package network.server;

import network.messages.*;
import network.messages.clientRequest.ClientRequestMessage;
import network.messages.clientRequest.PowToWeaponGrabClientRequest;
import network.messages.clientRequest.RunClientRequest;
import network.messages.clientRequest.SpawnPointClientRequest;
import network.messages.playerDataMessage.InfoID;
import network.server.rmi.GameRMISvr;
import network.server.socket.GameSocketSvr;
import utils.NotifyClient;

import java.rmi.RemoteException;
import java.util.*;

import static utils.NotifyClient.registerServer;


//la classe unisce sia il server Socket che RMI, in questo modo ho il vantaggio di poter gestire contemporaneamente
//entrambe le tipologie di connessione da parte dei connectionHandler

public class GameServer {

    private static final int MIN_PLAYER_NUMBER = 3;
    private static final int MAX_PLAYER_NUMBER = 5;
    private NotifyClient notifyClient;

    //----------------HashMap per Username e Client/ID----------------------//
    private HashMap<String,String> usernameToUserID;                        //Collega Username e IdPlayer
    private HashMap<String, ClientInterface> userIDToClientInterface;           //Non per forza utile
    private HashMap<String,GameRoom> userIDToIdGameRoom;                      //Collega IdPlayer e Partita in cui è inserito
    //Se non è ancora in GameRoom si potrebbe mettere il campo String a "WaitingRoom"
    //Così facendo eviterei di dovermi inventare altro per i WaitingPlayers
    //private HashMap<String,GameRoom> idGameRoomToGameRoom;                //TODO: quest'ultimo è evitabile secondo me

    //----------------------------------------------------------------------//

    private int socketServerPort=7218;
    private int rmiServerPort=1099;
    private WaitingRoom waitingRoom;                                        //Stanza per i giocatori in attesa

    // Implementazione vera e propria dei Server
    private GameSocketSvr gameSocketSvr;
    private GameRMISvr gameRMISvr;


    public static void main(String[] args) {
        GameServer gameServer = new GameServer();
        gameServer.launchServer();
        //TODO: parametri, lettura da file dei parametri
    }

    private GameServer(){
        this.gameSocketSvr=new GameSocketSvr(this);
        this.gameRMISvr=new GameRMISvr(this);
        this.waitingRoom= new WaitingRoom(this,MIN_PLAYER_NUMBER,MAX_PLAYER_NUMBER);
        this.usernameToUserID =new HashMap<>();
        this.userIDToClientInterface =new HashMap<>();
        this.userIDToIdGameRoom=new HashMap<>();
    }

    private void launchServer(){
        gameSocketSvr.start(socketServerPort);
        gameSocketSvr.start();
        try {
            gameRMISvr.start(rmiServerPort);
        }catch (RemoteException e){

            System.out.println(e.getMessage());
        }
        registerServer(this);


    }
    //TODO: synchronized? A che livello?
    //------------------------Metodi usati dalle ClientInterface------------------------------------//

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
                userIDToIdGameRoom.get(requestMessage.getUserID()).registerPlayerColor(requestMessage.getUserID(),requestMessage.getInfo());
                break;
            case "MapRequest":
                userIDToIdGameRoom.get(requestMessage.getUserID()).setMapChoice(requestMessage.getInfo());
                break;
            case "SpawnPointRequest":
                SpawnPointClientRequest spawnPointRequest=(SpawnPointClientRequest) requestMessage;
                userIDToIdGameRoom.get(spawnPointRequest.getUserID()).setSpawnPoint(spawnPointRequest.getUserID(),spawnPointRequest.getCoordinate(),Integer.parseInt(spawnPointRequest.getInfo()));
                break;
            case "ActionRequest":
                userIDToIdGameRoom.get(requestMessage.getUserID()).performAction(requestMessage.getUserID(), Integer.parseInt(requestMessage.getInfo()));
                break;
            case "RunRequest":
                RunClientRequest runRequest=(RunClientRequest) requestMessage;
                userIDToIdGameRoom.get(runRequest.getUserID()).performRun(requestMessage.getUserID(),runRequest.getDirection());
                break;
            case "WeaponGrabRequest":
                userIDToIdGameRoom.get(requestMessage.getUserID()).performWeaponGrab(requestMessage.getUserID(),Integer.parseInt(requestMessage.getInfo()));
                break;
            case "WeaponDiscardToGrabRequest":
                userIDToIdGameRoom.get(requestMessage.getUserID()).discardWeaponCardToGrab(requestMessage.getUserID(),Integer.parseInt(requestMessage.getInfo()));
                break;
            case "PowToWeaponGrabRequest":
                PowToWeaponGrabClientRequest message=(PowToWeaponGrabClientRequest) requestMessage;
                userIDToIdGameRoom.get(message.getUserID()).performWeaponGrabWithPowCard(message.getUserID(),Integer.parseInt(message.getInfo()),Integer.parseInt(message.getIndexPowCard()));
                break;
            case "PowCardDiscardRequest":
                userIDToIdGameRoom.get(requestMessage.getUserID()).discardPowCard(requestMessage.getUserID(),Integer.parseInt(requestMessage.getInfo()));

        }
    }

    public boolean isAlreadyInQueue(String requestedUsername) {
        return waitingRoom.isAlreadyInQueue(requestedUsername);
    }

    public synchronized void addClientToWR(String playerUsername, ClientInterface clientInterface){
        assignIDToUsername(playerUsername);
        assignClientHandlerToID(playerUsername,clientInterface);
        InfoID infoID=new InfoID( usernameToUserID.get(playerUsername));
        try {
            clientInterface.sendMessage(infoID);
        }catch (RemoteException e) {
            e.printStackTrace();
        }
        waitingRoom.addUserToRoom(playerUsername);
    }

    //TODO: possibile semplificare il tutto, meglio chiarezza o semplicità?

    private void  assignIDToUsername(String playerUsername) {
        UUID uid=UUID.randomUUID();
        String playerID=uid.toString();
        usernameToUserID.put(playerUsername,playerID);
    }
    private void assignClientHandlerToID(String playerUsername, ClientInterface clientHandler){
        String playerID=usernameToUserID.get(playerUsername);
        userIDToClientInterface.put(playerID,clientHandler);
    }
    //Metodo in cui viene lanciato effettivamente il gioco, si crea una stanza per i giocatori che hanno effettuato il login e
    //si chiama il metodo per runnare la partita.
    public void newGameRoom(List<String> usernameList) {
        HashMap<String, String> userList=new HashMap<>();
        for(String username:usernameList){
            userList.put(username,usernameToUserID.get(username));
        }
        GameRoom gameRoom=new GameRoom(userList,this);
        for(String playerUsername:usernameList){
            String playerID=usernameToUserID.get(playerUsername);
            userIDToIdGameRoom.put(playerID,gameRoom);
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
                    //TODO
                }
            }
        });
    }
    //Metodo per inviare un messaggio a un giocatore specifico.
    public synchronized void sendMessageToID(String userID, Message message) {
        try {
            userIDToClientInterface.get(userID).sendMessage(message);
        } catch (RemoteException e) {
            //TODO
        }
    }



    //-------------------------------Metodi da completare----------------------------//

    private void closeServer(){         //TODO: le connessioni vanno chiuse (capire dove)
        gameSocketSvr.close();
    }



    //TODO: implementare metodi disconnessione/gestione riconnessione
}










