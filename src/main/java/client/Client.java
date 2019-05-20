package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import utils.GetData;

/*In this class the client has its main, it is allowed to choose whether RMI or socket, or CLI or GUI
* as well*/
public class Client {

    private InputStreamReader reader= new InputStreamReader(System.in);
    private BufferedReader input = new BufferedReader(reader);
    private static final Logger LOGGER= Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static PrintStream printStream=System.out;

    private Client() {
        LOGGER.setLevel(Level.INFO);
    }

    public static void main(String[] args) {
        GetData getData=new GetData();

        printStream.println("Welcome to Adrenalina!");

        printStream.println("\nPlease, choose which communication protocol you want to use:");
        printStream.println("1. Socket");
        printStream.println("2. RMI");

        int CommunicationChoice=getData.getInt(1, 2);

        printStream.println("\nPlease, choose which graphics you want to play with:");
        printStream.println("1. CLI");
        printStream.println("2. GUI");
        int GraphicChoice=getData.getInt(1, 2);

        printStream.println("\nPlease, choose the color of the player:");
        printStream.println("Blue - Green - Yellow - Pink - Grey");
        String color = getData.getValidColorforPlayer();

        printStream.println("\nPlease, enter your name to play in the game:");
        String name= getData.getName();

    }
    /*TODO in questa classe bisognerà implementare la waiting room del client. Una volta che ha scelto il
    modo di comunicazione e la grafica da usare vengono lanciati gli appositi comandi per far partire le connessioni.
     */

}
