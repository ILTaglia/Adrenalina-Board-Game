package network.client;

import client.CLIView;
import client.View;
import client.gui.GUIViewAdapter;
import exceptions.InvalidUserIDException;
import exceptions.MaxNumberofCardsException;
import exceptions.UsernameAlreadyUsedException;
import model.PlayerVisibleData;
import network.client.rmi.RMIHandler;
import network.client.socket.SocketHandler;
import network.messages.Message;
import network.messages.playerDataMessage.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import static utils.Print.printOut;

public class Client {
    /**
     * Client is the client for communication Client-Server.
     *
     * PlayerVisibleData is a class that contains all the information that a single Client has to know regarding the match. A client
     * must know its own data and some (but not all) data about other players.
     *
     * isToUseSocket is a boolean to identify Socket Request for connection
     * userID is the String for userID
     * serverIP is the server IP address
     * serverPort is the serverPort
     * connectionHandler is the Handler for connection
     */
    private PlayerVisibleData playerVisibleData;
    private boolean isToUseSocket;
    private View view;
    private String userID;
    private String serverIP = "127.0.0.1";
    private int serverPort = 7218;

    private ConnectionHandler connectionHandler;


    /**
     * Client is automatically set on RMI
     */
    public Client(){
        isToUseSocket=false;            //Default RMI
    }

    /**
     * Main with no parameters a part for args
     * @param args are the supplied command-line arguments
     */
    public static void main(String[] args){

        Scanner userChoice;
        userChoice=new Scanner(System.in);
        printOut("Inserire 0 per usare GUIView, 1 per CLI:\t");             //MODIFICARE INSERIMENTO DATI
        //Ad ogni View si associa un Client e viceversa
        if(userChoice.nextInt()==1){
            Client client=new Client();
            View cliView =new CLIView(client);
            client.addView(cliView);
            cliView.start();
        }
        else{
            Client client=new Client();
            View guiview =new GUIViewAdapter(client);
            client.addView(guiview);
            guiview.start();
            //Avvia GUIView
        }
        //Una volta avviata la View si devono fare le richieste che servono alla View e avviare le connessioni.

    }

    /**
     * Method to have all the information for a Client to player the match
     * @return the class PlaterVisibleData that contains only visible information for every player
     */
    public PlayerVisibleData getPlayerVisibleData(){return this.playerVisibleData;}

    /**
     * Method to add view
     * @param view is the chosen view type to be added
     */
    private void addView(View view){
        this.view=view;
    }

    /**
     * Method to set connection if socket is required
     * @param socketRequired is teh boolean true if socket is required
     */
    public void setConnection(boolean socketRequired){
        isToUseSocket = socketRequired;
    }

    /**
     * Method to set connection with connectionHandler
     */
    public void setConnected() {
        connectionHandler.setConnected();
    }

    /**
     * Method to launch connection (socket or RMI according to the user choice)
     */
    public void launchConnection(){
        if(isToUseSocket){
            connectionHandler=new SocketHandler(serverIP, serverPort, this);
        }
        else{
            try {
                connectionHandler= new RMIHandler(this);
            } catch (RemoteException e) {
                e.printStackTrace();    //TODO: Solita roba delle eccezioni
            } catch (NotBoundException e) {
                e.printStackTrace();
            }
        }
    }
    /* Unico metodo di Login
    *  nel caso di Socket si manda il messaggio con la richiesta di login e in caso di insuccesso si gestirà tramite altri messaggi
    *  diverso il caso dell'RMI in cui gestisco con un try/catch, utile SOLO ALL'RMI (va bene così?)
    */

    /**
     * This is the only method for login. In case of Socket a message with Login request is sent and in case of failure, the connection
     * is managed with other messages. In case of RMI the situation is different, we choose to manage with a try/catch useful only
     * for RMI.
     * @param username is the username of the client to connect
     */
    public void requestToWR(String username){
        try {
            connectionHandler.registerToWR(username);
        }catch (UsernameAlreadyUsedException e){
            //Stampare sulla view la presenza dell'errore
            view.showException(e.getMessage());
            view.login();
        }
    }

    /**
     * Method to ask for reconnection
     * @param userIDToReConnect is the userID to reconnect
     */
    public void reConnectRequest(String userIDToReConnect) {
        try {
            connectionHandler.reConnectRequest(userIDToReConnect);
        }catch (InvalidUserIDException e) {
            view.showException(e.getMessage());
            view.askNewConnection();
        }

    }

    /**
     * Method for a new connection request
     * @param username is the username of the client
     */
    public void newConnectionRequest(String username){
        try{
            connectionHandler.newConnectionRequest(username);
        }catch (UsernameAlreadyUsedException e){
            //Stampare sulla view la presenza dell'errore
            view.showException(e.getMessage());
            view.askNewConnection();
        }

    }

    /**
     * Method to ask to try for reconnection. The user is asked if it wants to reconnect or not.
     */
    public void askToTryToReconnect() {
        //Chiedo all'utente se voglia riconnettersi
        if(view.askToTryToReConnect()){
            if(isToUseSocket){
                connectionHandler=new SocketHandler(serverIP, serverPort, this);
                connectionHandler.attemptToReconnect(userID);
            }
            else{
                try {
                    connectionHandler=new RMIHandler(this);
                } catch (RemoteException | NotBoundException e) {
                    askToTryToReconnect();
                }
            }
        }else{
            //Chiudo il client
            System.exit(0);
        }
    }


    /**
     * Method to send messages
     * @param message is the message to be sent
     */
    public void sendMessage(Message message){
        connectionHandler.sendMessage(message);
    }

    /**
     * Method to handle different types of generic message. Different methods are called in base of the type of message received.
     * Four default types of messages:
     * 1. error Messages
     * 2. gameRequest Messages
     * 3. infoGame Messages
     * 4. connection Messages
     * @param message is the received message
     */
    public void handleMessage(Message message){
        switch(message.getType()) {
            case "error":
                view.showInfoMessage(message);
                handleErrorMessage(message);
                break;
            case "gameRequest":
                view.showInfoMessage(message);
                handleRequestMessage(message);
                break;
            case "infoGame":
                handleInfoMessage(message);
                break;
            case "Connection":
                handleConnectionMessage(message);
                break;
        }
    }

    /**
     * Method to manage all the messages regarding information about the game (third type of message - infoGame - in the
     * previous method)
     * @param message is the infoGame message to be handled
     */
    private void handleInfoMessage(Message message) {
        switch (message.getContent()){
            case "InfoID":
                view.showInfoMessage(message);
                this.userID =message.getInfo();
                setConnected();
                break;
            case "NewPlayerData":
                view.showInfoMessage(message);
                this.playerVisibleData=((NewPlayerData) message).getPlayerVisibleData();
                //Prendo classe dal messaggio, Player già incluso
                break;
            case "OtherPlayerData":
                view.showInfoMessage(message);
                this.playerVisibleData.setEnemy(((OtherPlayerData) message).getPlayerName(),((OtherPlayerData) message).getPlayerColor());
                break;
                //Man mano che i Player sono creati invio notifiche a tutti della loro creazione.
            case "DashboardData":
                view.showInfoMessage(message);
                this.playerVisibleData.setDashboard(((DashboardData) message).getDashboard());
                break;
            case "NewPosition":
                view.showInfoMessage(message);
                this.playerVisibleData.getPlayer().setCel(((NewPosition)message).getCoordinate().getX(),((NewPosition)message).getCoordinate().getY());
                break;
            case "NewPowCard":
                view.showInfoMessage(message);
                try {
                    this.playerVisibleData.getPlayer().addPow(((NewPowCard) message).getPowCard());
                } catch (MaxNumberofCardsException e) {
                    //Not handled by Player
                }
                break;
            case "NewWeaponCard":
                view.showInfoMessage(message);
                try {
                    this.playerVisibleData.getPlayer().addWeapon(((NewWeaponCard) message).getWeaponCard());
                } catch (MaxNumberofCardsException e) {
                    //Not handled by Player
                }
                break;
            case "NewCardUsed":
                view.showInfoMessage(message);
                if(((NewCardUsed) message).getToC().equals("PowCard")){
                    playerVisibleData.getPlayer().removePow(((NewCardUsed) message).getIndexOfCard());
                }
                else{
                    playerVisibleData.getPlayer().removeWeapon(((NewCardUsed) message).getIndexOfCard());
                }
                break;
            case "NewAmmo":
                view.showInfoMessage(message);
                NewAmmo ammoMessage=(NewAmmo) message;
                for (int i=0;i<ammoMessage.getListAmmo().size();i++) {
                    playerVisibleData.setNumberOfAmmo(i,ammoMessage.getListAmmo().get(i));
                }
                break;
            case "InfoMatch":
                view.showInfoMessage(message);
                break;

        }
    }

    /**
     * Method to manage all the messages regarding request messages, so data from the client (type - RequestMessage)
     * @param message is the request message to be handled
     */
    private void handleRequestMessage(Message message) {
        if(message.getContent().equals("ColorRequest")){
            view.createPlayer();
        }
        if(message.getContent().equals("MapRequest")){
            view.chooseMap();
        }
        if(message.getContent().equals("SpawnRequest")){
            view.chooseStartingCell();
        }
        if(message.getContent().equals("ActionRequest")){
            view.chooseAction();
        }
        if(message.getContent().equals("RunDirectionRequest")){
            view.chooseRunDirection();
        }
        if(message.getContent().equals("WeaponGrabRequest")){
            view.chooseWeaponToGrab();
        }
        if(message.getContent().equals("PowToWeaponGrabRequest")){
            view.askUsePowToGrabWeapon();
        }
        if(message.getContent().equals("GunIndex"))
        {
            view.getGunIndex();
        }
        if(message.getContent().equals("serieIndex"))
        {
            view.getSerieIndex();
        }
        if(message.getContent().equals("PlayerIndex"))
        {
            view.getPlayerIndex();
        }
        if(message.getContent().equals("cellIndex"))
        {
            view.getCellIndex();
        }
        if(message.getContent().equals("nextAttack"))
        {
            view.getNextAttack();
        }
        if(message.getContent().equals("directionRequest"))
        {
            view.getNextAttack();
        }
        if(message.getContent().equals("indexPow"))
        {
            view.getPowIndex();
        }
        if(message.getContent().equals("indexPos"))
        {
            view.getPosIndex();
        }
        if(message.getContent().equals("indexDir"))
        {
            view.getDirectionIndex();
        }
        if(message.getContent().equals("indexStep"))
        {
            view.getNumberStep();
        }
        if(message.getContent().equals("indexPlayer"))
        {
            view.getMovePlayerIndex();
        }
        if(message.getContent().equals("idScope"))
        {
            view.getScopeIndex();
        }
        if(message.getContent().equals("idGranade"))
        {
            view.getGranadeIndex();
        }
    }

    /**
     * Method to manage all the messages regarding connection messages (type - ConnectionMessage)
     * @param message is the connection message to be handled
     */
    private void handleConnectionMessage(Message message){
        if(message.getContent().equals("ReConnectRequest")){
            view.askToReConnect();
        }
        if(message.getContent().equals("NewConnectionRequest")){
            view.askNewConnection();
        }
    }


    /**
     * Method to manage all the messages regarding error messages, useful for managing errors (type - ErrorMessage)
     * @param message is the error message to be handled
     */
    private void handleErrorMessage(Message message){
        if(message.getContent().equals("ConnectionError")){
            view.login();
        }
        if(message.getContent().equals("ColorError")){
            view.createPlayer();
        }
        if(message.getContent().equals("ActionError")){
            //Nothing to do, just info.
        }
        if(message.getContent().equals("RunError")){
            view.chooseRunDirection();
        }
        if(message.getContent().equals("GrabError")){
            view.chooseAction();
        }
        //TODO: questi due messaggi sono da spostare come RequestMessage
        if(message.getContent().equals("MaxWeaponCardError")){
            view.chooseDiscardWeapon();
        }
        if(message.getContent().equals("MaxPowCardError")){
            view.chooseDiscardPowCard();
        }
        if(message.getContent().equals("PaymentError"))
        {
            view.showPaymentError();
        }
    }

    /**
     * Method to have userID
     * @return the userID (String)
     */
    public String getUserID(){
        return this.userID;
    }


    public void close() {
        printOut("Sei stato disconnesso. Il gioco verrà chiuso, se vuoi riconnetterti dovrai inserire il tuo username seguito dal tuo ID.\nIl tuo ID è: "+userID);
        System.exit(0);
    }
}
