package controller;

import model.TypeAttack;
import model.Weapon;

import java.util.ArrayList;

public class ListAttackCreator {
    private ArrayList<TypeAttack> list;

    public ListAttackCreator()
    {
        this.list=new ArrayList<TypeAttack>();
    }

    public ArrayList<TypeAttack> getList()
    {
        return this.list;
    }

    public ArrayList<TypeAttack> elaboratelist(Weapon weapon, int type)
    {
        this.list=new ArrayList<TypeAttack>();
        for(int i=0;i<weapon.getNumberAttack();i++)
        {
            TypeAttack attack = weapon.getAttack(i);
            if(attack.getTypePlayer()==type)
            {
                this.list.add(attack);
            }
        }
        return this.list;
    }
}
