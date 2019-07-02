package client.gui;

import client.View;
import model.Player;
import network.client.Client;
import network.messages.Message;

import javax.swing.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GUIViewAdapter implements View {
    private static final Logger LOGGER= Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private GUIView guiView;
    private static Waiter waiter;
    private Client client;
    private int indexSelectedWeapon;
    private int requestedAction;

    /*Adapter to use GUIView. When GUIView is called you are in wait to be woken up by the desired information*/
    public GUIViewAdapter(Client client){
        this.guiView=new GUIView();
        waiter=new Waiter();
        //guiView.setWaiter(waiter);
        waiter.setGuiViewAdapter(this);
        this.client=client;
        LOGGER.setLevel(Level.INFO);
    }

    @Override
    public void start() {


    }

    @Override
    public void setConnection() {

    }

    @Override
    public void showException(String message) {

    }

    @Override
    public void showInfoMessage(Message message) {

    }

    @Override
    public void login() {

    }

    @Override
    public void createPlayer() {

    }

    @Override
    public void chooseMap() {

    }

    @Override
    public void chooseStartingCell(){}

    @Override
    public void welcomeMessage(int idClient) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                guiView.welcomeMessage(idClient);
            }
        });
    }

    @Override
    public void endMessage() {

    }

    @Override
    public void printMap() {

    }

    @Override
    public void showPlayerWeapons() {

    }

    @Override
    public void showSpawnPointWeapons() {

    }

    @Override
    public void showAmmoCard() {

    }

    @Override
    public void notifyAttackedPlayer(Player attackedplayer) {

    }

    @Override
    public void showPlayerPows() {

    }

    @Override
    public void showPlayerPowsForAttack() {

    }

    @Override
    public void showPlayerPowWithColors() {

    }

    @Override
    public void showPlayerAmmos() {

    }

    @Override
    public int getWeaponCard() {
        return 0;
    }

    @Override
    public String getDirection() {
        return null;
    }

    @Override
    public List<String> getListDirection() {
        return null;
    }

    @Override
    public int getNumberOfPow() {
        return 0;
    }

    @Override
    public int getPowCard() {
        return 0;
    }

    @Override
    public int getWeaponCardtoAttack() {
        return 0;
    }

    @Override
    public void printPlayerMove() {

    }

    @Override
    public void printPlayerData() {

    }

    @Override
    public void printDamagedPlayer(int numberdamages, String attackerplayername) {

    }

    @Override
    public void printMarkedPlayer(int numbermarks, String attackerplayername) {

    }

    @Override
    public void printDamagerAndMarkerPlayer(int numberdamages, int numbermarks, String attackedplayername) {

    }

    @Override
    public void chooseAction() {

    }

    @Override
    public void chooseRunDirection() {

    }

    @Override
    public void chooseWeaponToGrab() {

    }

    @Override
    public void askUsePowToGrabWeapon() {

    }

    @Override
    public void chooseDiscardWeapon() {

    }

    @Override
    public void chooseDiscardPowCard() {

    }

    @Override
    public void askToReConnect()
    {

    }

    @Override
    public boolean askToTryToReConnect() {
        return false;
    }


    @Override
    public void getGunIndex() {

    }

    @Override
    public void getSerieIndex() {

    }

    @Override
    public void getPlayerIndex() {

    }

    @Override
    public void getCellIndex() {

    }

    @Override
    public void showPaymentError() {

    }

    @Override
    public void getNextAttack() {

    }

    @Override
    public void getDirection() {

    }


}
