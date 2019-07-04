package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerVisibleData implements Serializable {
    /**
     * Class that contains all the information that must be known globally.
     *
     * Conventions on the Hash Map of int: every player has to know alla the information about the others. For example, player blue
     * (with color 0) will have the information of all damages (five elements) thta are the damages for player green, 1, the same for
     * yellow (2), and so on.
     *
     * Index is the color of the selected player
     */
    private Player player;
    private Dashboard dashboard;

    private ArrayList<Integer> playerAmmo;

    private Map<String,Integer> enemiesNameColor;
    private Map<String,Integer> numberOfDeath;

    private Map<Integer,Coordinate> playersPosition;
    //Il firstBlood sarebbe evitabile, l'informazione può essere presa dalle HashMap dei danni.
    private Map<String,String> playersFirstBlood;  //number of the player that gave the damage 1 for other players

    private Map<String,Map> damagesOfAll;               //In questa variabile salvo NomePlayer e relativa mappa dei danni
    private  Map<String,Integer> damagesOfPlayer;       //Nella mappa dei danni del SINGOLO PLAYER salvo nome di chi ha inflitto i danni e l'entità del danno inflitto

    private Map<String,Map> marksOfAll;
    private Map<String,Integer> marksOfPlayer;

    /**
     *
     * @param player is the player that is the client
     */
    public PlayerVisibleData(Player player){
        this.player=player;
        this.enemiesNameColor=new HashMap<>();
        numberOfDeath=new HashMap<>();
        playersPosition =new HashMap<>();
        playersFirstBlood =new HashMap<>();

        damagesOfAll=new HashMap<>();

        marksOfAll=new HashMap<>();
        playerAmmo=new ArrayList<>();
        for(int i=0;i<3;i++){
            playerAmmo.add(i,1);
        }
    }


    /**
     *
     * @param dashboard is the dashboard of the match
     */
    public void setDashboard(Dashboard dashboard){
        this.dashboard=dashboard;
    }
    /**
     *
     * @param playerName is the name of the player enemy
     * @param playerColor is the color (int) of the player enemy
     */
    public void setEnemy(String playerName,int playerColor){
        this.enemiesNameColor.put(playerName,playerColor);
        this.numberOfDeath.put(playerName,0);
        this.playersFirstBlood.put(playerName,null);
        this.damagesOfAll.put(playerName,null);
        this.marksOfAll.put(playerName,null);
    }
    /**
     *
     * @return the player corresponding to the client
     */
    public Player getPlayer(){
        return this.player;
    }
    /**
     *
     * @return the dashboard of the match
     */
    public Dashboard getDashboard(){
        return this.dashboard;
    }
    /**
     *
     * @return all the enemy players, with their names and color
     */
    public Map<String,Integer> getAllPlayersColorName(){
        return this.enemiesNameColor;
    }

    /**
     *
     * @param color is the color of the ammo
     * @param numberOfAmmo is the number of ammo to be added
     */
    public void setNumberOfAmmo(int color,int numberOfAmmo){
        playerAmmo.set(color,numberOfAmmo);
    }

    public void setPlayerPosition(String username, Coordinate coordinate) {
        playersPosition.put(enemiesNameColor.get(username),coordinate);
    }

    public boolean isPositionPresent(int userColor){
        return playersPosition.containsKey(userColor);
    }

    public Coordinate getPlayerPosition(int userColor){
        return playersPosition.get(userColor);
    }



    //TODO:Rimangono da fare i metodi per i danni, non utilizzabili in assenza della Shoot

}
