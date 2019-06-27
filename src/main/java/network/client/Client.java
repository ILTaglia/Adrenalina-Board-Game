package network.client;

import client.CLIView;
import client.View;
import client.gui.GUIView;
import client.gui.GUIViewAdapter;
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
            Client client=new Client();
            View guiview =new GUIViewAdapter(client);
            client.addView(guiview);
            guiview.start();
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
        }
    }

    private void handleInfoMessage(Message message) {
        switch (message.getContent()){
            case "InfoID":
                view.showInfoMessage(message);
                this.userID =message.getInfo();
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
                    //Gestita non dal Player //TODO
                }
                break;
            case "NewWeaponCard":
                view.showInfoMessage(message);
                try {
                    this.playerVisibleData.getPlayer().addWeapon(((NewWeaponCard) message).getWeaponCard());
                } catch (MaxNumberofCardsException e) {
                    //Gestita non dal Player //TODO
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
        if(message.getContent().equals("ReConnectionRequest")){
            view.askToReConnect();
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
            //Nothing to do, just info. //TODO
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
