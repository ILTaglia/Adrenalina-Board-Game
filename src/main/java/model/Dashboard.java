package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Dashboard {
    private Cell[][] map;
    private int trackindex;
    private int[][] killshottrack;
    private ArrayList<Integer> killshotpoints;
    private ArrayList<Integer> ord;
    private boolean stop;

    public Dashboard(int i){
        map = new Cell[3][4];

        //i is the index of the chosen map
        //index 1 for 1° map, good for 3 or 4 players
        if(i==1) {
            map[0][0] = new NormalCell(0, 0, 0, 1, 0); //blue
            map[0][1] = new NormalCell(0, 0, 0, 0, 0); //blue
            map[0][2] = new SpawnPointCell(0, 0, 0, 1, 0); //blue
            map[0][3] = new NormalCell(-1, 0, 0, 0, 0); //not exists
            map[1][0] = new SpawnPointCell(5, 1, 0, 0, 0); //red
            map[1][1] = new NormalCell(5, 0, 0, 1, 0); //red
            map[1][2] = new NormalCell(5, 1, 1, 0, 0); //red
            map[1][3] = new NormalCell(2, 0, 0, 0, 1); //yellow
            map[2][0] = new NormalCell(-1, 0, 0, 0, 0); //not exists
            map[2][1] = new NormalCell(4, 1, 0, 0, 0); //grey
            map[2][2] = new NormalCell(4, 0, 1, 0, 0); //grey
            map[2][3] = new SpawnPointCell(2, 0, 0, 0, 1); //yellow
        }
        //index 2 for 2° map, good for every number of players
        if(i==2) {
            map[0][0] = new NormalCell(0, 0, 0, 1, 0); //blue
            map[0][1] = new NormalCell(0, 0, 0, 0, 0); //blue
            map[0][2] = new SpawnPointCell(0, 0, 1, 1, 0); //blue
            map[0][3] = new NormalCell(1, 0, 0, 1, 1); //green
            map[1][0] = new SpawnPointCell(5, 1, 0, 0, 0); //red
            map[1][1] = new NormalCell(5, 0, 0 ,1, 0); //red
            map[1][2] = new NormalCell(2, 1, 0 ,0 ,0); //yellow
            map[1][3] = new NormalCell(2, 1, 0, 0, 0); //yellow
            map[2][0] = new NormalCell(-1, 0 , 0 , 0, 0); //not exists
            map[2][1] = new NormalCell(4, 1, 1, 0, 0); //grey
            map[2][2] = new NormalCell(2, 0 , 0, 0, 1); //yellow
            map[2][3] = new SpawnPointCell(2, 0, 0, 0, 0); //yellow
        }
        //index 3 for 3° map, good for 4 or 5 players
        if(i==3) {
            map[0][0] = new NormalCell(5, 0, 1, 0, 0); //red
            map[0][1] = new NormalCell(0, 0, 0, 1, 1); //blue
            map[0][2] = new SpawnPointCell(0, 0, 1, 1, 0); //blue
            map[0][3] = new NormalCell(1, 0, 0, 1, 1); //green
            map[1][0] = new SpawnPointCell(5, 0, 0, 1, 0); //red
            map[1][1] = new NormalCell(3,1, 0, 1, 0); //pink
            map[1][2] = new NormalCell(2, 1, 0 ,0 ,0); //yellow
            map[1][3] = new NormalCell(2, 1, 0, 0, 0); //yellow
            map[2][0] = new NormalCell(4, 1, 0, 0, 0); //grey
            map[2][1] = new NormalCell(4, 1, 1, 0, 0); //grey
            map[2][2] = new NormalCell(2, 0 , 0, 0, 1); //yellow
            map[2][3] = new SpawnPointCell(2, 0, 0, 0, 0); //yellow
        }

        trackindex =0;
        killshottrack = new int[2][8];
        killshotpoints = new ArrayList<>();
        killshotpoints.add(0);
        killshotpoints.add(0);
        killshotpoints.add(0);
        killshotpoints.add(0);
        killshotpoints.add(0);
        ord = new ArrayList<>();

    }

    public Cell getmap(int i, int j){ return this.map[i][j]; }

    public int getindex(){return this.trackindex;}

    public void setKillshottrack(Player player, int n){
        killshottrack[0][trackindex]=player.getcolor();
        //n is the int returned by the set_damage

        //m contains the number of signals a player already has in the killshot track
        int m = killshotpoints.get(player.getcolor());
        killshotpoints.set(player.getcolor(), n+m);
        if(!ord.contains(player.getcolor()))ord.add(player.getcolor());
        if(n==1) killshottrack[1][trackindex]= -1;
        if(n==2) killshottrack[1][trackindex]=player.getcolor();
        trackindex = trackindex +1;
        //TODO se si finisce l'array finisce la partita
        if(trackindex ==9) return; //endgame
    }

    public int getmaxkillshot(){
        int max = Collections.max(killshotpoints);
        int k = killshotpoints.indexOf(Collections.max(killshotpoints));
        stop=true;
        //number of maximum killshot point
        //check if same points, the first one is the max
        for(int i = 0; i< killshotpoints.size()-1; i++){
            for(int j = i+1; j< killshotpoints.size(); j++){
                if(max== killshotpoints.get(i) && killshotpoints.get(i).equals(killshotpoints.get(j))){
                    if(ord.indexOf(i)<ord.indexOf(j)) k = i;
                    else k = j;
                }
            }
        }
        killshotpoints.set(k, 0);
        for(int j = 0; j< killshotpoints.size(); j++){
            if(killshotpoints.get(j)!=0){
                stop=false;
                return k;
            }
        }
        return k;
    }

    public boolean stop(){return this.stop;}

    public List<Coordinate> getCells(){
        List<Coordinate> cells = new ArrayList<>();
        for(int i=0; i<3; i++){
            for(int j=0; j<4; j++){
                Coordinate c = new Coordinate(i,j);
                if(map[i][j].getcolor()!=-1){
                    cells.add(c);
                }
            }
        }
        return cells;
    }
}
