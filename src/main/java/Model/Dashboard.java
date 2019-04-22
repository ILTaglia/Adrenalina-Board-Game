package Model;

import java.util.ArrayList;
import java.util.Collections;

public class Dashboard {
    private Cell[][] map;
    private int track_index;
    private int[][] killshot_track;
    private ArrayList<Integer> killshot_points;
    private ArrayList<Integer> ord;
    private boolean stop;

    public Dashboard(int i){
        map = new Cell[3][4];
        //Spawn_Point Cells
        map[0][2] = new SpawnPoint_Cell(0); //blue
        map[1][0] = new SpawnPoint_Cell(5); //red
        map[2][3] = new SpawnPoint_Cell(2); //yellow

        //i is the index of the chosen map
        //index 1 for 1° map, good for 3 or 4 players
        if(i==1) {
            map[0][0] = new Normal_Cell(0); //blue
            map[0][1] = new Normal_Cell(0); //blue
            map[0][3] = new Normal_Cell(-1); //not exists
            map[1][1] = new Normal_Cell(5); //red
            map[1][2] = new Normal_Cell(5); //red
            map[1][3] = new Normal_Cell(2); //yellow
            map[2][0] = new Normal_Cell(-1); //not exists
            map[2][1] = new Normal_Cell(4); //grey
            map[2][2] = new Normal_Cell(4); //grey
        }
        //index 2 for 2° map, good for every number of players
        if(i==2) {
            map[0][0] = new Normal_Cell(0); //blue
            map[0][1] = new Normal_Cell(0); //blue
            map[0][3] = new Normal_Cell(1); //green
            map[1][1] = new Normal_Cell(5); //red
            map[1][2] = new Normal_Cell(2); //yellow
            map[1][3] = new Normal_Cell(2); //yellow
            map[2][0] = new Normal_Cell(-1); //not exists
            map[2][1] = new Normal_Cell(4); //grey
            map[2][2] = new Normal_Cell(2); //yellow
        }
        //index 3 for 3° map, good for 4 or 5 players
        if(i==3) {
            map[0][0] = new Normal_Cell(5); //red
            map[0][1] = new Normal_Cell(0); //blue
            map[0][3] = new Normal_Cell(1); //green
            map[1][1] = new Normal_Cell(3); //pink
            map[1][2] = new Normal_Cell(2); //yellow
            map[1][3] = new Normal_Cell(2); //yellow
            map[2][0] = new Normal_Cell(4); //grey
            map[2][1] = new Normal_Cell(4); //grey
            map[2][2] = new Normal_Cell(2); //yellow
        }

        track_index=0;
        killshot_track = new int[2][8];
        killshot_points = new ArrayList<>();
        killshot_points.add(0);
        killshot_points.add(0);
        killshot_points.add(0);
        killshot_points.add(0);
        killshot_points.add(0);
        ord = new ArrayList<>();

    }

    public int get_index(){return this.track_index;}

    public void setKillshot_track(Player player, int n){
        killshot_track[0][track_index]=player.getcolor();
        //n is the int returned by the set_damage

        //m contains the number of signals a player already has in the killshot track
        int m = killshot_points.get(player.getcolor());
        killshot_points.set(player.getcolor(), n+m);
        if(!ord.contains(player.getcolor()))ord.add(player.getcolor());
        if(n==1) killshot_track[1][track_index]= -1;
        if(n==2) killshot_track[1][track_index]=player.getcolor();
        track_index=track_index+1;
        //TODO se si finisce l'array finisce la partita
        if(track_index==9) return; //end_game
    }

    public int getmaxkillshot(){
        int max = Collections.max(killshot_points);
        int k = killshot_points.indexOf(Collections.max(killshot_points));
        stop=true;
        //number of maximum killshot point
        //check if same points, the first one is the max
        for(int i=0; i<killshot_points.size()-1; i++){
            for(int j=i+1; j<killshot_points.size(); j++){
                if(max==killshot_points.get(i) && killshot_points.get(i).equals(killshot_points.get(j))){
                    if(ord.indexOf(i)<ord.indexOf(j)) k = i;
                    else k = j;
                }
            }
        }
        killshot_points.set(k, 0);
        for(int j=0; j<killshot_points.size(); j++){
            if(killshot_points.get(j)!=0){
                stop=false;
                return k;
            }
        }
        return k;
    }

    public boolean stop(){return this.stop;}
}
