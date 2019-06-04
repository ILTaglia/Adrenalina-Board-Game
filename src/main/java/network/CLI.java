package network;

//CLASSE DA UNIRE CON CLIView, questa è una versione utile solo per la parte di rete

import client.View;
import model.Player;
import network.client.Client;
import network.messages.*;

import java.util.List;
import java.util.Scanner;

//TODO: Classe da eliminare, inutilizzata

public class CLI {

    private Client client;

    /*

    public CLI(Client client){
        this.client=client;
    }
    //Si chiede all'utente quale tipologia di connessione voglia utilizzare.
    public void start(){
        setConnection();
        login();
    }

    public void setConnection(){
        Scanner userChoice;
        userChoice=new Scanner(System.in);
        System.out.println("Scegliere quale tipologia di connessione utilizzare:\t\n" +
                "1. RMI" +
                "2. Socket");
        int connectionChoice=userChoice.nextInt();

        if(connectionChoice==1){
            client.setConnection(false);
        }
        else{
            client.setConnection(true);
        }
        client.launchConnection();
    }

    public void login(){
        Scanner username;
        username=new Scanner(System.in);
        System.out.println("Digitare proprio username:");
        String user=username.next();
        client.requestToWR(user);
    }


    //TODO: i messaggi li creo direttamente sulla view o se ne occupa il client?
    public void createPlayer() {
        Scanner color;
        color=new Scanner(System.in);
        System.out.println("Digitare proprio colore:"+ "players available colors are blue, green, yellow, pink, grey");
        String colorRequired=color.next();
        while(!(colorRequired.equals("blue")||colorRequired.equals("green")||colorRequired.equals("yellow")||colorRequired.equals("pink")||colorRequired.equals("grey"))){
            System.out.println(colorRequired + "is not a color.");
            System.out.println("Choose a color:"+ "players available colors are blue, green, yellow, pink, grey");
            colorRequired=color.next();
        }
        ColorGameRequest colorRequest=new ColorGameRequest(colorRequired);
        colorRequest.setUserID(client.getUserID());
        System.out.println("Your required the color: "+ colorRequired);
        client.sendMessage(colorRequest);
    }

    @Override
    public void chooseMap() {
        //TODO Stampare le scelte e chiedere di inserire il numero della mappa  scelta
        System.out.println("Digitare mappa prescelta:"+ "codici disponibili (?)");
        Scanner map;
        map=new Scanner(System.in);
        String mapRequired=map.next();
        while(!(mapRequired.equals("0")||mapRequired.equals("1")||mapRequired.equals("2")||mapRequired.equals("3"))){
            System.out.println(mapRequired + "is not a map.");
            System.out.println("Digitare mappa prescelta:"+ "codici disponibili (?)");
            mapRequired=map.next();
        }
        //TODO: pensare a messaggi "di risposta" e non di conferma da Client a Server
        MapClientRequest mapRequest=new MapClientRequest(mapRequired);
        mapRequest.setUserID(client.getUserID());
        client.sendMessage(mapRequest);
    }


    public void showException(String message){
        System.out.println(message);
    }


    public void showInfoMessage(Message message){
        if(message.getContent().equals("InfoID")) {
            System.out.println("You are in Waiting Room. Your ID is:" + message.getInfo());
        }
        else {
            System.out.println("Message received:" + message.getInfo());
        }
    }
    */
}
