import java.util.*;

public class Match {
    private int round;
    private ArrayList<Player> players;

    public Match(){
        this.round=1;
        this.players=new ArrayList<>();

    }
    public void set_round(){
        if(players.get(players.size()-1).get_action()==2) this.round=this.round+1;
        //increase the number of the round just if the last player in the turn (that is the last of the array)
        //has done its second action, finished its turn
    }

    public int get_round(){return this.round;}

    public int add_player(Player player){
        if(players.size()==5) return -1; //max number of players in the classical mode
        players.add(player);
        return 0;
    }

}
