package view;

import controller.Action;
import model.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/* This class represents the remote interface. When you have to communicate with a connectionHandler first of all the method selectCommunicator is called.
*  It gives back the the type of communicator that is for socket or RMI. In this way the RemoteBoardView remains independent from the effective type of
*  communication you choose to use.
*
*  In this way mixed matches are allowed, that is a match in which some clients use socket, others RMI.
*
*  Besides when a connectionHandler is asked some data, the remote board view makes the check that the returned value is really a valid value.
 */
public class RemoteBoardView extends BoardView{

    private Match match;
    private List<Object> clients;
    //private SocketCommunication socketView;
    //private RMICommunication rmiView;
    private Communication viewAdapter;
    private Object currentClient;
    private static final Logger LOGGER= Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


    /*To build the RemoteBoardView you have to build the two possible communicators
     * @param boardModel
     * @param socketsIn
     * @param socketsOut
     * @param clients: contiene la lista di Object che possono essere: il socket collegato al connectionHandler oppure la sua RMIClient
     */
    public RemoteBoardView(Match match, List<Object> clients, Map<Socket, ObjectOutputStream> socketsOut, Map<Socket, ObjectInputStream> socketsIn){
        this.match = match;
        //match.addObserver(this);
        this.clients=clients;
        //this.socketView=new SocketCommunication(socketsOut, socketsIn);
        //this.rmiView=new RMICommunication();
        LOGGER.setLevel(Level.INFO);
    }


    //Game is started
    @Override
    public void startingMessage(){
        // TODO settaggio del connectionHandler, selezione della comunicazione
        //this.viewAdapter=this.selectCommunicator(currentClient);
        try {
            viewAdapter.startingMessage(currentClient,match.getActivePlayer().getcolor());
        }catch (IOException e) {
            //this.DeleteClientFromControllerControl(currentClient);
            LOGGER.log(Level.FINEST,e.getMessage(),e);
        }
    }

    @Override
    public void endMessage() {

    }

    @Override
    public int getNumberOfPlayer() {
        return 0;
    }

    @Override
    public String getPlayerColor(String playercolor) {
        return null;
    }

    @Override
    public String getPlayerName(String playername) {
        return null;
    }

    @Override
    public Coordinate getPlayerStartCel() {
        return null;
    }

    @Override
    public void showPlayerCards() {

    }

    @Override
    public void chargedWeapon() {

    }

    @Override
    public void printMap1() {

    }

    @Override
    public void printMap2() {

    }

    @Override
    public void printMap3() {

    }

    @Override
    public void printMessage(String message) {

    }

    @Override
    public Action getaction() {
        return null;
    }

    @Override
    public Weapon getweapon() {
        return null;
    }

    @Override
    public PowCard getPow() {
        return null;
    }

    @Override
    public int getcard() {
        return 0;
    }

    @Override
    public void showFinalScore(List<Player> finalPlayers) {

    }

    @Override
    public void getPriceofWeapons(SpawnPointCell cell, int index) {

    }

    @Override
    public void getPriceofRechargeWeapons(Weapon weapon) {

    }

    @Override
    public void printMessageAtAll(String string) {

    }

    @Override
    public void printCurrentPlayerState(Match match) {

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
