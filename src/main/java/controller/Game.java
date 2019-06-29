package controller;
import exceptions.*;
import model.*;
import network.messages.*;
import network.messages.error.*;
import network.server.GameRoom;

import java.util.List;
import java.util.Map;

import static utils.NotifyClient.*;
import static utils.printStream.printOut;

public class Game{

    private Match match;
    private ManagingWeapons manageWeapon;
    private GameRoom gameRoom;      //CAPIRE SE USARE QUESTO O PREFERIRE LAVORARE DIRETTAMENTE CON NotifyClient
    private boolean isMovementBeforeGrab;
    private boolean isMovementBeforeShoot;

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
        match.notifyOfOtherPlayers();
        askMap((String)userList.values().toArray()[0]);
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
        askSpawnPoint();

    }

    private void askSpawnPoint() {
        gameRoom.askToChooseSpawnPoint(match.getActivePlayer().getID());
    }
    //Metodo che viene chiamato quando si riceve la risposta dal Client sul Pow da Usare

    //TODO: sistemare la validità della cella inserita dall'utente
    public void setSpawn(String userID, Coordinate coordinate,int powCardIndex){
        checkUserAction(userID);
        Spawn playerSpawn = new Spawn();
        try {
            playerSpawn.spawn(match, match.getActivePlayer(), coordinate.getX(), coordinate.getY(), powCardIndex);
        }catch (InvalidColorException e){
            Message errorMessage=new ActionError("Colore non valido, riprova (Migliorare messaggio)");
            gameRoom.sendErrorMessage(userID,errorMessage);
            askSpawnPoint();
        }
        //Se si è a inizio partita una volta generato il player effettivamente ha inizio il suo normale turno di gioco
        //Se invece il Player ha spawnato dopo il turno di un altro player si procede con il giocatore successivo a quello
        //che ha terminato il turno

        //TODO:Modifica per controllo turno
        askAction();

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
        gameRoom.askToChooseNextAction(match.getActivePlayer().getID());
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
                //this.askWeaponToShoot(); //TODO, HO COMMENTATO PERCHE' IL METODO ORA SI ASPETTA ALTRI PARAMETRI
                //Richiesta al giocatore con arma con cui vuole attaccare
                //Da cui poi si chiamerà la shoot

                //Per capire vedi funzionamento altre azioni.

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
               // this.shoot();   //TODO, HO COMMENTATO PERCHE' IL METODO ORA SI ASPETTA ALTRI PARAMETRI
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
        if(match.getActivePlayer().getCel().inmap(match.getDashboard(), x, y).getType()==0){
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
        SpawnPointCell cell = (SpawnPointCell) match.getActivePlayer().getCel().inmap(match.getDashboard(),xCoordinate,yCoordinate);
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

    //TODO:Chiedi indice arma da usare per l'attacco e il Player da attaccare.
    public void askWeaponToShoot(Player whoPerformsTheAttack){
        OfficialShootVersion shootelaborator = new OfficialShootVersion(this.match,whoPerformsTheAttack);
        if(!shootelaborator.checkplayer())
        {
            //TODO ERRORE GIOCATORE NON TROVATO --> NON DOVREBBE MAI ACCADERE PERO'
        }
        List<Weapon> listofguns=shootelaborator.getguns();
        int choose=0;
        //TODO INVIO LISTA DI ARMI E CHIEDO RESTITUZIONE DI UN INTERO IDENTIFICATIVO FINO A QUANDO L'INTERO NON E' CORRETTO
        shootelaborator.chooseweapon(listofguns.get(choose));
        shoot(whoPerformsTheAttack,shootelaborator);

    }
    //TODO
    //Completa attacco in base al funzionamento della parte della Shoot
    public void shoot(Player whoPerformsTheAttack, OfficialShootVersion shootelaborator){
        List<Integer> possibleTypesOfSeries = shootelaborator.gettypes();
        int choose=0;
        //TODO INVIO LISTA NUMERI POSSIBILI E MI ASPETTO UN INTERO COME RISPOSTA (CONTENUTO TRA I PRECEDENTI)
        shootelaborator.settype(choose);
        shootelaborator.generateattacks();
        shootelaborator.setfirstattack();
        shootelaborator=attacklauncher(shootelaborator);
        while(shootelaborator.checkotherattacks())
        {
            int checkstop=0;
            //TODO MESSAGGIO IN CUI CHIEDO SE HO INTENZIONE O MENO DI PROSEGUIRE CON IL PROSSIMO ATTACCO, RESTITUISCE UN NUMERO (0 O 1)
            if(checkstop==0)
            {
                shootelaborator.setsuccessiveattack();
                shootelaborator=attacklauncher(shootelaborator);
            }
        }



    }

    //-----------------------------------------Metodi necessari alla Shoot-------------------------------------------------//

    //############################################
    //##        ATTACK LAUNCHER              #####
    //############################################

    //This will start the attack defining the type of the attack and calling the correct method for that attack
    //Al termine verifica se era nel primo attacco, nel caso aggiorna i flag

    public OfficialShootVersion attacklauncher(OfficialShootVersion shootelaborator)
    {
        if(shootelaborator.payextra())
        {
            int typeattack= shootelaborator.getTypeAttack();
            if(typeattack==1||typeattack==2||typeattack==3||typeattack==4||typeattack==5||typeattack==8||typeattack==9||typeattack==11)
            {
                shootelaborator=standardattack(shootelaborator);
            }
            if(typeattack==7)
            {
                shootelaborator=allaroundattack(shootelaborator);
            }
            if(typeattack==12)
            {
                shootelaborator=movingtomeattack(shootelaborator);
            }

            if(shootelaborator.getFlagfirstattack()==0)
            {
                shootelaborator.setFlagfirstattack(1);
            }

        }
        else
        {
            //TODO MESSAGGIO ERRORE E ANNULLAMENTO ATTACCO
        }
        return shootelaborator;
    }


    //############################################
    //##        STANDARD EXECUTION           #####
    //############################################

    //Is used only by
    //Finite distance 	1
    //Undefined distance	2
    //MoreDistance	3
    //Cardinal	4
    //Not seen	5
    //Allroom	9
    //Infiniteline	11


    //L'attacco come prima cosa controlla se ci siano degli spostamenti che posso fare, in tal caso li esegue
    //Subito dopo elabora la lista di giocatori e celle attaccabili
    //Avvia i metodi di esecuzione effetti finchè ce ne siano

    private OfficialShootVersion moveAndList(OfficialShootVersion shootelaborator)
    {
        if(shootelaborator.getmoveme()!=0)
        {
            shootelaborator.run(shootelaborator.getPlayer(),shootelaborator.getmoveme());
        }
        shootelaborator.generatelistattackable(shootelaborator.getPlayer());
        return shootelaborator;
    }



    public OfficialShootVersion standardattack(OfficialShootVersion shootelaborator)
    {
        shootelaborator=moveAndList(shootelaborator);
        List <Player> attackablePlayers= shootelaborator.getlistattackable(1);
        List <Coordinate> attackableCells= shootelaborator.getlistattackable(1);
        while(shootelaborator.loadeffect())
        {
            int typetarget=shootelaborator.getTypeTarget();
            if(typetarget==1)
            {
                int choosevictim=0;
                //TODO MOSTRO ALL'UTENTE LA LISTA DEI PLAYER ATTACCABILI E CHIEDO UN INDICE, SE ERRATO O LISTA VUOTA MESSAGGIO DI ERRORE
                if(shootelaborator.setvictimplayer(attackablePlayers.get(choosevictim)))
                {
                    shootelaborator.run(attackablePlayers.get(choosevictim),shootelaborator.getMoveyou());
                }

            }
            else
            {
                int choosevictim=0;
                //TODO MOSTRO ALL'UTENTE LA LISTA DELLE CELLE ATTACCABILI E CHIEDO UN INDICE, SE ERRATO O LISTA VUOTA MESSAGGIO DI ERRORE
                shootelaborator.setvictimcell(attackableCells.get(choosevictim));
            }
        }


        return shootelaborator;
    }

    public OfficialShootVersion allaroundattack(OfficialShootVersion shootelaborator)
    {
        shootelaborator=moveAndList(shootelaborator);
        List <Player> attackablePlayers= shootelaborator.getlistattackable(1);
        List <Coordinate> attackableCells= shootelaborator.getlistattackable(1);
        while(shootelaborator.loadeffect())
        {
            int dir=0;
            shootelaborator.setdirectiontoshoot(dir);
            for(int i=0;i<4;i++)
            {
                int typetarget=shootelaborator.getTypeTarget();
                if(typetarget==1)
                {
                    int choosevictim=0;
                    //TODO MOSTRO ALL'UTENTE LA LISTA DEI PLAYER ATTACCABILI E CHIEDO UN INDICE, SE ERRATO O LISTA VUOTA MESSAGGIO DI ERRORE
                    if(shootelaborator.setvictimplayer(attackablePlayers.get(choosevictim)))
                    {
                        shootelaborator.run(attackablePlayers.get(choosevictim),shootelaborator.getMoveyou());
                    }
                    dir++;
                    shootelaborator.setdirectiontoshoot(dir);

                }
                else
                {
                    int choosevictim=0;
                    //TODO MOSTRO ALL'UTENTE LA LISTA DELLE CELLE ATTACCABILI E CHIEDO UN INDICE, SE ERRATO O LISTA VUOTA MESSAGGIO DI ERRORE
                    shootelaborator.setvictimcell(attackableCells.get(choosevictim));
                    dir++;
                    shootelaborator.setdirectiontoshoot(dir);
                }

            }
        }

        return shootelaborator;
    }


    public OfficialShootVersion movingtomeattack(OfficialShootVersion shootelaborator)
    {
        shootelaborator=moveAndList(shootelaborator);
        List <Player> attackablePlayers= shootelaborator.getlistattackable(1);
        int choosevictim=0;
        //TODO MOSTRO ALL'UTENTE LA LISTA DEI PLAYER SPOSTABILI E CHIEDO UN INDICE, SE ERRATO O LISTA VUOTA MESSAGGIO DI ERRORE
        while(shootelaborator.loadeffect())
        {
            shootelaborator.run(attackablePlayers.get(choosevictim),shootelaborator.getMoveyou());
            shootelaborator.setvictimplayer(attackablePlayers.get(choosevictim));
        }
        return shootelaborator;
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


    //----------------------------Metodi utili per set turno----------------------------------------------------------//

    //TODO: a fine turno gestire carte sulla dashboard ecc.-> non posso farlo a fine della singola azione perchè rischierei di pescare più di una volta lo stesso
    private void nextStep() {
        resetActionBool();
        if(match.getActivePlayer().getAction()<2) {
            match.updateEndAction();
            askAction();
        }
        else if(match.getActivePlayer().getAction()==2){
            match.updateEndTurn();
            setTurn();
        }
    }


    public void setTurn(){
        Player p = match.getActivePlayer();
        int index=0;
        for(int i=0; i<match.getPlayersSize(); i++){
            if(match.getPlayers().get(i).equals(p)){
                index = i;
                if(i==match.getPlayersSize()-1){
                    resetTurn();
                }
            }
        }
        match.getPlayerByIndex(index).setActive();
        index++;
        match.getPlayerByIndex(index).setActive();
    }

    private void resetTurn(){
        for(Player player:match.getPlayers()) player.resetAction();
        match.getPlayerByIndex(0).setActive();
    }
    private void resetActionBool(){
        isMovementBeforeShoot=false;
        isMovementBeforeGrab=false;
    }
    //TODO: completare metodi per Player Disconnesso
    public void setPlayerDisconnected(String userID) {
        match.getPlayerByID(userID);
    }

    public void reConnectPlayer(String userID) {
        match.getPlayerByID(userID);
    }


}
