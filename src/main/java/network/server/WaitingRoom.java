package network.server;

import java.util.*;

import static utils.Print.printOut;

public class WaitingRoom {

    /**
     * waitingClients for client waiting to act
     * gameServer is the gameServer
     * minNumberPlayer is the minimum number of players, maxNumberPLayer is the maximum number of players
     * timer to manage time
     * queue timer to manage the queue
     */
    private Queue<String> waitingClients;
    private GameServer gameServer;
    private final int minNumberPlayer;
    private final int maxNumberPlayer;
    private Timer timer;
    private final int queueTimer;            //in ms queue timer is 60*10^(3) ms => 60 s


    public WaitingRoom(GameServer server,int queueTimer,int min,int max){
        waitingClients=new LinkedList<>();
        this.gameServer=server;
        this.minNumberPlayer=min;
        this.maxNumberPlayer=max;
        this.queueTimer=queueTimer;
    }

    /**
     *
     * @param username is the username of the client to be added in the room
     */
    public void addUserToRoom(String username){
        waitingClients.add(username);
        printOut("New Client Added! Queue: "+waitingClients);
        if(waitingClients.size()==minNumberPlayer){
            timer= new Timer();
            printOut("Waiting " + (queueTimer/1000) + " seconds for new Players, then start match");
            startTimer();
        }
        if(waitingClients.size()==maxNumberPlayer){
            newGameRoom();
        }
    }

    /**
     * Method to start timer
     */
    private void startTimer(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(waitingClients.size()<minNumberPlayer){
                    printOut("Timer scaduto, meno giocatori del necessario");
                    timer.cancel();
                }
                else{
                    printOut("Timer scaduto, si inizia");
                    newGameRoom();
                }
            }
        },30000);
    }

    /**
     *
     * @param playerUsername is the username of the player to be removed from the queue
     */
    public void removePlayerInQueue(String playerUsername){
        waitingClients.remove(playerUsername);
        printOut(playerUsername+" è stato rimosso.");
    }

    /**
     *
     * @param requestedUsername is the username to be checked
     * @return a boolean, true if the player is in the queue
     */
    public boolean isAlreadyInQueue(String requestedUsername) {
        return waitingClients.contains(requestedUsername);
    }

    //A scadenza di timer si lancia una nuova GameRoom e lì vengono aggiunti i giocatori.

    /**
     * Method to create a new game room
     */
    public void newGameRoom(){
        printOut("Now New Game Room");
        timer.cancel();
        List<String> usernameList=new ArrayList<>();
        while(!waitingClients.isEmpty()){
            usernameList.add(waitingClients.poll());
        }
        gameServer.newGameRoom(usernameList);
    }


}

