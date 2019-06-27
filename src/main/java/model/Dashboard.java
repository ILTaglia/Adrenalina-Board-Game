package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Dashboard implements Serializable {
    private Cell[][] map;
    private int mapType; //index of the chosen map
    private int trackIndex;
    private int[][] killShotTrack;
    private ArrayList<Integer> killShotPoints;
    private ArrayList<Integer> ord;
    private boolean stop;

    public Dashboard(int i) {
        map = new Cell[3][4];
        mapType = i;

        //i is the index of the chosen map
        //index 1 for 1° map, good for 3 or 4 players
        if (i == 1) {
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
        if (i == 2) {
            map[0][0] = new NormalCell(0, 0, 0, 1, 0); //blue
            map[0][1] = new NormalCell(0, 0, 0, 0, 0); //blue
            map[0][2] = new SpawnPointCell(0, 0, 1, 1, 0); //blue
            map[0][3] = new NormalCell(1, 0, 0, 1, 1); //green
            map[1][0] = new SpawnPointCell(5, 1, 0, 0, 0); //red
            map[1][1] = new NormalCell(5, 0, 0, 1, 0); //red
            map[1][2] = new NormalCell(2, 1, 0, 0, 0); //yellow
            map[1][3] = new NormalCell(2, 1, 0, 0, 0); //yellow
            map[2][0] = new NormalCell(-1, 0, 0, 0, 0); //not exists
            map[2][1] = new NormalCell(4, 1, 1, 0, 0); //grey
            map[2][2] = new NormalCell(2, 0, 0, 0, 1); //yellow
            map[2][3] = new SpawnPointCell(2, 0, 0, 0, 0); //yellow
        }
        //index 3 for 3° map, good for 4 or 5 players
        if (i == 3) {
            map[0][0] = new NormalCell(5, 0, 1, 0, 0); //red
            map[0][1] = new NormalCell(0, 0, 0, 1, 1); //blue
            map[0][2] = new SpawnPointCell(0, 0, 1, 1, 0); //blue
            map[0][3] = new NormalCell(1, 0, 0, 1, 1); //green
            map[1][0] = new SpawnPointCell(5, 0, 0, 1, 0); //red
            map[1][1] = new NormalCell(3, 1, 0, 1, 0); //pink
            map[1][2] = new NormalCell(2, 1, 0, 0, 0); //yellow
            map[1][3] = new NormalCell(2, 1, 0, 0, 0); //yellow
            map[2][0] = new NormalCell(4, 1, 0, 0, 0); //grey
            map[2][1] = new NormalCell(4, 1, 1, 0, 0); //grey
            map[2][2] = new NormalCell(2, 0, 0, 0, 1); //yellow
            map[2][3] = new SpawnPointCell(2, 0, 0, 0, 0); //yellow
        }

        //index 4 for 4° map
        if (i == 4) {
            map[0][0] = new NormalCell(5, 0, 1, 0, 0); //red
            map[0][1] = new NormalCell(0, 0, 0, 1, 1); //blue
            map[0][2] = new SpawnPointCell(0, 0, 0, 1, 0); //blue
            map[0][3] = new NormalCell(-1, 0, 0, 0, 0); //not exists
            map[1][0] = new SpawnPointCell(5, 0, 0, 1, 0); //red
            map[1][1] = new NormalCell(3, 1, 0, 1, 0); //pink
            map[1][2] = new NormalCell(3, 1, 1, 0, 0); //pink
            map[1][3] = new NormalCell(2, 0, 0, 0, 1); //yellow
            map[2][0] = new NormalCell(4, 1, 0, 0, 0); //grey
            map[2][1] = new NormalCell(4, 1, 0, 0, 0); //grey
            map[2][2] = new NormalCell(4, 0, 1, 0, 0); //grey
            map[2][3] = new SpawnPointCell(2, 0, 0, 0, 1); //yellow
        }

        trackIndex = 0;
        killShotTrack = new int[2][8];
        killShotPoints = new ArrayList<>();
        killShotPoints.add(0);
        killShotPoints.add(0);
        killShotPoints.add(0);
        killShotPoints.add(0);
        killShotPoints.add(0);
        ord = new ArrayList<>();

    }

    public Cell getMap(int i, int j) {
        return this.map[i][j];
    }

    public int getIndex() {
        return this.trackIndex;
    }

    public int getMapType() {
        return this.mapType;
    }

    public void setKillShotTrack(Player player, int n) {
        killShotTrack[0][trackIndex] = player.getColor();
        //n is the int returned by the set_damage

        //m contains the number of signals a player already has in the killshot track
        int m = killShotPoints.get(player.getColor());
        killShotPoints.set(player.getColor(), n + m);
        if (!ord.contains(player.getColor())) ord.add(player.getColor());
        if (n == 1) killShotTrack[1][trackIndex] = -1;
        if (n == 2) killShotTrack[1][trackIndex] = player.getColor();
        trackIndex = trackIndex + 1;
        //TODO se si finisce l'array finisce la partita
        if (trackIndex == 9) return; //endgame
    }

    public int getMaxKillShot() {
        int max = Collections.max(killShotPoints);
        int k = killShotPoints.indexOf(Collections.max(killShotPoints));
        stop = true;
        //number of maximum killshot point
        //check if same points, the first one is the max
        for (int i = 0; i < killShotPoints.size() - 1; i++) {
            for (int j = i + 1; j < killShotPoints.size(); j++) {
                if (max == killShotPoints.get(i) && killShotPoints.get(i).equals(killShotPoints.get(j))) {
                    if (ord.indexOf(i) < ord.indexOf(j)) k = i;
                    else k = j;
                }
            }
        }
        killShotPoints.set(k, 0);
        for (Integer killShotPoint : killShotPoints) {
            if (killShotPoint != 0) {
                stop = false;
                return k;
            }
        }
        return k;
    }
    public int [][] getKillShotTrack()
    {
        return this.killShotTrack;
    }
    public int getRowDim() {
        return this.map.length;
    }
    public int getColDim(){
        return this.map[0].length;
    }

    public boolean stop() {
        return this.stop;
    }

    public List<Coordinate> getCells() {
        List<Coordinate> cells = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                Coordinate c = new Coordinate(i, j);
                if (map[i][j].getColor() != -1) {
                    cells.add(c);
                }
            }
        }
        return cells;
    }

}
