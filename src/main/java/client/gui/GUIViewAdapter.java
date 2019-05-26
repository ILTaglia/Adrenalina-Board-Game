package client.gui;

import client.View;
import model.Player;
import network.messages.Message;

import javax.swing.*;
import java.util.List;

public class GUIViewAdapter implements View {
    private GUIView guiView;
    private static Waiter waiter;

    /*Adapter to use GUIView. When GUIView is called you are in wait to be woken up by the desired information*/
    public GUIViewAdapter(){
        this.guiView=new GUIView();
        waiter=new Waiter();
        //guiView.setWaiter(waiter);
        waiter.setGuiViewAdapter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void showInfoMessage(Message message) {

    }

    @Override
    public void login() {

    }

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
    public void notifyAttackedPlayer(Player attackedplayer) {

    }

    @Override
    public void showPlayerPows() {

    }

    @Override
    public void showPlayerPows(Player player) {

    }

    @Override
    public void showPlayerPowsColors(Player player) {

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
}
