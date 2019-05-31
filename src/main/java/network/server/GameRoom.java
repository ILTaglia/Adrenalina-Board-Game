package network.server;

import controller.Game;

import java.util.ArrayList;
import java.util.List;

public class GameRoom {

    //In questa stanza istanzio il controller e da qua inizia la partita in cui vengono aggiunti i giocatori ecc.

    //TODO: capire se i messaggi vadano gestiti direttamente qua o inoltrati al controller


    private GameServer server;
    private Game gameController;
    private ArrayList<String> players;

    public GameRoom(List<String> username){
        gameController=new Game();
        players.addAll(username);
        registerPlayers();
        startGame();
    }


    //Metodo necessario per istanziare effettivamente dei player nel model e darne conto al client
    private void registerPlayers(){
        chooseMap();                //Only for the first player
    }

    //Metodo necessario per la scelta della mappa, viene fatta richiesta a un solo client
    private void chooseMap(){

    }

    //Metodo necessario ad istanziare tutto ciò che è necessario per il primo turno
    private void startGame(){

    }


}
