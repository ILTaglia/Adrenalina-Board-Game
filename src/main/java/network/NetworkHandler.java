package network;

import network.Messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetworkHandler {

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
        istanziaStream();
    }

    public void istanziaStream(){
        try {
            this.streamOut = new ObjectOutputStream(clientSocket.getOutputStream());
            this.streamIn = new ObjectInputStream(clientSocket.getInputStream());
        }catch (IOException e){
            //TODO: Implementare le catch
        }
    }

    //TODO: Verificare uso reset & flush

    public void send(Message message){
        try {
            streamOut.reset();
            streamOut.writeObject(message);
            streamOut.flush();
        }catch (IOException e){
            System.out.println("Errore nell'invio del messaggio");
        }
    }
}
