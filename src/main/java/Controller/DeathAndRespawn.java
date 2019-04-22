package Controller;
import Model.Player;
import Model.Match;
import Model.Dashboard;
import exceptions.NotExistingDashboardException;

import java.util.Collections;

public class DeathAndRespawn {
    private int [] points = {8, 6, 4, 2, 1, 1};
    private int death;

    //calculatescore the points to give to players that made damages to the player
    public DeathAndRespawn(){
        this.death=0;
    }

    //calculates score when a player dies
    public void calculatescore(Match m, Player player_killed, Player player_killer, int n) throws NotExistingDashboardException {
        //parameter is the killed player, and the killer
        //n is the int returned by the set_damage (if 1, just killing point, if 2, kill and revenge
        if(m.get_check()) m.get_dashboard().setKillshot_track(player_killer, n);
        else throw new NotExistingDashboardException();
        //adds signals to killshot track

        if(n==2) player_killer.setmarks(1, player_killed.getcolor());
        death= player_killed.get_death();
        player_killed.set_death();
        int player_color;

        int first_blood=player_killed.get_firstblood();
        m.get_player(first_blood).set_score(1);

        int flag=0;

        for(int k=0; k<5; k++){
            player_color = player_killed.getmaxdamages();
            if(death>=5) m.get_player(player_color).set_score(1);
            /*addition of the maximum number of points to the player that made
             * more damages. Use the number of death as a parameter.*/
            m.get_player(player_color).set_score(points[death]);
            death++;
            player_killed.setdamage(0, player_color);
            flag=1;
            /*in the copied list cancel the old max and assign the second score of the array points
             * to the new max element. If other players made damages flag is set back to zero and
             * the iteration is repeated, while, if no other players made damages flag rests 1 and
             * the attribution of scores is stopped.*/
            for(int h=0; h<5; h++) {
                if(player_killed.getnumberdamage(h)!=0 && h!=player_killed.getcolor()) flag=0;
            }
            if(flag==1) break;
        }
        if(m.get_dashboard().get_index()==9){
            end_game(m, m.get_dashboard());
            /*when a match ends the killshot track is full, so index is 9. The attribution of points considering the killshot
            * track is done by another method in order to better test the attribution of points (see DashboardTest, in
            * which the method end_game is used). Besides this choice makes the code more readable.*/
        }
    }

    public void end_game(Match m, Dashboard d){
        //calculates the final score considering the killshot track
        int in = 0;
        int color;
        boolean s = false;
        while(!s){
            color = d.getmaxkillshot();
            m.get_player(color).set_score(points[in]);
            in++;
            s=d.stop();
        }
    }


    public void respawn(Player player_killed){
        for(int i=0; i<5; i++) {
            if(i!=player_killed.getcolor()) player_killed.setdamage(0, i);
        }
        player_killed.reset_action();
        player_killed.reset_firstblood();
        player_killed.set_cel(-1, -1);
    }
}

