package network.server.rmi;


import exceptions.UsernameAlreadyUsedException;
import network.messages.Message;
import network.server.ClientInterface;
import network.server.GameServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerImplementation extends UnicastRemoteObject implements ServerInterface{

    private GameServer gameServer;

    public ServerImplementation(GameServer gameServer) throws RemoteException {
        this.gameServer=gameServer;
    }


    @Override
    public void registerToQueue(String requestedUsername, ClientInterface clientInterface) throws UsernameAlreadyUsedException {
        if (gameServer.isAlreadyInQueue(requestedUsername)){
            throw new UsernameAlreadyUsedException();
        }
        else{
            gameServer.addClientToWR(requestedUsername,clientInterface);
        }
    }


    @Override
    public void handleMessage(Message message) {
        gameServer.handleMessage(message);
    }
}
