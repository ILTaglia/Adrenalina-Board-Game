package controller;
import model.Player;
import model.Match;
import model.Dashboard;

import java.util.ArrayList;
import java.util.List;

public class DeathAndRespawn {
    /**
     * points is an ArrayList of all the assignable score
     * numberOfPlayerDeath is an int that represents how many times the player killed has died.
     * This is useful to know which is the highest assignable score.
     */
    private int [] points = {8, 6, 4, 2, 1, 1};
    private int numberOfPlayerDeath;

    public DeathAndRespawn(){
        this.numberOfPlayerDeath =0;
    }

    /**
     * Method to calculate the points to give to players that made damages to the killed player (called when a player dies
     * @param match is teh match
     * @param playerKiller is the player that kills
     * @param playerKilled is the killed player
     * @param withRevenge if just KillShot the boolean is false, if KillShoot and Revenge boolean is true
     */
    public void calculateScore(Match match, Player playerKiller,Player playerKilled, boolean withRevenge) {
        int playerColor;
        int firstBlood;
        int flag;
        List<Integer> score=new ArrayList<>();
        score.add(0, 0);
        score.add(1, 0);
        score.add(2, 0);
        score.add(3, 0);
        score.add(4, 0);
        match.playerDeath(playerKiller,playerKilled,withRevenge);

        numberOfPlayerDeath = playerKilled.getNumberOfDeath();
        playerKilled.setDeath();
        firstBlood=playerKilled.getFirstBlood();
        score.set(firstBlood, 1);
        //first assignemnt for firstblood
        match.assignScore(score, playerKilled);
        for(int k=0; k<5; k++){
            playerColor = playerKilled.getMaxDamages();
            //In case of firstblood I can't just set the number of damages, I have to increase it
            if(numberOfPlayerDeath >=5){
                score.set(playerColor, 1);
            }
            /*addition of the maximum number of points to the player that made
             * more damages. Use the number of numberOfPlayerDeath as a parameter.*/
            score.set(playerColor, points[numberOfPlayerDeath]);
            numberOfPlayerDeath++;
            playerKilled.setDamage(0, playerColor);
            flag=1;
            /* in the copied list cancel the old max and assign the second score of the array points
             * to the new max element. If other players made damages flag is set back to zero and
             * the iteration is repeated, while, if no other players made damages flag rests 1 and
             * the attribution of scores is stopped.*/
            for(int h=0; h<5; h++) {
                if(playerKilled.getNumberDamage(h)!=0 && h!=playerKilled.getColor()) flag=0;
            }
            if(flag==1) break;
        }
        match.assignScore(score,playerKilled);
    }

    /**
     * Method to assign the score of the KillShoot Track at the end of the game
     * @param m is the match
     * @param d is the dashboard
     */
    public void endgame(Match m, Dashboard d){
        //calculates the final score considering the killshot track
        int in = 0;
        int color;
        boolean s = false;
        while(!s){
            color = d.getMaxKillShot();
            m.getPlayer(color).setScore(points[in]);
            in++;
            s=d.stop();
        }
    }
    /**
     *
     * @param m is the match
     * @return the ID of the winning player
     */
    public String winner(Match m){
        String id = "";
        //Check of all the connected players
        List<Player> connectedPlayers = new ArrayList<>();
        for(Player pl:m.getPlayers()){
            if(pl.isConnected()){connectedPlayers.add(pl);}
        }
        //chooses the first player that is also connected
        Player player=connectedPlayers.get(0);
        for(int i=0; i<connectedPlayers.size(); i++){
            for(int j=i+1; j<connectedPlayers.size(); j++){
                if(player.getScore()<connectedPlayers.get(j).getScore()) player=connectedPlayers.get(j);
            }
            id=player.getID();
        }
        return id;
    }
    /**
     * Method to respawn for dead players
     * @param playerKilled is the dead player
     */
    public void respawn(Player playerKilled){
        for(int i=0; i<5; i++) {
            if(i!=playerKilled.getColor()) playerKilled.setDamage(0, i);
        }
        playerKilled.resetAction();
        playerKilled.resetFirstBlood();
        playerKilled.setCel(-1, -1);
    }
}

