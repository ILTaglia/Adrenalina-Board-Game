package model;//package player_test;
import exceptions.*;

import java.io.Serializable;
import java.util.*;

public class Player implements Serializable {
    private String name;
    private String id;
    private boolean active; //if true the player is active, if false the player is waiting its turn
    private ArrayList<Integer> damages; //list to sign every possible damage
    private ArrayList<Integer> marks; //list for marks given to this player by others
    private ArrayList<Ammo> ammo; //list to show how many ammo for each color you have
    private int color; //player color is represented by an id integer
    private Coordinate cel; //position of the player
    private ArrayList<Weapon> gun; //list for the weapons of the player
    private ArrayList<PowCard> pow; //list for the power up of the player
    private int numberOfDeath; //number to show how many times the player died
    private int action; //number of the action taken by the player in one turn
    private int firstBlood; //number of the player that gave the damage 1
    private int score; //points of the player
    private int round; //number to identify the number of turn is beeing played
    private ArrayList<Integer> order;

    private boolean connected;
    private boolean dead;


    public Player(String name, String color, String id){
        this.name=name;
        this.id=id;
        //this.active=true; //if the player is just born, it is active
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

        this.ammo = new ArrayList<>();
        ammo.add(new Ammo(0));
        ammo.add(new Ammo(1));
        ammo.add(new Ammo(2));
        //at the beginning you have 1 ammo for each color
        //0 is red, you start with one red ammo
        //1 is blue, you start with one blue ammo
        //2 is yellow, you start with one yellow ammo

        //players color are blue(0), green(1), yellow(2), pink(3), grey(4)
        switch (color) {
            case "Blue":
                this.color = 0;
                break;
            case "Green":
                this.color = 1;
                break;
            case "Yellow":
                this.color = 2;
                break;
            case "Pink":
                this.color = 3;
                break;
            case "Grey":
                this.color = 4;
                break;
            default:
                throw new InvalidColorException(); //invalid color
        }

        this.gun= new ArrayList<>();
        this.pow = new ArrayList<>();

        this.cel = new Coordinate(-1, -1); //index of the position of the player (default position is (-1, -1)
        this.numberOfDeath =0;
        this.firstBlood = -1;
        this.action=0;
        this.score=0;
        this.order= new ArrayList<>();
    }

    public String getName() {return this.name;}
    public String getID() {return this.id;}

    public int getColor(){return this.color;}

    public boolean getActive(){return this.active;}

    public void resetActive(){this.active = false;}

    public void setActive(){
        //the player hasn't played its turn yet
        if(this.action==0) this.active = true;
        //the player has ended its turn
        else if(this.action==2) this.active = false;
    }

    //return number of damages by a single enemy to set the score (parameter is the color of the enemy player)
    public int getNumberDamage(int c){
        if(c==this.getColor()) return -1; //not self made damages
        return damages.get(c);
    }

    //count the total amount of damages of the player
    public int getTotalDamage(){
        int j=0;
        for(int h=0; h<this.damages.size(); h++){
            if(h!=this.getColor()) j=j+ getNumberDamage(h);
        }
        return j;
    }

    public int getMaxDamages(){
        int max = Collections.max(damages);
        int k = damages.indexOf(Collections.max(damages));
        //number of maximum damages
        for(int i=0; i<damages.size()-1; i++){
            for(int j=i+1; j<damages.size(); j++){
                if(max==damages.get(i) && damages.get(i).equals(damages.get(j))){
                    if(order.indexOf(i)<order.indexOf(j)) k = i;
                    else k = j;
                }
            }
        }
        return k;
    }

    //set the number of damages, parameters are the number of damages to add (damage) and the color of the player that gave them (colorAttacker)
    public int setDamage(int damage, int colorAttacker){
        if(colorAttacker==this.getColor()) return -1;
        if(!order.contains(colorAttacker))order.add(colorAttacker);
        //command of reset
        if(damage==0){
            damages.set(colorAttacker, damage);
            return 2;
        }
        int j= getTotalDamage();
        if(j==0) this.firstBlood =colorAttacker;
        if(j+damage==11){
            damages.set(colorAttacker, (damages.get(colorAttacker)+damage));
            return 1; //killshot point to player with index colorAttacker
        }
        if(j+damage>=12) {
            int h=12-j; //possible damages to fill the board of the player
            damages.set(colorAttacker, (damages.get(colorAttacker)+h)); //extra damages are lost, a player cannot have more than 12 damages
            return 2; //killshot and revenge for player with index colorAttacker
        }
        damages.set(colorAttacker, (damages.get(colorAttacker)+damage));
        return 0;
    }

    //return number of marks given by a single enemy (parameter is the color of the enemy player)
    public int getMarks(int colorOfWhoMarked){
        if(colorOfWhoMarked==this.getColor()) return -1; //not self made marks
        /*Attention! You could test that for every player in the position this.color() you have zero for marks and damages*/
        int numberOfMarks=marks.get(colorOfWhoMarked);
        marks.set(colorOfWhoMarked,0);
        return numberOfMarks;
    }

    //set the number of marks, parameters are the number of marks to add(numberOfMarksToAdd) and the color of the player that gave them (colorOfWhoMarks)
    public void setMarks(int numberOfMarksToAdd, int colorOfWhoMarks){
        if(colorOfWhoMarks==this.getColor()) throw new InvalidColorException();
        else if(marks.get(colorOfWhoMarks)+numberOfMarksToAdd>=3) marks.set(colorOfWhoMarks, 3);
        else marks.set(colorOfWhoMarks, (marks.get(colorOfWhoMarks)+numberOfMarksToAdd));
    }

    //return number of ammo of color c
    public int getAmmo(int color) throws InvalidColorException {
        if(color<0 || color>2) throw new InvalidColorException();
        return (int) ammo.stream().filter(x->x.getAmmo()==color).count();
    }

    //add ammo
    public void addAmmo(Ammo ammo) throws MoreThanTreeAmmosException{
        if(getAmmo(ammo.getAmmo())>=3) {
            throw new MoreThanTreeAmmosException();
        } else this.ammo.add(ammo);
    }

    //discardWeapon number n of ammo passed as parameter
    public void removeAmmo(int n, Ammo ammo) throws NotEnoughAmmosException {
        int nAmmos=this.getAmmo(ammo.getAmmo());
        if(nAmmos<n) throw new NotEnoughAmmosException();
        for(int i = 0; i< this.ammo.size(); i++) {
            if(ammo.getAmmo()== this.ammo.get(i).getAmmo()){
                this.ammo.remove(i);
                n--;
            }
            if(n==0) return;
        }
    }

    //return weapon passed as argument
    public boolean weaponIspresent(Weapon weapon){
        for(Weapon weap:this.gun) {
            if (weap.equals(weapon)) return true;
        }
        return false;
    }

    public List<Weapon> getWeapons(){
        List<Weapon> list = (List)this.gun.clone();
        return list;
    }

    public Weapon getWeaponByIndex (int index){return this.gun.get(index);}

    public void addWeapon(Weapon weapon) throws MaxNumberofCardsException{
        if(gun.size()==3) throw new MaxNumberofCardsException(); //you have to discardWeapon a weapon, cannot have more than three
        gun.add(weapon);
    }



    public int getNumberWeapon(){return this.gun.size();}

    public boolean isPowPresent(PowCard p){
        for(PowCard powcard:this.pow){
            if(powcard.equals(p)) return true;
        }
        return false;
    }

    public void addPow(PowCard p) throws MaxNumberofCardsException{
        if(pow.size()==3) throw new MaxNumberofCardsException(); //discardWeapon one pow
        pow.add(p);
    }

    public void removePow(PowCard p) throws ZeroCardsOwnedException, NotOwnedCardException{
        if(pow.isEmpty()) throw new ZeroCardsOwnedException(); //invalid
        if(!isPowPresent(p)) throw new NotOwnedCardException(); //you don't have it
        pow.remove(p);
    }
    public void removeWeapon(Weapon weapon) throws ZeroCardsOwnedException, NotOwnedCardException{
        if(gun.isEmpty()) throw new ZeroCardsOwnedException();
        if(!weaponIspresent(weapon)) throw new NotOwnedCardException();
        gun.remove(weapon);
    }
    //----------------------------------Metodi utili sul Client ------------------------------------------------------//
    //------------------------------remove in base all'indice---------------------------------------------------------//
    public void removePow(int indexPowCard){        //TODO: controllare e completare con eccezioni
        pow.remove(indexPowCard);
    }
    public void removeWeapon(int indexWeaponCard){      //TODO: controllare e completare con eccezioni
        gun.remove(indexWeaponCard);
    }

    public List<PowCard> getPows(){
        List list = (List)this.pow.clone();     //TODO: perchè usi clone?
        return list;
    }

    public PowCard getPowByIndex(int index){return this.pow.get(index);}

    public int getNumberPow(){return this.pow.size();}

    public Coordinate getCel(){return this.cel;}

    public void setCel(int x, int y){cel.set(x, y);}

    public int getNumberOfDeath(){return this.numberOfDeath;}

    public void setDeath(){this.numberOfDeath +=1;}

    public void setDeathFrienzy(int n){this.numberOfDeath =n;}

    //return number of actions taken till this moment
    public int getAction(){return this.action;}

    public void setAction(){this.action+=1;}

    public void resetAction(){this.action=0;}

    public int getFirstBlood(){return this.firstBlood;}

    public void resetFirstBlood(){this.firstBlood = -1;}

    public int getScore(){return this.score;}

    //add the points of a single turn to the global score
    public void setScore(int s){this.score=this.score+s;}

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
    public boolean isConnected() {
        return connected;
    }


    public void setDead(boolean dead) {
        this.dead = dead;
    }
    public boolean isDead() {
        return dead;
    }



}
