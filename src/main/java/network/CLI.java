package network;

//CLASSE DA UNIRE CON CLIView, questa è una versione utile solo per la parte di rete

import client.View;
import model.Player;
import network.client.Client;
import network.messages.ConnectionRequest;
import network.messages.Message;

import java.util.List;
import java.util.Scanner;

public class CLI  implements View {

    private Client client;


    public CLI(Client client){
        this.client=client;
    }
    //Si chiede all'utente quale tipologia di connessione voglia utilizzare.
    public void start(){
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
        login();
    }

    public void login(){
        Scanner username;
        username=new Scanner(System.in);
        System.out.println("Digitare proprio username:");
        Message message=new ConnectionRequest(username.next());
        client.sendMessage(message);

    }





    //I metodi da qui in poi sono solo per l'interfaccia, non li impelemtno in questa CLI "copia"

    @Override
    public void welcomeMessage(int idClient) {

    }

    @Override
    public void endMessage() {

    }

    @Override
    public void printMap() {

    }

    @Override
    public void showPlayerWeapons() {

    }

    @Override
    public void showSpawnPointWeapons() {

    }

    @Override
    public void notifyAttackedPlayer(Player attackedplayer) {

    }

    @Override
    public void showPlayerPows() {

    }

    @Override
    public void showPlayerPows(Player player) {

    }

    @Override
    public void showPlayerAmmos() {

    }

    @Override
    public int getWeaponCard() {
        return 0;
    }

    @Override
    public String getDirection() {
        return null;
    }

    @Override
    public List<String> getListDirection() {
        return null;
    }

    @Override
    public int getPowCard() {
        return 0;
    }

    @Override
    public int getWeaponCardtoAttack() {
        return 0;
    }

    @Override
    public void printPlayerMove() {

    }

    @Override
    public void printPlayerData() {

    }

    @Override
    public void printDamagedPlayer(int numberdamages, String attackerplayername) {

    }

    @Override
    public void printMarkedPlayer(int numbermarks, String attackerplayername) {

    }

    @Override
    public void printDamagerAndMarkerPlayer(int numberdamages, int numbermarks, String attackedplayername) {

    }
}
