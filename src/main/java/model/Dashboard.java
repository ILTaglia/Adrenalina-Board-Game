package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Dashboard implements Serializable {
    /**
     * map is a matrix 3 x 4. Considering the conventions used, we have x as the number of line (o<=x<=2) and
     * y as the number of column (o<=y<=3)
     *    0   1   2   3
     * 0
     * 1
     * 2
     *
     * mapType is an index representing the number of chosen map, there are three possible choices (1, 2, 3, 4)
     * trackIndex is an int representing the index of the KillShot Track, the position of the last added skull.
     * It is useful to understand when game is over, and no more skulls can be assigned.
     * numberOfSkulls is an int representing the length of the KillShot Track, there are different possible choices in the game
     *
     * killShotTrack is a matrix of size 2 x numberOfSkulls that contains int that represents the colors of the players
     * that have killed someone. If the point is just for killshot, one int is added, if the point is for killshot and
     * revenge, two int are added.
     *
     * killShotPoints is an ArrayList containing all the possible points to be given when calculating score
     * ord is an ArrayList used to memorize the order in which the colors are added to the killshot track, it is useful
     * to manage play-offs
     * stop is a boolean that indicates when the killshot track is full
     */
    private Cell[][] map;
    private int mapType; //index of the chosen map
    private int trackIndex;
    private int numberOfSkulls;
    private int[][] killShotTrack;
    private ArrayList<Integer> killShotPoints;
    private ArrayList<Integer> ord;
    private boolean stop;
    /**
     *
     * @param i is the index of the chosen map
     * @param numberOfSkulls is the length of the KillSHot Track
     */
    public Dashboard(int i,int numberOfSkulls) {
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
        this.numberOfSkulls=numberOfSkulls;
        trackIndex = 0;
        killShotTrack = new int[2][numberOfSkulls];
        killShotPoints = new ArrayList<>();
        killShotPoints.add(0);
        killShotPoints.add(0);
        killShotPoints.add(0);
        killShotPoints.add(0);
        killShotPoints.add(0);
        ord = new ArrayList<>();
    }
    /**
     * Method to have a Cell
     * @param i is number of line
     * @param j is number of oolumn
     * @return the Cell at line i, column j
     */
    public Cell getMap(int i, int j) {
        return this.map[i][j];
    }
    /**
     *
     * @return the trackIndex, the index of the last added element in the KillShot Track
     */
    public int getIndex() {
        return this.trackIndex;
    }
    /**
     *
     * @return the type of the chosen map
     */
    public int getMapType() {
        return this.mapType;
    }
    /**
     * Method to set the elements of the KillShot Track
     * @param player is the killer, the player that has killed an other player
     * @param n is the number of damages he has done
     *          1 means just killshot
     *          2 means killshot and revenge
     */
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
    }
    /**
     *
     * @return a boolean to say if the KillShot Track is full or not
     */
    public boolean isKillShotTrackFull(){
        return trackIndex > numberOfSkulls;
    }
    /**
     *
     * @return the color of the player that has the maximum number of damage tokens
     *
     * The check with order is done to manage play-offs. In case two players have the same number of damage tokens,
     * the maximum score goes to the first one that has the token in the KillShot Track
     */
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
    /**
     *
     * @return the matrix of the KillShot Track
     */
    public int [][] getKillShotTrack()
    {
        return this.killShotTrack;
    }
    /**
     *
     * @return the total number of lines
     */
    public int getRowDim() {
        return this.map.length;
    }
    /**
     *
     * @return the total number of columns
     */
    public int getColDim(){
        return this.map[0].length;
    }
    /**
     *
     * @return the boolean stop
     */
    public boolean stop() {
        return this.stop;
    }
    /**
     *
     * @return all the cells contained in the Dashboard in an ArrayList
     */
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
