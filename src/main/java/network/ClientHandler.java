package network;

import controller.Game;

import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private final Game serverController;          //TODO servirà implementare una classe oltre a Game?


    ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;

        serverController=new Game();

    }

    @Override
    public void run(){}
}
