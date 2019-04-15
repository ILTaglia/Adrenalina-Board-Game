package Controller;
import Model.Player;
import Model.Match;
import Model.Dashboard;

public class DeathAndRespawn {
    private int [] points = {8, 6, 4, 2, 1, 1};
    private int death;

    //calculatescore the points to give to players that made damages to the player
    public DeathAndRespawn(){
        this.death=0;
    }

    public void calculatescore(Match m, Player player_killed, Player player_killer, int n){
        //parameter is the killed player, and the killer
        //n is the int returned by the set_damage (if 1, just killing point, if 2, kill and revenge
        //TODO d.setKillshot_track(player_killer, n);
        //manca aggiunta segnali al tracciato mortale, devo gestirlo
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

