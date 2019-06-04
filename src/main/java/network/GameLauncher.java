package network;

//TODO: Main del tutto temporaneo

import network.client.Client;
import network.server.GameServer;
import utils.GetData;

import java.util.Scanner;

public class GameLauncher{

    public static void main(String[] args) {
        GetData getData = new GetData();
        //Main temporaneo, scelta tra connectionHandler e server a primo avvio
        System.out.println("Inserire 0 per avviare Server, 1 per avviare Client:\t");
        int userChoice=getData.getInt(0, 1);
        if(userChoice==0){
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
