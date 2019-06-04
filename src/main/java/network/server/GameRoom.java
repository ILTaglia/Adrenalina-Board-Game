package network.server;

import controller.Game;
import network.messages.ColorError;
import network.messages.ColorGameRequest;
import network.messages.MapGameRequest;
import network.messages.Message;

import java.util.HashMap;
import java.util.Map;

public class GameRoom {

    //In questa stanza istanzio il controller e da qua inizia la partita in cui vengono aggiunti i giocatori ecc.

    //TODO: capire se i messaggi vadano gestiti direttamente qua o inoltrati al controller


    private GameServer gameServer;
    private Game gameController;
    private HashMap<String,String> userList;
    private HashMap<String,String> userIDtoColor;       //Variabile sarebbe da evitare ma non saprei come

    public GameRoom(Map<String,String> userList, GameServer gameServer){

        this.gameServer=gameServer;
        this.userList=(HashMap<String, String>) userList;
        this.userIDtoColor=new HashMap<>();
    }

    //------------------------Metodi per il set up iniziale della partita------------------------------------//


    //Metodo necessario per istanziare effettivamente dei player nel model e darne conto al client
    public void setUpGame(){
        //TODO: SISTEMARE MESSAGGI
        //TODO: PRIMA SI CHIEDONO INFORMAZIONI PLAYER O PRIMA SI SCEGLIE LA MAPPA DA USARE?
        this.gameController=new Game(this);
        Message registrationRequest= new ColorGameRequest("This message is to require a color to Client");
        gameServer.sendMessageToAll(userList.values(),registrationRequest);
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
            gameController.addPlayers(userList,userIDtoColor);
            //print solo per verificare che i colori siano assegnati correttamente quando tutti hanno risposto
            System.out.println("OKAY");
            userIDtoColor.forEach((key, value) -> System.out.println(key + ":" + value));
        }
    }

    //Metodo necessario per la scelta della mappa, viene fatta richiesta a un solo client
    private void askToChooseMap(String userID){
        Message message=new MapGameRequest("This message is to ask to choose a Map to the first Player");
        gameServer.sendMessageToID(userID,message);
    }

    public void setMapChoice(String mapRequired) {
        gameController.setMap(mapRequired);
    }
}
