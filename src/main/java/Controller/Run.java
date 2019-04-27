package Controller;

import Model.Dashboard;
import Model.Player;
import Model.Match;
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


    public void movement(Match m, Player p, String direction) throws InvalidDirectionException{
            int d = -1;
            int x, y;
            x = p.get_cel().getX();
            y = p.get_cel().getY();
            Dashboard map = m.get_dashboard();

            try{
                d = this.get_direction(direction);
            }
            catch (InvalidDirectionException e) {}
            //player wants to go to the north
            if(d==0 && y>0){
                int actual_color = p.get_cel().inmap(map, p.get_cel().getX(), p.get_cel().getY()).getcolor();
                int next_color = p.get_cel().inmap(map, p.get_cel().getX(), (p.get_cel().getY()-1)).getcolor();

                int port = p.get_cel().inmap(map, p.get_cel().getX(), p.get_cel().getY()).portIsPresent(0);
                if(actual_color==next_color || port==1){
                    y--;
                    p.set_cel(x, y);
                }
                else throw new InvalidDirectionException();
            }
            //player wants to go the the east
            else if(d==1 && x<3){
                int actual_color = p.get_cel().inmap(map, p.get_cel().getX(), p.get_cel().getY()).getcolor();
                int next_color = p.get_cel().inmap(map, (p.get_cel().getX()+1), p.get_cel().getY()).getcolor();

                int port = p.get_cel().inmap(map, p.get_cel().getX(), p.get_cel().getY()).portIsPresent(1);
                if(actual_color==next_color || port==1){
                    x++;
                    p.set_cel(x, y);
                }
                else throw new InvalidDirectionException();
            }
            //player wants to go the south
            else if(d==2 && y<2) {
                int actual_color = p.get_cel().inmap(map, p.get_cel().getX(), p.get_cel().getY()).getcolor();
                int next_color = p.get_cel().inmap(map, p.get_cel().getX(), (p.get_cel().getY()+1)).getcolor();

                int port = p.get_cel().inmap(map, p.get_cel().getX(), p.get_cel().getY()).portIsPresent(2);
                if(actual_color==next_color || port==1){
                    y++;
                    p.set_cel(x, y);
                }
                else throw new InvalidDirectionException();
            }
            //player wants to go to the west
            else if(d==3 && x>0) {
                int actual_color = p.get_cel().inmap(map, p.get_cel().getX(), p.get_cel().getY()).getcolor();
                int next_color = p.get_cel().inmap(map, (p.get_cel().getX()-1), p.get_cel().getY()).getcolor();

                int port = p.get_cel().inmap(map, p.get_cel().getX(), p.get_cel().getY()).portIsPresent(3);
                if(actual_color==next_color || port==1){
                    x--;
                    p.set_cel(x, y);
                }
                else throw new InvalidDirectionException();
            }
            else throw new InvalidDirectionException();
    }

}
