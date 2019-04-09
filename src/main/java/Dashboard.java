import exceptions.InvalidMapChoiceException;

public class Dashboard {
    private Cell[][] map;
    private int[][] killshot_track;

    public Dashboard(int i, int size) throws InvalidMapChoiceException{
        map = new Cell[3][4];
        //Spawn_Point Cells
        map[0][2] = new SpawnPoint_Cell(0); //blue
        map[1][0] = new SpawnPoint_Cell(5); //red
        map[2][3] = new SpawnPoint_Cell(2); //yellow

        //i indica il tipo di mappa scelta
        //indice 0 per mappa da 3 o 4 giocatori
        if(i==1) {
            if(size==3 || size==4){
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
            else throw new InvalidMapChoiceException();
        }

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

        if(i==3) {
            if(size==4 || size==5){
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
            else throw new InvalidMapChoiceException();
        }


        killshot_track = new int[2][8];
    }
}
