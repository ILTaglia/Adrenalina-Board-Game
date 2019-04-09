package Model;

import java.util.ArrayList;

public class Weapon {
    private String name;
    private ArrayList <Integer> cost;
    private int used;
    private ArrayList <Type_attack> Attack;
    public void addAttack(int id, int typeplayer,int distance,int moveme,int moveyou)
    {
        this.Attack.add(new Attack_Factory().getinstanceof(id,typeplayer,distance,moveme,moveyou));
    }
    public Type_attack getAttack(int i)
    {
        return this.Attack.get(i);
    }
    public int getnumberattack()
    {
        return this.Attack.size();
    }
    public Weapon(String name, ArrayList <Integer> cost)
    {
        this.Attack=new ArrayList<Type_attack>();
        this.name=name;
        this.cost=cost;
        this.used=0;
    }
    public String getName()
    {
        return this.name;
    }
    public ArrayList <Integer> getCost()
    {
        return this.cost;
    }
    public int getUsed()
    {
        return this.used;
    }
    public int recharge()
    {
        if(this.used==1)
        {
            this.used=0;
            return 0;
        }
        else
            return 1;
    }
    public int shooted()
    {
        if(this.used==0)
        {
            this.used++;
            return 0;
        }
        else
        {
            return 1;
        }
    }
}
