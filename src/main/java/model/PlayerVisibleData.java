package model;

import java.util.HashMap;
import java.util.Map;

public class PlayerVisibleData {

    /*convenzioni, negli arraylist di interi hai per ogni indice il valore della variabile (esempio primosangue giocatore blu
    all'indice zero dell'arraylist delle firstblood. */
    //INDICE PER TUTTI è IL COLORE DEL PLAYER. VALORE IN CORRISPONDENZA QUELLO DEL PLAYER SELEZIONATO

    private Player player;
    private Dashboard dashboard;

    private Map<String,Integer> enemiesNameColor;

    private Map<Boolean,String> activePlayer;
    private Map<String,Integer> numberOfDeath;
    private Map<String,Coordinate> playersPosition;
    //Il firstBlood sarebbe evitabile, l'informazione può essere presa dalle HashMap dei danni.
    private Map<String,String> playersFirstBlood;  //number of the player that gave the damage 1 for other players

    private Map<String,Integer> damagesOfPlayer;
    private Map<String,Map> damagesOfAll;

    private Map<String,Integer> marksOfPlayer;
    private Map<String,Map> marksOfAll;

    public PlayerVisibleData(Player player){
        this.player=player;
        activePlayer=new HashMap<>();
        numberOfDeath=new HashMap<>();
        playersPosition =new HashMap<>();
        playersFirstBlood =new HashMap<>();

        damagesOfAll=new HashMap<>();

        marksOfAll=new HashMap<>();
    }
    public void setDashboard(Dashboard dashboard){
        this.dashboard=dashboard;
    }

    public void setEnemy(String playerName,int playerColor){
        this.enemiesNameColor.put(playerName,playerColor);
        this.activePlayer.put(false,playerName);
        this.numberOfDeath.put(playerName,0);
        this.playersFirstBlood.put(playerName,null);
        this.damagesOfAll.put(playerName,new HashMap<>());
        this.marksOfAll.put(playerName,new HashMap<>());
    }


    public Player getPlayer(){
        return this.player;
    }

    public Dashboard getDashboard(){
        return this.dashboard;
    }

    public Map<String,Integer> getAllPlayersColorName(){
        return this.enemiesNameColor;
    }
    public String getActivePlayer(){
        return activePlayer.get(true);
    }
    //TODO: Get in base alle necessità:
    /*
    public List<Integer> getPlayerDeath(){return this.deathOtherPlayers;}

    public List<Coordinate> getPlayerPosition(){return this.positionOtherPlayers;}

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
