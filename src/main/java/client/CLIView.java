package client;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import controller.Game;
import controller.GrabWeapon;
import exceptions.FullCellException;
import exceptions.MaxNumberPlayerException;
import exceptions.MaxNumberofCardsException;
import exceptions.WeaponAlreadyUsedException;
import model.*;
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

        String s = "        ";
        for(int i=0; i<3; i++){
            for(int j=0; j<4; j++){
                map[i][j]=s;
            }
        }
        int line;
        int column;
        for(Player player:match.getPlayers()){
            line = player.getCel().getX();
            column = player.getCel().getY();
            map[line][column] = player.getid();
            printStream.printf("Player "+player.getid()+" is "+player.getname()+"\n");
        }

        printStream.printf(" _________________________________________________                 \n");
        printStream.printf("|      Blue      |       Blue     |     Blue      |                \n");
        printStream.printf("|                |                |               |                \n");
        printStream.printf("|    "+map[0][0]+"    |    "+map[0][1]+"    |    "+map[0][2]+"   |    "+map[0][3]+"    \n");
        printStream.printf("|                |                |               |                \n");
        printStream.printf("|                |                |               |                \n");
        printStream.printf("|                |                |   SpawnPoint  |                \n");
        printStream.printf("|_______| |______|________________|______| |______|_______________ \n");
        printStream.printf("|      Red       |      Red       |      Red      |    Yellow     |\n");
        printStream.printf("|                |                |               |               |\n");
        printStream.printf("|    "+map[1][0]+"    |    "+map[1][1]+"    |    "+map[1][2]+"   _    "+map[1][3]+"   |\n");
        printStream.printf("|                |                |               _               |\n");
        printStream.printf("|                |                |               |               |\n");
        printStream.printf("|   SpawnPoint   |                |               |               |\n");
        printStream.printf("|________________|______| |_______|_______________|_______________|\n");
        printStream.printf("                 |      Grey      |      Grey     |    Yellow     |\n");
        printStream.printf("                 |                |               |               |\n");
        printStream.printf("     "+map[2][0]+"    |    "+map[2][1]+"    |    "+map[2][2]+"   _    "+map[2][3]+"   |\n");
        printStream.printf("                 |                |               _               |\n");
        printStream.printf("                 |                |               |               |\n");
        printStream.printf("                 |                |               |   SpawnPoint  |\n");
        printStream.printf("                 |________________|_______________|_______________|\n");
    }
    private void printmap2(){
        String[][] map = new String[3][4];

        String s = "        ";
        for(int i=0; i<3; i++){
            for(int j=0; j<4; j++){
                map[i][j]=s;
            }
        }
        int line;
        int column;
        for(Player player:match.getPlayers()){
            line = player.getCel().getX();
            column = player.getCel().getY();
            map[line][column] = player.getid();
            printStream.printf("Player "+player.getid()+" is "+player.getname()+"\n");
        }
        printStream.printf(" _________________________________________________________________ \n");
        printStream.printf("|      Blue      |       Blue     |     Blue      |   Green       |\n");
        printStream.printf("|                |                |               |               |\n");
        printStream.printf("|    "+map[0][0]+"    _    "+map[0][1]+"    |    "+map[0][2]+"   _    "+map[0][3]+"   |\n");
        printStream.printf("|                _                |               _               |\n");
        printStream.printf("|                |                |               |               |\n");
        printStream.printf("|                |                |   SpawnPoint  |               |\n");
        printStream.printf("|______| |_______|________________|______| |______|______| |______|\n");
        printStream.printf("|      Red       |      Red       |     Yellow    |    Yellow     |\n");
        printStream.printf("|                |                |               |               |\n");
        printStream.printf("|    "+map[1][0]+"    |    "+map[1][1]+"    |    "+map[1][2]+"   |    "+map[1][3]+"   |\n");
        printStream.printf("|                |                |               |               |\n");
        printStream.printf("|                |                |               |               |\n");
        printStream.printf("|   SpawnPoint   |                |               |               |\n");
        printStream.printf("|________________|______| |_______|_______________|_______________|\n");
        printStream.printf("                 |      Grey      |     Yellow    |    Yellow     |\n");
        printStream.printf("                 |                |               |               |\n");
        printStream.printf("     "+map[2][0]+"    |    "+map[2][1]+"    _    "+map[2][2]+"   |    "+map[2][3]+"   |\n");
        printStream.printf("                 |                _               |               |\n");
        printStream.printf("                 |                |               |               |\n");
        printStream.printf("                 |                |               |   SpawnPoint  |\n");
        printStream.printf("                 |________________|_______________|_______________|\n");
    }
    private void printmap3(){
        String[][] map = new String[3][4];

        String s = "        ";
        for(int i=0; i<3; i++){
            for(int j=0; j<4; j++){
                map[i][j]=s;
            }
        }
        int line;
        int column;
        for(Player player:match.getPlayers()){
            line = player.getCel().getX();
            column = player.getCel().getY();
            map[line][column] = player.getid();
            printStream.printf("Player "+player.getid()+" is "+player.getname()+"\n");
        }
        printStream.printf(" _________________________________________________________________ \n");
        printStream.printf("|      Red       |       Blue     |     Blue      |   Green       |\n");
        printStream.printf("|                |                |               |               |\n");
        printStream.printf("|    "+map[0][0]+"    _    "+map[0][1]+"    |    "+map[0][2]+"   _    "+map[0][3]+"   |\n");
        printStream.printf("|                _                |               _               |\n");
        printStream.printf("|                |                |               |               |\n");
        printStream.printf("|                |                |   SpawnPoint  |               |\n");
        printStream.printf("|________________|______| |_______|______| |______|______| |______|\n");
        printStream.printf("|      Red       |     Pink       |     Yellow    |    Yellow     |\n");
        printStream.printf("|                |                |               |               |\n");
        printStream.printf("|    "+map[1][0]+"    _    "+map[1][1]+"    |    "+map[1][2]+"   |    "+map[1][3]+"   |\n");
        printStream.printf("|                _                |               |               |\n");
        printStream.printf("|                |                |               |               |\n");
        printStream.printf("|   SpawnPoint   |                |               |               |\n");
        printStream.printf("|______| |_______|______| |_______|_______________|_______________|\n");
        printStream.printf("|      Grey      |      Grey      |     Yellow    |    Yellow     |\n");
        printStream.printf("|                |                |               |               |\n");
        printStream.printf("|    "+map[2][0]+"    |    "+map[2][1]+"    _    "+map[2][2]+"   |    "+map[2][3]+"   |\n");
        printStream.printf("|                |                _               |               |\n");
        printStream.printf("|                |                |               |               |\n");
        printStream.printf("|                |                |               |   SpawnPoint  |\n");
        printStream.printf("|________________|________________|_______________|_______________|\n");
    }


    //Method to show player its weapon cards
    @Override
    public void showPlayerWeapons() {
        Player player=match.getActivePlayer();
        printStream.println("Player "+ player.getname()+" your Weapon Cards are: ");
        List<Weapon> weaponcards = player.getWeapons();

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
        printStream.println("Player "+ player.getname()+" your PowCards are: ");
        List<PowCard> powcards = player.getPows();

        int i=1;
        for(PowCard powcard:powcards){
            printStream.println(i+". "+powcard.getName());
            i++;
        }
    }

    //Method to show a player its PowCards, used in response to an attack
    @Override
    public void showPlayerPows(Player player){
        printStream.println("Player "+ player.getname()+" your PowCards are: ");
        List<PowCard> powcards = player.getPows();

        int i=1;
        for(PowCard powcard:powcards){
            printStream.println(i+". "+powcard.getName());
            i++;
        }
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
            numberRedAmmos=0;
            numberBlueAmmos=0;
            numberYellowAmmos=0;
            printStream.println(i+". "+weaponcards.get(i).getName()+" \nPrice:");
            for(int j=0; j<weaponcards.get(i).getCost().size(); j++){
               if(weaponcards.get(i).getCost().get(j)==0) numberRedAmmos++;
            }
            printStream.println(numberRedAmmos+" red Ammos");
            nRedAmmos.add(numberRedAmmos);
            for(int j=0; j<weaponcards.get(i).getCost().size(); j++){
                if(weaponcards.get(i).getCost().get(j)==1) numberBlueAmmos++;
            }
            printStream.println(numberBlueAmmos+" blue Ammos");
            nBlueAmmos.add(numberBlueAmmos);
            for(int j=0; j<weaponcards.get(i).getCost().size(); j++){
                if(weaponcards.get(i).getCost().get(j)==2) numberYellowAmmos++;
            }
            printStream.println(numberYellowAmmos+" yellow Ammos\n");
            nYellowAmmos.add(numberYellowAmmos);
        }

        numberOfWeapon=this.getData.getInt(-1, 2);
        if(numberOfWeapon!=-1 && nRedAmmos.get(numberOfWeapon)<=match.getActivePlayer().getAmmo(0) &&
                nBlueAmmos.get(numberOfWeapon)<=match.getActivePlayer().getAmmo(1) &&
                nYellowAmmos.get(numberOfWeapon)<=match.getActivePlayer().getAmmo(2)){
            CardToBuy= numberOfWeapon;
            try{
                grabweapon.grabWeapon(match, match.getActivePlayer(), CardToBuy);
            } catch(MaxNumberofCardsException e){
                printStream.println("You have too many Weapon cards. Please, remove one if you want to buy this card.");
            }
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
        int numberOfWeapon=this.getData.getInt(-1, 2);
        if(numberOfWeapon!=-1){
            try{
                //TODO
                match.getActivePlayer().getWeaponByIndex(numberOfWeapon).shooted();
            } catch(WeaponAlreadyUsedException e){
                printStream.println("You have already use this weapon, without recharging.");
            }
        }else{
            printStream.println("You don't own this weapon!");
        }
        return numberOfWeapon;
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
            if(!player.equals(p)) printStream.println("Total marks: "+player.getnumberdamage(p.getcolor())+"by Player "+p.getname());
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
        player4.setCel(2, 0); //Aries

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

        SpawnPointCell c = (SpawnPointCell)match.getDashboard().getmap(0, 2);
        try{
            c.Add_Weapon_Card(weapon1, 0);
            c.Add_Weapon_Card(weapon2, 1);
            c.Add_Weapon_Card(weapon3, 2);
        } catch (FullCellException e){}
        c = (SpawnPointCell)match.getDashboard().getmap(1, 0);
        try{
            c.Add_Weapon_Card(weapon4, 0);
            c.Add_Weapon_Card(weapon5, 1);
            c.Add_Weapon_Card(weapon6, 2);
        } catch (FullCellException e){}
        c = (SpawnPointCell)match.getDashboard().getmap(2, 3);
        try{
            c.Add_Weapon_Card(weapon7, 0);
            c.Add_Weapon_Card(weapon8, 1);
            c.Add_Weapon_Card(weapon9, 2);
        } catch (FullCellException e){}
        try {
            player1.addWeapon(weapon10);
            player1.addWeapon(weapon11);}
        catch (MaxNumberofCardsException e){ System.out.println("You have too many Weapon Cards, please remove one."); }
        game.startGame(match.getId());

        player1.setCel(0, 2); //Sirius
        View view = new CLIView(match);
        view.printMap();
        view.getWeaponCard();
        view.showPlayerWeapons();
        view.showSpawnPointWeapons();
    }
}