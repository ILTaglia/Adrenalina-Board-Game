package controller;
import client.CLIView;
import client.View;
import exceptions.*;
import model.Match;
import model.Player;
import model.PowCard;
import network.server.GameRoom;
import network.server.GameServer;
import utils.GetData;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static utils.NotifyClient.registerNewMatch;

//TODO: Capire se la view va istanziata nel controller oppure se va passata negli eventi che lancia al controller

public class Game{

    private Match match;
    private GameRoom gameRoom;      //TODO: Capire dove mettere questa variabile??
    public Game(GameRoom gameRoom){
        this.gameRoom=gameRoom;
        this.match = new Match();
        //TODO: controllare il senso metodo
        registerNewMatch(gameRoom,match);
    }

    //------------------------Metodi da completare-----------------------------------------------------------//
    public void addPlayers(Map<String,String> userList, Map<String,String> userIDtoColor) {
        for(String username:userList.keySet()){
            match.createPlayer(username,userIDtoColor.get(userList.get(username)),userList.get(username));
        }
        //TODO: metodo nella match utile a inizio partita per la creazione delle istanze dei singoli player.
        //match.addPlayer();
        //TODO:Inserisco qua la chiamata al metodo successivo per il SetUp della partita, non mi convince come soluzione
        //Chiamata temporanaea per testing
        System.out.println("ciaoOKAY");
        gameRoom.askToChooseMap(userList.values().stream().findFirst().get());
    }

    public void setMap(String mapRequired) {
        //TODO: aggiungere controllo validità mappa!!
        // se viene lanciata un'eccezione, si chiama l'errore sulla view e si richiede una nuova mappa
        System.out.println("Ok, mappa scelta:"+mapRequired);
        match.createDashboard(Integer.valueOf(mapRequired));
        //Anche in questo caso bisognerà notificare dal Model l'avvenuta modifica
        //startGame(); //TODO: Posso inviare da qui notifica per start Game?
    }



    //-----------------------------------------------------------------------------------------------------//
    public void startGame(){
        match.fillDashboard();
        match.getPlayerByIndex(0).setActive();
        for(int i=1; i<match.getPlayersSize(); i++){
            match.getPlayerByIndex(i).resetActive();
        }
        match.firstTurnPows(); //assign two powcards to each players to start
    }

    public void select(int i){
        match.createDashboard(i);
    }


    public void firstTurn(Player player, int powcardIndex, int x, int y) throws InvalidColorException {
        Spawn playerSpawn = new Spawn();
        playerSpawn.spawn(player, x, y, powcardIndex);
    }

    //TODO da fare già prima della chiamata il controllo sulla validità dell'azione
    public void setTurn(){
        Player p = match.getActivePlayer();
        int index=0;
        for(int i=0; i<match.getPlayersSize(); i++){
            if(match.getPlayers().get(i).equals(p)){
                index = i;
                if(i==match.getPlayersSize()-1){
                    this.resetTurn(match);
                    return;
                }
            }
        }
        match.getPlayerByIndex(index).setActive();
        index++;
        match.getPlayerByIndex(index).setActive();
    }

    private void resetTurn(Match match){
        for(Player p:match.getPlayers()) p.resetAction();
        match.getPlayerByIndex(0).setActive();
    }

    //metodo per turno di gioco del player in mode CLI
    //TODO bisogna sostituire le print con dei messaggi per la comunicazione tra client e server
    public void play(Match match, View view){
        PrintStream printStream=System.out;
        int counter=0;
        GetData getData=new GetData();
        ManagingWeapons manage = new ManagingWeapons(); //class for the managing of weapons and pows in particular cases
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
                        if(!manage.EnoughMoneytoBuy(match.getActivePlayer(), weapontograb)){
                            //case if you don't have enough ammos and you want to convert a PowCard
                            printStream.println("You don't have enough ammos, if you want to convert a PowCard digit 1, 0 otherwise");
                            int convert = getData.getInt(0, 1);
                            switch(convert){
                                case(0):
                                    //nothing to be done
                                    break;
                                case(1):
                                    printStream.println("Which Pow do you want to use?");
                                    view.showPlayerPowsColors(match.getActivePlayer());
                                    int convertpow = getData.getInt(1, 3);
                                    convertpow--;
                                    try {
                                        manage.ConvertPowToBuy(match, match.getActivePlayer(), weapontograb, convertpow);
                                    } catch(NotEnoughAmmosException e){
                                        printStream.println("You don't have enough ammos, and you can't even convert a PowCard to buy\n");
                                    }
                                    view.showPlayerWeapons();
                            }
                        }
                        try{
                            grabWeapon.grabWeapon(match, match.getActivePlayer(), weapontograb);
                            view.showPlayerWeapons();
                            counter++;
                            break;
                        } catch(MaxNumberofCardsException e){
                            printStream.println("You have to many weapons, if you want to remove one digit 1, 0 otherwise");
                            int removing = getData.getInt(0, 1);
                            //TODO powcard buy
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
                        if(manage.EnoughMoneytoBuy(match.getActivePlayer(), weapontograb)){
                            //case if you don't have enough ammos and you want to convert a PowCard
                            printStream.println("You don't have enough ammos, if you want to convert a PowCard digit 1, 0 otherwise");
                            int convert = getData.getInt(0, 1);
                            switch(convert){
                                case(0):
                                    //nothing to be done
                                    break;
                                case(1):
                                    printStream.println("Which Pow do you want to use?");
                                    view.showPlayerPowsColors(match.getActivePlayer());
                                    int convertpow = getData.getInt(1, 3);
                                    convertpow--;
                                    try {
                                        manage.ConvertPowToBuy(match, match.getActivePlayer(), weapontograb, convertpow);
                                    } catch(NotEnoughAmmosException e){
                                        printStream.println("You don't have enough ammos, and you can't even convert a PowCard to buy\n");
                                    }
                                    view.showPlayerWeapons();
                            }
                        }
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
                                    manage.Remove(match.getActivePlayer(), removedweapon);
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
                                    manage.RemovePow(match.getActivePlayer(), removedpow);

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



    //TODO: Non deve esserci main nel controller
    /*
    //TODO anche qui bisogna scrivere i messaggi. Questo è il main del controller
    public static void main(String[] args){
        PrintStream printStream=System.out;
        GetData getData=new GetData();

        printStream.println("Welcome to Adrenalina!");

        printStream.println("\nPlease, choose which communication protocol you want to use:");
        printStream.println("1. Socket");
        printStream.println("2. RMI");

        int CommunicationChoice=getData.getInt(1, 2);

        printStream.println("\nPlease, choose which graphics you want to play with:");
        printStream.println("1. CLI");
        printStream.println("2. GUIView");
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
        Match match = game.match;
        try {
            match.addPlayer(player);
            match.addPlayer(player1);
            match.addPlayer(player2);
        }
        catch (MaxNumberPlayerException e){ printStream.println("Maximum number of players reached.");}

        printStream.println("\nPlease, "+match.getPlayerByIndex(0).getname()+" choose the type of dashboard:1, 2, 3");
        int maptype = getData.getInt(1, 3);
        game.select(maptype);

        game.startGame();
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
        player.setdamage(6, 0);
        //TODO serve al connectionHandler a reference alla partita che sta giocando
        for(int i=0; i<match.getPlayersSize(); i++){
            game.play(match, view);
            game.setTurn();
        }
    }
    */

}
