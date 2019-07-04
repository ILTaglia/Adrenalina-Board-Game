package network.server.socket;

import network.messages.error.ConnectionError;
import network.messages.Message;
import network.messages.connection.ReConnectServerRequest;
import network.messages.error.SecondConnectionError;
import network.server.ClientInterface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static utils.Print.printOut;

public class ClientHandler implements Runnable, ClientInterface {
    private final Socket clientSocket;          //Canale di comunicazione
    private final GameSocketSvr server;
    private String playerID;
    private boolean connected;

    //Stream per serializzazione|de-serializzazione
    /**
     * Stream for serialization and deserialization
     */
    private ObjectInputStream streamIn;
    private ObjectOutputStream streamOut;

    public ClientHandler(GameSocketSvr server,Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;
        try {
            this.streamOut = new ObjectOutputStream(this.clientSocket.getOutputStream());
            this.streamIn = new ObjectInputStream(this.clientSocket.getInputStream());
        }catch(IOException e){
            printOut("Problem starting socket, please re launch Server.");
            printOut(e.getMessage());
        }
        this.connected=true;
    }

    /**
     *
     * @param playerID is the ID to be set
     */
    public void setPlayerID(String playerID){
        this.playerID=playerID;
    }

    /**
     *
     * @return the string of the playerID
     */
    @Override
    public String getPlayerID() {
        return this.playerID;
    }

    /**
     * Method for running clientHandler
     */
    @Override
    public void run(){
        try{
            while(connected){
                String requestedUsername;
                Message message=(Message) streamIn.readObject();
                if(message.getType().equals("Connection")) {
                    if (message.getContent().equals("ConnectionRequest")) {
                        requestedUsername = message.getInfo();
                        if (server.isPlayerDisconnected(requestedUsername)) {
                            requestToReconnect();
                        } else if (server.isAlreadyInQueue(requestedUsername)) {
                            ConnectionError errorMessage = new ConnectionError("An other user has already this username in your Match, please change it");
                            sendMessage(errorMessage);
                            //Send error Message "An other user has already this username in your Match, please change it"
                        }
                        //Controllo Username in primis sulla queue, altrimenti restituisce subito errore e si chiede un nuovo username.
                        else {
                            //Si può evitare di controllare partite già iniziate perchè il connectionHandler è univocamente collegato con ID.
                            server.addClientToWR(this, requestedUsername);
                        }
                    } else if (message.getContent().equals("ReConnectRequest")) {
                        if (server.checkUserID(message.getInfo())) {
                            setPlayerID(message.getInfo());
                            server.handleReConnect(message.getInfo(), this);
                        } else {
                            Message errorMessage = new SecondConnectionError("userID not valid. Connect as new Player.");
                            sendMessage(errorMessage);
                        }
                    } else if (message.getContent().equals("ReConnectAttempt")) {
                        if (server.checkUserID(message.getInfo())) {
                            setPlayerID(message.getInfo());
                            server.handleReConnect(message.getInfo(), this);
                        } else {
                            setPlayerID(message.getInfo());
                            server.reAddClientToWR(message.getInfo(), this);
                        }
                    } else if (message.getContent().equals("SecondConnectionRequest")) {
                        requestedUsername = message.getInfo();
                        if (server.isAlreadyInQueue(requestedUsername)) {
                            Message errorMessage = new SecondConnectionError("An other user has already this username in your Match, please change it");
                            sendMessage(errorMessage);
                            //Send error Message "An other user has already this username in your Match, please change it"
                        }
                        //Controllo Username in primis sulla queue, altrimenti restituisce subito errore e si chiede un nuovo username.
                        else {
                            //Si può evitare di controllare partite già iniziate perchè il connectionHandler è univocamente collegato con ID.
                            server.addClientToWR(this, requestedUsername);
                        }
                    }
                }
                else{
                    server.handleMessage(message);
                }
            }
        }catch (IOException|ClassNotFoundException e){
            server.handleDisconnect(this);
        }
    }

    /**
     *
     * @param message is the message to be sent
     */
    @Override
    public void sendMessage(Message message){
        try {
            streamOut.reset();
            streamOut.writeObject(message);
            streamOut.flush();
        }catch (IOException e){
            server.handleDisconnect(this);
        }
    }

    /**
     *
     * @param connected is the boolean to connect the client
     */
    @Override
    public void setConnection(boolean connected) {
        this.connected=connected;
    }

    /**
     * Method to close connection
     */
    @Override
    public void closeConnection() {
        try {
            this.clientSocket.close();
        } catch (IOException e) {
            printOut("Connection close Failed");
        }
    }

    /**
     * Method to request for reconnection
     */
    @Override
    public void requestToReconnect() {
        Message reConnectRequest = new ReConnectServerRequest();
        sendMessage(reConnectRequest);
    }

    /**
     * Method to set client connected, but not useful for socket
     */
    @Override
    public void setClientConnected() {
        //NOTHING TO DO IN SOCKET, NOT USED
    }


}
