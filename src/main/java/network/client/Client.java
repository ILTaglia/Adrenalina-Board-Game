package network.client;

import client.CLIView;
import client.View;
import exceptions.UsernameAlreadyUsedException;
import model.PlayerVisibleData;
import network.client.rmi.RMIHandler;
import network.client.socket.SocketHandler;
import network.messages.Message;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Client {
    private PlayerVisibleData playerVisibleData;
    private boolean isToUseSocket;
    private View view;
    private String userID;

    private ConnectionHandler connectionHandler;


    public Client(){
        isToUseSocket=false;            //Default RMI
    }

    public static void main(String[] args){

        Scanner userChoice;
        userChoice=new Scanner(System.in);
        System.out.println("Inserire 0 per usare GUIView, 1 per CLI:\t");

        //Ad ogni View si associa un Client e viceversa
        if(userChoice.nextInt()==1){
            Client client=new Client();
            View cliView =new CLIView(client);
            client.addView(cliView);
            cliView.start();
        }
        else{
            //Avvia GUIView
        }
        //Una volta avviata la View si devono fare le richieste che servono alla View e avviare le connessioni.

    }

    public PlayerVisibleData getPlayerVisibleData(){return this.playerVisibleData;}

    private void addView(View view){
        this.view=view;
    }

    public void setConnection(boolean socketRequired){
        isToUseSocket = socketRequired;
    }

    public void launchConnection(){
        if(isToUseSocket){
            String serverIP = "127.0.0.1";
            int serverPort = 7218;
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
    public void requestToWR(String username){
        try {
            connectionHandler.registerToWR(username);
        }catch (UsernameAlreadyUsedException e){
            //Stampare sulla view la presenza dell'errore
            view.showException(e.getMessage());
            view.login();
        }
    }


    public void sendMessage(Message message){
        connectionHandler.sendMessage(message);
    }

    public void handleMessage(Message message){
        switch(message.getType()) {
            case "confirmation":
                view.showInfoMessage(message);
                if(message.getContent().equals("InfoID")) this.userID =message.getInfo();
                break;
            case "error":
                view.showInfoMessage(message);
                handleErrorMessage(message);
                break;
            case "gameRequest":
                view.showInfoMessage(message);
                handleRequestMessage(message);
                break;
            case "InfoGame":
                handleInfoMessage(message);
                break;
        }
    }

    private void handleInfoMessage(Message message) {
        switch (message.getContent()){
            case "PlayerCreated":
                //Prendo classe dal messaggio, Player già incluso
                break;
            case "ListOfEnemies":
                //Man mano che i Player sono creati invio notifiche a tutti della loro creazione. Non semplice
            case "NewPowCard":

                break;
            case "NewWeaponCard":
                break;

        }
    }

    private void handleRequestMessage(Message message) {
        if(message.getContent().equals("ColorRequest")){
            view.createPlayer();
        }
        if(message.getContent().equals("MapRequest")){
            view.chooseMap();
        }
        if(message.getContent().equals("SpawnRequest")){
            view.chooseStartingCell();  //TODO:Sistemare funzione in base a quanto visto
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
    }

    private void handleErrorMessage(Message message){
        if(message.getContent().equals("ConnectionError")){
            view.login();
        }
        if(message.getContent().equals("ColorError")){
            view.createPlayer();
        }
        if(message.getContent().equals("ActionError")){
            //Nothing to do, not handled //TODO
        }
        if(message.getContent().equals("RunError")){
            view.chooseAction();
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
    }

    //-------------------------------Metodi da completare----------------------------//

    public String getUserID(){
        return this.userID;
    }


}
