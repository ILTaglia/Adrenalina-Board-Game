package network;

import controller.Game;
import network.Messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final GameSocketSvr server;


    //Stream per serializzazione|de-serializzazione
    private ObjectInputStream streamIn;
    private ObjectOutputStream streamOut;



    ClientHandler(GameSocketSvr server,Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.server = server;
        try {
            this.streamOut = new ObjectOutputStream(clientSocket.getOutputStream());
            this.streamIn = new ObjectInputStream(clientSocket.getInputStream());
        }catch(IOException e){
            System.out.println(e.getMessage());     //TODO:LOGGER
        }
    }

    @Override
    public void run(){
        try{
            while(true){
                Message message=(Message) streamIn.readObject();
                server.addClientToWR(clientSocket, message.getUsername());

            }
        }catch (IOException|ClassNotFoundException e){
            //TODO: Chiudere la connessione (?)
        }


        //TODO: si leggono i messaggi in arrivo dal client e si aggiunge lo user alla waitingRoom
        // (richiamando i metodi delle classi "superiori")


    }

}
