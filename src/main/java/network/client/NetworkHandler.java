package network.client;

import network.client.Client;
import network.messages.ConnectionError;
import network.messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

    //TODO Aggiungere Booleani per verifiche sulla connessione


public class NetworkHandler extends Thread{

    public Client client;
    private Socket clientSocket;

    //Stream per serializzazione|de-serializzazione
    private ObjectInputStream streamIn;
    private ObjectOutputStream streamOut;

    public NetworkHandler(String host, int port, Client client){
        this.client=client;
        try{
            this.clientSocket = new Socket(host, port);
        }catch (IOException e){
            //TODO: Implementare le catch
        }
        try {
            this.streamOut = new ObjectOutputStream(clientSocket.getOutputStream());
            this.streamIn = new ObjectInputStream(clientSocket.getInputStream());
        }catch (IOException|NullPointerException e){
            //TODO: Implementare le catch
        }
    }

    @Override
    public void run(){
        boolean bool=true;
        try{
            while(bool){
                Message message=(Message) streamIn.readObject();
                client.handleMessage(message);
            }
        }catch (IOException|ClassNotFoundException e){
            bool=false;
            //TODO
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
        }
    }

}
