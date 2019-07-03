package controller;

import model.TypeAttack;
import model.Weapon;

import java.util.ArrayList;

public class ListAttackCreator {
    private ArrayList<TypeAttack> list;

    /**
     * Create a list of attacks
     */
    public ListAttackCreator()
    {
        this.list=new ArrayList<>();
    }

    /**
     *
     * @return the list of attacks
     */
    public ArrayList<TypeAttack> getList()
    {
        return this.list;
    }

    /**
     *
     * @param weapon is the weapon
     * @param type is the type that must be equal to typeplayer
     * @return an ArrayList of the attacks with typeplayer equals to type
     */
    public ArrayList<TypeAttack> elaboratelist(Weapon weapon, int type)
    {
        this.list=new ArrayList<>();
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
