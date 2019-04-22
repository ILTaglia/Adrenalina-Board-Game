package Model;

import exceptions.InvalidColorException;
import exceptions.MaxNumberPlayerException;

import java.util.*;

public class Match {
    private int round;
    private ArrayList<Player> players;
    private Dashboard dashboard;
    private Ammo_Deck ammo_deck;
    private boolean check_dashboard=false;

    //i è parametro per la dashboard
    public Match(){
        this.round=1;
        this.players=new ArrayList<>();
        ammo_deck=new Ammo_Deck();
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

    public Player get_player(int color) throws InvalidColorException {
        for (Player p : this.players) {
            if (p.getcolor()==color) {
                return p;
            }
        }
        throw new InvalidColorException();
    }

    //i is the index of the chosen map
    public int create_dashboard(int i){
        if(players.size()>=3) {
            this.dashboard=new Dashboard(i);
            this.check_dashboard=true;
            return 0;
        }
        return 1;
    }

    public boolean get_check(){
        return this.check_dashboard;
    }

    public Dashboard get_dashboard(){return this.dashboard;}
}
