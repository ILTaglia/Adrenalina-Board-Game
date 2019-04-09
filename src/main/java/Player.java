//package player_test;
import exceptions.*;

import java.util.*;

public class Player {
    private String name;
    private String id;
    private boolean active; //if 1 the player is active, if 0 the player is waiting its turn
    private ArrayList<Integer> damages; //list to sign every possible damage
    private ArrayList<Integer> marks; //list for marks given to this player by others
    private ArrayList<Ammo> ammos; //list to show how many ammo for each color you have
    private int color; //player color is represented by an id integer
    private Coordinate cel; //position of the player
    private ArrayList<Weapon> gun; //list for the weapons of the player
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
        ammos.add(new Ammo(0));
        ammos.add(new Ammo(1));
        ammos.add(new Ammo(2));
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
        else throw new InvalidColorException(); //invalid color

        this.gun= new ArrayList<>();
        this.pow = new ArrayList<>();

        this.cel = new Coordinate(-1, -1); //index of the position of the player (default position is (-1, -1)
        this.death=0;
        this.action=0;
        this.score=0;
    }

    public String getname() {return this.name;}
    public String getid() {return this.id;}

    public int getcolor() throws InvalidColorException {
        if(this.color>=0 && this.color<=4) return this.color;
        else throw new InvalidColorException();
        //error in case player has not a color yet or the chosen color is not in the range
    }

    //return number of damages by a single enemy to set the score (parameter is the color of the enemy player)
    public int getnumberdamage(int c){
        if(c==this.getcolor()) return -1; //not self made damages
        int i=damages.get(c);
        return i;
    }

    //count the total amount of damages of the player
    public int gettotaldamage(){
        int j=0;
        for(int h=0; h<this.damages.size(); h++){
            if(h!=this.getcolor()) j=j+getnumberdamage(h);
        }
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
    public int get_ammo(int color) throws InvalidColorException {
        if(color<0 || color>3) throw new InvalidColorException();
        return (int) ammos.stream().filter(x->x.get_Ammo()==color).count();
    }

    //add ammo
    public void add_ammo(Ammo ammo) throws MoreThanTreeAmmosException{
        if(get_ammo(ammo.get_Ammo())>=3) {
            throw new MoreThanTreeAmmosException();
        } else ammos.add(ammo);
    }

    //remove number n of ammos of color c
    public void remove_ammo(int n, Ammo ammo) throws InvalidColorException, NotEnoughAmmosException {
        if(color<0 || color>3) throw new InvalidColorException();
        int n_ammos=this.get_ammo(ammo.get_Ammo());
        if(n_ammos<n) throw new NotEnoughAmmosException();
        for(int i=0; i<n; i++) ammos.remove(ammo);
    }

    //return weapon passed as argument
    public boolean weaponIspresent(Weapon weapon){
        for(int i=0; i<gun.size(); i++) {
            if (gun.get(i).equals(weapon)) return true;
        }
        return false;
    }

    public int add_weapon(Weapon weapon) throws MaxNumberofCardsException{
        if(gun.size()==3) throw new MaxNumberofCardsException(); //you have to remove a weapon, cannot have more than three
        gun.add(weapon);
        return 0;
    }

    public int remove_weapon(Weapon weapon) throws ZeroCardsOwnedException, NotOwnedCardException{
        if(gun.size()==0) throw new ZeroCardsOwnedException();
        if(!weaponIspresent(weapon)) throw new NotOwnedCardException();
        int i=gun.indexOf(weapon);
        gun.remove(i);
        return 0;
    }

    public boolean powIspresent(Pow_Card p){
        for(int i=0; i<pow.size(); i++){
            if(pow.get(i).equals(p)) return true;
        }
        return false;
    }

    public int add_pow(Pow_Card p) throws MaxNumberofCardsException{
        if(pow.size()==3) throw new MaxNumberofCardsException(); //remove one pow
        pow.add(p);
        return 0;
    }

    public int remove_pow(Pow_Card p) throws ZeroCardsOwnedException, NotOwnedCardException{
        if(pow.size()==0) throw new ZeroCardsOwnedException(); //invalid
        if(!powIspresent(p)) throw new NotOwnedCardException(); //you don't have it
        int i=pow.indexOf(p);
        pow.remove(i);
        return 0;
    }

    public Coordinate get_cel(){return this.cel;}

    public void set_cel(int x, int y){cel.set(x, y);}

    public int get_death(){return this.death;}

    public void set_death(){this.death+=1;}

    //return number of actions taken till this moment
    public int get_action(){return this.action;}

    public void set_action(){this.action+=1;}

    public int get_score(){return this.score;}

    //add the points of a single turn to the global score
    public void set_score(int s){this.score=this.score+s;}

}
