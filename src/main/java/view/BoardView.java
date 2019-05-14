package view;

import model.*;

import controller.Action;

import java.util.List;
import java.util.Observer;

/* Abstract class that describes methods that view must have. There is a local view, and a remote view that communicates with clients. Remote View is
made to check that the data given by client is correct.*/

public abstract class BoardView implements Observer{

    /* Welcome message to start*/
    public abstract void welcomeMessage();

    /* End message to finish*/
    public abstract void endMessage();

    /*Returns the number of players*/
    public abstract int getNumberOfPlayer();

    /*Asks players the color they wants to have
     * @throws DisconnectionException //TODO
     */
    public abstract String getPlayerColor(String playercolor);

    /*Asks players the name they wants to have
     * @throws DisconnectionException //TODO
     */
    public abstract String getPlayerName(String playername);

    /*Asks players the coordinates of the cel they want to start in (spawnpoint cells)
     @throws DisconnectionException //TODO
     */
    public abstract Coordinate getPlayerStartCel();

    /*Prints to player its cards
     @throws DisconnectionException //TODO
     */
    public abstract void showPlayerCards();

    /*Card weapon up if used, covered if recharged
    * @throws DisconnectionException //TODO
    * */
    public abstract void chargedWeapon();

    /*Prints map to all players
     @throws DisconnectionException //TODO
     */
    public abstract void printMap1();

    public abstract void printMap2();

    public abstract void printMap3();

    /*Used to send the player a message @param message
     */
    public abstract void printMessage(String message);

    /*Asks player which action it wants to take
     */
    public abstract Action getaction();

    /*Asks a Weapon to client if it has too many cards*/
    public abstract Weapon getweapon();

    /*Asks a Pow to client if it has too many cards*/
    public abstract PowCard getPow();

    /*Asks client the index of card he wants to buy in a spawn point cell
     */
    public abstract int getcard();

    /*Show final score to players*/
    public abstract void showFinalScore(List<Player> finalPlayers);

    /*Show the price of weapons in the spawn point cells*/
    public abstract void getPriceofWeapons(SpawnPointCell cell, int index);

    /*Show the price of weapons to recharge*/
    public abstract void getPriceofRechargeWeapons(Weapon weapon);


    /*Used to send message to all players
     @param message
     * @throws DisconnectionException
     */
    public abstract void printMessageAtAll(String string);

    /*Prints the current state pf the player, its points, its position, its number of damages and
    number of marks from other players
     */
    public abstract void printCurrentPlayerState(Match match);

}


