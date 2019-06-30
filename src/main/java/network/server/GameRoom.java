package network.server;

import controller.Game;
import model.Coordinate;
import network.messages.*;
import network.messages.error.ColorError;
import network.messages.error.PaymentError;
import network.messages.gameRequest.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utils.NotifyClient.registerNewGame;

public class GameRoom {

    //In questa stanza istanzio il controller e da qua inizia la partita in cui vengono aggiunti i giocatori ecc.

    private GameServer gameServer;
    private Game gameController;
    private HashMap<String,String> userList;
    private HashMap<String,String> userIDtoColor;

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
        this.gameController=new Game(this,30000);                    //TODO: IMPORTANTE, lettura da args durata timer
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
    public void disconnectPlayer(String userID) {
        gameController.setPlayerDisconnected(userID);
    }

    public void reConnectPlayer(String userID) {
        gameController.reConnectPlayer(userID);
    }
    public void closeConnection(String userID){
        gameServer.closeConnection(userID);
    }

    public void sendErrorMessage(String userID,Message message){
        gameServer.sendMessageToID(userID,message);
    }

    //Metodo che viene chiamato dal Controller per la scelta della mappa. Viene scelta da parte del primo utente ad essersi collegato
    public void askToChooseMap(String userID){
        Message message=new MapGameRequest("This message is to ask to choose a Map to the first Player");
        gameServer.sendMessageToID(userID,message);
    }
    //Quando viene ricevuto il messaggio viene settata nella classe gameController la mappa scelta dal primo player
    public void setMapChoice(int mapRequired) {
        gameController.setMap(mapRequired);
    }

    //Metodo per la richiesta al PLayer per dove Spawnare
    public void askToChooseSpawnPoint(String userID){
        Message message=new SpawnGameRequest("This message is to ask to choose a SpawnPoint");
        gameServer.sendMessageToID(userID,message);
    }

    public void setSpawnPoint(String userID, Coordinate coordinate,int powCardIndex) {
        gameController.setSpawn(userID,coordinate,powCardIndex);
    }


    public void askToChooseNextAction(String userID) {
        Message message=new ActionGameRequest("Choose an action between....");
        gameServer.sendMessageToID(userID,message);
    }

    public void performAction(String userID,int chosenAction){
        gameController.performAction(userID,chosenAction);
    }

    public void askDestinationRun(String userID){
        Message message=new RunDirectionGameRequest("Choose direction "+"TODO per la View stampare le direzioni una volta ricevuto questo messaggio");
        gameServer.sendMessageToID(userID,message);
    }
    public void performRun(String userID, List<String> direction){
        gameController.performRun(userID,direction);

    }

    public void askWeaponGrab(String userID) {
        Message message=new WeaponGrabGameRequest("Choose which weapon grab"+"TODO per la View stampare le carte disponibili");
        gameServer.sendMessageToID(userID,message);
    }
    public void performWeaponGrab(String userID,int indexWeapon){
        gameController.performWeaponGrab(userID,indexWeapon);
    }

    public void askWeaponGrabWithPowCard(String userID) {
        Message message=new PowToWeaponGrabGameRequest("You not have enough Ammos to grab weapon, do you want to use PowCard to grab?");
        gameServer.sendMessageToID(userID,message);
    }
    public void performWeaponGrabWithPowCard(String userID,int indexWeapon,int indexPowCard){
        gameController.performWeaponGrabWithPowCard(userID,indexWeapon,indexPowCard);
    }

    public void discardWeaponCardToGrab(String userID, int indexWeaponToGrab,int indexWeaponToDiscard) {
        gameController.discardWeaponCardToGrab(userID,indexWeaponToGrab,indexWeaponToDiscard);
    }

    public void discardPowCard(String userID, int indexPowCard) {
        gameController.discardPowCard(userID,indexPowCard);
    }

    public void askweapon(String userID)
    {
        Message message= new ShootingGunRequest("Insert the index of the gun you would like to use");
        gameServer.sendMessageToID(userID,message);
    }

    public void askIndexSerie(String userID)
    {
        Message message = new ShootingSerieIndexRequest("Insert the index of the serie you want to use");
        gameServer.sendMessageToID(userID,message);
    }

    public void informPaymentError(String userID)
    {
        Message message = new PaymentError("You don't have the ammo to buy that");
        gameServer.sendMessageToID(userID,message);
    }

    public void askPlayerIndex(String userID)
    {
        Message message= new PlayerIndexRequest("Insert the index of the player you want to attack");
        gameServer.sendMessageToID(userID,message);
    }

    public void askCellIndex(String userID)
    {
        Message message = new CellIndexRequest("Insert the index of the cell you would like to attack");
        gameServer.sendMessageToID(userID,message);
    }

    public void askNextAttack(String userID)
    {
        Message message=new NextAttackRequest("Digit 1 if you want to continue or 2 if you want to stop with the next attack");
        gameServer.sendMessageToID(userID,message);
    }



}

