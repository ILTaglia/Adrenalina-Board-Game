package network.server.socket;

import network.messages.error.ConnectionError;
import network.messages.Message;
import network.server.ClientInterface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable, ClientInterface {
    private final Socket clientSocket;
    private final GameSocketSvr server;
    private String playerUsername;


    //Stream per serializzazione|de-serializzazione
    private ObjectInputStream streamIn;
    private ObjectOutputStream streamOut;

    public ClientHandler(GameSocketSvr server,Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;
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
            boolean bool=true;
            while(bool){
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
                    else {
                        playerUsername = requestedUsername;
                        //server.saveUser(playerUsername,this);  // Alternativamente lo può fare direttamente quando aggiungo al WR
                        //Si può evitare di controllare partite già iniziate perchè il connectionHandler è univocamente collegato con ID.
                        server.addClientToWR(this, playerUsername);
                    }
                }
                else{
                    server.handleMessage(message);
                }
            }
        }catch (IOException|ClassNotFoundException e){
            //TODO: Chiudere la connessione (?)
        }
    }
    @Override
    public void sendMessage(Message message){
        try {
            streamOut.reset();
            streamOut.writeObject(message);
            streamOut.flush();
        }catch (IOException e){
            System.out.println("Errore nell'invio del messaggio");
        }
    }

}
