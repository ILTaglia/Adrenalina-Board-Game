package client;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import controller.GrabWeapon;
import exceptions.InvalidColorException;
import model.*;
import network.messages.ClientRequest.ActionClientRequest;
import network.messages.ClientRequest.ColorClientRequest;
import controller.Game;
import network.messages.ClientRequest.MapClientRequest;
import network.messages.Message;
import network.client.Client;
import network.messages.ClientRequest.RunClientRequest;
import utils.*;

public class CLIView implements View {
    private static final Logger LOGGER= Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static PrintStream printStream=System.out;
    private Game game;
    private Match match;        //TODO: Da sostituire con una classe apposita in cui la view conserva dati Player
    private Client client;
    private GetData getData=new GetData();

    //TODO: sostituire System.out con printStream

    public CLIView(Client client){
        this.client=client;
        LOGGER.setLevel(Level.INFO);
    }
    /*
     **********************************************************
     * Metodi per Avvio Client                                *
     **********************************************************
     */
    @Override
    public void start(){
        setConnection();
        login();
    }

    @Override
    public void setConnection(){
        printStream.println("Scegliere quale tipologia di connessione utilizzare:\t\n" +
                "1. RMI" +
                "2. Socket");
        int connectionChoice=getData.getInt(1, 2);

        if(connectionChoice==1){
            client.setConnection(false);
        }
        else{
            client.setConnection(true);
        }
        client.launchConnection();
    }

    @Override
    public void login(){
        System.out.println("Digitare proprio username:");
        String user=getData.getName();
        client.requestToWR(user);
    }

    /*
     **********************************************************
     * Metodi per Eventi/Eccezioni                            *
     **********************************************************
     */

    @Override
    public void showException(String message) {
        System.out.println(message);
    }


    @Override
    public void showInfoMessage(Message message){
        if(message.getContent().equals("InfoID")) {
            System.out.println("You are in Waiting Room. Your ID is:" + message.getInfo());
        }
        else if(message.getContent().equals("InfoPowCard")){
            System.out.println("You are drawn a Pow Card:" + message.getInfo());
            //TODO: Chiamare metodo che stampa Pow Card
        }
        else {
            System.out.println("Message received:" + message.getInfo());
        }
    }

    /*
     **********************************************************
     * Metodi per Setup Match                              *
     **********************************************************
     */

    @Override
    public void createPlayer(){
        System.out.println("Digitare proprio colore:"+ "players available colors are Blue - Green - Yellow - Pink - Grey");
        String colorRequired=getData.getValidColorforPlayer();
        ColorClientRequest colorRequest=new ColorClientRequest(colorRequired,client.getUserID());
        System.out.println("Your required the color: "+ colorRequired);
        client.sendMessage(colorRequest);
    }

    @Override
    public void chooseMap() {
        System.out.println("Mappa 1");
        this.printmap1();
        System.out.println("Mappa 2");
        this.printmap2();
        System.out.println("Mappa 3");
        this.printmap3();
        System.out.println("Digitare mappa prescelta: 1, 2, 3");
        GetData getData = new GetData();
        int choice = getData.getInt(1, 3);
        String mapRequired = Integer.toString(choice);

        Message mapRequest=new MapClientRequest(mapRequired,client.getUserID());
        client.sendMessage(mapRequest);
    }

    public void chooseAction(){
        printStream.println("0. Run");
        printStream.println("1. Grab");
        printStream.println("2. Shoot");
        printStream.println("3. Grab with movement");
        printStream.println("4. Shoot with movement");
        printStream.println("5. Recharge");
        int choice = getData.getInt(0, 5);
        String indexAction = Integer.toString(choice);

        Message actionRequest=new ActionClientRequest(indexAction,client.getUserID());
        client.sendMessage(actionRequest);
    }

    @Override
    public void chooseRunDirection() {      //TODO: nomi delle variabili sensati?
        List<String> direction=getData.getValidListDirectionforPlayer();
        Message message=new RunClientRequest(direction,client.getUserID());
        client.sendMessage(message);
    }

    @Override
    public void chooseStartingCell(){
        for(Player p:match.getPlayers()){
            System.out.println("\nPlease, "+p.getName()+" select the SpawnPoint cell where you want to start. Write number of line, then column.");
            System.out.println("There are three SpawnPoint cells in the game:");
            System.out.println("Line 0, column 2 - Blue cell");
            System.out.println("Line 1, column 0 - Red cell");
            System.out.println("Line 2, column 3 - Yellow cell");
            System.out.println("You have these PowCards, choose with the color of one of them the spawn point cell:");
            this.showPlayerPowsColors(p);
            System.out.println("Insert: \nLine\nColumn\nNumber PowCard to use");
            int x= getData.getInt(0, 2);
            int y= getData.getInt(0, 3);
            int powindex = getData.getInt(1, 2);
            while(!((x==0 && y==2)||(x==1&&y==0)||(x==2 && y==3))){
                System.out.println("Not a valid SpawnPoint; insert new: \nLine\nColumn\nNumber of PowCard to use");
                x= getData.getInt(0, 2);
                y= getData.getInt(0, 3);
                powindex = getData.getInt(1, 2);
            }
            powindex--;
            int flag=0;
            while(flag==0){
                try{
                    //game.firstTurn(p, powindex, x, y);
                    flag=1;
                } catch(InvalidColorException e){
                    System.out.println("Not a valid SpawnPoint; insert new: \nLine\nColumn\nNumber of PowCard to use");
                    x= getData.getInt(0, 2);
                    y= getData.getInt(0, 3);
                    powindex = getData.getInt(1, 2);
                    powindex--;}
            }
            this.showPlayerPows(p);
        }
    }


    /*
     **********************************************************
     * Metodi per Avvio Client   FINE                         *
     **********************************************************
     */
    @Override
    public void welcomeMessage(int idClient) { printStream.println("START."); }

    @Override
    public void endMessage() { printStream.println("GAME OVER."); }

    @Override
    public void printMap() {
        int indexMap = match.getDashboard().getMapType();
        if(indexMap==1){this.printmap1();}
        if(indexMap==2){this.printmap2();}
        if(indexMap==3){this.printmap3();}
    }

    //Supporting methods to be used only internally the print map method
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
        int line;
        int column;
        for(int h=0; h<match.getPlayersSize(); h++){
            Player player = match.getPlayerByIndex(h);
            line = player.getCel().getX();
            column = player.getCel().getY();
            if(map[line][column].equals(s)) map[line][column] = player.getID();
            else {
                if(map1[line][column].equals(s)) map1[line][column] = player.getID();
                else{
                    if(map2[line][column].equals(s)) map2[line][column] = player.getID();
                    else {
                        if(map3[line][column].equals(s)) map3[line][column] = player.getID();
                        else map4[line][column] = player.getID();
                    }
                }
            }
            printStream.printf("Player "+player.getID()+" is "+player.getName()+"\n");
        }
        printStream.printf(" _________________________________________________                 \n");
        printStream.printf("|      Blue      |       Blue     |     Blue      |                \n");
        printStream.printf("|    "+map[0][0]+"    |    "+map[0][1]+"    |    "+map[0][2]+"   |    "+map[0][3]+"    \n");
        printStream.printf("|    "+map1[0][0]+"    |    "+map1[0][1]+"    |    "+map1[0][2]+"   |    "+map1[0][3]+"    \n");
        printStream.printf("|    "+map2[0][0]+"    |    "+map2[0][1]+"    |    "+map2[0][2]+"   |    "+map2[0][3]+"    \n");
        printStream.printf("|    "+map3[0][0]+"    |    "+map3[0][1]+"    |    "+map3[0][2]+"   |    "+map3[0][3]+"    \n");
        printStream.printf("|    "+map4[0][0]+"    |    "+map4[0][1]+"    |    "+map4[0][2]+"   |    "+map4[0][3]+"    \n");
        printStream.printf("|                |                |   SpawnPoint  |                \n");
        printStream.printf("|_______| |______|________________|______| |______|_______________ \n");
        printStream.printf("|      Red       |      Red       |      Red      |    Yellow     |\n");
        printStream.printf("|    "+map[1][0]+"    |    "+map[1][1]+"    |    "+map[1][2]+"   |    "+map[1][3]+"   |\n");
        printStream.printf("|    "+map1[1][0]+"    |    "+map1[1][1]+"    |    "+map1[1][2]+"   _    "+map1[1][3]+"   |\n");
        printStream.printf("|    "+map2[1][0]+"    |    "+map2[1][1]+"    |    "+map2[1][2]+"   _    "+map2[1][3]+"   |\n");
        printStream.printf("|    "+map3[1][0]+"    |    "+map3[1][1]+"    |    "+map3[1][2]+"   |    "+map3[1][3]+"   |\n");
        printStream.printf("|    "+map4[1][0]+"    |    "+map4[1][1]+"    |    "+map4[1][2]+"   |    "+map4[1][3]+"   |\n");
        printStream.printf("|   SpawnPoint   |                |               |               |\n");
        printStream.printf("|________________|______| |_______|_______________|_______________|\n");
        printStream.printf("                 |      Grey      |      Grey     |    Yellow     |\n");
        printStream.printf("     "+map[2][0]+"    |    "+map[2][1]+"    |    "+map[2][2]+"   |    "+map[2][3]+"   |\n");
        printStream.printf("     "+map1[2][0]+"    |    "+map1[2][1]+"    |    "+map1[2][2]+"   _    "+map1[2][3]+"   |\n");
        printStream.printf("     "+map2[2][0]+"    |    "+map2[2][1]+"    |    "+map2[2][2]+"   _    "+map2[2][3]+"   |\n");
        printStream.printf("     "+map3[2][0]+"    |    "+map3[2][1]+"    |    "+map3[2][2]+"   |    "+map3[2][3]+"   |\n");
        printStream.printf("     "+map4[2][0]+"    |    "+map4[2][1]+"    |    "+map4[2][2]+"   |    "+map4[2][3]+"   |\n");
        printStream.printf("                 |                |               |   SpawnPoint  |\n");
        printStream.printf("                 |________________|_______________|_______________|\n");
    }
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
        int line;
        int column;
        for(int h=0; h<match.getPlayersSize(); h++){
            Player player = match.getPlayerByIndex(h);
            line = player.getCel().getX();
            column = player.getCel().getY();
            if(map[line][column].equals(s)) map[line][column] = player.getID();
            else {
                if(map1[line][column].equals(s)) map1[line][column] = player.getID();
                else{
                    if(map2[line][column].equals(s)) map2[line][column] = player.getID();
                    else {
                        if(map3[line][column].equals(s)) map3[line][column] = player.getID();
                        else map4[line][column] = player.getID();
                    }
                }
            }
            printStream.printf("Player "+player.getID()+" is "+player.getName()+"\n");
        }
        printStream.printf(" _________________________________________________________________ \n");
        printStream.printf("|      Blue      |       Blue     |     Blue      |   Green       |\n");
        printStream.printf("|    "+map[0][0]+"    |    "+map[0][1]+"    |    "+map[0][2]+"   |    "+map[0][3]+"   |\n");
        printStream.printf("|    "+map1[0][0]+"    |    "+map1[0][1]+"    |    "+map1[0][2]+"   _    "+map1[0][3]+"   |\n");
        printStream.printf("|    "+map2[0][0]+"    |    "+map2[0][1]+"    |    "+map2[0][2]+"   _    "+map2[0][3]+"   |\n");
        printStream.printf("|    "+map3[0][0]+"    |    "+map3[0][1]+"    |    "+map3[0][2]+"   |    "+map3[0][3]+"   |\n");
        printStream.printf("|    "+map4[0][0]+"    |    "+map4[0][1]+"    |    "+map4[0][2]+"   |    "+map4[0][3]+"   |\n");
        printStream.printf("|                |                |   SpawnPoint  |               |\n");
        printStream.printf("|______| |_______|________________|______| |______|______| |______|\n");
        printStream.printf("|      Red       |      Red       |     Yellow    |    Yellow     |\n");
        printStream.printf("|    "+map[1][0]+"    |    "+map[1][1]+"    |    "+map[1][2]+"   |    "+map[1][3]+"   |\n");
        printStream.printf("|    "+map1[1][0]+"    |    "+map1[1][1]+"    |    "+map1[1][2]+"   |    "+map1[1][3]+"   |\n");
        printStream.printf("|    "+map2[1][0]+"    |    "+map2[1][1]+"    |    "+map2[1][2]+"   |    "+map2[1][3]+"   |\n");
        printStream.printf("|    "+map3[1][0]+"    |    "+map3[1][1]+"    |    "+map3[1][2]+"   |    "+map3[1][3]+"   |\n");
        printStream.printf("|    "+map4[1][0]+"    |    "+map4[1][1]+"    |    "+map4[1][2]+"   |    "+map4[1][3]+"   |\n");
        printStream.printf("|   SpawnPoint   |                |               |               |\n");
        printStream.printf("|________________|______| |_______|_______________|_______________|\n");
        printStream.printf("                 |      Grey      |     Yellow    |    Yellow     |\n");
        printStream.printf("     "+map[2][0]+"    |    "+map[2][1]+"    |    "+map[2][2]+"   |    "+map[2][3]+"   |\n");
        printStream.printf("     "+map1[2][0]+"    |    "+map1[2][1]+"    _    "+map1[2][2]+"   |    "+map1[2][3]+"   |\n");
        printStream.printf("     "+map2[2][0]+"    |    "+map2[2][1]+"    _    "+map2[2][2]+"   |    "+map2[2][3]+"   |\n");
        printStream.printf("     "+map3[2][0]+"    |    "+map3[2][1]+"    |    "+map3[2][2]+"   |    "+map3[2][3]+"   |\n");
        printStream.printf("     "+map4[2][0]+"    |    "+map4[2][1]+"    |    "+map4[2][2]+"   |    "+map4[2][3]+"   |\n");
        printStream.printf("                 |                |               |   SpawnPoint  |\n");
        printStream.printf("                 |________________|_______________|_______________|\n");
    }
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
        int line;
        int column;
        for(int h=0; h<match.getPlayersSize(); h++){
            Player player = match.getPlayerByIndex(h);
            line = player.getCel().getX();
            column = player.getCel().getY();
            if(map[line][column].equals(s)) map[line][column] = player.getID();
            else {
                if(map1[line][column].equals(s)) map1[line][column] = player.getID();
                else{
                    if(map2[line][column].equals(s)) map2[line][column] = player.getID();
                    else {
                        if(map3[line][column].equals(s)) map3[line][column] = player.getID();
                        else map4[line][column] = player.getID();
                    }
                }
            }
            printStream.printf("Player "+player.getID()+" is "+player.getName()+"\n");
        }
        printStream.printf(" _________________________________________________________________ \n");
        printStream.printf("|      Red       |       Blue     |     Blue      |   Green       |\n");
        printStream.printf("|    "+map[0][0]+"    |    "+map[0][1]+"    |    "+map[0][2]+"   |    "+map[0][3]+"   |\n");
        printStream.printf("|    "+map1[0][0]+"    _    "+map1[0][1]+"    |    "+map1[0][2]+"   _    "+map1[0][3]+"   |\n");
        printStream.printf("|    "+map2[0][0]+"    _    "+map2[0][1]+"    |    "+map2[0][2]+"   _    "+map2[0][3]+"   |\n");
        printStream.printf("|    "+map3[0][0]+"    |    "+map3[0][1]+"    |    "+map3[0][2]+"   |    "+map3[0][3]+"   |\n");
        printStream.printf("|    "+map4[0][0]+"    |    "+map4[0][1]+"    |    "+map4[0][2]+"   |    "+map4[0][3]+"   |\n");
        printStream.printf("|                |                |   SpawnPoint  |               |\n");
        printStream.printf("|________________|______| |_______|______| |______|______| |______|\n");
        printStream.printf("|      Red       |     Pink       |     Yellow    |    Yellow     |\n");
        printStream.printf("|    "+map[1][0]+"    |    "+map[1][1]+"    |    "+map[1][2]+"   |    "+map[1][3]+"   |\n");
        printStream.printf("|    "+map1[1][0]+"    |    "+map1[1][1]+"    |    "+map1[1][2]+"   |    "+map1[1][3]+"   |\n");
        printStream.printf("|    "+map2[1][0]+"    |    "+map2[1][1]+"    |    "+map2[1][2]+"   |    "+map2[1][3]+"   |\n");
        printStream.printf("|    "+map3[1][0]+"    |    "+map3[1][1]+"    |    "+map3[1][2]+"   |    "+map3[1][3]+"   |\n");
        printStream.printf("|    "+map4[1][0]+"    |    "+map4[1][1]+"    |    "+map4[1][2]+"   |    "+map4[1][3]+"   |\n");
        printStream.printf("|   SpawnPoint   |                |               |               |\n");
        printStream.printf("|______| |_______|______| |_______|_______________|_______________|\n");
        printStream.printf("|      Grey      |      Grey      |     Yellow    |    Yellow     |\n");
        printStream.printf("|    "+map[2][0]+"    |    "+map[2][1]+"    |    "+map[2][2]+"   |    "+map[2][3]+"   |\n");
        printStream.printf("|    "+map1[2][0]+"    |    "+map1[2][1]+"    _    "+map1[2][2]+"   |    "+map1[2][3]+"   |\n");
        printStream.printf("|    "+map2[2][0]+"    |    "+map2[2][1]+"    _    "+map2[2][2]+"   |    "+map2[2][3]+"   |\n");
        printStream.printf("|    "+map3[2][0]+"    |    "+map3[2][1]+"    |    "+map3[2][2]+"   |    "+map3[2][3]+"   |\n");
        printStream.printf("|    "+map4[2][0]+"    |    "+map4[2][1]+"    |    "+map4[2][2]+"   |    "+map4[2][3]+"   |\n");
        printStream.printf("|                |                |               |   SpawnPoint  |\n");
        printStream.printf("|________________|________________|_______________|_______________|\n");
    }


    //Method to show player its weapon cards
    @Override
    public void showPlayerWeapons() {
        Player player=match.getActivePlayer();
        List<Weapon> weaponcards = player.getWeapons();
        if(!weaponcards.isEmpty()) printStream.println("Player "+ player.getName()+" your Weapon Cards are: ");
        else printStream.println("Player "+ player.getName()+" you have no Weapon Cards");

        int i=1;
        for(Weapon weaponcard:weaponcards){
            System.out.println(i+". "+weaponcard.getName());
            i++;
        }
    }

    //Method to show the active player its PowCards
    @Override
    public void showPlayerPows() {
        Player player=match.getActivePlayer();
        List<PowCard> powcards = player.getPows();
        if(!powcards.isEmpty()) printStream.println("Player "+ player.getName()+" your PowCards are: ");
        else printStream.println("Player "+ player.getName()+" your have no PowCards");

        int i=1;
        for(PowCard powcard:powcards){
            printStream.println(i+". "+powcard.getName());
            i++;
        }
    }

    //Method to show a player its PowCards, and colors to choose the spawn point cell and to convert Pows in Ammos
    @Override
    public void showPlayerPowsColors(Player player) {
        List<PowCard> powcards = player.getPows();
        printStream.println("Player "+ player.getName()+" your PowCards are: ");
        String color = "";

        int i=1;
        for(PowCard powcard:powcards){
            //Spawn point cell are just blue, red and yellow. Check of validity is made in controller class.
            if(powcard.getColor()==0) color = "Red";
            else if(powcard.getColor()==1) color = "Blue";
            else if(powcard.getColor()==2) color = "Yellow";
            printStream.println(i+". "+powcard.getName()+" with the color "+color);
            i++;
        }
    }

    //Method to show a player its PowCards, used in response to an attack
    @Override
    public void showPlayerPows(Player player){
        List<PowCard> powcards = player.getPows();
        printStream.println("Player "+ player.getName()+" your PowCards are: ");

        int i=1;
        for(PowCard powcard:powcards){
            printStream.println(i+". "+powcard.getName());
            i++;
        }
    }

    //Method to show the active player how many ammos he has
    @Override
    public void showPlayerAmmos(){
        printStream.println("You have:");
        printStream.println(match.getActivePlayer().getAmmo(0)+" red Ammos");
        printStream.println(match.getActivePlayer().getAmmo(1)+" blue Ammos");
        printStream.println(match.getActivePlayer().getAmmo(2)+" yellow Ammos");
    }

    //Method to notify the player he has been attacked, useful for players that use a Pow in response to an attack //TODO
    @Override
    public void notifyAttackedPlayer(Player attackedplayer){
        printStream.println("Player "+attackedplayer.getName()+"you have being attacked. Do you want to use any Pow?");
        printStream.println("0. Yes");
        printStream.println("1. No");
        int choice=this.getData.getInt(-1, 1);
        if(choice!=-1){
            printStream.println("Player "+attackedplayer.getName()+"which Pow do you want to use?");
            showPlayerPows(attackedplayer);
            int numberOfPow=this.getData.getInt(-1, 2);
            if(numberOfPow!=-1){
                match.getActivePlayer().getPowByIndex(numberOfPow).getLife();
                //TODO quale metodo per l'effetto del potenziamento
                //TODO verifica che il potenziamento sia uno di quelli che si possono usare anche non durante il proprio turno
            }else{
                printStream.println("You don't own this pow!");
            }
        }
    }


    //Method to show Weapon Cards in SpawnPoint Cell
    @Override
    public void showSpawnPointWeapons(){
        Player player=match.getActivePlayer();
        int x = player.getCel().getX();
        int y = player.getCel().getY();
        SpawnPointCell cell = (SpawnPointCell)match.getDashboard().getmap(x, y);
        printStream.println("In the SpawnPoint Cell at line "+x+" and column "+y+" there are these Weapon Cards: ");
        List<Weapon> weapons = cell.getSpawnPointCellWeapons();

        int i=1;
        for(Weapon weapon:weapons){
            printStream.println(i+". "+weapon.getName());
            i++;
        }
    }


    //Method to ask the player which cards he wants to buy if in a SpawnPoint Cell
    @Override
    public int getWeaponCard(){
        GrabWeapon grabweapon = new GrabWeapon();
        printStream.println(match.getActivePlayer().getName()+", which WeaponCard do you want to buy?");
        int numberOfWeapon=0;
        List<Weapon> weaponcards=new ArrayList<>();
        int CardToBuy=-1;
        //player can choose card 1, 2, 3. I take all the weapons in the spawn point cell
        int x = match.getActivePlayer().getCel().getX();
        int y = match.getActivePlayer().getCel().getY();
        SpawnPointCell cell = (SpawnPointCell) match.getDashboard().getmap(x, y);
        for(Weapon w:cell.getSpawnPointCellWeapons()){
           weaponcards.add(w);
        }

        //Print of weapon cards with their index and price and money of the player
        printStream.println("You have "+match.getActivePlayer().getAmmo(0)+" red Ammos");
        printStream.println("You have "+match.getActivePlayer().getAmmo(1)+" blue Ammos");
        printStream.println("You have "+match.getActivePlayer().getAmmo(2)+" yellow Ammos");
        printStream.println("Choose -1 not to buy.\n");
        int numberRedAmmos;
        int numberBlueAmmos;
        int numberYellowAmmos;
        List<Integer> nRedAmmos = new ArrayList<>();
        List<Integer> nBlueAmmos = new ArrayList<>();
        List<Integer> nYellowAmmos = new ArrayList<>();

        for(int i=0;i<weaponcards.size();i++){
            List<Integer> price = weaponcards.get(i).returnPrice();
            numberRedAmmos=price.get(0);
            numberBlueAmmos=price.get(1);
            numberYellowAmmos=price.get(2);
            printStream.println(i+". "+weaponcards.get(i).getName()+" \nPrice:");
            printStream.println(numberRedAmmos+" red Ammos");
            nRedAmmos.add(numberRedAmmos);
            printStream.println(numberBlueAmmos+" blue Ammos");
            nBlueAmmos.add(numberBlueAmmos);
            printStream.println(numberYellowAmmos+" yellow Ammos\n");
            nYellowAmmos.add(numberYellowAmmos);
        }

        numberOfWeapon=this.getData.getInt(-1, 2);
        if(numberOfWeapon!=-1 && nRedAmmos.get(numberOfWeapon)<=match.getActivePlayer().getAmmo(0) &&
                nBlueAmmos.get(numberOfWeapon)<=match.getActivePlayer().getAmmo(1) &&
                nYellowAmmos.get(numberOfWeapon)<=match.getActivePlayer().getAmmo(2)){
            CardToBuy= numberOfWeapon;
        }else{
            printStream.println("You don't have enough money to buy this card!");
        }
        return CardToBuy;
    }

    @Override
    public int getPowCard(){
        printStream.println(match.getActivePlayer().getName()+", which PowCard do you want to use?");
        showPlayerPows();
        int numberOfPow=this.getData.getInt(-1, 2);
        if(numberOfPow!=-1){
            match.getActivePlayer().getPowByIndex(numberOfPow).getLife();
            //TODO quale metodo per l'effetto del potenziamento
        }else{
            printStream.println("You don't own this pow!");
        }
        return numberOfPow;
    }

    @Override
    public int getWeaponCardtoAttack(){
        printStream.println(match.getActivePlayer().getName()+", which WeaponCard do you want to use?");
        showPlayerWeapons();
        int numberOfWeapon=this.getData.getInt(1, 3);
        /*if(numberOfWeapon!=-1){
            try{
                //TODO
                match.getActivePlayer().getWeaponByIndex(numberOfWeapon).shooted();
            } catch(WeaponAlreadyUsedException e){
                printStream.println("You have already use this weapon, without recharging.");
            }
        }else{
            printStream.println("You don't own this weapon!");
        }*/
        return numberOfWeapon;
    }

    //Method to ask the direction for movement
    @Override
    public String getDirection(){
        printStream.println(match.getActivePlayer().getName()+", which direction do you want to move for a single step? Write:");
        printStream.println("'N' for north");
        printStream.println("'E' for east");
        printStream.println("'S' for south");
        printStream.println("'W' for west");
        return this.getData.getValidDirectionforPlayer();
    }

    //Method to ask the list direction for movement
    @Override
    public List<String> getListDirection(){
        List<String> destination = new ArrayList<>();
        printStream.println(match.getActivePlayer().getName()+", write the sequence of movements you want to do:");
        printStream.println("'N' for north");
        printStream.println("'E' for east");
        printStream.println("'S' for south");
        printStream.println("'W' for west");
        printStream.println("'Stop' to terminate");
        String stop = "Stop";

        //Movements are maximum of three cells, so in three direction. In special movements for some actions there are restrictions
        //for example moving of maximum one before shooting but this method is for a general input of sequence
        for(int i=0; i<3; i++){
            String d = this.getData.getValidDirectionforPlayer();
            if(!d.equals(stop)) destination.add(d);
            else {
                return destination;
            }
        }
        return destination;
    }

    @Override
    public void printPlayerMove(){printStream.println("Player "+match.getActivePlayer()+" has moved.");}

    //Method to tell the player its state
    @Override
    public void printPlayerData(){
        Player player = match.getActivePlayer();
        printStream.println("You are in cell at line "+player.getCel().getX()+" and column "+player.getCel().getY());
        printStream.println("Total damages: "+player.gettotaldamage());
        for(Player p:match.getPlayers()){
            if(!player.equals(p)) printStream.println("Total marks: "+player.getnumberdamage(p.getcolor())+" by Player "+p.getName());
        }
        printStream.println("Actual score: "+player.getScore());

    }

    //Method to advise the player he has been damaged

    @Override
    public void printDamagedPlayer(int numberdamages, String attackerplayername){
        printStream.println("You have received "+numberdamages+" damages by Player "+attackerplayername);
    }

    //Method to advise the player he has been given marks
    @Override
    public void printMarkedPlayer(int numbermarks, String attackerplayername){
        printStream.println("You have received "+numbermarks+" marks by Player "+attackerplayername);
    }

    //Method to advise the player of the consequences of his attack
    @Override
    public void printDamagerAndMarkerPlayer(int numberdamages, int numbermarks, String attackedplayername){
        printStream.println("You have made "+numberdamages+" damages and "+numbermarks+" marks to Player "+attackedplayername);
    }


}
