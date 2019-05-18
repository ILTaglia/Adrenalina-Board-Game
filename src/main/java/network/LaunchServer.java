package network;

import java.io.IOException;

public class LaunchServer {
    public static void launch() throws IOException {
        //Per ora implemento Socket, ci sarà da completare con RMI
        GameServer server = new GameServer(7218);
        try {
            server.run();
        } finally {
            server.close();
        }
    }
}

