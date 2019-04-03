import java.util.ArrayList;

public class Players_list {

    private ArrayList<Player> Playerlist;
    private String n1="A", n2="B", n3="C", n4="D", n5="E", c1="blue", c2="pink", c3="grey", c4="yellow", c5="green";
    private String id="10146701";

    public Players_list(){
        this.Playerlist = new ArrayList<>();
        int i;
        //the list contains the players in the game, the first one will be the one
        //with the starting card and will be put in first position
        Player player1= new Player(n1, c1, id);
        Playerlist.add(player1);
        i = Integer.parseInt(id);
        ++i;
        id = Integer.toString(i);

        Player player2= new Player(n2, c2, id);
        Playerlist.add(player2);
        i = Integer.parseInt(id);
        ++i;
        id = Integer.toString(i);

        Player player3= new Player(n3, c3, id);
        Playerlist.add(player3);
        i = Integer.parseInt(id);
        ++i;
        id = Integer.toString(i);

        Player player4= new Player(n4, c4, id);
        Playerlist.add(player4);
        i = Integer.parseInt(id);
        ++i;
        id = Integer.toString(i);

        Player player5= new Player(n5, c5, id);
        Playerlist.add(player5);
        i = Integer.parseInt(id);
        ++i;
        id = Integer.toString(i);
    }
}
