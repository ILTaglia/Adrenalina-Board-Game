package model;
import model.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerVisibleData {
    Player player;
    /*convenzioni, negli arraylist di interi hai per ogni indice il valore della variabile (esempio primosangue giocatore blu
    all'indice zero dell'arraylist delle firstblood. */
    //INDICE PER TUTTI è IL COLORE DEL PLAYER. VALORE IN CORRISPONDENZA QUELLO DEL PLAYER SELEZIONATO
    private List<String> namesOtherPlayers;
    private List<Boolean> booleanActiveOtherPlayers;
    private List<Integer> deathOtherPlayers;
    private List<Coordinate> positionOtherPlayers;
    private List<Integer> firstbloodOherPlayers; //number of the player that gave the damage 1 for other players
    private List<Integer> scoreOherPlayers; //points of the other player

    private List<Integer> damagesforPlayer1;
    private List<Integer> damagesforPlayer2;
    private List<Integer> damagesforPlayer3;
    private List<Integer> damagesforPlayer4;
    private List<Integer> damagesforPlayer5;

    private List<Integer> marksforPlayer1;
    private List<Integer> marksforPlayer2;
    private List<Integer> marksforPlayer3;
    private List<Integer> marksforPlayer4;
    private List<Integer> marksforPlayer5;

    private Dashboard dashboard;



    public void setPlayer(Player pl){
        player=pl;
    }

    //TODO set per creare
    public void createDamagesStructure(){

    }

    //TODO set per prendere i dati delle informazioni

    //TODO per le get parametro passato è sempre il colore così è più comodo ritornare i valori

    public Player getSinglePlayer(){return this.player;}

    public Dashboard getSingleDashboard(){return this.dashboard;}

    public List<String> getAllPlayersName(){return this.namesOtherPlayers;}

    public List<Boolean> getbooleanActive(){return this.booleanActiveOtherPlayers;}

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
}
