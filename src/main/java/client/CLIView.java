package client;

import java.io.PrintStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CLIView implements View {
    private static final Logger LOGGER= Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static PrintStream printStream=System.out;

    public CLIView(){
        LOGGER.setLevel(Level.INFO);
    }

    @Override
    public void welcomeMessage(int idClient) {

    }

    @Override
    public void endMessage() {
        printStream.println("GAME OVER.");
    }

    @Override
    public void printMap() {

    }

    public void showPlayerCard() {
    }
}
