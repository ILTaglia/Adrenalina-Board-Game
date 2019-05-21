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
    private String playerUsername;


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
                String requestedUsername=message.getUsername();
                //Controllo Username in primis sulla queue, altrimenti restituisce subito errore e si chiede un nuovo username.
                if(server.isAlreadyInQueue(requestedUsername)){
                    //Send Error Message "An other user has already this username in your Match, please change it"
                }
                playerUsername=requestedUsername;       //TODO: Assegnamento sufficiente?
                server.assignIDtoUsername(playerUsername);  // Alternativamente lo può fare direttamente quando aggiungo al WR
                //Si può evitare di controllare partite già iniziate perchè il client è univocamente collegato con ID.
                server.addClientToWR(this, playerUsername);
            }
        }catch (IOException|ClassNotFoundException e){
            //TODO: Chiudere la connessione (?)
        }


        //TODO: si leggono i messaggi in arrivo dal client e si aggiunge lo user alla waitingRoom
        // (richiamando i metodi delle classi "superiori")


    }

}
