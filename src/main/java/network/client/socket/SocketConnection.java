package network.client.socket;

import network.client.ConnectionHandler;
import network.messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;

public class SocketConnection extends Thread {

    private SocketHandler connectionHandler;
    private Socket clientSocket;

    //Stream per serializzazione|de-serializzazione
    private ObjectInputStream streamIn;
    private ObjectOutputStream streamOut;

    public SocketConnection(String host, int port, SocketHandler connectionHandler){
        this.connectionHandler = connectionHandler;
        try{
            this.clientSocket = new Socket(host, port);
        }catch (IOException e){

        }
        try {
            this.streamOut = new ObjectOutputStream(clientSocket.getOutputStream());
            this.streamIn = new ObjectInputStream(clientSocket.getInputStream());
        }catch (IOException|NullPointerException e){
           //TODO
        }
    }

    @Override
    public void run(){
        boolean bool=true;
        try{
            while(bool){
                Message message=(Message) streamIn.readObject();
                connectionHandler.handleMessage(message);
            }
        }catch (IOException |ClassNotFoundException e){
            connectionHandler.tryToReconnect();
            bool=false;
            //System.out.println(e.getCause());
            //System.out.println(e.getMessage());
        }
    }

    //TODO: Verificare uso reset & flush
    //Metodo per inviare messaggi
    public synchronized void sendMessage(Message message){
        try {
            streamOut.reset();
            streamOut.writeObject(message);
            streamOut.flush();
        }catch (IOException e){
            System.out.println("Errore nell'invio del messaggio");
            connectionHandler.tryToReconnect();
        }
    }
}
