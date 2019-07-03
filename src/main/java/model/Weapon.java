package model;

import exceptions.WeaponAlreadyLoadedException;
import exceptions.WeaponAlreadyUsedException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
//TODO IMPORTANTE: Classe Card da Usare !
public class Weapon extends Card {
    /**
     * name of the weapon
     * price of the weapon is an ArrayList of three elements. Conventions:
     * 0 - number of required red ammos
     * 1 - number of required blue ammos
     * 2 - number of required yellow ammos
     *
     * Attack is a List containg all the type of attacks of the weapon
     */
    private String name;
    private ArrayList <Integer> cost;
    private ArrayList <TypeAttack> attack;

    /**
     *
     * @param id of the attack
     * @param typePlayer is the type of player to distinguish the attack
     * @param distance is an int that indicates the distance between the attacking player and the attacked player
     * @param moveMe is an int that indicates of how many steps the attacking player can move
     * @param moveYou is an int that indicates of how many steps the attacked player can move
     */
    public void addAttack(int id, int typePlayer, int distance, int moveMe, int moveYou){
        this.attack.add(new AttackFactory().getinstanceof(id, typePlayer,distance,moveMe,moveYou));
    }

    /**
     *
     * @param i is the index of the required attack
     * @return the attack in position i
     */
    public TypeAttack getAttack(int i){
        return this.attack.get(i);
    }

    /**
     *
     * @return the number of possible attacks
     */
    public int getNumberAttack(){
        return this.attack.size();
    }

    /**
     *
     * @param name of the weapon
     * @param cost defined with conventions written at the beginning of the class
     */
    public Weapon(String name, ArrayList <Integer> cost){
        this.attack =new ArrayList<>();
        this.name=name;
        this.cost=cost;
        this.used=false;
    }

    /**
     *
     * @return the name od the weapon
     */
    public String getName(){
        return this.name;
    }

    /**
     *
     * @return the price of the weapon
     */
    public ArrayList <Integer> getCost(){
        return this.cost;
    }

    /**
     * Method to recharge the weapon
     * @throws WeaponAlreadyLoadedException if the weapon doesn't have to be recharged
     */
    public void recharge() throws WeaponAlreadyLoadedException{
        if(used){
            setAvailable();
        }
        else{
            throw new WeaponAlreadyLoadedException();
        }
    }

    //TODO DANIELE: questo metodo serve? non è mai usato
    public void shooted() throws WeaponAlreadyUsedException{
        if(!used){
            setUsed();
        }
        else{
            throw new WeaponAlreadyUsedException();
        }
    }

    /**
     *
     * @param cost is the cost to be added for the weapon
     */
    public void addCost(int cost){
        this.cost.add(cost);
    }

    /**
     *
     * @return the number of ammos for payment. In this version the number is fixed to three, but with future developments the
     * number can become four or five, adding new ammos.
     */
    public int getNumberCost(){
        return this.cost.size();
    }

    /**
     *
     * @param i is the type of ammo (0 - red, 1 - blue, 2 - yellow)
     * @return number of required ammos of the given color
     */
    public int getCost(int i){
        return this.cost.get(i);
    }

    /**
     *
     * @param name is the name of teh weapon
     */
    public Weapon(String name){
        this.name=name;
        this.cost=new ArrayList<>();
        this.attack =new ArrayList<>();
        this.used=false;
    }

    /**
     *
     * @return teh cost to recharge a weapon
     */
    public List<Integer> getCostToRecharge(){
        List<Integer> weaponcost = new ArrayList<>();
        int numberRedAmmos=0;
        int numberBlueAmmos=0;
        int numberYellowAmmos=0;
        for(int j=0; j<this.getCost().size(); j++){
            if(this.getCost().get(j)==0) numberRedAmmos++;
            else if(this.getCost().get(j)==1) numberBlueAmmos++;
            else if(this.getCost().get(j)==2) numberYellowAmmos++;
        }
        weaponcost.add(numberRedAmmos);
        weaponcost.add(numberBlueAmmos);
        weaponcost.add(numberYellowAmmos);
        return weaponcost;
    }

    /**
     *
     * @return the cost to grab a weapon
     */
    public List<Integer> getCostToGrab(){
        List <Integer> weaponcost = getCostToRecharge();
        List <Integer> correctweaponcost = new ArrayList<Integer>();
        int freecost=getCost(0);
        int correctcost= weaponcost.get(freecost)-1;
        for(int i=0;i<3;i++)
        {
            if(i!=freecost)
            {
                correctweaponcost.add(weaponcost.get(i));
            }
            else
                correctweaponcost.add(correctcost);
        }
        return correctweaponcost;
    }
}
