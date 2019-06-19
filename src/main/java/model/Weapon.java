package model;

import exceptions.WeaponAlreadyLoadedException;
import exceptions.WeaponAlreadyUsedException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
//TODO IMPORTANTE: Classe Card da Usare !
public class Weapon extends Card {
    private String name;
    private ArrayList <Integer> cost;
    private ArrayList <TypeAttack> attack;
    public void addAttack(int id, int typePlayer, int distance, int moveMe, int moveYou){
        this.attack.add(new AttackFactory().getinstanceof(id, typePlayer,distance,moveMe,moveYou));
    }
    public TypeAttack getAttack(int i){
        return this.attack.get(i);
    }
    public int getNumberAttack(){
        return this.attack.size();
    }
    public Weapon(String name, ArrayList <Integer> cost){
        this.attack =new ArrayList<>();
        this.name=name;
        this.cost=cost;
        this.used=false;
    }
    public String getName(){
        return this.name;
    }
    public ArrayList <Integer> getCost(){
        return this.cost;
    }
    public void recharge() throws WeaponAlreadyLoadedException{
        if(used){
            setAvailable();
        }
        else{
            throw new WeaponAlreadyLoadedException();
        }
    }
    public void shooted() throws WeaponAlreadyUsedException{
        if(!used){
            setUsed();
        }
        else{
            throw new WeaponAlreadyUsedException();
        }
    }
    public void addCost(int cost){
        this.cost.add(cost);
    }
    public int getNumberCost(){
        return this.cost.size();
    }
    public int getCost(int i){
        return this.cost.get(i);
    }
    public Weapon(String name){
        this.name=name;
        this.cost=new ArrayList<>();
        this.attack =new ArrayList<>();
        this.used=false;
    }

    public List<Integer> returnPrice(){
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
}
