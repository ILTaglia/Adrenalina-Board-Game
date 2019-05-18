package client;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import exceptions.MaxNumberofCardsException;
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
            printStream.printf("Player"+player.getid()+" is "+player.getname()+"\n");
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
            printStream.printf("Player"+player.getid()+" is "+player.getname()+"\n");
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
            printStream.printf("Player"+player.getid()+" is "+player.getname()+"\n");
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

    //Method to show player its PowCards
    @Override
    public void showPlayerPows() {
        Player player=match.getActivePlayer();
        printStream.println("Player "+ player.getname()+" your PowCards are: ");
        List<PowCard> powcards = player.getPows();

        int i=1;
        for(PowCard powcard:powcards){
            System.out.println(i+". "+powcard.getName());
            i++;
        }
    }

    //Method to show Weapon Cards in SpawnPoint Cell
    public void showSpawnPointWeapons(){
        Player player=match.getActivePlayer();
        int x = player.getCel().getX();
        int y = player.getCel().getY();
        SpawnPointCell cell = (SpawnPointCell)match.getDashboard().getmap(x, y);
        printStream.println("In the SpawnPoint Cell at line "+x+" and column "+y+" there are these Weapon Cards: ");
        List<Weapon> weapons = cell.getSpawnPointCellWeapons();

        int i=1;
        for(Weapon weapon:weapons){
            System.out.println(i+". "+weapon.getName());
            i++;
        }
    }


    //Method to ask the player which cards he wants to buy if in a SpawnPoint Cell
    @Override
    public int getWeaponCard(){
        printStream.println(match.getActivePlayer().getname()+", which WeaponCard do you want to buy:");
        int n=0;
        List<Weapon> weaponcards=new ArrayList<Weapon>();
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
        printStream.println("-1 not to buy.");
        for(int i=0;i<weaponcards.size();i++){
            printStream.println(i+". "+weaponcards.get(i).getName()+"Price:");
            printStream.println(weaponcards.get(i).getCost().get(0)+"red Ammos");
            printStream.println(weaponcards.get(i).getCost().get(1)+"blue Ammos");
            printStream.println(weaponcards.get(i).getCost().get(2)+"yellow Ammos");
        }

        n=this.getData.getInt(-1, 2);
        if(n!=-1 && weaponcards.get(n).getCost().get(0)<=match.getActivePlayer().getAmmo(0) &&
                weaponcards.get(n).getCost().get(1)<=match.getActivePlayer().getAmmo(1) &&
                weaponcards.get(n).getCost().get(2)<=match.getActivePlayer().getAmmo(2)){
            CardToBuy= n;
            try{
                cell.Collect_Weapon(match.getActivePlayer(), CardToBuy);
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
        return 0;
    }

    @Override
    public int getWeaponCardtoAttack(){
        return 0;
    }

    @Override
    public void printPlayerMove(){}

    @Override
    public void printPlayerData(){}

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
