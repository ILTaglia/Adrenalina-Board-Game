package view;

import java.io.PrintStream;

import controller.Action;
import model.*;
import utils.GetData;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class CLIBoardView extends BoardView {
    private Dashboard dashboard;
    private PrintStream channelOut;
    private GetData getData;

    public CLIBoardView(Dashboard dashboard,PrintStream channelOut, int test) {
        this.channelOut=channelOut;
        getData=new GetData();
    }

    /* Welcome message to start*/
    @Override
    public void welcomeMessage() {
        channelOut.println("Welcome to Adrenalina!");
    }

    /* End message to finish*/
    @Override
    public void endMessage() {
        channelOut.println("The game is finish.");
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
        Player player = match.getActivePlayer();
        channelOut.println("Player:");
        channelOut.println(player.getname()+"Id"+player.getid());
        channelOut.print("Weapon Cards: ");
        List<Weapon> weapons = player.getWeapons();
        if(!weapons.isEmpty()){
            for(Weapon w:weapons){
                channelOut.print(" / "+w.getName());
            }
            channelOut.println("");
        } else{
            channelOut.println("You don't have Weapon Cards");
        }
        channelOut.print("PowCards: ");
        List<PowCard> pows = player.getPows();
        if(!pows.isEmpty()){
            for(PowCard p:pows){
                channelOut.print(" / "+p.getName());
            }
            channelOut.println("");
        } else{
            channelOut.println("You don't have PowCards");
        }
        channelOut.print("Position: ");
        channelOut.print("Line: "+player.getCel().getX()+"Column"+player.getCel().getY());
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
