package network.client;

import client.View;
import network.CLI;
import network.messages.Message;

import java.util.Scanner;

public class Client {

    private boolean isToUseSocket;
    private View view;
    private String username;

    private NetworkHandler networkHandler;

    private final String serverIP="127.0.0.1";
    private final int serverPort=7218;


    public Client(){
        isToUseSocket=false;            //Default RMI
    }

    public static void main(String[] args){

        Scanner userChoice;
        userChoice=new Scanner(System.in);
        System.out.println("Inserire 0 per usare GUI, 1 per CLI:\t");

        //Ad ogni View si associa un Client e viceversa
        if(userChoice.nextInt()==1){
            Client client=new Client();
            CLI cli =new CLI(client);
            client.addView(cli);
            cli.start();
        }
        else{
            //Avvia GUI
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
            networkHandler=new NetworkHandler(serverIP,serverPort,this);
            networkHandler.start();
        }
        else{
            //TODO: implementare RMI
        }
    }

    public void sendMessage(Message message){
        networkHandler.sendMessage(message);
    }

    public void handleMessage(Message message){
        switch(message.getType()) {
            case "Response":
                view.showInfoMessage(message);
                break;
            case "Error":
                handleErrorMessage(message);
                break;
        }
    }
    public void handleErrorMessage(Message message){
        if(message.getContent().equals("ConnectionError")){
            view.showInfoMessage(message);
            view.login();
        }
    }

    //-------------------------------Metodi da completare----------------------------//

    public void setUsername(String username){           //TODO: potrebbe essere anche un ID, valutare scelta

    }

}
