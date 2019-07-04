package model;//package player_test;
import exceptions.*;

import java.io.Serializable;
import java.util.*;

public class Player implements Serializable {
    /**
     * name, id are the identifier of the player
     * active is a boolean to say if the player is active or not
     * damages is an ArrayList containing the damages given by other player (every index corresponds to a color)
     * marks is an ArrayList containing the marks given by other player (every index corresponds to a color)
     * ammo is an ArrayList containing all the owned ammos by the player
     *
     * color is an int that represents the color. Conventions:
     * 0 = blue
     * 1 = green
     * 2 = yellow
     * 3 = pink
     * 4 = grey
     *
     * cel is a coordinate that identify tha player's position
     * gun is an ArrayList for player weapons
     * pow is an ArrayList for player PowCards
     * numberOfDeath is number to show how many times the player died
     * action is the number of the action taken by the player in one turn
     * firstBlood is the number of the player that gave the damage 1
     * score is the int that represents the points of the player
     * round is the number to identify the number of turn is beeing played
     *
     * order is an ArrayList to memorize the order in which the damage tokens have been given to the player,
     * it is useful to manage play-offs.
     *
     * connected is a boolean to verify the player is connected or not
     * dead is a boolean to verify the player is dead or not
     */
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
    private int numberOfDeath;
    private int action;
    private int firstBlood;
    private int score;
    private ArrayList<Integer> order;

    private boolean connected;
    private boolean dead;
    /**
     *
     * @param name chosen by the player
     * @param color chosen by the player
     * @param id given to the player
     */
    public Player(String name, String color, String id){
        this.name=name;
        this.id=id;
        //this.active=true; //if the player is just born, it is active
        //active will return 0 when two actions are made
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

    /**
     *
     * @return name of the player
     */
    public String getName() {return this.name;}
    /**
     * @return ID of the player
     */
    public String getID() {return this.id;}
    /**
     *
     * @return color (int) of the player
     */
    public int getColor(){return this.color;}
    /**
     *
     * @return the the color of the player
     */
    public String getColorAsString(){
        String s="";
        if(color==0) return "blue";
        if(color==1) return "green";
        if(color==2) return "yellow";
        if(color==3) return "pink";
        if(color==4) return "grey";
        else return s;
    }
    /**
     *
     * @return value of active
     */
    public boolean getActive(){return this.active;}
    /**
     * Method to set active to false at the end
     */
    public void resetActive(){this.active = false;}
    /**
     * Method to set active managing turns
     */
    public void setActive(){
        //the player hasn't played its turn yet
        if(this.action==0) this.active = true;
        //the player has ended its turn
        else if(this.action==2) this.active = false;
    }
    /**
     *
     * @param c is the color of the player
     * @return number of damages by a single enemy to set the score (parameter is the color of the enemy player)
     */
    public int getNumberDamage(int c){
        if(c==this.getColor()) return -1; //not self made damages
        return damages.get(c);
    }
    /**
     *
     * @return the total amount of damages of the player
     */
    public int getTotalDamage(){
        int j=0;
        for(int h=0; h<this.damages.size(); h++){
            if(h!=this.getColor()) j=j+ getNumberDamage(h);
        }
        return j; }
    /**
     *
     * @return the color of the player that has made the maximum number of damages
     */
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
    /**
     * Method to set damages
     * @param damage is the number of damages to add
     * @param colorAttacker is the color of the player that gave the damages
     * @return 1 if just KillShot, 2 if KillShot and Revenge, 0 otherwise
     */
    public int setDamage(int damage, int colorAttacker){
        if(colorAttacker==this.getColor()) return -1;
        if(!order.contains(colorAttacker))order.add(colorAttacker);
        //command of reset
        if(damage==0){
            damages.set(colorAttacker, damage);
            return 2; }
        int j= getTotalDamage();
        if(j==0) this.firstBlood =colorAttacker;
        if(j+damage==11){
            damages.set(colorAttacker, (damages.get(colorAttacker)+damage));
            return 1; }//killshot point to player with index colorAttacker
        if(j+damage>=12) {
            int h=12-j; //possible damages to fill the board of the player
            damages.set(colorAttacker, (damages.get(colorAttacker)+h)); //extra damages are lost, a player cannot have more than 12 damages
            return 2; //killshot and revenge for player with index colorAttacker
        }
        damages.set(colorAttacker, (damages.get(colorAttacker)+damage));
        return 0;
    }
    /**
     *
     * @param colorOfWhoMarked is the color of the enemy player
     * @return number of marks given by a the enemy
     */
    public int getMarks(int colorOfWhoMarked){
        if(colorOfWhoMarked==this.getColor()) return -1; //not self made marks
        /*Attention! You could test that for every player in the position this.color() you have zero for marks and damages*/
        return marks.get(colorOfWhoMarked);
    }
    /**
     *
     * @param colorOfWhoMarked is the color of the enemy player to be reset
     */
    public void resetMarks(int colorOfWhoMarked){
        marks.set(colorOfWhoMarked,0);
    }
    /**
     * Method to set the number of marks
     * @param numberOfMarksToAdd is the number of marks to add
     * @param colorOfWhoMarks is the color of the player that gave the marks
     */
    public void setMarks(int numberOfMarksToAdd, int colorOfWhoMarks){
        if(colorOfWhoMarks==this.getColor()) throw new InvalidColorException();
        else {
            int previousvalue = marks.get(colorOfWhoMarks);
            if(previousvalue+numberOfMarksToAdd>=3) marks.set(colorOfWhoMarks, 3);
            else marks.set(colorOfWhoMarks, (previousvalue+numberOfMarksToAdd));
        }
    }
    /**
     *
     * @param color of the ammo I want to know the number
     * @return number of ammo of color c
     * @throws InvalidColorException if the color passed as parameter is not allowed
     */
    public int getAmmo(int color) {
        if(color<0 || color>2) throw new InvalidColorException();
        return (int) ammo.stream().filter(x->x.getAmmo()==color).count();
    }
    /**
     *
     * @param ammo to be added to the player
     * @throws MoreThanTreeAmmosException if the player already has three ammos of the ammo color
     */
    public void addAmmo(Ammo ammo) throws MoreThanTreeAmmosException{
        if(getAmmo(ammo.getAmmo())>=3) {
            throw new MoreThanTreeAmmosException();
        } else this.ammo.add(ammo);
    }
    /**
     *
     * @param n is the number of ammos to discard
     * @param ammo is the type of ammo (color) to discard
     * @throws NotEnoughAmmosException is the player doesn't have that number of ammos of the required type to discard
     */
    public void removeAmmo(int n, Ammo ammo) throws NotEnoughAmmosException {
        int nAmmos=this.getAmmo(ammo.getAmmo());
        if(nAmmos<n) throw new NotEnoughAmmosException();
        for(int i = 0; i< this.ammo.size(); i++) {
            if(ammo.getAmmo()== this.ammo.get(i).getAmmo()){
                this.ammo.remove(i);
                n--; }
            if(n==0) return;
        }
    }
    /**
     *
     * @param weapon to be checked
     * @return true or false checking if the weapon is present or not
     */
    public boolean weaponIspresent(Weapon weapon){
        for(Weapon weap:this.gun) {
            if (weap.equals(weapon)) return true;
        }
        return false;
    }
    /**
     *
     * @return all the weapons of the player in a List
     */
    public List<Weapon> getWeapons(){
        return this.gun;
    }
    /**
     *
     * @param index of the required weapon
     * @return the weapon in position of index
     */
    public Weapon getWeaponByIndex (int index){return this.gun.get(index);}
    /**
     *
     * @param weapon is the weapon to be added to the player's weapons
     * @throws MaxNumberofCardsException if the player already has three weapons, that is the maximum number
     */
    public void addWeapon(Weapon weapon) throws MaxNumberofCardsException{
        if(gun.size()==3) throw new MaxNumberofCardsException(); //you have to discardWeapon a weapon, cannot have more than three
        gun.add(weapon);
    }
    /**
     *
     * @return the number of owned weapons
     */
    public int getNumberWeapon(){return this.gun.size();}
    /**
     *
     * @param p to be checked
     * @return true or false checking if the PowCard is present or not
     */
    public boolean isPowPresent(PowCard p){
        for(PowCard powcard:this.pow){
            if(powcard.equals(p)) return true;
        }
        return false;
    }
    /**
     *
     * @param powCard is the PowCard to be added to the player's weapons
     * @throws MaxNumberofCardsException if the player already has three PowCards, that is the maximum number
     */
    public void addPow(PowCard powCard) throws MaxNumberofCardsException{
        if(pow.size()==3&&!dead) throw new MaxNumberofCardsException(); //discardWeapon one pow
        pow.add(powCard);
    }
    /**
     *
     * @param p is the PowCard to be removed
     * @throws ZeroCardsOwnedException if the player doesn't own any PowCard
     * @throws NotOwnedCardException if the player doesn't own that PowCard
     */
    public void removePow(PowCard p) throws ZeroCardsOwnedException, NotOwnedCardException{
        if(pow.isEmpty()) throw new ZeroCardsOwnedException(); //invalid
        if(!isPowPresent(p)) throw new NotOwnedCardException(); //you don't have it
        pow.remove(p);
    }
    /**
     *
     * @param weapon is the weapon to be removed
     * @throws ZeroCardsOwnedException if the player doesn't own any weapon
     * @throws NotOwnedCardException if the player doesn't own that weapon
     */
    public void removeWeapon(Weapon weapon) throws ZeroCardsOwnedException, NotOwnedCardException{
        if(gun.isEmpty()) throw new ZeroCardsOwnedException();
        if(!weaponIspresent(weapon)) throw new NotOwnedCardException();
        gun.remove(weapon);
    }
    //----------------------------------Metodi utili sul Client ------------------------------------------------------//
    //------------------------------remove in base all'indice---------------------------------------------------------//
    /**
     *
     * @param indexPowCard to be removed
     */
    public void removePow(int indexPowCard){        //TODO: controllare e completare con eccezioni
        pow.remove(indexPowCard);
    }
    /**
     *
     * @param indexWeaponCard to be removed
     */
    public void removeWeapon(int indexWeaponCard){      //TODO: controllare e completare con eccezioni
        gun.remove(indexWeaponCard);
    }
    /**
     *
     * @return al the PowCards owned by the player
     */
    public List<PowCard> getPows(){
        return this.pow;
    }
    /**
     *
     * @param index of the required PowCard
     * @return teh PowCard corresponding to the index
     */
    public PowCard getPowByIndex(int index){return this.pow.get(index);}
    /**
     *
     * @return the number of PowCards
     */
    public int getNumberPow(){return this.pow.size();}
    /**
     *
     * @return the Coordinate of the player (position)
     */
    public Coordinate getCel(){return this.cel;}
    /**
     *
     * @param x is the line of the coordinate
     * @param y is the column of the coordinate
     */
    public void setCel(int x, int y){cel.set(x, y);}
    /**
     *
     * @return numberOfDeath
     */
    public int getNumberOfDeath(){return this.numberOfDeath;}
    /**
     * Method to increase numberOfDeath
     */
    public void setDeath(){this.numberOfDeath +=1;}
    /**
     *
     * @param n is the value in which to set numberOfDeath to activate Friensy
     */
    public void setDeathFrienzy(int n){this.numberOfDeath =n;}
    /**
     *
     * @return number of actions taken by the player till the present moment
     */
    public int getAction(){return this.action;}
    /**
     * Method to increase the number of actions
     */
    public void setAction(){this.action+=1;}
    /**
     * Method to reset the number of actions
     */
    public void resetAction(){this.action=0;}
    /**
     *
     * @return the firstblood
     */
    public int getFirstBlood(){return this.firstBlood;}
    /**
     * Method to reset the firstblood
     */
    public void resetFirstBlood(){this.firstBlood = -1;}
    /**
     *
     * @return the score of the player
     */
    public int getScore(){return this.score;}
    /**
     * Method to add the points of a single turn to the global score
     * @param s is the score to be added
     */
    public void setScore(int s){this.score=this.score+s;}
    /**
     *
     * @param connected is true or false whether the player is connected or not
     */
    public void setConnected(boolean connected) {
        this.connected = connected;
    }
    /**
     *
     * @return the boolean value for checking connection
     */
    public boolean isConnected() {
        return connected;
    }
    /**
     *
     * @param dead is true or false whether the player is dead or not
     */
    public void setDead(boolean dead) {
        this.dead = dead;
    }
    /**
     *
     * @return the boolean value for checking death
     */
    public boolean isDead() {
        return dead;
    }
}
