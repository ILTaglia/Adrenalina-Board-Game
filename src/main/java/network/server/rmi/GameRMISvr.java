package network.server.rmi;

import network.server.GameServer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static utils.Print.printOut;

public class GameRMISvr implements Remote {

    private GameServer gameServer;

    public GameRMISvr(GameServer gameServer){
        this.gameServer=gameServer;
    }

    public void start(int port) throws RemoteException {
        ServerImplementation serverImplementation= new ServerImplementation(gameServer);
        try {
            Registry registry = LocateRegistry.createRegistry(port);
            registry.rebind("Server", serverImplementation );
        }catch (Exception e){
            printOut("Error, re launch Server.");
        }
        System.out.println("RMI ON");
    }

}
