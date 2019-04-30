package Model;

import exceptions.FullCellException;
import exceptions.InvalidColorException;
import exceptions.MaxNumberPlayerException;

import java.util.*;

public class Match {
    private int round;
    private ArrayList<Player> players;
    private Dashboard dashboard;
    private Ammo_Deck ammo_deck;
    private Weapon_Deck weapon_deck;
    private boolean check_dashboard=false;

    //i è parametro per la dashboard
    public Match(){
        this.round=1;
        this.players=new ArrayList<>();
        ammo_deck=new Ammo_Deck();
        weapon_deck=new Weapon_Deck();
    }

    public void set_round(){
        if(players.get(players.size()-1).get_action()==2) this.round++;         //TODO: lasciamo il controllo al controller e mettiamo direttamente il ++?
        //increase the number of the round just if the last player in the turn (that is the last of the array)
        //has done its second action, finished its turn
    }

    public int get_round(){return this.round;}

    public void add_player(Player player) throws MaxNumberPlayerException, InvalidColorException {
        if(players.size()==5) throw new MaxNumberPlayerException(); //max number of players in the classical mode
        for (Player p : this.players) {
            if (p.getcolor()==player.getcolor()) {
                throw new InvalidColorException();
            }
        }
        players.add(player);
    }

    //returns player by color
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

    //returns player by index to check their ID
    public Player get_player_byindex(int index){ return this.players.get(index);}

    public int get_players_size(){ return this.players.size();}

    public Dashboard get_dashboard(){return this.dashboard;}

    //TODO:Test
    //Riaggiunge la carta Ammo dopo che è stata usata
    public void Add_AmmoCard(Normal_Cell cell){//TODO:pensare a nome più efficace
        try{
            cell.Add_Ammo_Card((Ammo_Card)ammo_deck.Draw_Card());
        }catch(FullCellException e){
            //TODO
        }

    }
    public void Add_WeaponCard(SpawnPoint_Cell cell,int index){//TODO:pensare a nome più efficace
        try{
            cell.Add_Weapon_Card((Weapon)weapon_deck.Draw_Card(),index);//TODO:controllare
        }catch(FullCellException e){
            //TODO
        }

    }

    //returns all the players seen by the given player
    public ArrayList<Player> visible_players(Player player){
        int x, y, cell_color;
        x = player.get_cel().getX();
        y = player.get_cel().getY();
        Cell cell_player = this.get_dashboard().getmap(x, y); //cell of the player
        cell_color = cell_player.getcolor(); //color of the cell of the player

        ArrayList<Player> visible = new ArrayList<>();

        //adds a player if it is in the same room
        for (Player p : this.players) {
            if (!p.equals(player)) {
                //color of the cell of the other player
                int other_player_cell_color = p.get_cel().inmap(this.dashboard, p.get_cel().getX(), p.get_cel().getY()).getcolor();
                if(other_player_cell_color == cell_color) visible.add(p);
            }
        }
        //adds a player if it is in a room connected to the parameter player by a port and the parameter player is across the port
        for(int i=0; i<4; i++){
            if(cell_player.portIsPresent(i)==1){
                //north port
                if(i==0){
                    x--;
                    Cell other_cell = this.get_dashboard().getmap(x, y);
                    for (Player p : this.players) {
                        if (!p.equals(player) && !visible.contains(p)) {
                            //color of the cell of the other player
                            int other_player_color = p.get_cel().inmap(this.dashboard, p.get_cel().getX(), p.get_cel().getY()).getcolor();
                            if(other_player_color==other_cell.getcolor()) visible.add(p);
                        }
                    }
                    x++;
                }
                //east port
                else if(i==1){
                    y++;
                    Cell other_cell = this.get_dashboard().getmap(x, y);
                    for (Player p : this.players) {
                        if (!p.equals(player) && !visible.contains(p)) {
                            //color of the cell of the other player
                            int other_player_color = p.get_cel().inmap(this.dashboard, p.get_cel().getX(), p.get_cel().getY()).getcolor();
                            if(other_player_color==other_cell.getcolor()) visible.add(p);
                        }
                    }
                    y--;
                }
                //south port
                else if(i==2){
                    x++;
                    Cell other_cell = this.get_dashboard().getmap(x, y);
                    for (Player p : this.players) {
                        if (!p.equals(player) && !visible.contains(p)) {
                            //color of the cell of the other player
                            int other_player_color = p.get_cel().inmap(this.dashboard, p.get_cel().getX(), p.get_cel().getY()).getcolor();
                            if(other_player_color==other_cell.getcolor()) visible.add(p);
                        }
                    }
                    x--;
                }
                //west port
                else if(i==3){
                    y--;
                    Cell other_cell = this.get_dashboard().getmap(x, y);
                    for (Player p : this.players) {
                        if (!p.equals(player) && !visible.contains(p)) {
                            //color of the cell of the other player
                            int other_player_color = p.get_cel().inmap(this.dashboard, p.get_cel().getX(), p.get_cel().getY()).getcolor();
                            if(other_player_color==other_cell.getcolor()) visible.add(p);
                        }
                    }
                    y++;
                }
            }
        }
        return visible;
    }

    //returns the list of players in the same line of the given player
    public ArrayList<Player> same_line_players(Player player){
        ArrayList<Player> list = new ArrayList<>();
        int x = player.get_cel().getX(); //player line
        for (Player p : this.players) {
            if (!p.equals(player)) {
                int other_player_x = p.get_cel().getX();
                if(other_player_x == x) list.add(p);
            }
        }
        return list;
    }

    public ArrayList<Player> same_column_players(Player player){
        ArrayList<Player> list = new ArrayList<>();
        int y = player.get_cel().getY(); //player column
        for (Player p : this.players) {
            if (!p.equals(player)) {
                int other_player_y = p.get_cel().getY();
                if(other_player_y == y) list.add(p);
            }
        }
        return list;
    }

    public int manhattan_distance (Player player1, Player player2){
        int distance=-1;
        int x1, y1, x2, y2;
        x1 = player1.get_cel().getX();
        y1 = player1.get_cel().getY();
        x2 = player2.get_cel().getX();
        y2 = player2.get_cel().getY();
        if(x1==x2) {
            distance = Math.abs(y2-y1);
        }
        else if(y1==y2) {
            distance = Math.abs(x2-x1);
        }
        return distance;
    }
}
