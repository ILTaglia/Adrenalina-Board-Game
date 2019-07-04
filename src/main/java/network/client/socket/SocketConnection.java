package network.client.socket;

import network.messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static utils.Print.printOut;

public class SocketConnection extends Thread {
    /**
     * Class for Socket connection. It sends messages to the Socket Handler (connectionHandler)
     * host is the host ID
     * port is the port for connection
     * clientSocket is the Socket
     * connectionEstablished is a boolean to check wheteìher the connection is established or not
     */
    private SocketHandler connectionHandler;
    private String host;
    private int port;
    private Socket clientSocket;
    private boolean connectionEstablished;

    /**
     * Stream for serialization and de-serialization
     */
    private ObjectInputStream streamIn;
    private ObjectOutputStream streamOut;

    /**
     *
     * @param host is the host ID
     * @param port is the port to set connection
     * @param connectionHandler is the handler for messages sent by the SocketConnection
     */
    public SocketConnection(String host, int port, SocketHandler connectionHandler){
        this.connectionHandler = connectionHandler;
        this.host=host;
        this.port=port;
        try{
            this.clientSocket = new Socket(host, port);
            connectionEstablished=true;
        }catch (IOException e){
            connectionEstablished=false;
        }
        try {
            this.streamOut = new ObjectOutputStream(clientSocket.getOutputStream());
            this.streamIn = new ObjectInputStream(clientSocket.getInputStream());
        }catch (IOException|NullPointerException e){
            connectionEstablished=false;
        }
    }

    /**
     * Method for running
     */
    @Override
    public void run(){
        try{
            while(connectionEstablished){
                Message message=(Message) streamIn.readObject();
                connectionHandler.handleMessage(message);
            }
        }catch (IOException |ClassNotFoundException e){
            connectionEstablished=false;
            connectionHandler.askToTryToReconnect();
        }
    }

    /**
     * Method for sending messages
     * @param message is the message to be sent
     */
    public synchronized void sendMessage(Message message){
        try {
            streamOut.reset();
            streamOut.writeObject(message);
            streamOut.flush();
        }catch (IOException e){
            connectionEstablished=false;
            connectionHandler.askToTryToReconnect();
        }
    }

    /**
     * Method to close socket
     */
    public void closeSocket() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            printOut("Failed to close connection");
        }
    }

}
