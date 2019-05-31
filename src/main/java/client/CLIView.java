package client;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import controller.Game;
import controller.GrabWeapon;
import controller.ManagingWeapons;
import exceptions.FullCellException;
import exceptions.MaxNumberPlayerException;
import exceptions.MaxNumberofCardsException;
import exceptions.NotEnoughAmmosException;
import model.*;
import network.messages.Message;
import utils.*;

public class CLIView implements View {
    private static final Logger LOGGER= Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static PrintStream printStream=System.out;
    private Match match;
    private int PlayerID;
    private GetData getData=new GetData();

    public CLIView(Match match){
        this.match = match;
        LOGGER.setLevel(Level.INFO);
    }
    /*
     **********************************************************
     * Metodi per Avvio Client                                *
     **********************************************************
     */
    @Override
    public void start(){
        //
    }

    @Override
    public void showException(String message) {

    }


    @Override
    public void showInfoMessage(Message message){

    }
    @Override
    public void login(){

    }



    /*
     **********************************************************
     * Metodi per Avvio Client                                *
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
            if(map[line][column].equals(s)) map[line][column] = player.getid();
            else {
                if(map1[line][column].equals(s)) map1[line][column] = player.getid();
                else{
                    if(map2[line][column].equals(s)) map2[line][column] = player.getid();
                    else {
                        if(map3[line][column].equals(s)) map3[line][column] = player.getid();
                        else map4[line][column] = player.getid();
                    }
                }
            }
            printStream.printf("Player "+player.getid()+" is "+player.getname()+"\n");
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
            if(map[line][column].equals(s)) map[line][column] = player.getid();
            else {
                if(map1[line][column].equals(s)) map1[line][column] = player.getid();
                else{
                    if(map2[line][column].equals(s)) map2[line][column] = player.getid();
                    else {
                        if(map3[line][column].equals(s)) map3[line][column] = player.getid();
                        else map4[line][column] = player.getid();
                    }
                }
            }
            printStream.printf("Player "+player.getid()+" is "+player.getname()+"\n");
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
            if(map[line][column].equals(s)) map[line][column] = player.getid();
            else {
                if(map1[line][column].equals(s)) map1[line][column] = player.getid();
                else{
                    if(map2[line][column].equals(s)) map2[line][column] = player.getid();
                    else {
                        if(map3[line][column].equals(s)) map3[line][column] = player.getid();
                        else map4[line][column] = player.getid();
                    }
                }
            }
            printStream.printf("Player "+player.getid()+" is "+player.getname()+"\n");
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
        if(!weaponcards.isEmpty()) printStream.println("Player "+ player.getname()+" your Weapon Cards are: ");
        else printStream.println("Player "+ player.getname()+" you have no Weapon Cards");

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
        if(!powcards.isEmpty()) printStream.println("Player "+ player.getname()+" your PowCards are: ");
        else printStream.println("Player "+ player.getname()+" your have no PowCards");

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
        printStream.println("Player "+ player.getname()+" your PowCards are: ");
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
        printStream.println("Player "+ player.getname()+" your PowCards are: ");

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
        printStream.println("Player "+attackedplayer.getname()+"you have being attacked. Do you want to use any Pow?");
        printStream.println("0. Yes");
        printStream.println("1. No");
        int choice=this.getData.getInt(-1, 1);
        if(choice!=-1){
            printStream.println("Player "+attackedplayer.getname()+"which Pow do you want to use?");
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
        printStream.println(match.getActivePlayer().getname()+", which WeaponCard do you want to buy?");
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
        printStream.println(match.getActivePlayer().getname()+", which PowCard do you want to use?");
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
        printStream.println(match.getActivePlayer().getname()+", which WeaponCard do you want to use?");
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
        printStream.println(match.getActivePlayer().getname()+", which direction do you want to move for a single step? Write:");
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
        printStream.println(match.getActivePlayer().getname()+", write the sequence of movements you want to do:");
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
            if(!player.equals(p)) printStream.println("Total marks: "+player.getnumberdamage(p.getcolor())+" by Player "+p.getname());
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

    public static void main(String[] args) {
        Game game;
        Match match;
        Player player1;
        Player player2;
        Player player3;
        Player player4;
        game = new Game();
        int index = game.getMatchesSize()-1;
        match = game.getMatchByIndex(index);
        player1 = new Player("Sirius", "Blue", "10583741");
        player2 = new Player("Calypso", "Pink", "14253954");
        player3 = new Player("Hermione", "Green", "18263100");
        player4 = new Player("Aries", "Yellow", "18992302");
        try {
            match.addPlayer(player1);
            match.addPlayer(player2);
            match.addPlayer(player3);
            match.addPlayer(player4);
        }
        catch (MaxNumberPlayerException e){}
        match.createDashboard(3);

        player1.setCel(0, 3); //Sirius
        player2.setCel(0, 1); //Calypso
        player3.setCel(2, 2); //Hermione
        player4.setCel(2, 2); //Aries

        WeaponDeck weaponDeck = new WeaponDeck();
        weaponDeck.setWeapons("Armi");
        weaponDeck.drawCard();
        Weapon weapon1 = (Weapon)weaponDeck.drawCard();
        Weapon weapon2 = (Weapon)weaponDeck.drawCard();
        Weapon weapon3 = (Weapon)weaponDeck.drawCard();
        Weapon weapon4 = (Weapon)weaponDeck.drawCard();
        Weapon weapon5 = (Weapon)weaponDeck.drawCard();
        Weapon weapon6 = (Weapon)weaponDeck.drawCard();
        Weapon weapon7 = (Weapon)weaponDeck.drawCard();
        Weapon weapon8 = (Weapon)weaponDeck.drawCard();
        Weapon weapon9 = (Weapon)weaponDeck.drawCard();
        Weapon weapon10 = (Weapon)weaponDeck.drawCard();
        Weapon weapon11 = (Weapon)weaponDeck.drawCard();

        SpawnPointCell spawncell = (SpawnPointCell)match.getDashboard().getmap(0, 2);
        try{
            spawncell.Add_Weapon_Card(weapon1, 0);
            spawncell.Add_Weapon_Card(weapon2, 1);
            spawncell.Add_Weapon_Card(weapon3, 2);
        } catch (FullCellException e){}
        spawncell = (SpawnPointCell)match.getDashboard().getmap(1, 0);
        try{
            spawncell.Add_Weapon_Card(weapon4, 0);
            spawncell.Add_Weapon_Card(weapon5, 1);
            spawncell.Add_Weapon_Card(weapon6, 2);
        } catch (FullCellException e){}
        spawncell = (SpawnPointCell)match.getDashboard().getmap(2, 3);
        try{
            spawncell.Add_Weapon_Card(weapon7, 0);
            spawncell.Add_Weapon_Card(weapon8, 1);
            spawncell.Add_Weapon_Card(weapon9, 2);
        } catch (FullCellException e){}
        try {
            player1.addWeapon(weapon10);
            player1.addWeapon(weapon11);}
        catch (MaxNumberofCardsException e){ System.out.println("You have too many Weapon Cards, please remove one."); }
        game.startGame(match.getId());

        player1.setCel(0, 2); //Sirius
        View view = new CLIView(match);
        view.printMap();
        view.showPlayerWeapons();
        int card = view.getWeaponCard();
        try{
            GrabWeapon grabweapon = new GrabWeapon();
            grabweapon.grabWeapon(match, match.getActivePlayer(), card);
        } catch(MaxNumberofCardsException e){
            printStream.println("You have too many Weapon cards. Please, remove one if you want to buy this card.");
        }
        view.showPlayerWeapons();
        view.showSpawnPointWeapons();

        player1.setdamage(5, 2);
        AmmoTile ammoTile = new AmmoTile(0,0,1);
        AmmoPowTile ammoPowTile = new AmmoPowTile(2,1);
        NormalCell cell1 = (NormalCell)player1.getCel().inmap(match.getDashboard(), 1,3);
        NormalCell cell2 = (NormalCell)player1.getCel().inmap(match.getDashboard(), 1,2);
        try{
            cell1.Add_Ammo_Card(ammoTile);
            cell2.Add_Ammo_Card(ammoPowTile);
        } catch (FullCellException e){}
    }
}
