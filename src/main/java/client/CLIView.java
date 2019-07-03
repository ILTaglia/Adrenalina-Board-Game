package client;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.*;
import network.messages.ReConnectClientRequest;
import network.messages.clientRequest.*;
import network.messages.Message;
import network.client.Client;
import network.messages.clientRequest.PowCardDiscardClientRequest;
import utils.*;

import static utils.Print.printOut;

public class CLIView implements View {
    private static final Logger LOGGER= Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private Client client;
    private GetData getData=new GetData();

    //Indici provvisori, verificare se possibile lasciarli. Servono per memorizzare delle scelte da parte dell'utente.
    private int indexSelectedWeapon;
    private int requestedAction;


    public CLIView(Client client){
        this.client=client;
        LOGGER.setLevel(Level.INFO);
    }
    /*
     **********************************************************
     * Metodi per Avvio Client                                *
     **********************************************************
     */

    /**
     * Method to start
     */
    @Override
    public void start(){
        setConnection();
        login();
    }

    /**
     * Method to setConnection
     */
    @Override
    public void setConnection(){
        printOut("Choose the type of connection you want to use:\t\n" +
                "1. RMI" +
                "2. Socket");
        int connectionChoice=getData.getInt(1, 2);

        client.setConnection(connectionChoice==2);

        client.launchConnection();
    }

    /**
     * Method to login
     */
    @Override
    public void login(){
        printOut("Digit your username:");
        String user=getData.getName();
        client.requestToWR(user);
    }

    /**
     * Method to ask to reconnect
     */
    @Override
    public void askToReConnect() {
        printOut("Have you been disconnected?\n");
        if(getData.askYesOrNo()) {
            printOut("Insert the userID you have been assigned at the start of the game:");
            String userIDToReConnect = getData.getName();
            client.reConnectRequest(userIDToReConnect);
        }
        else{
            printOut("Insert again the username you want to use:");
            String username = getData.getName();
            client.newConnectionRequest(username);
        }
    }

    /**
     *
     * @return the response to the request to reconnect
     */
    public boolean askToTryToReConnect(){
        printOut("You have been disconnected");
        printOut("Do you want to try to re connect?\n");
        if(getData.askYesOrNo()){
            printOut("Ok, connection attempt in progress ...");
            return true;
        }
        else{
            printOut("If you want to reconnect re open Game. Bye Bye.");
            return false;
        }
    }


    /*
     **********************************************************
     * Metodi per Eventi/Eccezioni                            *
     **********************************************************
     */

    /**
     * Method to show exception
     */
    @Override
    public void showException(String message) {
        printOut(message);
    }

    /**
     * Method to ashow info message
     */
    @Override
    public void showInfoMessage(Message message){
        printOut("Message received:" + message.getInfo());

    }

    /*
     **********************************************************
     * Metodi per Setup Match                              *
     **********************************************************
     */

    /**
     * Method to create player
     */
    @Override
    public void createPlayer(){
        printOut("Digit you color:"+ "players available colors are Blue - Green - Yellow - Pink - Grey");
        String colorRequired=getData.getValidColorForPlayer();
        ColorClientRequest colorRequest=new ColorClientRequest(colorRequired,client.getUserID());
        printOut("Your required the color: "+ colorRequired);
        client.sendMessage(colorRequest);
    }

    /**
     * Method to ask to use PowCard to grab weapon
     */
    @Override
    public void chooseMap() {
        printOut("Map 1");
        this.printmap1();
        printOut("Map 2");
        this.printmap2();
        printOut("Map 3");
        this.printmap3();
        printOut("Map 4");
        this.printmap4();
        printOut("Digit chosen map: 1, 2, 3, 4");
        int choice = getData.getInt(1, 4);
        String mapRequired = Integer.toString(choice);
        Message mapRequest=new MapClientRequest(mapRequired,client.getUserID());
        client.sendMessage(mapRequest);
    }

    /**
     * Method to choose Action
     */
    public void chooseAction(){
        printPlayerData();
        int x = client.getPlayerVisibleData().getPlayer().getCel().getX();
        int y = client.getPlayerVisibleData().getPlayer().getCel().getY();
        if(client.getPlayerVisibleData().getDashboard().getMap(x, y).getType()==0){showSpawnPointWeapons();}
        else{showAmmoCard();}
        printOut("0. Run");
        printOut("1. Grab in this Cell");
        printOut("2. Shoot");
        printOut("3. Run & Grab");
        printOut("4. Run & Shoot");
        printOut("5. recharge");
        requestedAction = getData.getInt(0, 5);
        //Salvo l'azione selezionata, in modo da agire diversamente nel caso si tratti di una Grab o una Grab with *
        String indexAction = Integer.toString(requestedAction);
        Message actionRequest=new ActionClientRequest(indexAction,client.getUserID());
        client.sendMessage(actionRequest);
    }

    /**
     * Method to ask to choose run direction
     */
    @Override
    public void chooseRunDirection() {
        List<String> direction;
        printMap();
        showDirection();
        do{
            direction=getData.getValidListDirectionForPlayer();
            if(direction.isEmpty()) printOut("You need to choose at least one direction.");
        }while(direction.isEmpty());
        Message message=new RunClientRequest(direction,client.getUserID());
        client.sendMessage(message);
    }

    /**
     * Method to choose to discard a weapon
     */
    @Override
    public void chooseDiscardWeapon() {
        int indexWeaponToDiscard;
        if(getData.askYesOrNo()){
            showPlayerWeapons();
            printOut("Which Weapon do you want to discard?");
            indexWeaponToDiscard=getData.getInt(0,client.getPlayerVisibleData().getPlayer().getNumberWeapon())-1;
            Message message=new CardToWeaponGrabClientRequest(Integer.toString(indexSelectedWeapon),Integer.toString(indexWeaponToDiscard),client.getUserID());
            client.sendMessage(message);
        }
        else{
            printOut("You can't have more than three Weapon Card. Choose an other Action");
            chooseAction();
        }
    }

    /**
     * Method to achoose a weapon to grab
     */
    @Override
    public  void chooseWeaponToGrab(){
        showSpawnPointWeapons();
        showPlayerAmmos();
        printOut("Which Weapon do you want to grab?");
        //Salvo informazione dell'arma scelta, nel caso
        indexSelectedWeapon=getData.getInt(1,3)-1;
        Message message=new WeaponGrabClientRequest(Integer.toString(indexSelectedWeapon),client.getUserID());
        client.sendMessage(message);
    }

    /**
     * Method to ask to use PowCard to grab weapon
     */
    @Override
    public void askUsePowToGrabWeapon() {
        int indexPowCard;
        if(getData.askYesOrNo()){
            showPlayerPows();
            printOut("Which pow card do you want to use to grab Weapon?");
            indexPowCard=getData.getInt(1,getNumberOfPow())-1;
            Message message=new CardToWeaponGrabClientRequest(Integer.toString(indexSelectedWeapon),Integer.toString(indexPowCard),client.getUserID());
            client.sendMessage(message);
        }
        else{
            printOut("If you don't want to grab this Weapon you can choose an other action");
            chooseAction();
        }


    }

    /**
     * Method to choose discard PowCard
     */
    @Override
    public void chooseDiscardPowCard() {
        int indexPowCard;
        if(getData.askYesOrNo()){
            showPlayerPows();
            printOut("Which PowCard do you want to discard?");
            indexPowCard=getData.getInt(0,client.getPlayerVisibleData().getPlayer().getNumberPow())-1;
            Message message=new PowCardDiscardClientRequest(Integer.toString(indexPowCard),client.getUserID());
            client.sendMessage(message);
        }
        else{
            printOut("You can't have more than three PowCard. You can't draw new PowCard.\n");
            //Non chiedo ulteriori info perchè il Player comunque ha raccolto e ha accettato di non prendere la PowCard.
        }
    }


    /**
     * Method to getNumberPow
     */
    @Override
    public int getNumberOfPow(){
        return client.getPlayerVisibleData().getPlayer().getPows().size();
    }

    /**
     *
     * @return 0
     */
    @Override
    public int getPowCard() {
        return 0;
    }


    /**
     * Method to choose starting cell
     */
    @Override
    public void chooseStartingCell(){
        List<Integer> coordinate;
        int powIndex;
        printOut("\nSelect the SpawnPoint cell where you want to start. Write number of line, then column.");
        printOut("There are three SpawnPoint cells in the game:");
        printOut("Line 0, column 2 - Blue cell");
        printOut("Line 1, column 0 - Red cell");
        printOut("Line 2, column 3 - Yellow cell");
        printOut("You have these PowCards, choose with the color of one of them the spawn point cell:");
        this.showPlayerPowWithColors();
        printOut("Insert: \nLine (Enter)\nColumn (Enter)\nNumber PowCard to use (Enter)\n");
        coordinate=getData.getCoordinate(0,2,0,3);
        while(!((coordinate.get(0)==0 && coordinate.get(1)==2)||(coordinate.get(0)==1&&coordinate.get(1)==0)||(coordinate.get(0)==2 && coordinate.get(1)==3))){
            printOut("Not a valid SpawnPoint\n");
            coordinate=getData.getCoordinate(0,2,0,3);
        }
        powIndex = getData.getInt(1, 2);
        powIndex--;
        Message message=new SpawnPointClientRequest(coordinate.get(0),coordinate.get(1),powIndex,client.getUserID());
        client.sendMessage(message);
    }


    /*
     **********************************************************
     * Metodi per Avvio Client   FINE                         *
     **********************************************************
     */
    /**
     * Method to print welcome message
     */
    @Override
    public void welcomeMessage(int idClient) { printOut("START."); }

    /**
     * Method to print end message
     */
    @Override
    public void endMessage() { printOut("GAME OVER."); }

    /**
     * Method to print map, supported by private methods
     */
    @Override
    public void printMap() {
        int indexMap = client.getPlayerVisibleData().getDashboard().getMapType();
        if(indexMap==1){this.printmap1();}
        if(indexMap==2){this.printmap2();}
        if(indexMap==3){this.printmap3();}
        if(indexMap==4){this.printmap4();}
    }

    //Supporting methods to be used only internally the print map method
    /**
     * Method to print map1
     */
    private void printmap1(){
        String[][] map = new String[3][4];
        String[][] map1 = new String[3][4];
        String[][] map2 = new String[3][4];
        String[][] map3 = new String[3][4];
        String[][] map4 = new String[3][4];

        String s = "        ";
        for(int i=0; i<3; i++){
            for(int j=0; j<4; j++){
                map[i][j]=s;
                map1[i][j]=s;
                map2[i][j]=s;
                map3[i][j]=s;
                map4[i][j]=s;
            }
        }

        printOut(" _________________________________________________                 ");
        printOut("|      Blue      |       Blue     |     Blue      |                ");
        printOut("|    "+map[0][0]+"    |    "+map[0][1]+"    |    "+map[0][2]+"   |    "+map[0][3]+"    ");
        printOut("|    "+map1[0][0]+"    |    "+map1[0][1]+"    |    "+map1[0][2]+"   |    "+map1[0][3]+"    ");
        printOut("|    "+map2[0][0]+"    |    "+map2[0][1]+"    |    "+map2[0][2]+"   |    "+map2[0][3]+"    ");
        printOut("|    "+map3[0][0]+"    |    "+map3[0][1]+"    |    "+map3[0][2]+"   |    "+map3[0][3]+"    ");
        printOut("|    "+map4[0][0]+"    |    "+map4[0][1]+"    |    "+map4[0][2]+"   |    "+map4[0][3]+"    ");
        printOut("|                |                |   SpawnPoint  |                ");
        printOut("|_______| |______|________________|______| |______|_______________ ");
        printOut("|      Red       |      Red       |      Red      |    Yellow     |");
        printOut("|    "+map[1][0]+"    |    "+map[1][1]+"    |    "+map[1][2]+"   |    "+map[1][3]+"   |");
        printOut("|    "+map1[1][0]+"    |    "+map1[1][1]+"    |    "+map1[1][2]+"   _    "+map1[1][3]+"   |");
        printOut("|    "+map2[1][0]+"    |    "+map2[1][1]+"    |    "+map2[1][2]+"   _    "+map2[1][3]+"   |");
        printOut("|    "+map3[1][0]+"    |    "+map3[1][1]+"    |    "+map3[1][2]+"   |    "+map3[1][3]+"   |");
        printOut("|    "+map4[1][0]+"    |    "+map4[1][1]+"    |    "+map4[1][2]+"   |    "+map4[1][3]+"   |");
        printOut("|   SpawnPoint   |                |               |               |");
        printOut("|________________|______| |_______|_______________|_______________|");
        printOut("                 |      Grey      |      Grey     |    Yellow     |");
        printOut("     "+map[2][0]+"    |    "+map[2][1]+"    |    "+map[2][2]+"   |    "+map[2][3]+"   |");
        printOut("     "+map1[2][0]+"    |    "+map1[2][1]+"    |    "+map1[2][2]+"   _    "+map1[2][3]+"   |");
        printOut("     "+map2[2][0]+"    |    "+map2[2][1]+"    |    "+map2[2][2]+"   _    "+map2[2][3]+"   |");
        printOut("     "+map3[2][0]+"    |    "+map3[2][1]+"    |    "+map3[2][2]+"   |    "+map3[2][3]+"   |");
        printOut("     "+map4[2][0]+"    |    "+map4[2][1]+"    |    "+map4[2][2]+"   |    "+map4[2][3]+"   |");
        printOut("                 |                |               |   SpawnPoint  |");
        printOut("                 |________________|_______________|_______________|");
    }
    /**
     * Method to print map2
     */
    private void printmap2(){
        String[][] map = new String[3][4];
        String[][] map1 = new String[3][4];
        String[][] map2 = new String[3][4];
        String[][] map3 = new String[3][4];
        String[][] map4 = new String[3][4];

        String s = "        ";
        for(int i=0; i<3; i++){
            for(int j=0; j<4; j++){
                map[i][j]=s;
                map1[i][j]=s;
                map2[i][j]=s;
                map3[i][j]=s;
                map4[i][j]=s;
            }
        }
        printOut(" _________________________________________________________________ ");
        printOut("|      Blue      |       Blue     |     Blue      |   Green       |");
        printOut("|    "+map[0][0]+"    |    "+map[0][1]+"    |    "+map[0][2]+"   |    "+map[0][3]+"   |");
        printOut("|    "+map1[0][0]+"    |    "+map1[0][1]+"    |    "+map1[0][2]+"   _    "+map1[0][3]+"   |");
        printOut("|    "+map2[0][0]+"    |    "+map2[0][1]+"    |    "+map2[0][2]+"   _    "+map2[0][3]+"   |");
        printOut("|    "+map3[0][0]+"    |    "+map3[0][1]+"    |    "+map3[0][2]+"   |    "+map3[0][3]+"   |");
        printOut("|    "+map4[0][0]+"    |    "+map4[0][1]+"    |    "+map4[0][2]+"   |    "+map4[0][3]+"   |");
        printOut("|                |                |   SpawnPoint  |               |");
        printOut("|______| |_______|________________|______| |______|______| |______|");
        printOut("|      Red       |      Red       |     Yellow    |    Yellow     |");
        printOut("|    "+map[1][0]+"    |    "+map[1][1]+"    |    "+map[1][2]+"   |    "+map[1][3]+"   |");
        printOut("|    "+map1[1][0]+"    |    "+map1[1][1]+"    |    "+map1[1][2]+"   |    "+map1[1][3]+"   |");
        printOut("|    "+map2[1][0]+"    |    "+map2[1][1]+"    |    "+map2[1][2]+"   |    "+map2[1][3]+"   |");
        printOut("|    "+map3[1][0]+"    |    "+map3[1][1]+"    |    "+map3[1][2]+"   |    "+map3[1][3]+"   |");
        printOut("|    "+map4[1][0]+"    |    "+map4[1][1]+"    |    "+map4[1][2]+"   |    "+map4[1][3]+"   |");
        printOut("|   SpawnPoint   |                |               |               |");
        printOut("|________________|______| |_______|_______________|_______________|");
        printOut("                 |      Grey      |     Yellow    |    Yellow     |");
        printOut("     "+map[2][0]+"    |    "+map[2][1]+"    |    "+map[2][2]+"   |    "+map[2][3]+"   |");
        printOut("     "+map1[2][0]+"    |    "+map1[2][1]+"    _    "+map1[2][2]+"   |    "+map1[2][3]+"   |");
        printOut("     "+map2[2][0]+"    |    "+map2[2][1]+"    _    "+map2[2][2]+"   |    "+map2[2][3]+"   |");
        printOut("     "+map3[2][0]+"    |    "+map3[2][1]+"    |    "+map3[2][2]+"   |    "+map3[2][3]+"   |");
        printOut("     "+map4[2][0]+"    |    "+map4[2][1]+"    |    "+map4[2][2]+"   |    "+map4[2][3]+"   |");
        printOut("                 |                |               |   SpawnPoint  |");
        printOut("                 |________________|_______________|_______________|");
    }
    /**
     * Method to print map3
     */
    private void printmap3(){
        String[][] map = new String[3][4];
        String[][] map1 = new String[3][4];
        String[][] map2 = new String[3][4];
        String[][] map3 = new String[3][4];
        String[][] map4 = new String[3][4];

        String s = "        ";
        for(int i=0; i<3; i++){
            for(int j=0; j<4; j++){
                map[i][j]=s;
                map1[i][j]=s;
                map2[i][j]=s;
                map3[i][j]=s;
                map4[i][j]=s;
            }
        }
        printOut(" _________________________________________________________________ ");
        printOut("|      Red       |       Blue     |     Blue      |   Green       |");
        printOut("|    "+map[0][0]+"    |    "+map[0][1]+"    |    "+map[0][2]+"   |    "+map[0][3]+"   |");
        printOut("|    "+map1[0][0]+"    _    "+map1[0][1]+"    |    "+map1[0][2]+"   _    "+map1[0][3]+"   |");
        printOut("|    "+map2[0][0]+"    _    "+map2[0][1]+"    |    "+map2[0][2]+"   _    "+map2[0][3]+"   |");
        printOut("|    "+map3[0][0]+"    |    "+map3[0][1]+"    |    "+map3[0][2]+"   |    "+map3[0][3]+"   |");
        printOut("|    "+map4[0][0]+"    |    "+map4[0][1]+"    |    "+map4[0][2]+"   |    "+map4[0][3]+"   |");
        printOut("|                |                |   SpawnPoint  |               |");
        printOut("|________________|______| |_______|______| |______|______| |______|");
        printOut("|      Red       |     Pink       |     Yellow    |    Yellow     |");
        printOut("|    "+map[1][0]+"    |    "+map[1][1]+"    |    "+map[1][2]+"   |    "+map[1][3]+"   |");
        printOut("|    "+map1[1][0]+"    |    "+map1[1][1]+"    |    "+map1[1][2]+"   |    "+map1[1][3]+"   |");
        printOut("|    "+map2[1][0]+"    |    "+map2[1][1]+"    |    "+map2[1][2]+"   |    "+map2[1][3]+"   |");
        printOut("|    "+map3[1][0]+"    |    "+map3[1][1]+"    |    "+map3[1][2]+"   |    "+map3[1][3]+"   |");
        printOut("|    "+map4[1][0]+"    |    "+map4[1][1]+"    |    "+map4[1][2]+"   |    "+map4[1][3]+"   |");
        printOut("|   SpawnPoint   |                |               |               |");
        printOut("|______| |_______|______| |_______|_______________|_______________|");
        printOut("|      Grey      |      Grey      |     Yellow    |    Yellow     |");
        printOut("|    "+map[2][0]+"    |    "+map[2][1]+"    |    "+map[2][2]+"   |    "+map[2][3]+"   |");
        printOut("|    "+map1[2][0]+"    |    "+map1[2][1]+"    _    "+map1[2][2]+"   |    "+map1[2][3]+"   |");
        printOut("|    "+map2[2][0]+"    |    "+map2[2][1]+"    _    "+map2[2][2]+"   |    "+map2[2][3]+"   |");
        printOut("|    "+map3[2][0]+"    |    "+map3[2][1]+"    |    "+map3[2][2]+"   |    "+map3[2][3]+"   |");
        printOut("|    "+map4[2][0]+"    |    "+map4[2][1]+"    |    "+map4[2][2]+"   |    "+map4[2][3]+"   |");
        printOut("|                |                |               |   SpawnPoint  |");
        printOut("|________________|________________|_______________|_______________|");
    }

    /**
     * Method to print map4
     */
    private void printmap4(){
        String[][] map = new String[3][4];
        String[][] map1 = new String[3][4];
        String[][] map2 = new String[3][4];
        String[][] map3 = new String[3][4];
        String[][] map4 = new String[3][4];

        String s = "        ";
        for(int i=0; i<3; i++){
            for(int j=0; j<4; j++){
                map[i][j]=s;
                map1[i][j]=s;
                map2[i][j]=s;
                map3[i][j]=s;
                map4[i][j]=s;
            }
        }
        printOut(" _________________________________________________                 ");
        printOut("|      Red       |       Blue     |     Blue      |                ");
        printOut("|    "+map[0][0]+"    |    "+map[0][1]+"    |    "+map[0][2]+"   |    "+map[0][3]+"    ");
        printOut("|    "+map1[0][0]+"    _    "+map1[0][1]+"    |    "+map1[0][2]+"   |    "+map1[0][3]+"    ");
        printOut("|    "+map2[0][0]+"    _    "+map2[0][1]+"    |    "+map2[0][2]+"   |    "+map2[0][3]+"    ");
        printOut("|    "+map3[0][0]+"    |    "+map3[0][1]+"    |    "+map3[0][2]+"   |    "+map3[0][3]+"    ");
        printOut("|    "+map4[0][0]+"    |    "+map4[0][1]+"    |    "+map4[0][2]+"   |    "+map4[0][3]+"    ");
        printOut("|                |                |   SpawnPoint  |                ");
        printOut("|________________|______| |_______|______| |______|_______________ ");
        printOut("|      Red       |     Pink       |     Pink      |    Yellow     |");
        printOut("|    "+map[1][0]+"    |    "+map[1][1]+"    |    "+map[1][2]+"   |    "+map[1][3]+"   |");
        printOut("|    "+map1[1][0]+"    |    "+map1[1][1]+"    |    "+map1[1][2]+"   _    "+map1[1][3]+"   |");
        printOut("|    "+map2[1][0]+"    |    "+map2[1][1]+"    |    "+map2[1][2]+"   _    "+map2[1][3]+"   |");
        printOut("|    "+map3[1][0]+"    |    "+map3[1][1]+"    |    "+map3[1][2]+"   |    "+map3[1][3]+"   |");
        printOut("|    "+map4[1][0]+"    |    "+map4[1][1]+"    |    "+map4[1][2]+"   |    "+map4[1][3]+"   |");
        printOut("|   SpawnPoint   |                |               |               |");
        printOut("|______| |_______|______| |_______|_______________|_______________|");
        printOut("|      Grey      |      Grey      |     Grey      |    Yellow     |");
        printOut("|    "+map[2][0]+"    |    "+map[2][1]+"    |    "+map[2][2]+"   |    "+map[2][3]+"   |");
        printOut("|    "+map1[2][0]+"    |    "+map1[2][1]+"    |    "+map1[2][2]+"   _    "+map1[2][3]+"   |");
        printOut("|    "+map2[2][0]+"    |    "+map2[2][1]+"    |    "+map2[2][2]+"   _    "+map2[2][3]+"   |");
        printOut("|    "+map3[2][0]+"    |    "+map3[2][1]+"    |    "+map3[2][2]+"   |    "+map3[2][3]+"   |");
        printOut("|    "+map4[2][0]+"    |    "+map4[2][1]+"    |    "+map4[2][2]+"   |    "+map4[2][3]+"   |");
        printOut("|                |                |               |   SpawnPoint  |");
        printOut("|________________|________________|_______________|_______________|");
    }


    /**
     * Method to show player its weapon cards
     */
    @Override
    public void showPlayerWeapons() {
        List<Weapon> weaponcards = client.getPlayerVisibleData().getPlayer().getWeapons();
        if(!weaponcards.isEmpty()) printOut("Your Weapon Cards are: ");
        else printOut("You have no Weapon Cards");
        int i=1;
        for(Weapon weaponcard:weaponcards){
            printOut(i+". "+weaponcard.getName());
            showWeaponInfo(weaponcard);
            i++;
        }
    }
    /**
     * Method to show the active player its PowCards
     */
    @Override
    public void showPlayerPows() {
        List<PowCard> powcards = client.getPlayerVisibleData().getPlayer().getPows();
        if(!powcards.isEmpty()) printOut("Your PowCards are: ");
        else printOut("Your have no PowCards");
        int i=1;
        for(PowCard powcard:powcards){
            printOut(i+". "+powcard.getName());
            i++;
        }
    }

    /**
     * Method to show a player its PowCards, and colors to choose the spawn point cell and to convert Pows in Ammos
     */
    @Override
    public void showPlayerPowWithColors() {
        List<PowCard> powCards = client.getPlayerVisibleData().getPlayer().getPows();
        printOut("Your PowCards are: ");
        int i=1;
        for(PowCard powCard:powCards){
            //Spawn point cell are just blue, red and yellow. Check of validity is made in controller class.
            printOut(i+". "+powCard.getName()+" with the color: "+ getData.getColorFromInt(powCard.getColor()));
            i++;
        }
    }

    /**
     * Method to show a player its PowCards, used in response to an attack
     */
    @Override
    public void showPlayerPowsForAttack(){
        List<PowCard> powcards = client.getPlayerVisibleData().getPlayer().getPows();
        printOut("Player "+ client.getPlayerVisibleData().getPlayer().getName()+" your PowCards are: ");

        int i=1;
        for(PowCard powcard:powcards){
            printOut(i+". "+powcard.getName());
            i++;
        }
    }

    /**
     * Method to show the active player how many ammos he has
     */
    @Override
    public void showPlayerAmmos(){
        printOut("You have:");
        printOut(client.getPlayerVisibleData().getPlayer().getAmmo(0)+" red Ammos");
        printOut(client.getPlayerVisibleData().getPlayer().getAmmo(1)+" blue Ammos");
        printOut(client.getPlayerVisibleData().getPlayer().getAmmo(2)+" yellow Ammos");
    }

    /**
     * Method to show Weapon Cards in SpawnPoint Cell
     */
    @Override
    public void showSpawnPointWeapons(){
        int x = client.getPlayerVisibleData().getPlayer().getCel().getX();
        int y = client.getPlayerVisibleData().getPlayer().getCel().getY();
        SpawnPointCell cell = (SpawnPointCell)client.getPlayerVisibleData().getDashboard().getMap(x, y);
        printOut("In the SpawnPoint Cell at line "+x+" and column "+y+" there are these Weapon Cards: ");
        List<Weapon> weapons = cell.getSpawnPointCellWeapons();
        int i=1;
        for(Weapon weapon:weapons){
            try {
                printOut(i + ". " + weapon.getName() + " at this price:");
                printOut(weapon.getCostToGrab().get(0) + " red Ammos");
                printOut(weapon.getCostToGrab().get(1) + " blue Ammos");
                printOut(weapon.getCostToGrab().get(2) + " yellow Ammos");
                i++;
            } catch (NullPointerException e){
                printOut(i + ". Arma già raccolta.");
                i++;
            } catch(IndexOutOfBoundsException e){
                printOut("You have yet to spawn. ");
            }
        }
    }

    /**
     * Method to show AmmoCard in Normal Cell
     */
    @Override
    public void showAmmoCard(){
        int x = client.getPlayerVisibleData().getPlayer().getCel().getX();
        int y = client.getPlayerVisibleData().getPlayer().getCel().getY();
        NormalCell cell = (NormalCell)client.getPlayerVisibleData().getDashboard().getMap(x, y);
        AmmoCard ammo = cell.getAmmoCard();
        if(ammo.getType()==1){printOut("With this AmmoCard you can collect a PowCard");}
        printOut("In the Cell at line "+x+" and column "+y+" there is an Ammo Card that contains: ");
        for(int i=0; i<ammo.toString().length(); i++){
            printOut("One "+getData.getColorFromInt(ammo.toString().charAt(i))+" ammo");
        }
    }

    /**
     * Method to notify attacked player
     */
    @Override
    public void notifyAttackedPlayer(Player attackedplayer) {

    }

    /**
     * Method to ask the player which cards he wants to buy if in a SpawnPoint Cell
     * @return the index of teh chosen weapon
     */
    @Override
    public int getWeaponCard(){
        printOut("Which WeaponCard do you want to buy?");
        int numberOfWeapon=0;
        List<Weapon> weaponcards=new ArrayList<>();
        int CardToBuy=-1;
        //player can choose card 1, 2, 3. I take all the weapons in the spawn point cell
        int x = client.getPlayerVisibleData().getPlayer().getCel().getX();
        int y = client.getPlayerVisibleData().getPlayer().getCel().getY();
        SpawnPointCell cell = (SpawnPointCell) client.getPlayerVisibleData().getDashboard().getMap(x, y);
        for(Weapon w:cell.getSpawnPointCellWeapons()){
           weaponcards.add(w);
        }

        //Print of weapon cards with their index and price and money of the player
        printOut("You have "+client.getPlayerVisibleData().getPlayer().getAmmo(0)+" red Ammos");
        printOut("You have "+client.getPlayerVisibleData().getPlayer().getAmmo(1)+" blue Ammos");
        printOut("You have "+client.getPlayerVisibleData().getPlayer().getAmmo(2)+" yellow Ammos");
        printOut("Choose -1 not to buy.\n");
        int numberRedAmmos;
        int numberBlueAmmos;
        int numberYellowAmmos;
        List<Integer> nRedAmmos = new ArrayList<>();
        List<Integer> nBlueAmmos = new ArrayList<>();
        List<Integer> nYellowAmmos = new ArrayList<>();

        for(int i=0;i<weaponcards.size();i++){
            List<Integer> price = weaponcards.get(i).getCostToGrab();
            numberRedAmmos=price.get(0);
            numberBlueAmmos=price.get(1);
            numberYellowAmmos=price.get(2);
            printOut(i+". "+weaponcards.get(i).getName()+" \nPrice:");
            printOut(numberRedAmmos+" red Ammos");
            nRedAmmos.add(numberRedAmmos);
            printOut(numberBlueAmmos+" blue Ammos");
            nBlueAmmos.add(numberBlueAmmos);
            printOut(numberYellowAmmos+" yellow Ammos\n");
            nYellowAmmos.add(numberYellowAmmos);
        }

        numberOfWeapon=this.getData.getInt(-1, 2);
        if(numberOfWeapon!=-1 && nRedAmmos.get(numberOfWeapon)<=client.getPlayerVisibleData().getPlayer().getAmmo(0) &&
                nBlueAmmos.get(numberOfWeapon)<=client.getPlayerVisibleData().getPlayer().getAmmo(1) &&
                nYellowAmmos.get(numberOfWeapon)<=client.getPlayerVisibleData().getPlayer().getAmmo(2)){
            CardToBuy= numberOfWeapon;
        }else{
            printOut("You don't have enough money to buy this card!");
        }
        return CardToBuy;
    }

    /**
     * Method to get weapon card to attack
     * @return teh weapon card to attack
     */
    @Override
    public int getWeaponCardtoAttack(){
        printOut("Which WeaponCard do you want to use?");
        showPlayerWeapons();
        int numberOfWeapon=this.getData.getInt(1, 3);
        return numberOfWeapon;
    }

    /**
     * Method to show the player direction
     */
    @Override
    public void showDirection(){
    printOut("Write the sequence of movements you want to do:");
        printOut("'N' for north");
        printOut("'E' for east");
        printOut("'S' for south");
        printOut("'W' for west");
        printOut("'Stop' to terminate");
    }

    /**
     * Method to print the player move
     */
    @Override
    public void printPlayerMove(){printOut("You have moved.");}

    /**
     * Method to print the player data
     */
    @Override
    public void printPlayerData() {
        printOut("Total damages: "+client.getPlayerVisibleData().getPlayer().getTotalDamage());
        int line = client.getPlayerVisibleData().getPlayer().getCel().getX();
        int column = client.getPlayerVisibleData().getPlayer().getCel().getY();
        printOut("You are at line: "+line+", column: "+column);
        printOut("Total weapons: "+client.getPlayerVisibleData().getPlayer().getNumberWeapon());
        showPlayerWeapons();
        printOut("Total pows: "+client.getPlayerVisibleData().getPlayer().getNumberPow());
        showPlayerPows();
    }
    /**
     * Method to advise the player he has been damaged
     */
    @Override
    //TODO
    public void printDamagedPlayer(int numberdamages, String attackerplayername){
        printOut("You have received "+numberdamages+" damages by Player "+attackerplayername);
    }
    /**
     * Method to advise the player he has been given marks
     */
    @Override
    //TODO
    public void printMarkedPlayer(int numbermarks, String attackerplayername){
        printOut("You have received "+numbermarks+" marks by Player "+attackerplayername);
    }
    /**
     * Method to advise the player of the consequences of his attack
     */
    @Override
    //TODO
    public void printDamagerAndMarkerPlayer(int numberdamages, int numbermarks, String attackedplayername){
        printOut("You have made "+numberdamages+" damages and "+numbermarks+" marks to Player "+attackedplayername);
    }

    /**
     * Method to show weapon info
     */
    @Override
    public void showWeaponInfo(Weapon weapon){
        printOut(weapon.getName());
        for(int i=0; i<weapon.getNumberAttack(); i++){
            printOut("You can move of "+weapon.getAttack(i).getMoveMe()+" before shooting");
            printOut("You can move your enemy of "+weapon.getAttack(i).getMoveYou()+" before shooting");
        }
    }

    /**
     * Method to get gun index
     */
    @Override
    public void getGunIndex()
    {

        printOut("Insert the index of the gun you would like to use:");
        int gunindex= getData.getInt(0,2);
        String index = Integer.toString(gunindex);
        Message message = new ShootIndexRequest(index,client.getUserID());
        client.sendMessage(message);

    }

    /**
     * Method to get serie index
     */
    @Override
    public void getSerieIndex()
    {
        printOut("Insert the index of the serie you would like to use:");
        int gunIndex= getData.getInt(0,1);
        String index = Integer.toString(gunIndex);
        Message message = new ShootIndexRequest(index,client.getUserID());
        client.sendMessage(message);
    }

    /**
     * Method to get player index
     */
    @Override
    public void getPlayerIndex()
    {
        printOut("Insert the index of the Player you Would like to shoot to");
        int gunIndex= getData.getInt(0,5);
        String index = Integer.toString(gunIndex);
        Message message = new ShootIndexRequest(index,client.getUserID());
        client.sendMessage(message);
    }

    /**
     * Method to get cell index
     */
    @Override
    public void getCellIndex()
    {
        printOut("Insert the index of the Cell you would like to shoot to:");
        int gunIndex= getData.getInt(0,50);
        String index = Integer.toString(gunIndex);
        Message message = new ShootIndexRequest(index,client.getUserID());
        client.sendMessage(message);
    }

    /**
     * Method to have next attack
     */
    @Override
    public void getNextAttack()
    {
        printOut("Do you want to continue with the next attack of the card?");
        printOut("1: Yes");
        printOut("2: No");
        int gunIndex= getData.getInt(1,2);
        String index = Integer.toString(gunIndex);
        Message message = new ShootIndexRequest(index,client.getUserID());
        client.sendMessage(message);
    }

    /**
     * Method to show Payment error
     */
    @Override
    public void showPaymentError()
    {
        printOut("You don't have enougth ammo!");
    }

    /**
     * Method to get the Pow index
     */
    @Override
    public void getPowIndex() {
        printOut("Digit 1 to use Teleporter, 2 to use Newton, 3 to cancel");
        int gunIndex= getData.getInt(1,3);
        String index = Integer.toString(gunIndex);
        Message messaage = new MuxPowRequest(index,client.getUserID());
        client.sendMessage(messaage);
    }

    /**
     * Method to get the position index
     */
    @Override
    public void getPosIndex() {
        printOut("the number of the cell (from 1 to 12)");
        int gunIndex= getData.getInt(1,12);
        String index = Integer.toString(gunIndex);
        Message messaage = new MuxPowRequest(index,client.getUserID());
        client.sendMessage(messaage);

    }

    /**
     * Method to get the direction index
     */
    @Override
    public void getDirectionIndex() {
        printOut("Digit the direction");
        int gunIndex= getData.getInt(0,3);
        String index = Integer.toString(gunIndex);
        Message messaage = new MuxPowRequest(index,client.getUserID());
        client.sendMessage(messaage);

    }

    /**
     * Method to get number steps
     */
    @Override
    public void getNumberStep() {
        printOut("Digit the number of steps (max 3)");
        int gunIndex= getData.getInt(1,3);
        String index = Integer.toString(gunIndex);
        Message messaage = new MuxPowRequest(index,client.getUserID());
        client.sendMessage(messaage);

    }

    /**
     * Method to get move player index
     */
    @Override
    public void getMovePlayerIndex() {
        printOut("Digit the number of the player");
        int gunIndex= getData.getInt(1,5);
        String index = Integer.toString(gunIndex);
        Message messaage = new MuxPowRequest(index,client.getUserID());
        client.sendMessage(messaage);
    }

    /**
     * Method to get scope index
     */
    @Override
    public void getScopeIndex() {
        printOut("Digit the number of the player you want to attack with the scope, digit -1 not to attack them");
        int gunIndex= getData.getInt(-1,5);
        String index = Integer.toString(gunIndex);
        Message messaage = new UseScopeRequest(index,client.getUserID());
        client.sendMessage(messaage);

    }

    /**
     * Method to get granade index
     */
    @Override
    public void getGranadeIndex() {
        printOut("Digit 1 to use granade, 0 if you don't want");
        int gunIndex= getData.getInt(1,2);
        String index = Integer.toString(gunIndex);
        Message messaage = new UseGranadeRequest(index,client.getUserID());
        client.sendMessage(messaage);
    }


}
