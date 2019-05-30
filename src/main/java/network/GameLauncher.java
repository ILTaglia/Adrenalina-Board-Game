package network;

//TODO: Main del tutto temporaneo

import network.client.Client;
import network.server.GameServer;

import java.util.Scanner;

public class GameLauncher{

    public static void main(String[] args) {
        //Main temporaneo, scelta tra connectionHandler e server a primo avvio
        Scanner userChoice;
        userChoice=new Scanner(System.in);
        System.out.println("Inserire 0 per avviare Server, 1 per avviare Client:\t");
        if(userChoice.nextInt()==0){
            GameServer.main(args);
            /*try {
                GameServer.main(args);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }*/
        }else{
            Client.main(args);
        }
    }
}
