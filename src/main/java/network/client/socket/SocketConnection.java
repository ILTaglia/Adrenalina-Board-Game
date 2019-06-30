package network.client.socket;

import network.messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static utils.printStream.printOut;

public class SocketConnection extends Thread {

    private SocketHandler connectionHandler;
    private String host;
    private int port;
    private Socket clientSocket;
    private boolean connectionEstablished;

    //Stream per serializzazione|de-serializzazione
    private ObjectInputStream streamIn;
    private ObjectOutputStream streamOut;

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

    //Metodo per inviare messaggi
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

    public void closeSocket() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            printOut("Failed to close connection");
        }
    }

}
