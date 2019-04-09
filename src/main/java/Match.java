import exceptions.InvalidMapChoiceException;
import exceptions.MaxNumberPlayerException;

import java.util.*;

public class Match {
    private int round;
    private ArrayList<Player> players;
    private Dashboard dashboard;

    //i è parametro per la dashboard
    public Match(){
        this.round=1;
        this.players=new ArrayList<>();

    }
    public void set_round(){
        if(players.get(players.size()-1).get_action()==2) this.round++;         //TODO: lasciamo il controllo al controller e mettiamo direttamente il ++?
        //increase the number of the round just if the last player in the turn (that is the last of the array)
        //has done its second action, finished its turn
    }

    public int get_round(){return this.round;}

    public void add_player(Player player) throws MaxNumberPlayerException {
        if(players.size()==5) throw new MaxNumberPlayerException(); //max number of players in the classical mode
        players.add(player);
    }

    //i is the index of the chosen map
    public void create_dashboard(int i) throws InvalidMapChoiceException {
        if(players.size()>=3) this.dashboard=new Dashboard(i, this.players.size());
    }

}
