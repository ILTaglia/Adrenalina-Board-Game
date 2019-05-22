package network;

import client.View;

import java.util.Scanner;

public class Client {
    
    private Client client;
    private boolean isToUseSocket;
    private View view;
    private String username;

    private NetworkHandler networkHandler;

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

        }
        else{

        }
    }

    public void setUsername(String username){

    }

}
