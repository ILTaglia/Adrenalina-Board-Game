package client;

import model.Player;
import model.Weapon;
import network.messages.Message;

import java.util.List;

public interface View {

    /*
    **********************************************************
    * Metodi per Avvio Client                                *
    **********************************************************
    */
    void start();

    void setConnection();

    void login();

    void askToReConnect();

    boolean askToTryToReConnect();

    /*
     **********************************************************
     * Metodi per Eventi/Eccezioni                              *
     **********************************************************
     */


    void showException(String message);

    void showInfoMessage(Message message);

    /*
     **********************************************************
     * Metodi per  Match                              *
     **********************************************************
     */


    void createPlayer();

    void chooseMap();

    void chooseStartingCell();

    void chooseAction();

    void chooseRunDirection();

    void chooseWeaponToGrab();

    void askUsePowToGrabWeapon();

    void chooseDiscardWeapon();

    void chooseDiscardPowCard();



    //Message to advise the player the game has started
    void welcomeMessage(int idClient);

    //Message to advise the player the game has finished
    void endMessage();

    //Method to print the map to the connectionHandler
    void printMap();

    //Method to show player its weapon cards
    void showPlayerWeapons();

    //Method to show Weapon Cards in SpawnPoint Cell
    void showSpawnPointWeapons();

    //Method to show AmmoCard in Normal Cell
    void showAmmoCard();

    void notifyAttackedPlayer(Player attackedplayer);

    //Method to show the active player its PowCards
    void showPlayerPows();

    //Method to show a player its PowCards, used in response to an attack
    void showPlayerPowsForAttack();

    //Method to show a player its PowCards, and colors to choose the spawn point cell
    void showPlayerPowWithColors();

    //Method to show the active player how many ammos he has
    void showPlayerAmmos();

    //Method to ask the player which cards he wants to buy if in a SpawnPoint Cell
    int getWeaponCard();

    //Method to show all the possible directions
    void showDirection();

    int getNumberOfPow();

    //Method to ask the player which PowCard he wants to use to attack or in response, @return the position of card,
    //according to the print of player cards
    int getPowCard();

    //Method to ask the player which Weapon card he wants to use to attack, @return the position of card,
    //according to the print of player cards
    int getWeaponCardtoAttack();

    //Method to show other players a player has moved
    void printPlayerMove();

    //Method to show the player its current data
    void printPlayerData();

    //Method to advise the player he has been damaged
    void printDamagedPlayer(int numberdamages, String attackerplayername);

    //Method to advise the player he has been given marks
    void printMarkedPlayer(int numbermarks, String attackerplayername);

    //Method to advise the player of the consequences of his attack
    void printDamagerAndMarkerPlayer(int numberdamages, int numbermarks, String attackedplayername);

    void showWeaponInfo(Weapon weapon);

    //Method for attacks

    void getGunIndex();

    void getSerieIndex();

    void getPlayerIndex();

    void getCellIndex();

    void showPaymentError();

    void getNextAttack();

    void getDirection();

    void getPowIndex();

    void getPosIndex();

    void getDirectionIndex();

    void getNumberStep();

    void getMovePlayerIndex();

    void getScopeIndex();

    void getGranadeIndex();




}
