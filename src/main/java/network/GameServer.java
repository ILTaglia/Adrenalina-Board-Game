package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


//la classe unisce sia il server Socket che RMI, in questo modo ho il vantaggio di poter gestire contemporaneamente
//entrambe le tipologie di connessione da parte dei client

//TODO: per ora presente solo tecnologia Socket
public class GameServer {

    private static final int MIN_PLAYER_NUMBER = 3;
    private static final int MAX_PLAYER_NUMBER = 5;


    private int socketServerPort=7218;
    private WaitingRoom waitingRoom;        //Lista di giocatori in attesa

    private GameSocketSvr gameSocketSvr;
    //private RMIServer rmiServer;


    public static void main() throws IOException {
        //Per ora implemento Socket, ci sarà da completare con RMI
        try {
            GameServer gameServer = new GameServer();
            gameServer.LaunchServer();                  //TODO: parametri, lettura da file dei parametri
        }catch (Exception e){
            System.out.println("Ciaone Errore 1");
        }
    }
    private void GameServer(){
        this.waitingRoom= new WaitingRoom();


        this.gameSocketSvr=new GameSocketSvr(this);
    }

    private void LaunchServer(){
        gameSocketSvr.start(socketServerPort);
        gameSocketSvr.run();

    }
}








