package controller;
import client.View;
import exceptions.*;
import model.*;
import network.messages.*;
import network.server.GameRoom;

import java.util.List;
import java.util.Map;

import static utils.NotifyClient.*;

public class Game{

    private Match match;
    private ManagingWeapons manageWeapon;
    private GameRoom gameRoom;      //CAPIRE SE USARE QUESTO O PREFERIRE LAVORARE DIRETTAMENTE CON NotifyClient
    public Game(GameRoom gameRoom){
        this.gameRoom=gameRoom;
        this.match = new Match();
        manageWeapon=new ManagingWeapons(match);
        registerNewMatch(gameRoom,match);
    }

    //TODO: Coda giocatori in partita



    //TODO IMPORTANTE metodi isValid

    //------------------------Metodi per il SetUp della partita-----------------------------------------------------------//
    //Vado a creare i singoli Player e quindi ad aggiungerli al Model (li istanzio singolarmente)
    public void addPlayers(Map<String,String> userList, Map<String,String> userIDtoColor) {

        userList.keySet().forEach(username -> match.createPlayer(username, userIDtoColor.get(userList.get(username)), userList.get(username)));
        //una volta creati i Player informo tutti i Player (eccetto lo stesso) dei dati degli altri
        match.notifyPlayers();
        askMap((String)userList.values().toArray()[0]);
    }

    private void askMap(String userID){
        gameRoom.askToChooseMap(userID);
    }

    public void setMap(String mapRequired) {
        System.out.println("Selected Map: " + mapRequired);
        match.createDashboard(Integer.valueOf(mapRequired));
        //HO TUTTO IL NECESSARIO PER INIZIARE LA PARTITA E ISTANZIARE EFFETTIVAMENTE TUTTO NELLA MATCH
        setGameReady();
    }

    //-------------------------Gestisco il primo turno-----------------------------------------------------//
    //Questo metodo si pone l'obiettivo di iniziare la partita, poi chiamera il metodo per il primo turno (forse)
    //Nel Model sono già stati istanziati i mazzi e i Player, questo metodo gestisce la prima fase di gioco in cui
    //si distribuiscono carte ecc.
    private void setGameReady(){
        match.shuffleAllDecks();            //shuffle all the decks
        match.fillDashboard();              //this method assign 3 Weapons for each SpawnPoint Cell and an AmmoCard for each NormalCell
        setPlayerReady();
    }

    private void setPlayerReady(){

        //Assign to each player two pows card.
        match.firstTurnPow();

        //first Player is the first in Players List in Model. It would be the first logged on Server.
        //Set active the first player, set not-active the rest of players
        match.getPlayerByIndex(0).setActive();
        for(int i=1; i<match.getPlayersSize(); i++) match.getPlayerByIndex(i).resetActive();
        //Da adesso si chiamano tutti i metodi per la gestione della partita del primo giocatore, poi aggiorno e passo al secondo
        //richiamando gli stessi
        //Poi bisogna sviluppare dei cicli sensati di turni
        askSpawnPoint();

    }

    private void askSpawnPoint() {
        gameRoom.askToChooseSpawnPoint(match.getActivePlayer().getID());
    }
    //Metodo che viene chiamato quando si riceve la risposta dal Client sul Pow da Usare
    //TODO: sistemare la validità della cella
    public void setSpawn(String userID, Coordinate coordinate,int powCardIndex){
        checkUserAction(userID);
        Spawn playerSpawn = new Spawn();
        //save powCard before Spawn (it's removed from Player) to discard it after Spawn
        PowCard powCardToDiscard=match.getActivePlayer().getPowByIndex(powCardIndex);       //TODO: migliorabile?
        playerSpawn.spawn(match.getActivePlayer(), coordinate.getX(), coordinate.getY(), powCardIndex);
        match.discardPowCard(powCardToDiscard);
        //TODO: devo avvisare il Player dal discard
        //Se si è a inizio partita una volta generato il player effettivamente ha inizio il suo normale turno di gioco
        //Se invece il Player ha spawnato dopo il turno di un altro player si procede con il giocatore successivo a quello
        //che ha terminato il turno
        askAction();

    }
    //-----------------------------------Metodi veri e propri del turno-----------------------------------------------//

    //TODO: verificare utilità metodo
    private void checkUserAction(String userID){
        if(!match.getActivePlayer().getID().equals(userID)){
            Message message=new ActionError("Not your turn");
            gameRoom.sendErrorMessage(userID,message);
            askAction();        //TODO: verificare che metodo chiamare
        }
    }


    private void askAction(){
        gameRoom.askToChooseNextAction(match.getActivePlayer().getID());
    }
    /*
    Le possibili azioni sono:
    0. Run
    1. Grab
    2. Shoot
    3. Grab with movement
    4. Shoot with movement
    5. Recharge*/

    public void performAction(String userID, int chosenAction) {
        checkUserAction(userID);
        switch(chosenAction) {
            case (0):
                this.askRun();
                break;
            case (1):
                this.selectGrab();
                break;
            case (2):
                //Richiesta al giocatore con arma con cui vuole attaccare
                //Da cui poi si chiamerà la shoot

                //Solita struttura askWeaponToAttack->performAttack (Shoot)
                /*
                int attackingweapon = view.getWeaponCardtoAttack();
                //TODO chiede al giocatore con che arma (restituisce l'indice) vuole attaccare
                attackingweapon--;
                this.shoot();
                //TODO LE ARMIIIII
                break;*/
            case (3):
                /*Grabbing with movement*/
                /*
                List<String> destination2 = view.getListDirection();
                GrabAmmo grabAmmo = new GrabAmmo();
                if (!grabAmmo.movementBeforeGrab(match, match.getActivePlayer(), destination2)) {
                    printStream.println("You are not allowed to move this way.");
                    return;
                }
                this.grab(view, counter);
                */
                break;
            case (4):
                /*
                List<String> destination3 = view.getListDirection();
                Shoot shoot = new Shoot();
                if (!shoot.movementBeforeShoot(match, match.getActivePlayer(), destination3)) {
                    printStream.println("You are not allowed to move this way.");
                    return;
                }
                //controllo di validità movimento già fatto, mancano solo gli effetti arma
                this.shoot();
                //TODO LE ARMIIIII
                */
                break;
            case (5):
                this.recharge();
                break;
        }
        //TODO aziona di ricarica sarà fatta dopo l'uso dell'arma e alla fine del turno giocatore
        //TODO: in base alla chosenAction si chiama una funzione del Controller che va a gestire quell'azione, con eventuali ulteriori chiamate al client
        //Da aumentare di una l'azione già sfruttata
    }



    private void askRun(){
        gameRoom.askDestinationRun(match.getActivePlayer().getID());
    }

    public void performRun(String userID,List<String> destination){
        /*Control if running is valid, in case counter is decremented to neutralize the counter++ after break, as
         * the player can take an other action*/
        //Sequenza ricevuta dall'utente via Evento di rete
        checkUserAction(userID);
        Run run = new Run();
        try{
            run.getMovement(match, match.getActivePlayer(), destination);
            run.registerMovementAction(match);
            //In caso di successo dell'azione aumento di 1 la variabile azione del Player
            //match.getActivePlayer().setAction(); già fatto nel metodo
            //Chiamo metodo per la gestione della successiva azione
            nextStep();
        } catch(InvalidDirectionException e){           //TODO: migliorare eccezione con motivo dell'errore
            Message errorMessage=new RunError("Invalid Direction. Choose an other direction");
            gameRoom.sendErrorMessage(match.getActivePlayer().getID(),errorMessage);
        }
    }

    private void selectGrab(){
        int x = match.getActivePlayer().getCel().getX();
        int y = match.getActivePlayer().getCel().getY();
        if(match.getActivePlayer().getCel().inmap(match.getDashboard(), x, y).getType()==0){
            //this is a SpawnPoint cell
            /*In a SpawnPoint cell the player choose which weapon to buy, if it has too many weapons he is asked
             * if he wants to discardWeapon one of them, and in positive case he chooses which one to discardWeapon, then the selected one
             * is added to his weapon cards*/
            this.checkWeaponGrab();
        }
        else {
            //this is not a SpawnPoint cell
            this.grabAmmoTile();
        }
    }
    private void checkWeaponGrab(){
        GrabWeapon grabWeapon = new GrabWeapon();
        try {
            grabWeapon.isValid(match.getActivePlayer());
            askWeaponGrab();
        }catch (MaxNumberofCardsException e) {
            askToDiscardWeaponCard();
        }
    }

    private void askToDiscardWeaponCard() {
        Message errorMessage=new MaxWeaponCardError("You have already three Weapon Card, you can discard one");
        gameRoom.sendErrorMessage(match.getActivePlayer().getID(),errorMessage);
    }

    public void discardWeaponCardToGrab(String userID, int indexWeapon) {
        checkUserAction(userID);
        manageWeapon.discardWeapon(match.getActivePlayer(),indexWeapon);
        askWeaponGrab();
    }

    private void askWeaponGrab(){
        gameRoom.askWeaponGrab(match.getActivePlayer().getID());
    }
    public void performWeaponGrab(String userID,int indexWeapon){
        checkUserAction(userID);
        GrabWeapon grabWeapon = new GrabWeapon();
        Weapon weaponToGrab=getWeaponToGrab(indexWeapon);
        if(!manageWeapon.areEnoughAmmoToGrabWeapon(match.getActivePlayer(),weaponToGrab)){
            // case if you don't have enough ammos and you want to convert a PowCard
            askWeaponGrabWithPowCard();
        }
        else {
            try {
                grabWeapon.grabWeapon(match, match.getActivePlayer(), indexWeapon);
                nextStep();
            } catch (MaxNumberofCardsException e) {
                //Ho già verificato prima di iniziare la GrabWeapon che questo ramo del try/catch non si attiverà mai.
                //TODO:Valutare se rimuovere o cosa fare
            }
        }
    }


    private void askWeaponGrabWithPowCard(){
        gameRoom.askWeaponGrabWithPowCard(match.getActivePlayer().getID());
    }
    public void performWeaponGrabWithPowCard(String userID,int indexWeapon,int indexPowCard) {
        checkUserAction(userID);
        Weapon weaponToGrab=getWeaponToGrab(indexWeapon);
        try {
            manageWeapon.convertPowToGrab(match.getActivePlayer(), weaponToGrab, indexPowCard);
            GrabWeapon grabWeapon=new GrabWeapon();
            grabWeapon.grabWeapon(match, match.getActivePlayer(), indexWeapon);     //Completo raccolta
            nextStep();
        } catch(NotEnoughAmmosException e){             //Se lanciata eccezione mando messaggio d'errore e chiudo Grab arma
            Message errorMessage=new GrabError("You don't have enough ammos, and you can't even convert a PowCard to buy\n");
            gameRoom.sendErrorMessage(match.getActivePlayer().getID(),errorMessage);
        } catch (MaxNumberofCardsException e) {
            //Ho già verificato prima di iniziare la GrabWeapon che questo ramo del try/catch non si attiverà mai.
            //TODO:Valutare se rimuovere o cosa fare
        }
    }

    private Weapon getWeaponToGrab(int indexWeapon){
        int xCoordinate=match.getActivePlayer().getCel().getX();
        int yCoordinate=match.getActivePlayer().getCel().getY();
        SpawnPointCell cell = (SpawnPointCell) match.getActivePlayer().getCel().inmap(match.getDashboard(),xCoordinate,yCoordinate);
        return cell.getSpawnPointCellWeapons().get(indexWeapon);
    }

    private void grabAmmoTile(){
        GrabAmmo grabAmmo = new GrabAmmo();
        try{
            grabAmmo.grabAmmo(match, match.getActivePlayer());
            nextStep();
        } catch(MaxNumberofCardsException exc){
            askToDiscardPowCard();
        } catch (CardAlreadyCollectedException e) {
            //TODO GESTIONE ERRORE
        }
    }

    private void askToDiscardPowCard(){
        Message errorMessage=new MaxPowCardError("You have already three PowCard, you can discard one");
        gameRoom.sendErrorMessage(match.getActivePlayer().getID(),errorMessage);
    }

    public void discardPowCard(String userID, int indexPowCard) {
        checkUserAction(userID);
        manageWeapon.discardPowCard(match.getActivePlayer(),indexPowCard);
        try {
            match.assignPowCard(match.getActivePlayer());
        } catch (MaxNumberofCardsException e) {
            //Impossibile dato che ho appena scartato una carta
        }
        match.getActivePlayer().setAction();
        nextStep();
    }

    private void shoot(){

    }

    private void recharge(){
        /*Recharge a weapon checking if the weapon is already loaded*/
        System.out.println("Which weapon do you want to recharge?");
        int weaponToRecharge=0;
        try{
            manageWeapon.Recharge(match.getActivePlayer(), weaponToRecharge);
        } catch(WeaponAlreadyLoadedException e){
            System.out.println("You have already loaded this weapon");
        }
    }


    //TODO bisogna sostituire le print con dei messaggi per la comunicazione tra client e server
    public void play(Match match, View view){
        int counter=0;
        int choice;
        while(counter<2){

            }

        view.printMap();
        System.out.println(match.getActivePlayer().getName()+" you have ended your turn.");

        //view.printPlayerData();
    }

    //----------------------------Metodi utili per set turno----------------------------------------------------------//

    //TODO: a fine turno gestire carte sulla dashboard ecc.-> non posso farlo a fine della singola azione perchè rischierei di pescare più di una volta lo stesso
    private void nextStep() {
        //IF qualcuno è morto, chiamare la spawn per lui, poi continuare normalmente (da Gestire!)
        if(match.getActivePlayer().getAction()<2) {
            askAction();
        }
        else if(match.getActivePlayer().getAction()==2){
            //TODO: messaggio fine turno, sulla View fare in modo che si stampino le info aggiornate del Player
            //TODO: modificare coda Players e giocatore attivo
        }
    }


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
        String color = getData.getValidColorForPlayer();

        printStream.println("\nPlease, enter your name to play in the game:");
        String name= getData.getName();

        Player player = new Player(name, color, "12345678");

        printStream.println("\nPlease, choose the color of the player:");
        printStream.println("Blue - Green - Yellow - Pink - Grey");
        color = getData.getValidColorForPlayer();
        printStream.println("\nPlease, enter your name to play in the game:");
        name= getData.getName();
        Player player1 = new Player(name, color, "10583741");

        printStream.println("\nPlease, choose the color of the player:");
        printStream.println("Blue - Green - Yellow - Pink - Grey");
        color = getData.getValidColorForPlayer();
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

        printStream.println("\nPlease, "+match.getPlayerByIndex(0).getName()+" choose the type of dashboard:1, 2, 3");
        int maptype = getData.getInt(1, 3);
        game.select(maptype);

        game.startGame();
        View view = new CLIView(match);
        for(Player p:match.getPlayers()){
            printStream.println(p.getName());
            view.showPlayerPowWithColors(p);
        }
        for(Player p:match.getPlayers()){
            printStream.println("\nPlease, "+p.getName()+" select the SpawnPoint cell where you want to start. Write number of line, then column.");
            printStream.println("There are three SpawnPoint cells in the game:");
            printStream.println("Line 0, column 2 - Blue cell");
            printStream.println("Line 1, column 0 - Red cell");
            printStream.println("Line 2, column 3 - Yellow cell");
            printStream.println("You have these PowCards, choose with the color of one of them the spawn point cell:");
            view.showPlayerPowWithColors(p);
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
        player.setDamage(6, 0);
        //TODO serve al connectionHandler a reference alla partita che sta giocando
        for(int i=0; i<match.getPlayersSize(); i++){
            game.play(match, view);
            game.setTurn();
        }
    }
    */

}
