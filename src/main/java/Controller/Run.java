package Controller;

import Model.Player;
import Model.Coordinate;
import exceptions.InvalidDirectionException;

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
         * le x vanno da 0 a 3, le y da 0 a 2, ATTENZIONE all'unica convenzione non intuitiva. Se si va a nord la coordinata viene decrementata
         * mentre se si va a sud la coordinata aumenta
         */
    }


    public void movement(Player p, String direction) throws InvalidDirectionException{
            int d = -1;
            int x, y;
            x = p.get_cel().getX();
            y = p.get_cel().getY();

            try{
                d = this.get_direction(direction);
            }
            catch (InvalidDirectionException e) {}
            if(d==0 && y>0){
                y--;
                p.set_cel(x, y);
            }
            else if(d==1 && x<3){
                x++;
                p.set_cel(x, y);
            }
            else if(d==2 && y<2) {
                y++;
                p.set_cel(x, y);
            }
            else if(d==3 && x>0) {
                x--;
                p.set_cel(x, y);
            }
            else throw new InvalidDirectionException();
    }

}
