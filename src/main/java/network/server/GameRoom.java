package network.server;

import controller.Game;
import network.messages.Message;
import network.messages.PlayerDataRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameRoom {

    //In questa stanza istanzio il controller e da qua inizia la partita in cui vengono aggiunti i giocatori ecc.

    //TODO: capire se i messaggi vadano gestiti direttamente qua o inoltrati al controller


    private GameServer gameServer;
    private Game gameController;
    private HashMap<String,String> userList;

    public GameRoom(Map<String,String> userList, GameServer gameServer){
        this.gameServer=gameServer;
        this.gameController=new Game();
        this.userList=(HashMap<String, String>) userList;
        registerPlayers();
        startGame();
    }


    //Metodo necessario per istanziare effettivamente dei player nel model e darne conto al client
    private void registerPlayers(){
        //TODO: SISTEMARE MESSAGGI
        Message registrationRequest= new PlayerDataRequest("This message is to require a color to Client");
        gameServer.sendMessageToAll(userList.keySet(),registrationRequest);
        chooseMap();                //Only for the first player
    }

    //Metodo necessario per la scelta della mappa, viene fatta richiesta a un solo client
    private void chooseMap(){

    }

    //Metodo necessario ad istanziare tutto ciò che è necessario per il primo turno
    private void startGame(){

    }


}
