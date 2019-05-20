package network;

import controller.Game;

import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final GameSocketSvr server;




    ClientHandler(GameSocketSvr server,Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.server = server;



    }

    @Override
    public void run(){}
}
