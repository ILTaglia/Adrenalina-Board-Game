package client;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import exceptions.InvalidColorException;
import model.*;
import network.messages.ClientRequest.*;
import network.messages.Message;
import network.client.Client;
import network.messages.ClientRequest.PowCardDiscardClientRequest;
import utils.*;

public class CLIView implements View {
    private static final Logger LOGGER= Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static PrintStream printStream=System.out;
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
            printStream.println("You are in Waiting Room. Your ID is:" + message.getInfo());
        }
        else if(message.getContent().equals("InfoPowCard")){
            printStream.println("You are drawn a Pow Card:" + message.getInfo());
            //TODO: Chiamare metodo che stampa Pow Card
        }
        else {
            printStream.println("Message received:" + message.getInfo());
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
        String colorRequired=getData.getValidColorForPlayer();
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
        //TODO: STAMPARE LE INFO NECESSARIE PER SCEGLIERE
        // EX: CELLA IN CUI SI TROVA IL GIOCATORE COSA CONTIENE!
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
    public void chooseRunDirection() {
        List<String> direction;
        do{
            direction=getData.getValidListDirectionForPlayer();
            if(direction.isEmpty()) printStream.println("You need to choose at least one direction.");
        }while(direction.isEmpty());
        Message message=new RunClientRequest(direction,client.getUserID());
        client.sendMessage(message);
    }

    @Override
    public void chooseDiscardWeapon() {
        int indexWeapon;
        printStream.println("Answer 'Yes' or 'No'");
        if(getData.askYesOrNo()){
            showPlayerWeapons();
            printStream.println("Which Weapon do you want to discard?");
            indexWeapon=getData.getInt(0,client.getPlayerVisibleData().getSinglePlayer().getNumberWeapon())-1;
            Message message=new WeaponDiscardToGrabClientRequest(Integer.toString(indexWeapon),client.getUserID());
            client.sendMessage(message);
        }
        else{
            printStream.println("You can't have more than three Weapon Card. Choose an other Action");
            chooseAction();
        }
    }



    @Override
    public  void chooseWeaponToGrab(){
        int indexWeapon;
        showSpawnPointWeapons();
        showPlayerAmmos();
        printStream.println("Which Weapon do you want to grab?");
        indexWeapon=getData.getInt(0,2);       //TODO: correggere
        Message message=new WeaponGrabClientRequest(Integer.toString(indexWeapon),client.getUserID());
        client.sendMessage(message);
    }

    @Override
    public void askUsePowToGrabWeapon() {
        int indexWeapon;
        int indexPowCard;
        printStream.println("Answer 'Yes' or 'No'");
        if(getData.askYesOrNo()){
            printStream.println("Which Weapon do you want to grab?");
            indexWeapon=getData.getInt(0,2);
            showPlayerPows();
            printStream.println("Which pow card do you want to use to grab Weapon?");
            indexPowCard=getData.getInt(1,getNumberOfPow())-1;
            Message message=new PowToWeaponGrabClientRequest(Integer.toString(indexWeapon),Integer.toString(indexPowCard),client.getUserID());
            client.sendMessage(message);
        }
        else{
            printStream.println("If you don't want to grab this Weapon you can choose an other action");
            chooseAction();
            //TODO: richiamo la richiesta di azione o chiedo un altro Weapon?
        }


    }

    @Override
    public void chooseDiscardPowCard() {
        int indexPowCard;
        printStream.println("Answer 'Yes' or 'No'");
        if(getData.askYesOrNo()){
            showPlayerPows();
            printStream.println("Which PowCard do you want to discard?");
            indexPowCard=getData.getInt(0,client.getPlayerVisibleData().getSinglePlayer().getnumberpow())-1;
            Message message=new PowCardDiscardClientRequest(Integer.toString(indexPowCard),client.getUserID());
            client.sendMessage(message);
        }
        else{
            printStream.println("You can't have more than three PowCard. You can't draw new PowCard.\n");
            //Non chiedo ulteriori info perchè il Player comunque ha raccolto e ha accettato di non prendere la PowCard.
        }
    }


    @Override
    public int getNumberOfPow(){
        return client.getPlayerVisibleData().getSinglePlayer().getPows().size();
    }


    @Override
    public void chooseStartingCell(){
        System.out.println("\nSelect the SpawnPoint cell where you want to start. Write number of line, then column.");
        System.out.println("There are three SpawnPoint cells in the game:");
        System.out.println("Line 0, column 2 - Blue cell");
        System.out.println("Line 1, column 0 - Red cell");
        System.out.println("Line 2, column 3 - Yellow cell");
        System.out.println("You have these PowCards, choose with the color of one of them the spawn point cell:");
        this.showPlayerPowsColors();
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
        this.showPlayerPows();
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
        int indexMap = client.getPlayerVisibleData().getSingleDashboard().getMapType();
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
        List<Coordinate> position = client.getPlayerVisibleData().getPlayerPosition();
        for(int h=0; h<position.size(); h++){
            line=position.get(h).getX();
            column=position.get(h).getY();
            if(map[line][column].equals(s)) map[line][column] = "   "+h+"   ";
            else {
                if(map1[line][column].equals(s)) map1[line][column] = "   "+h+"   ";
                else{
                    if(map2[line][column].equals(s)) map2[line][column] = "   "+h+"   ";
                    else {
                        if(map3[line][column].equals(s)) map3[line][column] = "   "+h+"   ";
                        else map4[line][column] = "   "+h+"   ";
                    }
                }
            }
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
        List<Coordinate> position = client.getPlayerVisibleData().getPlayerPosition();
        for(int h=0; h<position.size(); h++){
            line=position.get(h).getX();
            column=position.get(h).getY();
            if(map[line][column].equals(s)) map[line][column] = "   "+h+"   ";
            else {
                if(map1[line][column].equals(s)) map1[line][column] = "   "+h+"   ";
                else{
                    if(map2[line][column].equals(s)) map2[line][column] = "   "+h+"   ";
                    else {
                        if(map3[line][column].equals(s)) map3[line][column] = "   "+h+"   ";
                        else map4[line][column] = "   "+h+"   ";
                    }
                }
            }
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
        List<Coordinate> position = client.getPlayerVisibleData().getPlayerPosition();
        for(int h=0; h<position.size(); h++){
            line=position.get(h).getX();
            column=position.get(h).getY();
            if(map[line][column].equals(s)) map[line][column] = "   "+h+"   ";
            else {
                if(map1[line][column].equals(s)) map1[line][column] = "   "+h+"   ";
                else{
                    if(map2[line][column].equals(s)) map2[line][column] = "   "+h+"   ";
                    else {
                        if(map3[line][column].equals(s)) map3[line][column] = "   "+h+"   ";
                        else map4[line][column] = "   "+h+"   ";
                    }
                }
            }
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
        List<Weapon> weaponcards = client.getPlayerVisibleData().getSinglePlayer().getWeapons();
        if(!weaponcards.isEmpty()) printStream.println("Your Weapon Cards are: ");
        else printStream.println("You have no Weapon Cards");
        int i=1;
        for(Weapon weaponcard:weaponcards){
            System.out.println(i+". "+weaponcard.getName());
            i++;
        }
    }

    //Method to show the active player its PowCards
    @Override
    public void showPlayerPows() {
        List<PowCard> powcards = client.getPlayerVisibleData().getSinglePlayer().getPows();
        if(!powcards.isEmpty()) printStream.println("Your PowCards are: ");
        else printStream.println("Your have no PowCards");
        int i=1;
        for(PowCard powcard:powcards){
            printStream.println(i+". "+powcard.getName());
            i++;
        }
    }

    //Method to show a player its PowCards, and colors to choose the spawn point cell and to convert Pows in Ammos
    @Override
    public void showPlayerPowsColors() {
        List<PowCard> powcards = client.getPlayerVisibleData().getSinglePlayer().getPows();
        printStream.println("Your PowCards are: ");
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
    public void showPlayerPowsForAttack(){
        List<PowCard> powcards = client.getPlayerVisibleData().getSinglePlayer().getPows();
        printStream.println("Player "+ client.getPlayerVisibleData().getSinglePlayer().getName()+" your PowCards are: ");

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
        printStream.println(client.getPlayerVisibleData().getSinglePlayer().getAmmo(0)+" red Ammos");
        printStream.println(client.getPlayerVisibleData().getSinglePlayer().getAmmo(1)+" blue Ammos");
        printStream.println(client.getPlayerVisibleData().getSinglePlayer().getAmmo(2)+" yellow Ammos");
    }

    //Method to notify the player he has been attacked, useful for players that use a Pow in response to an attack //TODO
    @Override
    //TODO da rivedere serve notificare a chi è attaccato
    public void notifyAttackedPlayer(Player attackedplayer){
        printStream.println("Player "+attackedplayer.getName()+"you have being attacked. Do you want to use any Pow?");
        printStream.println("0. Yes");
        printStream.println("1. No");
        int choice=this.getData.getInt(-1, 1);
        if(choice!=-1){
            printStream.println("Player "+attackedplayer.getName()+"which Pow do you want to use?");
            showPlayerPows();
            int numberOfPow=this.getData.getInt(-1, 2);
            if(numberOfPow!=-1){
                client.getPlayerVisibleData().getSinglePlayer().getPowByIndex(numberOfPow).getLife();
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
        int x = client.getPlayerVisibleData().getSinglePlayer().getCel().getX();
        int y = client.getPlayerVisibleData().getSinglePlayer().getCel().getY();
        SpawnPointCell cell = (SpawnPointCell)client.getPlayerVisibleData().getSingleDashboard().getmap(x, y);
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
        printStream.println("Which WeaponCard do you want to buy?");
        int numberOfWeapon=0;
        List<Weapon> weaponcards=new ArrayList<>();
        int CardToBuy=-1;
        //player can choose card 1, 2, 3. I take all the weapons in the spawn point cell
        int x = client.getPlayerVisibleData().getSinglePlayer().getCel().getX();
        int y = client.getPlayerVisibleData().getSinglePlayer().getCel().getY();
        SpawnPointCell cell = (SpawnPointCell) client.getPlayerVisibleData().getSingleDashboard().getmap(x, y);
        for(Weapon w:cell.getSpawnPointCellWeapons()){
           weaponcards.add(w);
        }

        //Print of weapon cards with their index and price and money of the player
        printStream.println("You have "+client.getPlayerVisibleData().getSinglePlayer().getAmmo(0)+" red Ammos");
        printStream.println("You have "+client.getPlayerVisibleData().getSinglePlayer().getAmmo(1)+" blue Ammos");
        printStream.println("You have "+client.getPlayerVisibleData().getSinglePlayer().getAmmo(2)+" yellow Ammos");
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
        //TODO questo serve per dire al player che non ha abbastanza soldi per comprare la carta selezionata
        if(numberOfWeapon!=-1 && nRedAmmos.get(numberOfWeapon)<=client.getPlayerVisibleData().getSinglePlayer().getAmmo(0) &&
                nBlueAmmos.get(numberOfWeapon)<=client.getPlayerVisibleData().getSinglePlayer().getAmmo(1) &&
                nYellowAmmos.get(numberOfWeapon)<=client.getPlayerVisibleData().getSinglePlayer().getAmmo(2)){
            CardToBuy= numberOfWeapon;
        }else{
            printStream.println("You don't have enough money to buy this card!");
        }
        return CardToBuy;
    }

    @Override
    public int getPowCard(){
        printStream.println("Which PowCard do you want to use?");
        showPlayerPows();
        int numberOfPow=this.getData.getInt(-1, 2);
        if(numberOfPow!=-1){
            client.getPlayerVisibleData().getSinglePlayer().getPowByIndex(numberOfPow).getLife();
            //TODO quale metodo per l'effetto del potenziamento
        }else{
            printStream.println("You don't own this pow!");
        }
        return numberOfPow;
    }

    @Override
    public int getWeaponCardtoAttack(){
        printStream.println("Which WeaponCard do you want to use?");
        showPlayerWeapons();
        int numberOfWeapon=this.getData.getInt(1, 3);
        return numberOfWeapon;
    }

    //Method to ask the direction for movement
    @Override
    public String getDirection(){
        printStream.println("Which direction do you want to move for a single step? Write:");
        printStream.println("'N' for north");
        printStream.println("'E' for east");
        printStream.println("'S' for south");
        printStream.println("'W' for west");
        return this.getData.getValidDirectionForPlayer();
    }

    //Method to ask the list direction for movement
    @Override
    public List<String> getListDirection(){
        List<String> destination = new ArrayList<>();
        printStream.println("Write the sequence of movements you want to do:");
        printStream.println("'N' for north");
        printStream.println("'E' for east");
        printStream.println("'S' for south");
        printStream.println("'W' for west");
        printStream.println("'Stop' to terminate");
        String stop = "Stop";

        //Movements are maximum of three cells, so in three direction. In special movements for some actions there are restrictions
        //for example moving of maximum one before shooting but this method is for a general input of sequence
        for(int i=0; i<3; i++){
            String d = this.getData.getValidDirectionForPlayer();
            if(!d.equals(stop)) destination.add(d);
            else {
                return destination;
            }
        }
        return destination;
    }

    @Override
    public void printPlayerMove(){printStream.println("You have moved.");}

    //Method to tell the player its state
    @Override
    public void printPlayerData(){
        printMap();
        printStream.println("Total damages: "+client.getPlayerVisibleData().getSinglePlayer().gettotaldamage());
        for(int i=0; i<5; i++){
            if(i!=client.getPlayerVisibleData().getSinglePlayer().getcolor()){
                printStream.println("Damages for player with color "+i);
                printStream.println(client.getPlayerVisibleData().getPlayerDamages(i).get(0)+" damages by player (0) Blue");
                printStream.println(client.getPlayerVisibleData().getPlayerDamages(i).get(1)+" damages by player (1) Green");
                printStream.println(client.getPlayerVisibleData().getPlayerDamages(i).get(2)+" damages by player (2) Yellow");
                printStream.println(client.getPlayerVisibleData().getPlayerDamages(i).get(3)+" damages by player (3) Pink");
                printStream.println(client.getPlayerVisibleData().getPlayerDamages(i).get(4)+" damages by player (4) Grey");
            }
        }
        for(int i=0; i<5; i++){
            if(i!=client.getPlayerVisibleData().getSinglePlayer().getcolor()){
                printStream.println("Marks for player with color "+i);
                printStream.println(client.getPlayerVisibleData().getPlayerMarks(i).get(0)+" damages by player (0) Blue");
                printStream.println(client.getPlayerVisibleData().getPlayerMarks(i).get(1)+" damages by player (1) Green");
                printStream.println(client.getPlayerVisibleData().getPlayerMarks(i).get(2)+" damages by player (2) Yellow");
                printStream.println(client.getPlayerVisibleData().getPlayerMarks(i).get(3)+" damages by player (3) Pink");
                printStream.println(client.getPlayerVisibleData().getPlayerMarks(i).get(4)+" damages by player (4) Grey");
            }
        }
        //TODO
        printStream.println("Actual score: "+client.getPlayerVisibleData().getSinglePlayer().getScore());

    }

    //Method to advise the player he has been damaged

    @Override
    //TODO
    public void printDamagedPlayer(int numberdamages, String attackerplayername){
        printStream.println("You have received "+numberdamages+" damages by Player "+attackerplayername);
    }

    //Method to advise the player he has been given marks
    @Override
    //TODO
    public void printMarkedPlayer(int numbermarks, String attackerplayername){
        printStream.println("You have received "+numbermarks+" marks by Player "+attackerplayername);
    }

    //Method to advise the player of the consequences of his attack
    @Override
    //TODO
    public void printDamagerAndMarkerPlayer(int numberdamages, int numbermarks, String attackedplayername){
        printStream.println("You have made "+numberdamages+" damages and "+numbermarks+" marks to Player "+attackedplayername);
    }


}
