import java.util.ArrayList;
import java.util.List;
public class Weapon {
    private String name;
    private ArrayList <Integer> cost;    private int used;
    private ArrayList <Type_attack> Action;
    public Weapon(String name, ArrayList <Integer> cost)
    {
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
