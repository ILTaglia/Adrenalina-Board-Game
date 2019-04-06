//package player_test;
import java.util.*;

public class Player {
    private String name;
    private String id;
    private boolean active; //if 1 the player is active, if 0 the player is waiting its turn
    private ArrayList<Integer> damages; //list to sign every possible damage
    private ArrayList<Integer> marks; //list for marks given to this player by others
    private ArrayList<Integer> ammos; //list to show how many ammo for each color you have
    private int color; //player color is represented by an id integer
    private int [] cel; //signal to say if is in the map or not
    private ArrayList<Arma> gun; //list for the weapons of the player
    private ArrayList<Pow_Card> pow; //list for the power up of the player
    private int death; //number to show how many times the player died
    private int action; //number of the action taken by the player in one turn
    private int firstblood; //number of the player that gave the damage 1
    private int score; //points of the player
    private int round; //number to identify the number of turn is beeing played


    public Player(String name, String color, String id){
        this.name=name;
        this.id=id;
        this.active=true; //if the player is just born, it is active
        //active will return 0 when two actions are made
        this.round=1; //first turn identified with 1
        this.damages=new ArrayList<>();
        this.damages.add(0);
        this.damages.add(0);
        this.damages.add(0);
        this.damages.add(0);
        this.damages.add(0);

        //you can receive marks by just four players, but you have an array of 5 elements, because
        //every index represent the color of the player that gave the mark
        this.marks=new ArrayList<>();
        this.marks.add(0);
        this.marks.add(0);
        this.marks.add(0);
        this.marks.add(0);
        this.marks.add(0);

        this.ammos = new ArrayList<>();
        ammos.add(1);
        ammos.add(1);
        ammos.add(1);
        //at the beginning you have 1 ammo for each color
        //0 is red, you start with three red ammo
        //1 is blue, you start with three blue ammo
        //2 is yellow, you start with three yellow ammo

        //players color are blue(0), green(1), yellow(2), pink(3), grey(4)
        if(color.equals("blue")) this.color=0;
        else if(color.equals("green")) this.color=1;
        else if(color.equals("yellow")) this.color=2;
        else if(color.equals("pink")) this.color=3;
        else if(color.equals("grey")) this.color=4;
        else throw new IllegalArgumentException(); //invalid color

        this.gun= new ArrayList<>();
        this.pow = new ArrayList<>();

        this.cel = new int[2]; //index of the position of the player (default position is (-1, -1)
        Arrays.fill(cel, -1);
        this.death=0;
        this.action=0;
        this.score=0;
    }

    public String getname() {return this.name;}
    public String getid() {return this.id;}

    public int getcolor() {
        if(this.color>=0 && this.color<=4) return this.color;
        else throw new IllegalArgumentException();
        //error in case player has not a color yet
    }

    //return number of damages by a single enemy to set the score (parameter is the color of the enemy player)
    public int getnumberdamage(int c){
        if(c==this.getcolor()) return 0; //not self made damages
        int i=damages.get(c);
        return i;
    }

    //count the total amount of damages of the player
    public int gettotaldamage(){
        int j=0;
        for(int h=0; h<this.damages.size(); h++) j=j+getnumberdamage(h);
        return j;
    }

    //set the number of damages, parameters are the number of damages to add (n) and the color of the player that gave them (c)
    public int setdamage(int n, int c){
        if(c==this.getcolor()) return -1;
        int j=gettotaldamage();
        if(j+n==11){
            damages.set(c, (damages.get(c)+n));
            return 1; //killshot point to player with index c
        }
        if(j+n>=12) {
            int h=12-j; //possible damages to fill the board of the player
            damages.set(c, (damages.get(c)+h)); //extra damages are lost, a player cannot have more than 12 damages
            return 2; //killshot and revenge for player with index c
        }
        damages.set(c, (damages.get(c)+n));
        return 0;
    }

    //return number of marks given by a single enemy (parameter is the color of the enemy player)
    public int getmarks(int c){
        if(c==this.getcolor()) return -1; //not self made marks
        /*Attention! You could test that for every player in the position this.color() you have zero for marks and damages*/
        int i=marks.get(c);
        return i;
    }

    //set the number of marks, paramters are the number of marks to add(n) and the color of the player that gave them (c)
    public int setmarks(int n, int c){
        if(c==this.getcolor()) return -1;
        if(marks.get(c)+n>=3) marks.set(c, 3);
        else marks.set(c, (marks.get(c)+n));
        return 0;
    }

    //return number of ammos of color c
    public int get_ammo(int c){
        if(c<0 || c>3) throw new IllegalArgumentException();
        int i=ammos.get(c);
        return i;
    }

    //add number n of ammos of color c
    public int add_ammo(int n, int c){
        if(c<0 || c>3) return -1;
        if(ammos.get(c)+n>=3) ammos.set(c, 3);
        else ammos.set(c,(ammos.get(c)+n));
        return 0;
    }

    //remove number n of ammos of color c
    public int remove_ammo(int n, int c){
        if(c<0 || c>3) return -1;
        if(ammos.get(c)<n) return -2;
        ammos.set(c, (ammos.get(c)-n));
        return 0;
    }

    //return weapon passed as argument
    public int weaponIspresent(Arma weapon){
        for(int i=0; i<gun.size(); i++) {
            if (gun.get(i).equals(weapon)) return 1;
        }
        return 0;
    }

    public int add_weapon(Arma weapon){
        if(gun.size()==3) return -1; //you have to remove a weapon, cannot have more than three
        gun.add(weapon);
        return 0;
    }

    public int remove_weapon(Arma weapon){
        if(gun.size()==0) return -1;
        if(weaponIspresent(weapon)==0) return -2;
        int i=gun.indexOf(weapon);
        gun.remove(i);
        return 0;
    }

    public int powIspresent(Pow_Card p){
        for(int i=0; i<pow.size(); i++){
            if(pow.get(i).equals(p)) return 1;
        }
        return 0;
    }

    public int add_pow(Pow_Card p){
        if(pow.size()==3) return -1; //remove one pow
        pow.add(p);
        return 0;
    }

    public int remove_pow(Pow_Card p){
        if(pow.size()==0) return -1; //invalid
        if(powIspresent(p)==0) return -2; //you don't have it
        int i=pow.indexOf(p);
        pow.remove(i);
        return 0;
    }

    public int get_cel(){
        return 0;
    } //TODO in realtà ritorna oggetto CEL

    public void set_cel(int x, int y){
        cel[0]=x;
        cel[1]=y;
    }

    public int get_death(){return this.death;}

    public void set_death(){this.death+=1;}

    //return number of actions taken till this moment
    public int get_action(){return this.action;}

    public void set_action(){this.action+=1;}

    public int get_score(){return this.score;}

    //add the points of a single turn to the global score
    public void set_score(int s){
        this.score=this.score+s;
    }

}
