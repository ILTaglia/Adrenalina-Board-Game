package network.server;

import network.messages.InfoID;
import network.messages.Message;
import network.server.rmi.GameRMISvr;
import network.server.socket.GameSocketSvr;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


//la classe unisce sia il server Socket che RMI, in questo modo ho il vantaggio di poter gestire contemporaneamente
//entrambe le tipologie di connessione da parte dei connectionHandler

//TODO: per ora presente solo tecnologia Socket
public class GameServer {

    private static final int MIN_PLAYER_NUMBER = 3;
    private static final int MAX_PLAYER_NUMBER = 5;

    //----------------HashMap per Username e Client/ID----------------------//
    private HashMap<String,String> usernameToUserID;                        //Collega Username e IdPlayer
    private HashMap<String, ClientInterface> userIDToClientHandler;           //Non per forza utile
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

        //TODO: RMI
    }

    private GameServer(){
        this.gameSocketSvr=new GameSocketSvr(this);
        this.gameRMISvr=new GameRMISvr(this);

        this.waitingRoom= new WaitingRoom(this,MIN_PLAYER_NUMBER,MAX_PLAYER_NUMBER);
        this.usernameToUserID =new HashMap<>();
        this.userIDToClientHandler=new HashMap<>();
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


    }
    //TODO: synchronized? A che livello?
    //------------------------Metodi usati dalle ClientInterface------------------------------------//

    public void handleMessage(Message message) {

    }

    public boolean isAlreadyInQueue(String requestedUsername) {
        return waitingRoom.isAlreadyInQueue(requestedUsername);
    }

    public synchronized void addClientToWR(String playerUsername, ClientInterface clientInterface){
        assignIDToUsername(playerUsername);
        assignClientHandlerToID(playerUsername,clientInterface);
        InfoID infoID=new InfoID("You are in Waiting Room. Your ID is:" + usernameToUserID.get(playerUsername));
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
        userIDToClientHandler.put(playerID,clientHandler);
    }

    public void newGameRoom(List<String> usernameList) {
        GameRoom gameRoom=new GameRoom(usernameList);
        for(String playerUsername:usernameList){
            String playerID=usernameToUserID.get(playerUsername);
            userIDToIdGameRoom.put(playerID,gameRoom);
        }
    }

    //-------------------------------Metodi da completare----------------------------//

    private void closeServer(){         //TODO: le connessioni vanno chiuse (capire dove)
        gameSocketSvr.close();
    }

    //TODO: implementare metodi disconnessione/gestione riconnessione
}









