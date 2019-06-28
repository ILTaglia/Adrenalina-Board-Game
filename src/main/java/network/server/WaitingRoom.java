package network.server;

import network.server.GameServer;

import java.util.*;

public class WaitingRoom {

    private Queue<String> waitingClients;
    private GameServer gameServer;
    private final int minNumberPlayer;
    private final int maxNumberPlayer;
    private Timer timer;
    private final int queueTimer=30000;            //in ms queue timer is 60*10^(3) ms => 60 s


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
        System.out.println("New Client Added! Queue: ["+waitingClients+"]");
        if(waitingClients.size()==2){
            timer.cancel();
            newGameRoom();
        }
    }

    public void startTimer(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(waitingClients.size()<minNumberPlayer){
                    System.out.println("Only " + waitingClients.size()+" players in Queue, waiting for new players...");
                    startTimer();
                }
                else{//TODO: Metodo che verifichi che tutti sono ancora online???
                        newGameRoom();
                }
            }
        },queueTimer);

    }

    public void removePlayerInQueue(String playerUsername){
        waitingClients.remove(playerUsername);
    }

    public boolean isAlreadyInQueue(String requestedUsername) {
        return waitingClients.contains(requestedUsername);
    }

    //A scadenza di timer si lancia una nuova GameRoom e lì vengono aggiunti i giocatori.
    public void newGameRoom(){

        System.out.println("Now New Game Room");

        List<String> usernameList=new ArrayList<>();
        while(!waitingClients.isEmpty()){
            usernameList.add(waitingClients.poll());
        }
        gameServer.newGameRoom(usernameList);
    }


}

