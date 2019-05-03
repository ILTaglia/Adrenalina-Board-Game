package Controller;

import Model.Dashboard;
import Model.Player;
import Model.Match;
import exceptions.InvalidDirectionException;
import java.util.ArrayList;

public class Run extends Action {
    public Run(){
        //parameter is just the player, because the action of running is atomic, just one step
        //TODO
        /*ci si sposterà a seconda dei vincoli della mappa e a seconda dei vincoli della plancia, non ho ancora messo le pareti*/
        /* Schema dashboard per le coordinate
         *    0   1   2   3
         * 0
         * 1
         * 2
         * le righe vanno da 0 a 2, le colonne da 0 a 3, ATTENZIONE all'unica convenzione non intuitiva. Se si va a nord la coordinata viene decrementata
         * mentre se si va a sud la coordinata aumenta (x vanno da 0 a 3, y vanno da 0 a 2)
         */
    }


    //parameter passed from Game will be a String Array, each direction will be decomposed in atomic movements
    public void getMovement(Match m, Player player, ArrayList<String> destination) throws InvalidDirectionException{
        if(destination.size()>3) throw new InvalidDirectionException();
        for(int i=0; i<destination.size(); i++){
            this.movement(m, player, destination.get(i));
        }
    }
    public void movement(Match m, Player player, String direction) throws InvalidDirectionException{
            int d = -1;
            int x;
            int y;
            x = player.getCel().getX();
            y = player.getCel().getY();
            Dashboard map = m.getDashboard();

            try{
                d = this.getdirection(direction);
            }
            catch (InvalidDirectionException e) {}
            if(this.isvalid(map, player, x, y, d)){
                //player wants to go to the north
                if(d==0){
                    x--;
                    player.setCel(x, y);
                }
                //player wants to go the the east
                else if(d==1){
                    y++;
                    player.setCel(x, y);
                }
                //player wants to go the south
                else if(d==2) {
                    x++;
                    player.setCel(x, y);
                }
                //player wants to go to the west
                else if(d==3) {
                    y--;
                    player.setCel(x, y);
                }
            }
            else throw new InvalidDirectionException();
    }

    public boolean isvalid(Dashboard map, Player player, int x, int y, int direction) {
        //player wants to go to the north
        if(direction==0 && x>0){
            int actualcolor = player.getCel().inmap(map, x, y).getcolor();
            int nextcolor = player.getCel().inmap(map, x-1, y).getcolor();

            int port = player.getCel().inmap(map, x, y).portIsPresent(0);
            if(actualcolor==nextcolor || port==1){
                return true;
            }
            else return false;
        }
        //player wants to go the the east
        else if(direction==1 && y<3){
            int actualcolor = player.getCel().inmap(map, x, y).getcolor();
            int nextcolor = player.getCel().inmap(map, x, y+1).getcolor();

            int port = player.getCel().inmap(map, x, y).portIsPresent(1);
            if(actualcolor==nextcolor || port==1){
                return true;
            }
            else return false;
        }
        //player wants to go the south
        else if(direction==2 && x<2) {
            int actualcolor = player.getCel().inmap(map, x, y).getcolor();
            int nextcolor = player.getCel().inmap(map, x+1, y).getcolor();

            int port = player.getCel().inmap(map, x, y).portIsPresent(2);
            if(actualcolor==nextcolor || port==1){
                return true;
            }
            else return false;
        }
        //player wants to go to the west
        else if(direction==3 && y>0) {
            int actualcolor = player.getCel().inmap(map, x, y).getcolor();
            int nextcolor = player.getCel().inmap(map, x, y-1).getcolor();

            int port = player.getCel().inmap(map, x, y).portIsPresent(3);
            if(actualcolor==nextcolor || port==1){
                return true;
            }
            else return false;
        }
        else return false;
    }

}
