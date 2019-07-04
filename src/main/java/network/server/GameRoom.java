package network.server;

import controller.Game;
import model.Coordinate;
import model.Player;
import network.messages.*;
import network.messages.error.ColorError;
import network.messages.error.PaymentError;
import network.messages.gameRequest.*;
import network.messages.playerDataMessage.Winner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utils.NotifyClient.registerNewGame;
import static utils.Print.printOut;

public class GameRoom {


    /**
     * In this room controller is instantiated. Here the match starts, players are added
     */
    private GameServer gameServer;
    private Game gameController;
    private HashMap<String,String> userList;
    private HashMap<String,String> userIDtoColor;
    private final int inGameTimer;

    public GameRoom(Map<String,String> userList, GameServer gameServer, int inGameTimer){
        this.inGameTimer=inGameTimer;
        this.gameServer=gameServer;
        this.userList=(HashMap<String, String>) userList;
        this.userIDtoColor=new HashMap<>();
        registerNewGame(userList.values(),this);
    }

    //------------------------Metodi per il set up iniziale della partita------------------------------------//

    //Metodo chiamato direttamente dal GameServer che da l'avvio della GameRoom
    //Nel metodo viene richiesto il colore al Player tramite un messaggio di richiesta

    /**
     * Method called directly fromGameServer that start GameRoom
     * In the method the player is required which color he wants to have with a request message
     */
    public void setUpGame(){
        this.gameController=new Game(this,inGameTimer);
        Message registrationRequest= new ColorGameRequest("This message is to require a color to Client");
        gameServer.sendMessageToAll(userList.values(),registrationRequest);
    }
    //Quando viene ricevuto il messaggio con i dettagli del PLayer questo metodo raccoglie i dati in un HashMap
    //Quando l'HashMap ha la dimensione pari al #utenti collegati allora si passa il tutto al Controller che istanzia i Player nel Model

    /**
     * When the message with details of player is received, when HashMap has the same size of the number of users connected, all the Controller is
     * passed, and players are instantiated in the model
     * @param userID is the userID
     * @param color is the color
     */
    public void registerPlayerColor(String userID,String color) {
        if(!userIDtoColor.values().contains(color)){
            userIDtoColor.put(userID,color);
        }
        else{
            Message colorError= new ColorError("Color Already Used, please change it. Choose an other color:");
            gameServer.sendMessageToID(userID,colorError);
        }
        if(userIDtoColor.size()==userList.size()){
            printOut("Sono stati assegnati i seguenti colori ai rispettivi PlayerID");
            userIDtoColor.forEach((key, value) -> printOut(key + ":" + value));
            gameController.addPlayers(userList,userIDtoColor);
        }
    }

    /**
     *
     * @param userID is the userID to disconnect
     */
    public void disconnectPlayer(String userID) {
        gameController.setPlayerDisconnected(userID);
    }

    /**
     *
     * @param userID is the userID to reconnect
     */
    public void reConnectPlayer(String userID) {
        gameController.reConnectPlayer(userID);
    }

    /**
     *
     * @param userID is the userID to whom close connection
     */
    public void closeConnection(String userID){
        gameServer.closeConnection(userID);
    }

    /**
     *
     * @param userID is the userID to send the message
     * @param message is the message to be sent
     */
    public void sendErrorMessage(String userID,Message message){
        gameServer.sendMessageToID(userID,message);
    }

    //Metodo che viene chiamato dal Controller per la scelta della mappa. Viene scelta da parte del primo utente ad essersi collegato

    /**
     *
     * @param userID is the userID to be asked the map (it is the first player connected in the match)
     */
    public void askToChooseMap(String userID){
        Message message=new MapGameRequest("This message is to ask to choose a Map to the first Player");
        gameServer.sendMessageToID(userID,message);
    }
    //Quando viene ricevuto il messaggio viene settata nella classe gameController la mappa scelta dal primo player

    /**
     * When the message is received in GameController class the chosen map is set
     * @param mapRequired is the int for the required map
     */
    public void setMapChoice(int mapRequired) {
        gameController.setMap(mapRequired);
    }

    //Metodo per la richiesta al PLayer per dove Spawnare

    /**
     * Method to ask the player where he wants to spawn
     * @param userID is the userID to whom choose where to spawn
     */
    public void askToChooseSpawnPoint(String userID){
        Message message=new SpawnGameRequest("This message is to ask to choose a SpawnPoint");
        gameServer.sendMessageToID(userID,message);
    }

    /**
     *
     * @param userID is the user to be set with the SpawnPoint
     * @param coordinate is the coordinate where to spawn
     * @param powCardIndex is the chosen PowCard to spawn
     */
    public void setSpawnPoint(String userID, Coordinate coordinate,int powCardIndex) {
        gameController.setSpawn(userID,coordinate,powCardIndex);
    }

    /**
     *
     * @param userID is the userID tho whom ask the next action
     */
    public void askToChooseNextAction(String userID) {
        Message message=new ActionGameRequest("Choose an action between....");
        gameServer.sendMessageToID(userID,message);
    }

    /**
     *
     * @param userID is the userID to whom perform action
     * @param chosenAction is the index of the chosen action
     */
    public void performAction(String userID,int chosenAction){
        gameController.performAction(userID,chosenAction);
    }

    /**
     *
     * @param userID is the userID to whom ask destination for running
     */
    public void askDestinationRun(String userID){
        Message message=new RunDirectionGameRequest("Choose direction");
        gameServer.sendMessageToID(userID,message);
    }

    /**
     *
     * @param userID is the userID to be performed with running
     * @param direction is the direction in where to run
     */
    public void performRun(String userID, List<String> direction){
        gameController.performRun(userID,direction);

    }

    /**
     *
     * @param userID is the userID to whom ask which weapon to grab
     */
    public void askWeaponGrab(String userID) {
        Message message=new WeaponGrabGameRequest("Choose which weapon grab");
        gameServer.sendMessageToID(userID,message);
    }

    /**
     *
     * @param userID is the userID to be performed with grabbing weapon
     * @param indexWeapon is the index of weapon to grab
     */
    public void performWeaponGrab(String userID,int indexWeapon){
        gameController.performWeaponGrab(userID,indexWeapon);
    }

    /**
     *
     * @param userID is the userID of the player to grab weapon with a PowCard
     */
    public void askWeaponGrabWithPowCard(String userID) {
        Message message=new PowToWeaponGrabGameRequest("You not have enough Ammos to grab weapon, do you want to use PowCard to grab?");
        gameServer.sendMessageToID(userID,message);
    }

    /**
     *
     * @param userID is the userID to whom perform grabbing weapon with PowCard
     * @param indexWeapon is the index of weapon to grab
     * @param indexPowCard is the index of PowCard to use to grab
     */
    public void performWeaponGrabWithPowCard(String userID,int indexWeapon,int indexPowCard){
        gameController.performWeaponGrabWithPowCard(userID,indexWeapon,indexPowCard);
    }

    /**
     *
     * @param userID is the userID that wants to discard a weapon, to grab an aother weapon
     * @param indexWeaponToGrab is the index of weapon to grab
     * @param indexWeaponToDiscard is the index of the PowCard to discard
     */
    public void discardWeaponCardToGrab(String userID, int indexWeaponToGrab,int indexWeaponToDiscard) {
        gameController.discardWeaponCardToGrab(userID,indexWeaponToGrab,indexWeaponToDiscard);
    }

    /**
     *
     * @param userID is the userID  that discards the PowCard
     * @param indexPowCard is the index of the PowCard to be discarded
     */
    public void discardPowCard(String userID, int indexPowCard) {
        gameController.discardPowCard(userID,indexPowCard);
    }

    /**
     *
     * @param userID is the userID
     * @param index is the index of weapon to shoot
     */
    public void getIndexShoot(String userID, int index)
    {
        gameController.wakeupshoot(index,userID);
    }

    /**
     *
     * @param userID is the userID
     * @param index is the index of PowCard to use
     */
    public void getMuxIndex(String userID, int index)
    {
        gameController.muxPow(index,userID);
    }

    /**
     *
     * @param userID is the userID to whom ask weapon
     */
    public void askWeapon(String userID)
    {
        Message message= new ShootingGunRequest("Insert the index of the gun you would like to use");
        gameServer.sendMessageToID(userID,message);
    }

    /**
     *
     * @param userID is the userID to whom ask the serie
     */
    public void askIndexSerie(String userID)
    {
        Message message = new ShootingSerieIndexRequest("Insert the index of the serie you want to use");
        gameServer.sendMessageToID(userID,message);
    }

    /**
     *
     * @param userID is the userID to inform of payment error
     */
    public void informPaymentError(String userID)
    {
        Message message = new PaymentError("You don't have the ammo to buy that");
        gameServer.sendMessageToID(userID,message);
    }

    /**
     *
     * @param userID is the userID to whom ask player index to attack
     */
    public void askPlayerIndex(String userID)
    {
        Message message= new PlayerIndexRequest("Insert the index of the player you want to attack");
        gameServer.sendMessageToID(userID,message);
    }

    /**
     *
     * @param userID is the userID to whom ask cell index to attack
     */
    public void askCellIndex(String userID)
    {
        Message message = new CellIndexRequest("Insert the index of the cell you would like to attack");
        gameServer.sendMessageToID(userID,message);
    }

    /**
     *
     * @param userID is the userID to whom ask for next attack
     */
    public void askNextAttack(String userID)
    {
        Message message=new NextAttackRequest("Digit 1 if you want to continue or 2 if you want to stop with the next attack");
        gameServer.sendMessageToID(userID,message);
    }

    /**
     *
     * @param userID is the userID to whom ask direction to shoot
     */
    public void askDirectionToShoot(String userID)
    {
        Message message = new DirectionRequest("Insert the index of the direction you want to choose");
        gameServer.sendMessageToID(userID,message);
    }

    /**
     *
     * @param userID is the userID to whom ask index of PowCard
     */
    public void askIndexPow(String userID)
    {
        Message message = new IndexPowRequest("Insert the type of powerup");
        gameServer.sendMessageToID(userID,message);
    }

    /**
     *
     * @param userID is the userID to whom ask position
     */
    public void askPos(String userID)
    {
        Message message = new PositionRequest("Insert the index of the position");
        gameServer.sendMessageToID(userID,message);
    }

    /**
     *
     * @param userID is the userID to whom ask direction
     */
    public void askDirection(String userID)
    {
        Message message = new DirRequest("Insert the index of the direction you want to choose");
        gameServer.sendMessageToID(userID,message);
    }

    /**
     *
     * @param userID is the userID to whom ask step
     */
    public void askStep(String userID)
    {
        Message message = new NumberStepRequest("Insert the number of steps");
        gameServer.sendMessageToID(userID,message);
    }

    /**
     *
     * @param userID is the userID to whom ask the index of player to move
     */
    public void askPlayer(String userID)
    {
        Message message = new MovePlayerRequest("Insert the index of the player");
        gameServer.sendMessageToID(userID,message);
    }

    /**
     *
     * @param userID is the userID to whom ask Scope pow
     */
    public void askScopePow(String userID)
    {
        Message message = new AskScope("Insert the index of the player you want to use the scope to, or press -1 not to use it");
        gameServer.sendMessageToID(userID,message);
    }

    /**
     *
     * @param userID is the userID to whom ask granade pow
     */
    public void askGranadePow(String userID)
    {
        Message message = new AskGranade("Insert 1 if you want to use the granade, 2 if you don't want");
        gameServer.sendMessageToID(userID,message);
    }

    /**
     *
     * @param index is the chosen index
     * @param userID is the userID to whom ask the selected scope index
     */
    public void getScopeIndex(int index, String userID)
    {
        gameController.useScope(index,userID);
    }

    /**
     *
     * @param index is the chosen index
     * @param userID is the userID to whom ask the selected granade index
     */
    public void getGranadeIndex(int index, String userID)
    {
        gameController.useGranade(index,userID);
    }


    /**
     *
     * @param winnerPlayerName is the player to whom declare victory
     */
    public void declareWinner(String winnerPlayerName) {
        Message winner= new Winner(winnerPlayerName);
        gameServer.sendMessageToAll(userList.values(),winner);
    }
}

