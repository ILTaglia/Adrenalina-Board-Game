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

    private Map<String,Coordinate> playersPosition;
    //Il firstBlood sarebbe evitabile, l'informazione può essere presa dalle HashMap dei danni.
    private Map<String,String> playersFirstBlood;  //number of the player that gave the damage 1 for other players

    private  Map<String,Integer> damagesOfPlayer;
    private Map<String,Map> damagesOfAll;

    private Map<String,Integer> marksOfPlayer;
    private Map<String,Map> marksOfAll;
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
        this.damagesOfAll.put(playerName,new HashMap<>());
        this.marksOfAll.put(playerName,new HashMap<>());
        //TODO serve la posizione dei player this.playersPosition(playerName, );
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



    /*Metodi get*/
    //public List<Coordinate> getPlayerPosition(){return this.positionOtherPlayers;}

    //TODO: Get in base alle necessità:
    /*
    public List<Integer> getPlayerDeath(){return this.deathOtherPlayers;}



    public List<Integer> getPlayerFirstblood(){return this.firstbloodOherPlayers;}

    public List<Integer> getPlayerscore(){return this.scoreOherPlayers;}

    public List<Integer> getPlayerMarks(int color){
        List<Integer> bin = new ArrayList<>();
        if(color==0){ return this.marksforPlayer1;}
        else if(color==1){ return this.marksforPlayer2;}
        else if(color==2){ return this.marksforPlayer3;}
        else if(color==3){ return this.marksforPlayer4;}
        else if(color==4){ return this.marksforPlayer5;}
        return bin;
    }
    //TODO qui serve il colore del player o l'indice per scegliete di quale stampare i valori
    public List<Integer> getPlayerDamages(int color){
        List<Integer> bin = new ArrayList<>();
        if(color==0){ return this.damagesforPlayer1;}
        else if(color==1){ return this.damagesforPlayer2;}
        else if(color==2){ return this.damagesforPlayer3;}
        else if(color==3){ return this.damagesforPlayer4;}
        else if(color==4){ return this.damagesforPlayer5;}
        return bin;
    }
    */
}
