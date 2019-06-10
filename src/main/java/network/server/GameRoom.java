package network.server;

import controller.Game;
import model.PowCard;
import network.messages.*;
import network.messages.ClientRequest.ActionClientRequest;

import java.util.HashMap;
import java.util.Map;

import static utils.NotifyClient.registerNewGame;

public class GameRoom {

    //In questa stanza istanzio il controller e da qua inizia la partita in cui vengono aggiunti i giocatori ecc.

    private GameServer gameServer;
    private Game gameController;
    private HashMap<String,String> userList;
    private HashMap<String,String> userIDtoColor;       //TODO:Variabile sarebbe da evitare ma non saprei come

    public GameRoom(Map<String,String> userList, GameServer gameServer){
        this.gameServer=gameServer;
        this.userList=(HashMap<String, String>) userList;
        this.userIDtoColor=new HashMap<>();
        registerNewGame(userList.values(),this);
    }

    //------------------------Metodi per il set up iniziale della partita------------------------------------//

    //Metodo chiamato direttamente dal GameServer che da l'avvio della GameRoom
    //Nel metodo viene richiesto il colore al Player tramite un messaggio di richiesta
    public void setUpGame(){
        this.gameController=new Game(this);
        Message registrationRequest= new ColorGameRequest("This message is to require a color to Client");
        gameServer.sendMessageToAll(userList.values(),registrationRequest);
    }
    //Quando viene ricevuto il messaggio con i dettagli del PLayer questo metodo raccoglie i dati in un HashMap
    //Quando l'HashMap ha la dimensione pari al #utenti collegati allora si passa il tutto al Controller che istanzia i Player nel Model
    public void registerPlayerColor(String userID,String color) {
        if(!userIDtoColor.values().contains(color)){
            userIDtoColor.put(userID,color);
        }
        else{
            Message colorError= new ColorError("Color Already Used, please change it. Choose an other color:");
            gameServer.sendMessageToID(userID,colorError);
        }
        if(userIDtoColor.size()==userList.size()){
            System.out.println("Sono stati assegnati i seguenti colori ai rispettivi PlayerID");
            userIDtoColor.forEach((key, value) -> System.out.println(key + ":" + value));
            gameController.addPlayers(userList,userIDtoColor);
        }
    }
    //Metodo che viene chiamato dal Controller per la scelta della mappa. Viene scelta da parte del primo utente ad essersi collegato
    public void askToChooseMap(String userID){
        Message message=new MapGameRequest("This message is to ask to choose a Map to the first Player");
        gameServer.sendMessageToID(userID,message);
    }
    //Quando viene ricevuto il messaggio viene settata nella classe gameController la mappa scelta dal primo player
    public void setMapChoice(String mapRequired) {
        gameController.setMap(mapRequired);
    }

    //Metodo per la richiesta al PLayer per dove Spawnare
    public void askToChooseSpawnPoint(String userID){
        Message message=new SpawnGameRequest("This message is to ask to choose a SpawnPoint");
        gameServer.sendMessageToID(userID,message);
    }

    public void setSpawnPoint(String userID, PowCard powCard) {
        gameController.setSpawn(userID,powCard);
    }


    public void askToChooseNextAction(String userID) {
        Message message=new ActionGameRequest("Choose an action between these:");
        gameServer.sendMessageToID(userID,message);
    }

    public void performAction(String userID,int chosenAction){
        gameController.performAction(userID,chosenAction); //TODO: DA FARE
    }
}
