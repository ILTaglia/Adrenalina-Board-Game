package controller;

import model.Dashboard;
import model.Player;
import model.Match;
import model.Cell;

import exceptions.InvalidDirectionException;
import java.util.ArrayList;
import java.util.List;

public class Run extends Action {
    public Run() {
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


    //method for complete movement, to be decomposed in atomic movements
    public void getMovement(Match m, Player player, List<String> destination) throws InvalidDirectionException {
        if(destination.size() > 3) throw new InvalidDirectionException();
        if(isValid(m, player, player.getCel().getX(), player.getCel().getY(), destination)){
            for(String s : destination){
                this.movement(m, player, s);
            }
            return;
        }
        throw new InvalidDirectionException();
    }

    //Method to increase the number of action of the active player. It must be done because grabbing and shooting actions have in their mainly
    //methods the increase of variable Action of the active player. If setAction was in getMovement method, as shooting and grabbing can call the
    //method for players with a certain number of damages, action would be increased twice.
    public void registerMovementAction(Match m){
        m.getActivePlayer().setAction();
    }

    //method for atomic movements
    private void movement(Match m, Player player, String direction) throws InvalidDirectionException {
        int d = this.getDirection(direction);
        int x;
        int y;
        x = player.getCel().getX();
        y = player.getCel().getY();

        //player wants to go to the north
        if (d == 0) {
            x--;
            player.setCel(x, y);
        }
        //player wants to go the the east
        else if (d == 1) {
            y++;
            player.setCel(x, y);
        }
        //player wants to go the south
        else if (d == 2) {
            x++;
            player.setCel(x, y);
        }
        //player wants to go to the west
        else if (d == 3) {
            y--;
            player.setCel(x, y);
        }
    }

    //method to check the atomic validity of each movement
    public boolean atomicValidity(Dashboard map, Player player, int x, int y, int direction) {
        //player wants to go to the north
        if (direction == 0 && x > 0) {
            int actualcolor = player.getCel().inmap(map, x, y).getcolor();
            int nextcolor = player.getCel().inmap(map, x - 1, y).getcolor();

            int port = player.getCel().inmap(map, x, y).portIsPresent(0);
            if (actualcolor == nextcolor || port == 1) {
                return true;
            } else return false;
        }
        //player wants to go the the east
        else if (direction == 1 && y < 3) {
            int actualcolor = player.getCel().inmap(map, x, y).getcolor();
            int nextcolor = player.getCel().inmap(map, x, y + 1).getcolor();

            int port = player.getCel().inmap(map, x, y).portIsPresent(1);
            if (actualcolor == nextcolor || port == 1) {
                return true;
            } else return false;
        }
        //player wants to go the south
        else if (direction == 2 && x < 2) {
            int actualcolor = player.getCel().inmap(map, x, y).getcolor();
            int nextcolor = player.getCel().inmap(map, x + 1, y).getcolor();

            int port = player.getCel().inmap(map, x, y).portIsPresent(2);
            if (actualcolor == nextcolor || port == 1) {
                return true;
            } else return false;
        }
        //player wants to go to the west
        else if (direction == 3 && y > 0) {
            int actualcolor = player.getCel().inmap(map, x, y).getcolor();
            int nextcolor = player.getCel().inmap(map, x, y - 1).getcolor();

            int port = player.getCel().inmap(map, x, y).portIsPresent(3);
            if (actualcolor == nextcolor || port == 1) {
                return true;
            } else return false;
        } else return false;
    }

    //method to check the global validity of the action of movement
    public boolean isValid(Match m, Player player, int x, int y, List<String> destination) {
        if (destination.size() > 3) return false;
        Dashboard map = m.getDashboard();
        int direction;
        for(String s : destination){
            try {
                direction = this.getDirection(s);
            } catch (InvalidDirectionException e) { return false; }
            if(!this.atomicValidity(map, player, x, y, direction)){return false;}
            if(direction==0){x--;}
            if(direction==1){y++;}
            if(direction==2){x++;}
            if(direction==3){y--;}
        }
        return true;
    }
}
