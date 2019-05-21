package network;

import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class WaitingRoom {

    private Queue<String> waitingClients;
    private GameServer gameServer;
    private final int minNumberPlayer;
    private final int maxNumberPlayer;


    public WaitingRoom(GameServer server,int min,int max){
        waitingClients=new LinkedList<>();
        this.gameServer=server;
        this.minNumberPlayer=min;
        this.maxNumberPlayer=max;
    }
    public void addUserToRoom(String username){
        if(waitingClients.peek()==null){
            //START TIMER
        }
        waitingClients.add(username);
        if(waitingClients.size()==maxNumberPlayer){
            newGameRoom();
        }
        //TODO: creare lista di player in coda e aggiungere quest'ultimo
    }


    public boolean isAlreadyInQueue(String requestedUsername) {
        return waitingClients.contains(requestedUsername);
    }

    public void newGameRoom(){
        List<String> usernameList=new ArrayList<>();
        //for(String username:waitingClients){
            usernameList.addAll(waitingClients);
        //}
        gameServer.newGameRoom(usernameList);

        //TODO: Migliorare
    }

    //A scadenza di timer si lancia una nuova GameRoom e lì vengono aggiunti i giocatori. Controllare prima se numero
    //giocatori è sufficiente
}

