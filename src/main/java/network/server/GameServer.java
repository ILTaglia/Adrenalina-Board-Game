package network.server;

import network.messages.InfoID;
import network.messages.Message;
import network.server.socket.ClientHandler;
import network.server.socket.GameSocketSvr;

import javax.sound.sampled.Line;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


//la classe unisce sia il server Socket che RMI, in questo modo ho il vantaggio di poter gestire contemporaneamente
//entrambe le tipologie di connessione da parte dei client

//TODO: per ora presente solo tecnologia Socket
public class GameServer {

    private static final int MIN_PLAYER_NUMBER = 3;
    private static final int MAX_PLAYER_NUMBER = 5;

    //----------------HashMap per Username e Client/ID----------------------//
    private HashMap<String,String> usernameToUserID;                        //Collega Username e IdPlayer
    private HashMap<String, ClientHandler> userIDToClientHandler;           //Non per forza utile
    private HashMap<String,String> userIDToIdGameRoom;                      //Collega IdPlayer e ID Partita in cui è inserito
    //Se non è ancora in GameRoom si potrebbe mettere il campo String a "WaitingRoom"
    //Così facendo eviterei di dovermi inventare altro per i WaitingPlayers
    //private HashMap<String,GameRoom> idGameRoomToGameRoom;                //TODO: quest'ultimo è evitabile secondo me

    //----------------------------------------------------------------------//

    private int socketServerPort=7218;
    private WaitingRoom waitingRoom;                                        //Stanza per i giocatori in attesa
    private ArrayList<GameRoom> gameRooms;                                  //Lista delle Stanze di Gioco in corso TODO: penso sia evitabile se si usa l'HashMap

    // Implementazione vera e propria dei Server
    private GameSocketSvr gameSocketSvr;
    //private RMIServer gameRMISvr;


    public static void main(String[] args) {
        GameServer gameServer = new GameServer();
        gameServer.launchServer();
        //TODO: parametri, lettura da file dei parametri

        //TODO: RMI
    }

    private GameServer(){
        this.gameSocketSvr=new GameSocketSvr(this);

        this.waitingRoom= new WaitingRoom(this,MIN_PLAYER_NUMBER,MAX_PLAYER_NUMBER);
        this.usernameToUserID =new HashMap<>();
        this.userIDToClientHandler=new HashMap<>();
    }

    private void launchServer(){
        gameSocketSvr.start(socketServerPort);
        gameSocketSvr.run();


    }

    //------------------------Metodi usati dal ClientHandler------------------------------------//

    public void handleMessage(Message message) {

    }

    public boolean isAlreadyInQueue(String requestedUsername) {     //TODO: synchronized? A che livello?
        return waitingRoom.isAlreadyInQueue(requestedUsername);
    }

    public synchronized void addClientToWR(ClientHandler clientHandler,String playerUsername){
        assignIDToUsername(playerUsername);
        assignClientHandlerToID(playerUsername,clientHandler);
        InfoID infoID=new InfoID("You are in Waiting Room. Your ID is:" + usernameToUserID.get(playerUsername));
        clientHandler.sendMessage(infoID);
        waitingRoom.addUserToRoom(playerUsername);
    }
    //TODO: possibile semplificare il tutto, meglio chiarezza o semplicità?
    private void  assignIDToUsername(String playerUsername) {
        UUID uid=UUID.randomUUID();
        String playerID=uid.toString();
        usernameToUserID.put(playerUsername,playerID);
    }
    private void assignClientHandlerToID(String playerUsername,ClientHandler clientHandler){
        String playerID=usernameToUserID.get(playerUsername);
        userIDToClientHandler.put(playerID,clientHandler);
    }





    public void newGameRoom(List<String> usernameList) {
        GameRoom gameRoom=new GameRoom(usernameList);
        //TODO: capire come aggiungere singoli player a stessa GameRoom nella HashMap
        //Poi costruire nuova GameRoom e istanziare il tutto
    }




    //-------------------------------Metodi da completare----------------------------//

    private void closeServer(){         //TODO: le connessioni vanno chiuse (capire dove)
        gameSocketSvr.close();
    }

    //TODO: implementare metodi disconnessione/gestione riconnessione
}










