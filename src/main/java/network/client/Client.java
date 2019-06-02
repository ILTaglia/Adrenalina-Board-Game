package network.client;

import client.View;
import exceptions.UsernameAlreadyUsedException;
import network.CLI;
import network.client.rmi.RMIHandler;
import network.client.socket.SocketHandler;
import network.messages.Message;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Client {

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
            CLI cli =new CLI(client);
            client.addView(cli);
            cli.start();
        }
        else{
            //Avvia GUIView
        }
        //Una volta avviata la View si devono fare le richieste che servono alla View e avviare le connessioni.

    }

    public void addView(View view){
        this.view=view;
    }

    public void setConnection(boolean socketRequired){
        if(socketRequired){
            isToUseSocket=true;
        }
        else{
            isToUseSocket=false;
        }
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
    /*Unico metodo di Login
    * nel caso di Socket si manda il messaggio con la richiesta di login e in caso di insuccesso si gestirà tramite altri messaggi
    * diverso il caso dell'RMI in cui gestisco con un try/catch, utile SOLO ALL'RMI (va bene così?)
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
            case "Response":
                view.showInfoMessage(message);
                if(message.getContent().equals("InfoID")) this.userID =message.getInfo();
                break;
            case "Error":
                handleErrorMessage(message);
                break;
            case "Request":
                handleRequestMessage(message);
                break;
        }
    }

    private void handleRequestMessage(Message message) {
        if(message.getContent().equals("PlayerDataRequest")){
            view.showInfoMessage(message);
            view.createPlayer();
        }
    }

    public void handleErrorMessage(Message message){
        if(message.getContent().equals("ConnectionError")){
            view.showInfoMessage(message);
            view.login();
        }
        if(message.getContent().equals("ColorError")){
            view.showInfoMessage(message);
            view.createPlayer();
        }
    }

    //-------------------------------Metodi da completare----------------------------//

    public String getUserID(){           //TODO: potrebbe essere anche un ID, valutare scelta
        return this.userID;
    }


}
