package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

import controller.*;
import exceptions.*;
import model.*;
import utils.GetData;

/*In this class the connectionHandler has its main, it is allowed to choose whether RMI or socket, or CLI or GUIView
* as well*/
public class Client {

    private InputStreamReader reader= new InputStreamReader(System.in);
    private BufferedReader input = new BufferedReader(reader);
    private static final Logger LOGGER= Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static PrintStream printStream=System.out;

    private Client() {
        LOGGER.setLevel(Level.INFO);
    }
    /*TODO in questa classe bisognerà implementare la waiting room del connectionHandler. Una volta che ha scelto il
    modo di comunicazione e la grafica da usare vengono lanciati gli appositi comandi per far partire le connessioni.
     */


}
