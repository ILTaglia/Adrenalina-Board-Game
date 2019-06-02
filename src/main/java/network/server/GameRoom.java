package network.server;

import controller.Game;
import network.messages.ColorError;
import network.messages.Message;
import network.messages.PlayerDataRequest;

import java.util.HashMap;
import java.util.Map;

public class GameRoom {

    //In questa stanza istanzio il controller e da qua inizia la partita in cui vengono aggiunti i giocatori ecc.

    //TODO: capire se i messaggi vadano gestiti direttamente qua o inoltrati al controller


    private GameServer gameServer;
    private Game gameController;
    private HashMap<String,String> userList;
    private HashMap<String,String> userIDtoColor;

    public GameRoom(Map<String,String> userList, GameServer gameServer){
        this.gameServer=gameServer;
        this.userList=(HashMap<String, String>) userList;
        this.userIDtoColor=new HashMap<>();
        createGame();
        startGame();

    }


    //Metodo necessario per istanziare effettivamente dei player nel model e darne conto al client
    private void createGame(){
        this.gameController=new Game();
        //TODO: SISTEMARE MESSAGGI
        Message registrationRequest= new PlayerDataRequest("This message is to require a color to Client");
        gameServer.sendMessageToAll(userList.values(),registrationRequest);
        chooseMap();                //Only for the first player




    }

    public void registerPlayerColor(String userID,String color) {
        if(!userIDtoColor.values().contains(color)){
            userIDtoColor.put(userID,color);
        }
        else{
            Message colorError= new ColorError("Color Already Used, please change it. Choose an other color:");
            gameServer.sendMessageToID(userID,colorError);
        }
        if(userIDtoColor.size()==userList.size()){

            //TODO: creazione oggetto Player e aggiunta alla classe match

            System.out.println("OKAY");
            userIDtoColor.forEach((key, value) -> System.out.println(key + ":" + value));
        }
    }

    //Metodo necessario per la scelta della mappa, viene fatta richiesta a un solo client
    private void chooseMap(){

    }

    //Metodo necessario ad istanziare tutto ciò che è necessario per il primo turno
    private void startGame(){

    }



}
