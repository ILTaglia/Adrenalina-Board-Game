//package player_test;
import java.util.*;

public class Player {
    private String name;
    private int id;
    private int [] damage; //array to sign every possible damage
    private int [] ammo; //array to show how many ammo for each color you have
    private int color; //player color is represented by an id integer
    private int [] marks; //array for marks given to this player by others
    private Arma [] gun; //array for the weapons of the player
    private Potenziamento [] pow; //array for the power up of the player
    private int cel; //signal to say if is in the map or not
    private int death; //number to show how many times the player died
    private int action; //number of the action taken by the player in one turn
    private int firstblood; //number of the player that gave the damage 1
    private int score; //points of the player

    public Player(String name, String color){
        this.name=name;
        this.id=00000001;
        this.damage = new int [4];
        Arrays.fill(damage, 0);
        //you can receive marks by just four players, but you have an array of 5 elements, because
        //every index represent the color of the player that gave the mark
        this.marks = new int[4];
        Arrays.fill(marks, 0);

        this.ammo = new int[3];
        Arrays.fill(ammo, 3);
        //0 is red, you start with three red ammo
        //1 is blue, you start with three blue ammo
        //2 is yellow, you start with three yellow ammo

        //players color are blue(0), green(1), yellow(2), pink(3), grey(4)
        if(color.equals("blue")) this.color=1;
        if(color.equals("green")) this.color=2;
        if(color.equals("yellow")) this.color=3;
        if(color.equals("pink")) this.color=4;
        if(color.equals("grey")) this.color=5;
        //TODO

        this.cel=0; //player is not in the game yet
        this.death=0;
        this.action=0;
        this.score=0;
    }

    public String getname() {return this.name;}
    public int getid() {return this.id;}

    public int getcolor() {
        if(this.color>=1 && this.color<=5) return this.color;
        return -1; //error in case player has not a color yet
    }
    //return number of damages by a single enemy to set the score
    public int getnumberdamage(Player enemy){
        int i=damage[enemy.getcolor()];
        return i;
    }

    //count the total amount of damages of the player
    public int gettotaldamage(){
        int j=0;
        for(int h=0; h<this.damage.length; h++) j+=j;
        return j;
    }


}
