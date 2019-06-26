package controller;
import model.Player;
import model.Match;
import model.Dashboard;
import exceptions.NotExistingDashboardException;

import java.util.ArrayList;
import java.util.List;

public class DeathAndRespawn {
    private int [] points = {8, 6, 4, 2, 1, 1};
    private int death;

    //calculateScore the points to give to players that made damages to the player
    public DeathAndRespawn(){
        this.death=0;
    }

    //calculates score when a player dies
    public void calculateScore(Match match, Player playerKilled, Player playerKiller, int n) throws NotExistingDashboardException {
        int playerColor;
        int firstBlood;
        int flag;
        List<Integer> score=new ArrayList<>();
        //parameter is the killed player, and the killer
        //n is the int returned by the set_damage (if 1, just killing point, if 2, kill and revenge
        if(match.getCheck()){
            match.getDashboard().setKillShotTrack(playerKiller, n);
        }
        else throw new NotExistingDashboardException();
        //adds signals to killshot track

        if(n==2) playerKiller.setmarks(1, playerKilled.getColor()); //revenge mark in the killed player
        death= playerKilled.getDeath();
        playerKilled.setDeath();
        firstBlood=playerKilled.getFirstBlood();
        score.add(firstBlood, 1);
        //match.getPlayer(firstBlood).setScore(1);
        for(int k=0; k<5; k++){
            playerColor = playerKilled.getMaxDamages();
            int previousPointsAlreadyGiven = score.get(k);
            //In case of firstblood I can't just set the number of damages, I have to increase it
            if(death>=5){
                score.set(playerColor, 1+previousPointsAlreadyGiven);
                //match.getPlayer(playerColor).setScore(1);
            }
            /*addition of the maximum number of points to the player that made
             * more damages. Use the number of death as a parameter.*/
            score.set(playerColor, points[death]+previousPointsAlreadyGiven);
            //match.getPlayer(playerColor).setScore(points[death]);
            death++;
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
        match.assignScore(score);
    }

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

    public String winner(Match m){
        //returns the color of the winner player
        String id = "";
        Player player=m.getPlayerByIndex(0);
        for(int i=0; i<m.getPlayersSize(); i++){
            for(int j=i+1; j<m.getPlayersSize(); j++){
                if(player.getScore()<m.getPlayerByIndex(j).getScore()) player=m.getPlayerByIndex(j);
            }
            id=player.getID();
        }
        return id;
    }


    public void respawn(Player playerKilled){
        for(int i=0; i<5; i++) {
            if(i!=playerKilled.getColor()) playerKilled.setDamage(0, i);
        }
        playerKilled.resetAction();
        playerKilled.resetFirstBlood();
        playerKilled.setCel(-1, -1);
    }
}

