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
        Client c = new Client();
        GetData getData=new GetData();


        /*Speedy test
        Game game2 = new Game();
        int index2 = game2.getMatchesSize()-1;
        Match match2 = game2.getMatchByIndex(index2);
        Player p1 = new Player("Sirius", "Blue", "10583741");
        Player p2 = new Player("Calypso", "Pink", "14253954");
        Player p3 = new Player("Hermione", "Green", "18263100");
        Player p4 = new Player("Jackie", "Yellow", "13299954");
        Player p5 = new Player("Kate", "Grey", "19263542");
        try {
            match2.addPlayer(p1);
            match2.addPlayer(p2);
            match2.addPlayer(p3);
            match2.addPlayer(p4);
            match2.addPlayer(p5);
        }
        catch (MaxNumberPlayerException e){ printStream.println("Maximum number of players reached.");}
        game2.select(3);
        game2.startGame(match2.getId());
        View view2 = new CLIView(match2);
        p1.setCel(2,3);
        p2.setCel(0,2);
        p3.setCel(1,0);
        p4.setCel(1,0);
        p5.setCel(1,0);
        view2.printMap();*/

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

        Player player = new Player(name, color, "12345678");

        printStream.println("\nPlease, choose the color of the player:");
        printStream.println("Blue - Green - Yellow - Pink - Grey");
        color = getData.getValidColorforPlayer();
        printStream.println("\nPlease, enter your name to play in the game:");
        name= getData.getName();
        Player player1 = new Player(name, color, "10583741");

        printStream.println("\nPlease, choose the color of the player:");
        printStream.println("Blue - Green - Yellow - Pink - Grey");
        color = getData.getValidColorforPlayer();
        printStream.println("\nPlease, enter your name to play in the game:");
        name= getData.getName();
        Player player2 = new Player(name, color, "14253954");

        Game game = new Game();
        int index = game.getMatchesSize()-1;
        Match match = game.getMatchByIndex(index);
        try {
            match.addPlayer(player);
            match.addPlayer(player1);
            match.addPlayer(player2);
        }
        catch (MaxNumberPlayerException e){ printStream.println("Maximum number of players reached.");}

        printStream.println("\nPlease, "+match.getPlayerByIndex(0).getname()+" choose the type of dashboard:1, 2, 3");
        int maptype = getData.getInt(1, 3);
        game.select(maptype);

        AmmoTile ammoTile1 = new AmmoTile(0,0,1);
        AmmoTile ammoTile2= new AmmoTile(0,1,0);
        AmmoTile ammoTile3 = new AmmoTile(1,0,1);
        AmmoTile ammoTile4 = new AmmoTile(0,2,0);
        AmmoPowTile ammoPowTile1 = new AmmoPowTile(2,1);
        AmmoPowTile ammoPowTile2 = new AmmoPowTile(1,1);
        AmmoPowTile ammoPowTile3 = new AmmoPowTile(0,2);
        AmmoPowTile ammoPowTile4 = new AmmoPowTile(1,2);
        AmmoPowTile ammoPowTile5 = new AmmoPowTile(2,2);
        NormalCell cell1 = (NormalCell)match.getDashboard().getmap(0,0);
        NormalCell cell2 = (NormalCell)match.getDashboard().getmap(0,1);
        NormalCell cell3 = (NormalCell)match.getDashboard().getmap(0,3);
        NormalCell cell4 = (NormalCell)match.getDashboard().getmap(1,1);
        NormalCell cell5 = (NormalCell)match.getDashboard().getmap(1,2);
        NormalCell cell6 = (NormalCell)match.getDashboard().getmap(1,3);
        NormalCell cell7 = (NormalCell)match.getDashboard().getmap(2,0);
        NormalCell cell8 = (NormalCell)match.getDashboard().getmap(2,1);
        NormalCell cell9 = (NormalCell)match.getDashboard().getmap(2,2);
        try{
            cell1.Add_Ammo_Card(ammoTile1);
            cell2.Add_Ammo_Card(ammoPowTile1);
            cell3.Add_Ammo_Card(ammoTile2);
            cell4.Add_Ammo_Card(ammoPowTile2);
            cell5.Add_Ammo_Card(ammoTile3);
            cell6.Add_Ammo_Card(ammoPowTile3);
            cell7.Add_Ammo_Card(ammoPowTile4);
            cell8.Add_Ammo_Card(ammoTile4);
            cell9.Add_Ammo_Card(ammoPowTile5);
        } catch (FullCellException e){}
        game.startGame(match.getId());
        View view = new CLIView(match);
        for(Player p:match.getPlayers()){
            printStream.println(p.getname());
            view.showPlayerPowsColors(p);
        }

        for(Player p:match.getPlayers()){
            printStream.println("\nPlease, "+p.getname()+" select the SpawnPoint cell where you want to start. Write number of line, then column.");
            printStream.println("There are three SpawnPoint cells in the game:");
            printStream.println("Line 0, column 2 - Blue cell");
            printStream.println("Line 1, column 0 - Red cell");
            printStream.println("Line 2, column 3 - Yellow cell");
            printStream.println("You have these PowCards, choose with the color of one of them the spawn point cell:");
            view.showPlayerPowsColors(p);
            printStream.println("Insert: \nLine\nColumn\nNumber PowCard to use");
            int x= getData.getInt(0, 2);
            int y= getData.getInt(0, 3);
            int powindex = getData.getInt(1, 2);
            while(!((x==0 && y==2)||(x==1&&y==0)||(x==2 && y==3))){
                printStream.println("Not a valid SpawnPoint; insert new: \nLine\nColumn\nNumber of PowCard to use");
                x= getData.getInt(0, 2);
                y= getData.getInt(0, 3);
                powindex = getData.getInt(1, 2);
            }
            powindex--;
            int flag=0;
            while(flag==0){
                try{
                    game.firstTurn(p, powindex, x, y);
                    flag=1;
                } catch(InvalidColorException e){
                    printStream.println("Not a valid SpawnPoint; insert new: \nLine\nColumn\nNumber of PowCard to use");
                    x= getData.getInt(0, 2);
                    y= getData.getInt(0, 3);
                    powindex = getData.getInt(1, 2);
                    powindex--;}
            }
            view.showPlayerPows(p);
        }
        match.fillDashboard();
        player.setdamage(6, 0);
        //TODO serve al client a reference alla partita che sta giocando
        for(int i=0; i<match.getPlayersSize(); i++){
            c.play(match, view);
            game.setTurn(match);
        }


    }
    /*TODO in questa classe bisognerà implementare la waiting room del client. Una volta che ha scelto il
    modo di comunicazione e la grafica da usare vengono lanciati gli appositi comandi per far partire le connessioni.
     */

    //metodo per turno di gioco del player in mode CLI
    public void play(Match match, View view){
        int counter=0;
        GetData getData=new GetData();
        int choice;
        while(counter<2){
            printStream.println("\nTurn of player "+match.getActivePlayer().getname());
            printStream.println("\nWhat to you want to do?");
            printStream.println("0. Run"); //done
            printStream.println("1. Grab"); //done
            printStream.println("2. Shoot");
            printStream.println("3. Grab with movement"); //done
            printStream.println("4. Shoot with movement");
            printStream.println("5. Recharge"); //done ATTENTA, questo è extra, il conteggio di counter per le azioni non va incrementato. Probabilmente lo metterò dopo la shoot come extra
            view.printMap();
            printStream.println((counter+1)+" action.");
            choice = getData.getInt(0, 5);
            switch(choice){
                case(0):
                    /*Control if running is valid, in case counter is decremented to neutralize the counter++ after break, as
                    * the player can take an other action*/
                    List<String> destination = view.getListDirection();
                    Run run = new Run();
                    try{
                        run.getMovement(match, match.getActivePlayer(), destination);
                        run.registerMovementAction(match);
                        counter++;
                    } catch(InvalidDirectionException e){
                        printStream.println("You have chosen a not valid direction");
                    }
                    break;
                case(1):
                    int x = match.getActivePlayer().getCel().getX();
                    int y = match.getActivePlayer().getCel().getY();
                    if((x==0 && y==2) ||(x==1 && y==0) ||(x==2 && y==3)){
                        //this is a SpawnPoint cell
                        /*In a SpawnPoint cell the player choose which weapon to buy, if it has too many weapons he is asked
                         * if he wants to remove one of them, and in positive case he chooses which one to remove, then the selected one
                         * is added to his weapon cards*/
                        view.showPlayerWeapons();
                        view.showSpawnPointWeapons();
                        int weapontograb = view.getWeaponCard();
                        GrabWeapon grabWeapon = new GrabWeapon();
                        try{
                            grabWeapon.grabWeapon(match, match.getActivePlayer(), weapontograb);
                            view.showPlayerWeapons();
                            counter++;
                            break;
                        } catch(MaxNumberofCardsException e){
                            printStream.println("You have to many weapons, if you want to remove one digit 1, 0 otherwise");
                            int removing = getData.getInt(0, 1);
                            switch(removing){
                                case(0):
                                    //invalid action for the player, choice will be repeated
                                    break;
                                case(1):
                                    printStream.println("Which weapon do you want to remove?");
                                    view.showPlayerWeapons();
                                    int removedweapon = getData.getInt(1, 3);
                                    removedweapon--;
                                    ManagingWeapons remove = new ManagingWeapons();
                                    remove.Remove(match.getActivePlayer(), removedweapon);
                                    counter++;
                                    try{
                                        grabWeapon.grabWeapon(match, match.getActivePlayer(), weapontograb);
                                        view.showPlayerWeapons();
                                    } catch(MaxNumberofCardsException ex){return;}
                            }
                        }
                        break;
                    }
                    else {
                        //this is not a SpawnPoint cell
                        GrabAmmo grabAmmo = new GrabAmmo();
                        printStream.println("Before grabbing");
                        view.showPlayerAmmos();
                        view.showPlayerPows();
                        try{
                            grabAmmo.grabAmmo(match, match.getActivePlayer());
                            printStream.println("After grabbing");
                            view.showPlayerAmmos();
                            view.showPlayerPows();
                            counter++;
                        } catch(MaxNumberofCardsException exc){
                            printStream.println("You have to many PowCards, if you want to remove one digit 1, 0 otherwise");
                            int removing = getData.getInt(0, 1);
                            switch(removing){
                                case(0):
                                    printStream.println("After grabbing without collecting the PowCard of AmmoPowTile");
                                    view.showPlayerAmmos();
                                    view.showPlayerPows();
                                    break; //nothing to be done, just Ammos are taken
                                case(1):
                                    printStream.println("Which PowCard do you want to remove?");
                                    view.showPlayerPows();
                                    int removedpow = getData.getInt(1, 3);
                                    removedpow--;
                                    ManagingWeapons removepow = new ManagingWeapons();
                                    removepow.RemovePow(match.getActivePlayer(), removedpow);

                                    try{
                                        match.assignPowCard(match.getActivePlayer());
                                    } catch(MaxNumberofCardsException ex){return;}
                                    printStream.println("After grabbing collecting the PowCard of AmmoPowTile");
                                    view.showPlayerPows();
                                    view.showPlayerAmmos();
                                    break;
                            }
                        }
                    }
                    break;
                case(2):
                    int attackingweapon = view.getWeaponCardtoAttack();
                    attackingweapon--;
                    Shoot shoot = new Shoot();
                    break;
                case(3):
                    /*Grabbing with movement*/
                    List<String> destination2 = view.getListDirection();
                    GrabAmmo grabAmmo = new GrabAmmo();
                    if(!grabAmmo.movementBeforeGrab(match, match.getActivePlayer(), destination2)){
                        printStream.println("You are not allowed to move this way.");
                        return;
                    }
                    int x3 = match.getActivePlayer().getCel().getX();
                    int y3 = match.getActivePlayer().getCel().getY();
                    if((x3==0 && y3==2) ||(x3==1 && y3==1) ||(x3==2 && y3==3)){
                        //this is a SpawnPoint cell
                        /*In a SpawnPoint cell the player choose which weapon to buy, if it has too many weapons he is asked
                         * if he wants to remove one of them, and in positive case he chooses which one to remove, then the selected one
                         * is added to his weapon cards*/
                        view.showPlayerWeapons();
                        view.showSpawnPointWeapons();
                        int weapontograb = view.getWeaponCard();
                        GrabWeapon grabWeapon = new GrabWeapon();
                        try{
                            grabWeapon.grabWeapon(match, match.getActivePlayer(), weapontograb);
                            view.showPlayerWeapons();
                            counter++;
                        } catch(MaxNumberofCardsException e){
                            printStream.println("You have to many weapons, if you want to remove one digit 1, 0 otherwise");
                            int removing = getData.getInt(0, 1);
                            switch(removing){
                                case(0):
                                    //invalid action for the player, choice will be repeated
                                    break;
                                case(1):
                                    printStream.println("Which weapon do you want to remove?");
                                    view.showPlayerWeapons();
                                    int removedweapon = getData.getInt(1, 3);
                                    removedweapon--;
                                    ManagingWeapons remove = new ManagingWeapons();
                                    remove.Remove(match.getActivePlayer(), removedweapon);
                                    counter++;
                                    try{
                                        grabWeapon.grabWeapon(match, match.getActivePlayer(), weapontograb);
                                        view.showPlayerWeapons();
                                    } catch(MaxNumberofCardsException ex){return;}
                            }
                        }
                        break;
                    }
                    else {
                        //this is not a SpawnPoint cell
                        try{
                            grabAmmo.grabAmmo(match, match.getActivePlayer());
                            printStream.println("Before grabbing");
                            view.showPlayerAmmos();
                            view.showPlayerPows();
                            counter++;
                        } catch(MaxNumberofCardsException exc){
                            printStream.println("You have to many PowCards, if you want to remove one digit 1, 0 otherwise");
                            int removing = getData.getInt(0, 1);
                            switch(removing){
                                case(0):
                                    printStream.println("After grabbing without collecting the PowCard of AmmoPowTile");
                                    view.showPlayerAmmos();
                                    view.showPlayerPows();
                                    break; //nothing to be done, just Ammos are taken
                                case(1):
                                    printStream.println("Which PowCard do you want to remove?");
                                    view.showPlayerPows();
                                    int removedpow = getData.getInt(1, 3);
                                    removedpow--;
                                    ManagingWeapons removepow = new ManagingWeapons();
                                    removepow.RemovePow(match.getActivePlayer(), removedpow);

                                    try{
                                        match.assignPowCard(match.getActivePlayer());
                                    } catch(MaxNumberofCardsException ex){return;}
                                    printStream.println("After grabbing collecting the PowCard of AmmoPowTile");
                                    view.showPlayerAmmos();
                                    view.showPlayerPows();
                            }
                        }
                    }

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

                    //TODO aziona di ricarica sarà fatta dopo l'uso dell'arma e alla fine del turno giocatore
            }
        }
        view.printMap();
        printStream.println(match.getActivePlayer().getname()+" you have ended your turn.");

        view.printPlayerData();
    }

}
