package network;

import network.client.Client;
import network.server.GameServer;
import utils.GetData;

public class GameLauncher{

    /**
     * Main launcher for the whole game
     * @param args from command line
     */
    public static void main(String[] args) {
        GetData getData = new GetData();
        //Main temporaneo, scelta tra connectionHandler e server a primo avvio
        System.out.println("Type 0 to start Server, 1 to start Client:\t");
        int userChoice=getData.getInt(0, 1);
        if(userChoice==0){
            GameServer.main(args);
        }else{
            Client.main(args);
        }
    }
}
