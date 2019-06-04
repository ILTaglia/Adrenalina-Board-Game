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

    //calculatescore the points to give to players that made damages to the player
    public DeathAndRespawn(){
        this.death=0;
    }

    //calculates score when a player dies
    public void calculatescore(Match m, Player playerkilled, Player playerkiller, int n) throws NotExistingDashboardException {
        //parameter is the killed player, and the killer
        //n is the int returned by the set_damage (if 1, just killing point, if 2, kill and revenge
        if(m.getCheck()) m.getDashboard().setKillshottrack(playerkiller, n);
        else throw new NotExistingDashboardException();
        //adds signals to killshot track

        if(n==2) playerkiller.setmarks(1, playerkilled.getcolor()); //revenge mark in the killed player
        death= playerkilled.getDeath();
        playerkilled.setDeath();
        int playercolor;

        int firstblood=playerkilled.getFirstblood();
        m.getPlayer(firstblood).setScore(1);

        int flag=0;

        for(int k=0; k<5; k++){
            playercolor = playerkilled.getmaxdamages();
            if(death>=5) m.getPlayer(playercolor).setScore(1);
            /*addition of the maximum number of points to the player that made
             * more damages. Use the number of death as a parameter.*/
            m.getPlayer(playercolor).setScore(points[death]);
            death++;
            playerkilled.setdamage(0, playercolor);
            flag=1;
            /* in the copied list cancel the old max and assign the second score of the array points
             * to the new max element. If other players made damages flag is set back to zero and
             * the iteration is repeated, while, if no other players made damages flag rests 1 and
             * the attribution of scores is stopped.*/
            for(int h=0; h<5; h++) {
                if(playerkilled.getnumberdamage(h)!=0 && h!=playerkilled.getcolor()) flag=0;
            }
            if(flag==1) break;
        }
        if(m.getDashboard().getindex()==9){
            endgame(m, m.getDashboard());
            /*when a match ends the killshot track is full, so index is 9. The attribution of points considering the killshot
            * track is done by another method in order to better test the attribution of points (see DashboardTest, in
            * which the method endgame is used). Besides this choice makes the code more readable.*/
        }
    }

    public void endgame(Match m, Dashboard d){
        //calculates the final score considering the killshot track
        int in = 0;
        int color;
        boolean s = false;
        while(!s){
            color = d.getmaxkillshot();
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
            id=player.getid();
        }
        return id;
    }


    public void respawn(Player playerkilled){
        for(int i=0; i<5; i++) {
            if(i!=playerkilled.getcolor()) playerkilled.setdamage(0, i);
        }
        playerkilled.resetAction();
        playerkilled.resetFirstblood();
        playerkilled.setCel(-1, -1);
    }
}

