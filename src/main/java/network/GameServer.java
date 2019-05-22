package network;

import java.io.IOException;
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

    //TODO: HashMap per Username e Client/ID
    private HashMap<String,String> usernameToUserID;         //Collega Username e IdPlayer
    private HashMap<String, ClientHandler> userIDToClientHandler;   //Non per forza utile
    private HashMap<String,String> userIDToIdGameRoom;   //Collega IdPlayer e ID Partita in cui è inserito
    //Se non è ancora in GameRoom si potrebbe mettere il campo String a "WaitingRoom"
    //Così facendo eviterei di dovermi inventare altro per i WaitingPlayers

    //private HashMap<String,GameRoom> idGameRoomToGameRoom;      //TODO: quest'ultimo è evitabile secondo me


    private int socketServerPort=7218;
    private WaitingRoom waitingRoom;        //Lista di giocatori in attesa

    private GameSocketSvr gameSocketSvr;
    //private RMIServer rmiServer;

    private ArrayList<GameRoom> gameRooms;


    public static void main(String[] args) {
        //try {
            GameServer gameServer = new GameServer();
            gameServer.launchServer();
            //TODO: parametri, lettura da file dei parametri
        /*
        }catch (Exception e) {
            System.out.println("Ciaone Errore 1");
        }
        //TODO: RMI
        */
    }

    private GameServer(){
        this.waitingRoom= new WaitingRoom(this,MIN_PLAYER_NUMBER,MAX_PLAYER_NUMBER);
        this.gameSocketSvr=new GameSocketSvr(this);
        usernameToUserID =new HashMap<>();
    }

    private void launchServer(){
        gameSocketSvr.start(socketServerPort);
        gameSocketSvr.run();

    }
    private void closeServer(){         //TODO: le connessioni vanno chiuse (capire dove)
        gameSocketSvr.close();
    }

    public void addClientToWR(ClientHandler clientHandler,String username){
        //TODO: modificare e usare clientHandler solo per associarlo con l'id, mettere in coda solo l'username
        //Aggiungere anche numero player min/max per la coda.
        waitingRoom.addUserToRoom(username);
    }

    public boolean isAlreadyInQueue(String requestedUsername) {     //TODO: synchronized? A che livello?
        return waitingRoom.isAlreadyInQueue(requestedUsername);
    }

    public synchronized void  assignIDtoUsername(String playerUsername) {
        UUID uid=UUID.randomUUID();
        String playerID=uid.toString();
        usernameToUserID.put(playerUsername,playerID);      //TODO:Test
    }

    public void newGameRoom(List<String> usernameList) {
        GameRoom gameRoom=new GameRoom(usernameList);
        //TODO: capire come aggiungere singoli player a stessa GameRoom nella HashMap
        //Poi costruire nuova GameRoom e istanziare il tutto
    }
    /*
    public static void main(String[] args) {
        String test="username casuale";
        GameServer server=new GameServer();
        server.assignIDtoUsername(test);
    }
    */
}



//TODO: implementare metodi disconnessione/gestione riconnessione







