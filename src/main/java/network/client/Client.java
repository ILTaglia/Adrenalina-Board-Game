package network.client;

import client.CLIView;
import client.View;
import exceptions.UsernameAlreadyUsedException;
import model.PlayerVisibleData;
import network.client.rmi.RMIHandler;
import network.client.socket.SocketHandler;
import network.messages.Message;
import network.messages.ClientRequest.VisitorRequest;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Client {
    private PlayerVisibleData PlayerVisibleDATA;
    private boolean isToUseSocket;
    private View view;
    private String userID;

    private ConnectionHandler connectionHandler;

    private final String serverIP="127.0.0.1";
    private final int serverPort=7218;


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

    public PlayerVisibleData getPlayerVisibleDATA(){return this.PlayerVisibleDATA;}

    public void addView(View view){
        this.view=view;
    }

    public void setConnection(boolean socketRequired){
        isToUseSocket = socketRequired;
    }

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
            case "Confirmation":
                view.showInfoMessage(message);
                if(message.getContent().equals("InfoID")) this.userID =message.getInfo();
                break;
            case "Error":
                view.showInfoMessage(message);
                handleErrorMessage(message);
                break;
            case "GameRequest":
                view.showInfoMessage(message);
                handleRequestMessage(message);
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
        if(message.getContent().equals("MaxWeaponCardError")){
            view.chooseDiscardWeapon();
        }
    }

    //-------------------------------Metodi da completare----------------------------//

    public String getUserID(){
        return this.userID;
    }


}
