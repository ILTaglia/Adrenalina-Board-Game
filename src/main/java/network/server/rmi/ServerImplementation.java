package network.server.rmi;


import exceptions.InvalidColorException;
import exceptions.InvalidUserIDException;
import exceptions.UsernameAlreadyUsedException;
import network.client.Client;
import network.messages.Message;
import network.server.ClientInterface;
import network.server.GameServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
//Server RMI, i metodi sono messi a disposizione del Client che li può chiamare direttamente attraverso la classe RMI Handler che vede il server (con comandi RMI)
public class ServerImplementation extends UnicastRemoteObject implements ServerInterface{

    /**
     * Server RMI, methods are for client that can call them directly by the class RMI handler that sees server with RMI commands
     */
    private GameServer gameServer;

    public ServerImplementation(GameServer gameServer) throws RemoteException {
        this.gameServer=gameServer;
    }

    //Il metodo serve al Client a registrarsi al server, chiede lo username e la backend (clientInterface) da registrare sul Server per poter interagire con il Client

    /**
     * Method for client to register to server, ask the username and the backend (clientInterface) to register on server to communicate with Client
     * @param requestedUsername is the requested username
     * @param clientInterface is the backend
     * @throws UsernameAlreadyUsedException if the username has already been collected
     */
    @Override
    public void registerToQueue(String requestedUsername, ClientInterface clientInterface) throws UsernameAlreadyUsedException {
        if(gameServer.hasPlayerDisconnected(requestedUsername)){
            try {
                clientInterface.requestToReconnect();
            }
            catch (RemoteException e) {
            }
        }
        else if (gameServer.isAlreadyInQueue(requestedUsername)){
            throw new UsernameAlreadyUsedException();
        }
        else{
            gameServer.addClientToWR(requestedUsername,clientInterface);
        }
    }

    /**
     *
     * @param userIDToReConnect is userID to reconnect
     * @param clientInterface is the backend
     * @throws InvalidUserIDException is the user is not valid for connection
     */
    @Override
    public void reConnectRequest(String userIDToReConnect, ClientInterface clientInterface) throws InvalidUserIDException{
        if(gameServer.checkUserID(userIDToReConnect)) {
            gameServer.handleReConnect(userIDToReConnect, clientInterface);
        }
        else throw new InvalidUserIDException();
    }

    //Questo metodo è utile nel caso in cui un Player non voglia entrare in partita ma voglia iniziarne un'altra seppur con lo stesso username

    /**
     * Method for a player that doesn't want to enter a match but wants to start an other one
     * @param username is the username of the client
     * @param clientInterface is the backend
     * @throws UsernameAlreadyUsedException is the name has already been chosen
     */
    @Override
    public void newRegistrationToQueue(String username, ClientInterface clientInterface) throws UsernameAlreadyUsedException {
        if (gameServer.isAlreadyInQueue(username)){
            throw new UsernameAlreadyUsedException();
        }
        else{
            gameServer.addClientToWR(username,clientInterface);
        }
    }

    /**
     *
     * @param userID is the userID to reconnect
     * @param clientInterface is the backend
     */
    public void reConnectAttempt(String userID, ClientInterface clientInterface){
        if (gameServer.checkUserID(userID)) {
            gameServer.handleReConnect(userID, clientInterface);
        } else {
            gameServer.reAddClientToWR(userID, clientInterface);
        }
    }


    //Metodo che serve al Client per mandare messaggi al Server che verranno gestiti e porteranno avanti il gioco.

    /**
     *
     * @param message is the message to be sent from Client to Server, these will manage the game
     */
    @Override
    public void handleMessage(Message message) {
        gameServer.handleMessage(message);
    }


}
