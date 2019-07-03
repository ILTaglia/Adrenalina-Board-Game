package controller;
import exceptions.*;
import model.*;
import network.messages.*;
import network.messages.error.*;
import network.server.GameRoom;

import java.util.*;

import static utils.NotifyClient.*;
import static utils.Print.printOut;

public class Game{

    private Match match;
    private ManagingWeapons manageWeapon;
    private GameRoom gameRoom;
    private boolean isMovementBeforeGrab;
    private boolean isMovementBeforeShoot;
    private OfficialShootVersion shootElaborator;
    private int counterTurn;                        //Conto numero di turni con meno di tre giocatori in partita
    //Gestione Timer
    private Timer timer;
    private final int queueTimer;
    private SupportPow supportPow;

    public Game(GameRoom gameRoom, int queueTimer){
        this.queueTimer=queueTimer;
        this.gameRoom=gameRoom;
        this.match = new Match();
        this.counterTurn=0;
        manageWeapon=new ManagingWeapons(match);
        registerNewMatch(gameRoom,match);
    }

    //TODO IMPORTANTE metodi isValid

    //------------------------Metodi per il SetUp della partita-----------------------------------------------------------//
    //Vado a creare i singoli Player e quindi ad aggiungerli al Model (li istanzio singolarmente)
    public void addPlayers(Map<String,String> userList, Map<String,String> userIDtoColor) {

        userList.keySet().forEach(username -> match.createPlayer(username, userIDtoColor.get(userList.get(username)), userList.get(username)));
        match.getPlayers().forEach(player-> player.setConnected(true));
        //una volta creati i Player informo tutti i Player (eccetto lo stesso) dei dati degli altri
        match.notifyOfOtherPlayers();
        askMap(match.getPlayerByIndex(0).getID());

        //askMap((String)userList.values().toArray()[0]);
    }

    private void askMap(String userID){
        gameRoom.askToChooseMap(userID);
    }

    public void setMap(int mapRequired) {
        printOut("Selected Map: " + mapRequired);
        match.createDashboard(mapRequired);
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
        match.notifyNewMap();
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
        askSpawnPoint(match.getActivePlayer().getID());

    }

    private void askSpawnPoint(String userID) {
        gameRoom.askToChooseSpawnPoint(userID);
    }
    //Metodo che viene chiamato quando si riceve la risposta dal Client sul Pow da Usare

    //TODO: sistemare la validità della cella inserita dall'utente
    public void setSpawn(String userID, Coordinate coordinate,int powCardIndex){
        Spawn playerSpawn = new Spawn();
        try {
            playerSpawn.spawn(match, match.getActivePlayer(), coordinate.getX(), coordinate.getY(), powCardIndex);
        }catch (InvalidColorException e){
            Message errorMessage=new ActionError("Colore non valido, riprova (Migliorare messaggio)");
            gameRoom.sendErrorMessage(userID,errorMessage);
            askSpawnPoint(userID);
        }
        //Se si è a inizio partita una volta generato il player effettivamente ha inizio il suo normale turno di gioco
        //Se invece il Player ha spawnato dopo il turno di un altro player si procede con il giocatore successivo a quello
        //che ha terminato il turno
        if(match.getRound()==1&&match.getActivePlayer().getID().equals(userID)) {
            nextStep();
        }
    }
    //-----------------------------------Metodi veri e propri del turno-----------------------------------------------//

    //TODO: verificare utilità metodo
    private void checkUserAction(String userID){
        if(!match.getActivePlayer().getID().equals(userID)){
            Message message=new ActionError("Not your turn");
            gameRoom.sendErrorMessage(userID,message);
        }
    }


    private void askAction(){
        //timer= new Timer();
        //printOut("Waiting " + (queueTimer/1000) + " seconds for an Answer, then disconnect Player");
        //startTimer();
        gameRoom.askToChooseNextAction(match.getActivePlayer().getID());
    }
    private void startTimer() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //TODO: InfoMessage al Player in cui lo si informa che salterà il turno e cade connessione
                disconnectPlayer(match.getActivePlayer().getID());
                nextStep();
            }
        }, queueTimer);
    }
    /*
    Le possibili azioni sono:
    0. Run
    1. Grab in this Cell
    2. Shoot
    3. Run & Grab
    4. Run & Shoot
    5. recharge
    */
    public void performAction(String userID, int chosenAction) {
        //per sicurezza li rimetto a false (inizializzo)
        resetActionBool();
        checkUserAction(userID);
        switch(chosenAction) {
            case (0):
                this.askRun();
                break;
            case (1):
                this.selectGrab(userID);
                break;
            case (2):
                this.askWeaponToShoot();
                //Richiesta al giocatore con arma con cui vuole attaccare
                break;
            case (3):
                this.askRun();
                isMovementBeforeGrab=true;
                break;
            case (4):
                this.askRun();
                isMovementBeforeShoot=true;
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
        Run run = new Run();
        try{
            run.movement(match,userID,destination,isMovementBeforeGrab,isMovementBeforeShoot);   //Controllo all'interno del metodo se la movement sia valida o meno, se non lo è lancio eccezione
            if(isMovementBeforeGrab){
                selectGrab(userID);
            }
            if(isMovementBeforeShoot){
               this.askWeaponToShoot();
            }
            run.registerMovementAction(match);
            //In caso di successo dell'azione aumento di 1 la variabile azione del Player
            //match.getActivePlayer().setAction(); già fatto nel metodo
            //Chiamo metodo per la gestione della successiva azione
            nextStep();
        } catch(InvalidDirectionException e){           //migliorare eccezione con motivo dell'errore
            Message errorMessage=new RunError("Invalid Direction. Choose an other direction");
            gameRoom.sendErrorMessage(match.getActivePlayer().getID(),errorMessage);
        } catch (NotYourTurnException e) {
            Message message=new ActionError("Not your turn");
            gameRoom.sendErrorMessage(userID,message);
        } catch (ActionNotAllowedException e) {
            Message message=new ActionError(e.getMessage());
            gameRoom.sendErrorMessage(userID,message);
            askRun();           //TODO: Modificare a livello CLI View
        }
    }

    private void selectGrab(String userID){
        int x = match.getActivePlayer().getCel().getX();
        int y = match.getActivePlayer().getCel().getY();
        if(match.getActivePlayer().getCel().inMap(match.getDashboard(), x, y).getType()==0){
            //this is a SpawnPoint cell
            /*In a SpawnPoint cell the player choose which weapon to buy, if it has too many weapons he is asked
             * if he wants to discardWeapon one of them, and in positive case he chooses which one to discardWeapon, then the selected one
             * is added to his weapon cards*/
            this.askWeaponGrab();
        }
        else {
            //this is not a SpawnPoint cell
            this.grabAmmoTile(userID);
        }
    }

    private void askWeaponGrab(){
        gameRoom.askWeaponGrab(match.getActivePlayer().getID());
    }

    public void performWeaponGrab(String userID,int indexWeapon){
        checkUserAction(userID);
        GrabWeapon grabWeapon = new GrabWeapon();
        if(!grabWeapon.isValid(match,userID)){
            askToDiscardWeaponCard();
        }
        if(!manageWeapon.areEnoughAmmoToGrabWeapon(match.getActivePlayer(),getWeaponToGrabCost(indexWeapon))){
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

    private void askToDiscardWeaponCard() {
        Message errorMessage=new MaxWeaponCardError("You have already three Weapon Card, you can discard one to Grab a new one");
        gameRoom.sendErrorMessage(match.getActivePlayer().getID(),errorMessage);
    }

    public void discardWeaponCardToGrab(String userID, int indexWeaponToGrab,int indexWeaponToDiscard) {
        manageWeapon.discardWeapon(match.getActivePlayer(),indexWeaponToDiscard);
        performWeaponGrab(userID,indexWeaponToGrab);
    }

    private void askWeaponGrabWithPowCard(){
        gameRoom.askWeaponGrabWithPowCard(match.getActivePlayer().getID());
    }

    public void performWeaponGrabWithPowCard(String userID,int indexWeapon,int indexPowCard) {
        checkUserAction(userID);
        try {
            manageWeapon.convertPowToGrab(match.getActivePlayer(), getWeaponToGrabCost(indexWeapon), indexPowCard);
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

    private List<Integer> getWeaponToGrabCost(int indexWeapon){
        int xCoordinate=match.getActivePlayer().getCel().getX();
        int yCoordinate=match.getActivePlayer().getCel().getY();
        SpawnPointCell cell = (SpawnPointCell) match.getActivePlayer().getCel().inMap(match.getDashboard(),xCoordinate,yCoordinate);
        return cell.getSpawnPointCellWeapons().get(indexWeapon).getCostToRecharge();
    }

    private void grabAmmoTile(String userID){
        GrabAmmo grabAmmo = new GrabAmmo();
        try{
            grabAmmo.grabAmmo(match, userID);
            nextStep();
        } catch(MaxNumberofCardsException exc){
            askToDiscardPowCard();
        } catch (CardAlreadyCollectedException e) {
            Message message=new ActionError("You have already used this Card, cambia azione.");
            gameRoom.sendErrorMessage(userID,message);
            askAction();
        } catch (NotYourTurnException e) {
            Message message=new ActionError("Not your turn");
            gameRoom.sendErrorMessage(userID,message);
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


    public void askWeaponToShoot(){
        this.shootElaborator = new OfficialShootVersion(this.match,this.match.getActivePlayer());
        shootElaborator.setstatus(1);
        supportPow= new SupportPow();
        supportPow.setAttacker(this.match.getActivePlayer());
        gameRoom.askWeapon(match.getActivePlayer().getID());
    }

    public void wakeupshoot(int chosenindex, String userID)
    {
        if(match.getActivePlayer().getID().equals(userID))
        {
            if(shootElaborator.getStatus()==1)
            {
                shootElaborator.chooseweapon(shootElaborator.getguns().get(chosenindex));
                askserieToShoot();
            }
            if(shootElaborator.getStatus()==2)
            {
                verifyIndexSerie(chosenindex);
            }

            if(shootElaborator.getStatus()==4)
            {
                checkplayertoattack(chosenindex);
            }

            if(shootElaborator.getStatus()==5)
            {
                checkcelltoattack(chosenindex);
            }

            if(shootElaborator.getStatus()==6)
            {
                checkeffectorchangeattack();
            }

            if(shootElaborator.getStatus()==8)
            {
                answertocontinue(chosenindex);
            }
            if(shootElaborator.getStatus()==9)
            {
                setDirectionToShoot(chosenindex);
            }
        }

    }

    public void setDirectionToShoot(int index)
    {
        shootElaborator.setdirectiontoshoot(index);
        shootElaborator.generatelistattackable(shootElaborator.getPlayer());
        List <Coordinate> list = shootElaborator.getlistattackable(2);
        for(Coordinate c : list)
        {
            shootElaborator.setvictimcell(c);
        }
    }

    public void askserieToShoot()
    {

        shootElaborator.setstatus(2);
        gameRoom.askIndexSerie(match.getActivePlayer().getID());
    }

    public void verifyIndexSerie(int index)
    {
        List<Integer> possibleTypesOfSeries = shootElaborator.gettypes();
        if(!possibleTypesOfSeries.contains(index))
        {
            askserieToShoot();
        }
        else
        {
            shootElaborator.settype(index);
            shootElaborator.generateattacks();
            shootElaborator.setfirstattack();
            askPaymentbeforeShoot();
        }
    }

    public void askPaymentbeforeShoot()
    {
        shootElaborator.setstatus(3);
        if(shootElaborator.payextra())
        {
            decideShootingMethod();
        }
        else
        {
            gameRoom.informPaymentError(match.getActivePlayer().getID());
            nextStep();
        }
    }

    public void decideShootingMethod()
    {
        int typeattack= shootElaborator.getTypeAttack();
        if(typeattack==1||typeattack==2||typeattack==3||typeattack==4||typeattack==5||typeattack==8||typeattack==9||typeattack==11)
        {
            standardattack();
        }
        if(typeattack==10)
        {
            ricorsiveattack();
        }
        if(typeattack==11)
        {
            allroomattack();
        }

        if(shootElaborator.getFlagfirstattack()==0)
        {
            shootElaborator.setFlagfirstattack(1);
        }
    }

    public void allroomattack()
    {
        shootElaborator.setAttackmethod(3);
        moveAndList();
        starteffect();
    }

    public void standardattack()
    {
        shootElaborator.setAttackmethod(1);
        moveAndList();
        starteffect();
    }

    public void ricorsiveattack()
    {
        shootElaborator.setAttackmethod(2);
        moveAndList();
        starteffect();
    }

    public void starteffect()
    {
        if(shootElaborator.loadeffect())
        {
            askBersaglio();
        }
        else
        {
            nextStep();
        }
    }

    public void askBersaglio()
    {
        int typetarget= shootElaborator.getTypeTarget();

        if(typetarget==1)
        {
            shootElaborator.setstatus(4);
            gameRoom.askPlayerIndex(match.getActivePlayer().getID());
        }
        else
        {
            shootElaborator.setstatus(5);
            gameRoom.askCellIndex(match.getActivePlayer().getID());
        }
    }

    public void checkplayertoattack(int index)
    {
        List <Player> attackablePlayers= shootElaborator.getlistattackable(1);
        if(!(index>=attackablePlayers.size()||index<0))
        {
            if(shootElaborator.setvictimplayer(attackablePlayers.get(index)))
            {
                if(!supportPow.getVictims().contains(attackablePlayers.get(index)))
                {
                    supportPow.addVictim(attackablePlayers.get(index));
                }
                if(shootElaborator.getMoveyou()!=0)
                {
                    shootElaborator.setstatus(6);
                    shootElaborator.run(attackablePlayers.get(index), shootElaborator.getMoveyou());
                }
                else
                {
                    checkeffectorchangeattack();

                }
            }
            else
            {
                askBersaglio();
            }
        }
        else
        {
            askBersaglio();
        }

    }

    public void checkeffectorchangeattack()
    {
        if(shootElaborator.checkothereffects())
        {
            starteffect();
        }
        else
        {
            if(shootElaborator.checkotherattacks())
            {
                continueshootinganswer();
            }
            else
            {
                //nextStep();
                particoularpowers();
            }
        }
    }



    public void particoularpowers()
    {
        //Controllo se l'attacker ha un potenziamento di tipo mirino
        int flag=0;
        for(PowCard p : match.getActivePlayer().getPows())
        {
            if(p.getType()==1)
            {
                flag=1;
            }
        }
        if(flag==1)
        {
            gameRoom.askScopePow(match.getActivePlayer().getID());
        }

        for(Player p : supportPow.getVictims())
        {
            int flag2=0;
            for(PowCard pow : p.getPows())
            {
                if(pow.getType()==3)
                {
                    flag2=1;
                }
            }
            if(flag2==1)
            {
                gameRoom.askGranadePow(p.getID());
            }
        }

        if(flag!=1)
        {
            nextStep();
        }
    }

    public void useScope(int index, String userID)
    {
        if(match.getActivePlayer().getID().equals(userID))
        {
            if(index<supportPow.getVictims().size()&&index>=0)
            {
                match.getPlayer(supportPow.getVictims().get(index).getColor()).setDamage(1, supportPow.getAttacker().getColor());
                nextStep();
            }
        }
    }

    public void useGranade(int index, String userID)
    {
        if(index==1)
        {
            match.setMarks(match.getPlayerByID(userID),match.getActivePlayer(),1);
        }
    }


    public void continueshootinganswer()
    {
        shootElaborator.setstatus(8);
        gameRoom.askNextAttack(match.getActivePlayer().getID());
    }

    public void checkcelltoattack(int index)
    {
        List <Coordinate> attackableCells= shootElaborator.getlistattackable(2);
        if(!(index<0||index>=attackableCells.size()))
        {
            if(shootElaborator.setvictimcell(attackableCells.get(index)))
            {
                checkeffectorchangeattack();

            }
            else
            {
                askBersaglio();
            }
        }
        else
        {
            askBersaglio();
        }
    }

    public void answertocontinue(int index)
    {
        if(index==1)
        {
            shootElaborator.setsuccessiveattack();
            askPaymentbeforeShoot();
        }
        else
        {
            nextStep();
        }

    }

    private void moveAndList()
    {
        if(shootElaborator.getmoveme()!=0)
        {
            shootElaborator.run(shootElaborator.getPlayer(),shootElaborator.getmoveme());
        }
        if(shootElaborator.getTypeAttack()==3)
        {
            shootElaborator.setstatus(9);
            gameRoom.askDirectionToShoot(match.getActivePlayer().getID());
        }
        else
        {
            shootElaborator.generatelistattackable(shootElaborator.getPlayer());
        }

    }

    //-----------------------------------------Fine Metodi necessari alla Shoot--------------------------------------------//



    //TODO: Quando è lecita la recharge?
    private void recharge(){
        /*recharge a weapon checking if the weapon is already loaded*/
        System.out.println("Which weapon do you want to recharge?");
        int weaponToRecharge=0;
        try{
            manageWeapon.recharge(match.getActivePlayer(), weaponToRecharge);
        } catch(WeaponAlreadyLoadedException e){
            printOut("You have already loaded this weapon");
        }
    }


    //-----------------------------Metodi per potenziamenti------------------------------------------------------------//

    public void usePow()
    {
        supportPow= new SupportPow();
        askIndexPow();
    }

    public void askIndexPow()
    {
        supportPow.setStatus(1);
        gameRoom.askIndexPow(match.getActivePlayer().getID());
    }

    public void muxPow(int index, String userID)
    {
        if(match.getActivePlayer().getID().equals(userID))
        {
            if(supportPow.getStatus()==1&&index==1)
            {
            checkTeleporter();
            }
            if(supportPow.getStatus()==1&&index==2)
            {
                checkNewton();
            }
            if(supportPow.getStatus()==1&&index==3)
            {
                nextStep();
            }
            if(supportPow.getStatus()==2)
            {
                useTeleport(index);
            }
            if(supportPow.getStatus()==3)
            {
                supportPow.setDirection(index);
                askNumberSteps();
            }
            if(supportPow.getStatus()==4)
            {
                supportPow.setSteps(index);
                askPlayerIndex();
            }
            if(supportPow.getStatus()==5)
            {
                executeSteps(index);
            }
        }

    }

    public void checkTeleporter()
    {
        int flag=0;
        for(PowCard p : match.getActivePlayer().getPows())
        {
            if(p.getType()==2)
            {
                flag=1;
            }
        }
        if(flag==0)
        {
            askIndexPow();
        }
        else
        {
            askPosition();
        }
    }

    public void askPosition()
    {
        supportPow.setStatus(2);
        gameRoom.askPos(match.getActivePlayer().getID());
    }

    public void checkNewton()
    {
        int flag=0;
        for(PowCard p : match.getActivePlayer().getPows())
        {
            if(p.getType()==0)
            {
                flag=1;
            }
        }
        if(flag==0)
        {
            askIndexPow();
        }
        else
        {
            askDirection();
        }
    }

    public void askDirection()
    {
        supportPow.setStatus(3);
        gameRoom.askDirection(match.getActivePlayer().getID());
    }

    public void askNumberSteps()
    {
        supportPow.setStatus(4);
        gameRoom.askStep(match.getActivePlayer().getID());
    }

    public void useTeleport(int index)
    {
        //TODO CONTROLLO SUL FATTO CHE LA MAPPA LO CONSENTA
        Coordinate coordinate = new Coordinate(0,0);
        if(index==1)
        {
            coordinate=new Coordinate(0,0);
        }
        if(index==2)
        {
            coordinate=new Coordinate(0,1);
        }
        if(index==3)
        {
            coordinate=new Coordinate(0,2);
        }
        if(index==4)
        {
            coordinate=new Coordinate(0,3);
        }
        if(index==5)
        {
            coordinate=new Coordinate(1,0);
        }
        if(index==6)
        {
            coordinate=new Coordinate(1,1);
        }
        if(index==7)
        {
            coordinate=new Coordinate(1,2);
        }
        if(index==8)
        {
            coordinate=new Coordinate(1,3);
        }
        if(index==9)
        {
            coordinate=new Coordinate(2,0);
        }
        if(index==10)
        {
            coordinate=new Coordinate(2,1);
        }
        if(index==11)
        {
            coordinate=new Coordinate(2,2);
        }
        if(index==12)
        {
            coordinate=new Coordinate(2,3);
        }
        Run movement = new Run();
        movement.resetPosition(match.getActivePlayer(),coordinate);
        nextStep();
    }

    public void askPlayerIndex()
    {
        supportPow.setStatus(5);
        gameRoom.askPlayer(match.getActivePlayer().getID());
    }

    public void executeSteps(int index)
    {
        match.getPlayers().get(index);
        Run run = new Run();
        //TODO CONTROLLO CHE IL MOVIMENTO SIA VALIDO E NEL CASO LO SPOSTO
        nextStep();
    }



    //-----------------------------Fine Metodi per potenziamenti------------------------------------------------------//


    //----------------------------Metodi utili per set turno----------------------------------------------------------//

    private void nextStep() {
        //timer.cancel();
        resetActionBool();
        //Controllo validità partita
        checkGameValidity();
        //Controllo eventuale Frenesia Finale
        if(match.getDashboard().isKillShotTrackFull()){
            //ULTIMO TURNO!
        }
        if((match.getActivePlayer().getAction() < 2) && match.getActivePlayer().isConnected()) {
            match.updateEndAction();
            askAction();
        }
        else if(match.getActivePlayer().getAction()==2||!match.getActivePlayer().isConnected()){
            match.updateEndTurn();
            checkDeadPlayers();
        }
        //Se il giocatore successivo è disconnesso passa a quello dopo.
        //Controllo che i giocatori siano più di 3 se no dichiaro vincitore!

    }
    private void checkGameValidity(){
        if(!isGameValid()){
            if(counterTurn>2){
                endGame();
            }else
                counterTurn++;
        }
        else{
            counterTurn=0;
        }
    }

    private boolean isGameValid() {
        int connectedPlayers=0;
        for (Player player : match.getPlayers()) {
            if(player.isConnected()) connectedPlayers++;
        }
        return connectedPlayers >= 3;
    }

    private void checkDeadPlayers(){
        int counter=0;
        List<Player> deadConnectedPlayers=new ArrayList<>();
        for (Player player : match.getPlayers()) {
            if(match.getRound()==1&&player.getAction()==0) break;       //In the first turn players with 0 actions has not yet spawned
            else if (player.isDead()) {
                try {
                    match.assignPowCard(player);
                } catch (MaxNumberofCardsException e) {
                    //At spawn player can own up to 4 cards
                }
                counter++;
                if (player.isConnected()) {
                    deadConnectedPlayers.add(player);
                }
            }
        }
        if(counter==0){
            nextTurn();
        }
        else{
            spawnConnectedPlayers(deadConnectedPlayers);
        }

    }

    private void endGame(){
        DeathAndRespawn deathAndRespawn=new DeathAndRespawn();
        String winnerID=deathAndRespawn.winner(match);
        //TODO: messaggi vincitore ecc
    }

    private void spawnConnectedPlayers(List<Player> deadConnectedPlayers){
        for (Player player : deadConnectedPlayers) {
            askSpawnPoint(player.getID());
        }
        timer=new Timer();
        startSpawnTimer();
    }
    private void spawnDisconnectedPlayers(){
        match.getPlayers().forEach(player ->{
            if(player.isDead()&&!player.isConnected()){
                match.spawnDeadPlayer(player);
            }
        });
        nextTurn();
    }

    private void nextTurn(){
        Player activePlayer = match.getActivePlayer();
        int index=0;
        for(int i=0; i<match.getPlayersSize(); i++){
            if(match.getPlayers().get(i).equals(activePlayer)){
                index = i;
                if(i==match.getPlayersSize()-1){
                    nextRound();
                }
            }
        }
        match.getPlayerByIndex(index).setActive();
        index++;
        while(!match.getPlayerByIndex(index).isConnected()){
            index++;
        }
        match.getPlayerByIndex(index).setActive();
        if(match.getRound()!=1) {
            askAction();
        }
        else if(match.getRound()==1){
            askSpawnPoint(match.getActivePlayer().getID());
        }
    }
    private void nextRound(){
        int index=0;
        for(Player player:match.getPlayers()) player.resetAction();
        match.setRound();
        while(!match.getPlayerByIndex(index).isConnected()){
            index++;
        }
        match.getPlayerByIndex(index).setActive();
        askAction();
    }
    private void resetActionBool(){
        isMovementBeforeShoot=false;
        isMovementBeforeGrab=false;
    }

    //TODO: completare metodi per Player Disconnesso

    public void disconnectPlayer(String userID) {
        gameRoom.closeConnection(userID);
        //La chiamata sulla match viene fatta in seguito
    }

    public void reConnectPlayer(String userID) {
        match.setPlayerReConnected(userID);
    }


    public void setPlayerDisconnected(String userID) {
        match.setPlayerDisconnected(userID);
    }
    //----------------------------startTimer() per le ≠ richieste al Client-------------------------------------------//

    public void startSpawnTimer(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                match.getPlayers().forEach(player ->{
                    if(player.isDead()){
                        disconnectPlayer(player.getID());
                        spawnDisconnectedPlayers();
                    }
                });

            }
        }, queueTimer);
    }










}

