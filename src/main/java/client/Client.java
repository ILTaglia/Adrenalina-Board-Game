package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

import controller.*;
import exceptions.InvalidDirectionException;
import exceptions.MaxNumberofCardsException;
import exceptions.WeaponAlreadyLoadedException;
import utils.GetData;
import model.Match;

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

        Match match = new Match();
        //TODO serve al client a reference alla partita che sta giocando
        play(match);

    }
    /*TODO in questa classe bisognerà implementare la waiting room del client. Una volta che ha scelto il
    modo di comunicazione e la grafica da usare vengono lanciati gli appositi comandi per far partire le connessioni.
     */

    //metodo per turno di gioco del player in mode CLI
    public static void play(Match match){
        View view = new CLIView(match);
        int counter=0;
        GetData getData=new GetData();
        printStream.println("\nWhat to you want to do?");
        printStream.println("0. Run"); //done
        printStream.println("1. Grab");
        printStream.println("2. Shoot");
        printStream.println("3. Grab with movement");
        printStream.println("4. Shoot with movement");
        printStream.println("5. Recharge"); //done ATTENTA, questo è extra, il conteggio di counter per le azioni non va incrementato. Probabilmente lo metterò dopo la shoot come extra
        printStream.println("6. Buy a weapon card"); //done
        int choice = getData.getInt(0, 6);
        while(counter==2){
            switch(choice){
                case(0):
                    /*Control if running is valid, in case counter is decremented to neutralize the counter++ after break, as
                    * the player can take an other action*/
                    List<String> destination = view.getListDirection();
                    Run run = new Run();
                    try{
                        run.getMovement(match, match.getActivePlayer(), destination);
                    } catch(InvalidDirectionException e){
                        printStream.println("You have chosen a not valid direction");
                        counter--;
                    }
                    break;
                case(1):
                    break;
                case(2):
                    int attackingweapon = view.getWeaponCardtoAttack();
                    attackingweapon--;
                    Shoot shoot = new Shoot();
                    break;
                case(3):
                    /*Grabbing with movement*/
                    List<String> destination2 = view.getListDirection();
                    //TODO ho bisogno di distinguere tra normal e spawn point cell per creare un oggetto grabammo o grabweapon
                    break;
                case(4):
                    List<String> destination3 = view.getListDirection();

                    break;
                case(5):
                    /*Recharge a weapon checking if the weapon is already loaded*/
                    printStream.println("Which weapon do you want to recharge?");
                    view.showPlayerWeapons();
                    int weapontorecharge = view.getWeaponCard();
                    weapontorecharge--;
                    ManagingWeapons manage = new ManagingWeapons();
                    try{
                        manage.Recharge(match.getActivePlayer(), weapontorecharge);
                    } catch(WeaponAlreadyLoadedException e){
                        printStream.println("You have already loaded this weapon");
                    }
                    break;
                case(6):
                    /*In a SpawnPoint cell the player choose which weapon to buy, if it has too many weapons he is asked
                    * if he wants to remove one of them, and in positive case he chooses which one to remove, then the selected one
                    * is added to his weapon cards*/
                    view.showSpawnPointWeapons();
                    int weapontograb = view.getWeaponCard();
                    GrabWeapon grabWeapon = new GrabWeapon();
                    try{
                        grabWeapon.grabWeapon(match, match.getActivePlayer(), weapontograb);
                    } catch(MaxNumberofCardsException e){
                        printStream.println("You have to many weapons, if you want to remove one digit 1, 0 otherwise");
                        int removing = getData.getInt(0, 1);
                        switch(removing){
                            case(0):
                                counter--; //invalid action for the player, choice will be repeated
                                break;
                            case(1):
                                printStream.println("Which weapon do you want to remove?");
                                view.showPlayerWeapons();
                                int removedweapon = getData.getInt(1, 3);
                                removedweapon--;
                                ManagingWeapons remove = new ManagingWeapons();
                                remove.Remove(match.getActivePlayer(), removedweapon);
                                try{
                                    grabWeapon.grabWeapon(match, match.getActivePlayer(), weapontograb);
                                } catch(MaxNumberofCardsException ex){return;}
                        }
                    }
                    break;
            }
            counter++;
        }
    }

}
