package network.server;

import network.server.GameServer;

import java.util.*;

public class WaitingRoom {

    private Queue<String> waitingClients;
    private GameServer gameServer;
    private final int minNumberPlayer;
    private final int maxNumberPlayer;
    private Timer timer;
    private final int queueTimer=600000;            //in ms queue timer is 60*10^(3) ms pari a 60 s


    public WaitingRoom(GameServer server,int min,int max){
        waitingClients=new LinkedList<>();
        this.gameServer=server;
        this.minNumberPlayer=min;
        this.maxNumberPlayer=max;
    }
    public void addUserToRoom(String username){
        if(waitingClients.isEmpty()){
            timer= new Timer();
            startTimer();
        }
        waitingClients.add(username);
        System.out.println(waitingClients);
        if(waitingClients.size()==maxNumberPlayer){
            timer.cancel();
            newGameRoom();
        }
    }

    public void startTimer(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(waitingClients.size()<minNumberPlayer){
                    startTimer();
                }
                else{
                    newGameRoom();
                }
            }
        },queueTimer);

    }


    public boolean isAlreadyInQueue(String requestedUsername) {
        return waitingClients.contains(requestedUsername);
    }

    public void newGameRoom(){
        List<String> usernameList=new ArrayList<>();
        while(!waitingClients.isEmpty()){
            usernameList.add(waitingClients.poll());
        }
        gameServer.newGameRoom(usernameList);
    }

    //A scadenza di timer si lancia una nuova GameRoom e lì vengono aggiunti i giocatori. Controllare prima se numero
    //giocatori è sufficiente
}

