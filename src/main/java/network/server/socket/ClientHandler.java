package network.server.socket;

import network.messages.ReConnectClientRequest;
import network.messages.error.ConnectionError;
import network.messages.Message;
import network.messages.gameRequest.ReConnectServerRequest;
import network.server.ClientInterface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static utils.printStream.printOut;

public class ClientHandler implements Runnable, ClientInterface {
    private final Socket clientSocket;
    private final GameSocketSvr server;
    private String playerID;
    private boolean connected;


    //Stream per serializzazione|de-serializzazione
    private ObjectInputStream streamIn;
    private ObjectOutputStream streamOut;

    public ClientHandler(GameSocketSvr server,Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;
        try {
            this.streamOut = new ObjectOutputStream(this.clientSocket.getOutputStream());
            this.streamIn = new ObjectInputStream(this.clientSocket.getInputStream());
        }catch(IOException e){
            System.out.println(e.getMessage());     //TODO:LOGGER
        }
        this.connected=true;
    }
    public void setPlayerID(String playerID){
        this.playerID=playerID;
    }

    @Override
    public String getPlayerID() {
        return this.playerID;
    }

    @Override
    public void run(){
        try{
            while(connected){
                String requestedUsername;
                Message message=(Message) streamIn.readObject();
                if(message.getType().equals("clientRequest")&&message.getContent().equals("ConnectionRequest")){
                    requestedUsername = message.getInfo();
                    //Controllo Username in primis sulla queue, altrimenti restituisce subito errore e si chiede un nuovo username.
                    if (server.isAlreadyInQueue(requestedUsername)) {
                        ConnectionError errorMessage = new ConnectionError("An other user has already this username in your Match, please change it");
                        sendMessage(errorMessage);
                        //Send error Message "An other user has already this username in your Match, please change it"
                    }
                    else if(server.isPlayerDisconnected(requestedUsername)) {
                        Message reConnectRequest = new ReConnectServerRequest();
                        sendMessage(reConnectRequest);
                    }
                    else {
                        //Si può evitare di controllare partite già iniziate perchè il connectionHandler è univocamente collegato con ID.
                        server.addClientToWR(this, requestedUsername);
                    }
                }
                else if(message.getType().equals("ConnectionMessage")&&message.getContent().equals("ReConnectRequest")){
                    ReConnectClientRequest reConnectRequest=(ReConnectClientRequest) message;
                    if(server.checkUserID(reConnectRequest.getInfo(),reConnectRequest.getUserIDToReconnect())){
                        setPlayerID(reConnectRequest.getUserIDToReconnect());
                        server.handleReconnect(reConnectRequest.getUserIDToReconnect(),this);
                    }
                    else{
                        ConnectionError errorMessage = new ConnectionError("userID not valid. Connect as new Player.");
                        sendMessage(errorMessage);
                    }
                }
                else if(message.getType().equals("ConnectionMessage")&&message.getContent().equals("ReConnectAttempt")){
                    if(message.getContent().equals(playerID)){

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

    @Override
    public void setConnection(boolean connected) {
        this.connected=connected;
    }

    @Override
    public void closeConnection() {
        try {
            this.clientSocket.close();
        } catch (IOException e) {
            printOut("Connection close Failed");
        }
    }

}
