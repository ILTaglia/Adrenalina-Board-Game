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

    public CLIView(){
        LOGGER.setLevel(Level.INFO);
    }

    @Override
    public void welcomeMessage(int idClient) { printStream.println("START."); }

    @Override
    public void endMessage() { printStream.println("GAME OVER."); }

    @Override
    public void printMap() {
        Dashboard d = match.getDashboard();
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
