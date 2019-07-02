package network.server.rmi;


import exceptions.UsernameAlreadyUsedException;
import network.messages.Message;
import network.server.ClientInterface;
import network.server.GameServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
//Server RMI, i metodi sono messi a disposizione del Client che li può chiamare direttamente attraverso la classe RMI Handler che vede il server (con comandi RMI)
public class ServerImplementation extends UnicastRemoteObject implements ServerInterface{

    private GameServer gameServer;

    public ServerImplementation(GameServer gameServer) throws RemoteException {
        this.gameServer=gameServer;
    }

    @Override
    public void registerToQueue(String requestedUsername, ClientInterface clientInterface) throws UsernameAlreadyUsedException {
        if(gameServer.isPlayerDisconnected(requestedUsername)){
            clientInterface.requestToReconnect();
        }
        if (gameServer.isAlreadyInQueue(requestedUsername)){
            throw new UsernameAlreadyUsedException();
        }
        else{
            gameServer.addClientToWR(requestedUsername,clientInterface);
        }
    }
    @Override
    public void newRegistrationToQueue(String username, ClientInterface clientInterface) throws UsernameAlreadyUsedException {
        if (gameServer.isAlreadyInQueue(username)){
            throw new UsernameAlreadyUsedException();
        }
        else{
            gameServer.addClientToWR(username,clientInterface);
        }
    }

    @Override
    public void handleMessage(Message message) {
        gameServer.handleMessage(message);
    }


}
