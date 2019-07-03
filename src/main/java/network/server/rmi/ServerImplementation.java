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

    //Il metodo serve al Client a registrarsi al server, chiede lo username e la backend (clientInterface) da registrare sul Server per poter interagire con il Client
    @Override
    public void registerToQueue(String requestedUsername, ClientInterface clientInterface) throws UsernameAlreadyUsedException {
        if(gameServer.isPlayerDisconnected(requestedUsername)){
            try {
                clientInterface.requestToReconnect();
            } catch (RemoteException e) {

            }
        }
        if (gameServer.isAlreadyInQueue(requestedUsername)){
            throw new UsernameAlreadyUsedException();
        }
        else{
            gameServer.addClientToWR(requestedUsername,clientInterface);
        }
    }

    //Questo metodo è utile nel caso in cui un Player non voglia entrare in partita ma voglia iniziarne un'altra seppur con lo stesso username
    @Override
    public void newRegistrationToQueue(String username, ClientInterface clientInterface) throws UsernameAlreadyUsedException {
        if (gameServer.isAlreadyInQueue(username)){
            throw new UsernameAlreadyUsedException();
        }
        else{
            gameServer.addClientToWR(username,clientInterface);
        }
    }

    //Metodo che serve al Client per mandare messaggi al Server che verranno gestiti e porteranno avanti il gioco.
    @Override
    public void handleMessage(Message message) {
        gameServer.handleMessage(message);
    }


}
