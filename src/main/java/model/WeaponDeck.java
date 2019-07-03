package model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class WeaponDeck extends Deck{
    /**
     * List of weapons
     */
    private List<Weapon> W;

    //TODO IMPORTANTE: Classe Deck da Usare !
    /**
     * stack is the ArrayList containing the cards to be collected.
     * stackDiscarded is the ArrayList containing the cards already used and discarded.
     *
     * At the beginning stack is full of all weapons, while stackDiscarded is empty.
     */
    public WeaponDeck(){
        stack =new ArrayList<>();
        stackDiscarded =new ArrayList<>();

        ArrayList <Integer> list;

    }

    /**
     *
     * @param filename is to add weapons read by file
     */
    public void setWeapons(String filename)
    {
        this.stack =(ArrayList)this.readWeaponCards( filename);
    }

    /**
     *
     * @param fileName for reading weapons by file
     * @return the list of card created reading by file
     */
    public List<Card> readWeaponCards(String fileName)
    {
        List<Weapon> weaponArrayList;
        weaponArrayList=new ArrayList<>();
        List<Card> cards=new ArrayList<>();
        String stringa=new String();
        int CurrentState=1;
        int cont=0;
        int contextra=0;
        try (FileReader file = new FileReader(fileName+".mdr")) {
            int next;
            do {
                next=file.read();
                char nextc=(char)next;
                switch(CurrentState)
                {
                    case 1:
                    {

                        if(next!=-1)
                        {

                            if(nextc!=' ')
                            {
                                stringa=stringa+nextc;
                            }
                            else
                            {
                                weaponArrayList.add(new Weapon(stringa));
                                stringa=new String();
                                CurrentState=2;
                            }
                        }
                        break;
                    }
                    case 2:
                    {
                        if(nextc!=' ')
                        {
                            weaponArrayList.get(weaponArrayList.size()-1).addCost(Character.getNumericValue(nextc));
                        }
                        else
                        if(nextc==' ')
                        {
                            CurrentState=3;
                            //cont++;
                        }
                        break;
                    }
                    case 3:
                    {
                        if(nextc=='f')
                        {
                            weaponArrayList.get(weaponArrayList.size()-1).addAttack(1,0,0,0,0);
                        }
                        else
                        if(nextc=='u')
                        {
                            weaponArrayList.get(weaponArrayList.size()-1).addAttack(2,0,0,0,0);
                        }
                        else
                        if(nextc=='m')
                        {
                            weaponArrayList.get(weaponArrayList.size()-1).addAttack(3,0,0,0,0);
                        }
                        else
                        if(nextc=='c')
                        {
                            weaponArrayList.get(weaponArrayList.size()-1).addAttack(4,0,0,0,0);
                        }
                        else
                        if(nextc=='n')
                        {
                            weaponArrayList.get(weaponArrayList.size()-1).addAttack(5,0,0,0,0);
                        }
                        else
                        if(nextc=='w')
                        {
                            weaponArrayList.get(weaponArrayList.size()-1).addAttack(6,0,0,0,0);
                        }
                        else
                        if(nextc=='a')
                        {
                            weaponArrayList.get(weaponArrayList.size()-1).addAttack(7,0,0,0,0);
                        }
                        else
                        if(nextc=='k')
                        {
                            weaponArrayList.get(weaponArrayList.size()-1).addAttack(8,0,0,0,0);
                        }
                        else
                        if(nextc=='r')
                        {
                            weaponArrayList.get(weaponArrayList.size()-1).addAttack(9,0,0,0,0);
                        }
                        else
                        if(nextc=='t')
                        {
                            weaponArrayList.get(weaponArrayList.size()-1).addAttack(10,0,0,0,0);
                        }
                        else
                        if(nextc=='l')
                        {
                            weaponArrayList.get(weaponArrayList.size()-1).addAttack(11,0,0,0,0);
                        }
                        else
                        if(nextc=='o')
                        {
                            weaponArrayList.get(weaponArrayList.size()-1).addAttack(12,0,0,0,0);
                        }

                        CurrentState=4;
                        break;
                    }
                    case 4:
                    {
                        weaponArrayList.get(weaponArrayList.size()-1).getAttack(weaponArrayList.get(weaponArrayList.size()-1).getNumberAttack()-1).addExtra(Character.getNumericValue(nextc));
                        contextra++;
                        if(contextra==3)
                        {
                            CurrentState=5;
                            contextra=0;
                        }
                        break;
                    }
                    case 5:
                    {
                        weaponArrayList.get(weaponArrayList.size()-1).getAttack(weaponArrayList.get(weaponArrayList.size()-1).getNumberAttack()-1).setTypePlayer(Character.getNumericValue(nextc));
                        CurrentState=6;
                        break;
                    }
                    case 6:
                    {
                        weaponArrayList.get(weaponArrayList.size()-1).getAttack(weaponArrayList.get(weaponArrayList.size()-1).getNumberAttack()-1).setDistance(Character.getNumericValue(nextc));
                        CurrentState=7;
                        break;
                    }
                    case 7:
                    {
                        weaponArrayList.get(weaponArrayList.size()-1).getAttack(weaponArrayList.get(weaponArrayList.size()-1).getNumberAttack()-1).setMoveMe(Character.getNumericValue(nextc));
                        CurrentState=8;
                        break;
                    }
                    case 8:
                    {
                        weaponArrayList.get(weaponArrayList.size()-1).getAttack(weaponArrayList.get(weaponArrayList.size()-1).getNumberAttack()-1).setMoveYou(Character.getNumericValue(nextc));
                        CurrentState=9;
                        break;
                    }
                    case 9:
                    {
                        if(nextc=='p')
                        {
                            weaponArrayList.get(weaponArrayList.size()-1).getAttack(weaponArrayList.get(weaponArrayList.size()-1).getNumberAttack()-1).addEffect(new EffectFactory().getinstanceof(1,0));
                        }
                        else
                        {
                            weaponArrayList.get(weaponArrayList.size()-1).getAttack(weaponArrayList.get(weaponArrayList.size()-1).getNumberAttack()-1).addEffect(new EffectFactory().getinstanceof(2,0));
                        }
                        CurrentState=10;
                        break;
                    }
                    case 10:
                    {
                        weaponArrayList.get(weaponArrayList.size()-1).getAttack(weaponArrayList.get(weaponArrayList.size()-1).getNumberAttack()-1).getEffect(weaponArrayList.get(weaponArrayList.size()-1).getAttack(weaponArrayList.get(weaponArrayList.size()-1).getNumberAttack()-1).getNumberEffect()-1).setId(Character.getNumericValue(nextc));
                        CurrentState=11;
                        break;
                    }
                    case 11:
                    {
                        if(nextc=='l')
                        {
                            weaponArrayList.get(weaponArrayList.size()-1).getAttack(weaponArrayList.get(weaponArrayList.size()-1).getNumberAttack()-1).getEffect(weaponArrayList.get(weaponArrayList.size()-1).getAttack(weaponArrayList.get(weaponArrayList.size()-1).getNumberAttack()-1).getNumberEffect()-1).adddamage(1,0);
                        }
                        else
                        {
                            weaponArrayList.get(weaponArrayList.size()-1).getAttack(weaponArrayList.get(weaponArrayList.size()-1).getNumberAttack()-1).getEffect(weaponArrayList.get(weaponArrayList.size()-1).getAttack(weaponArrayList.get(weaponArrayList.size()-1).getNumberAttack()-1).getNumberEffect()-1).adddamage(2,0);
                        }
                        CurrentState=12;
                        break;
                    }
                    case 12:
                    {
                        weaponArrayList.get(weaponArrayList.size()-1).getAttack(weaponArrayList.get(weaponArrayList.size()-1).getNumberAttack()-1).getEffect(weaponArrayList.get(weaponArrayList.size()-1).getAttack(weaponArrayList.get(weaponArrayList.size()-1).getNumberAttack()-1).getNumberEffect()-1).getDamage(weaponArrayList.get(weaponArrayList.size()-1).getAttack(weaponArrayList.get(weaponArrayList.size()-1).getNumberAttack()-1).getEffect(weaponArrayList.get(weaponArrayList.size()-1).getAttack(weaponArrayList.get(weaponArrayList.size()-1).getNumberAttack()-1).getNumberEffect()-1).getnumberdamage()-1).setDamage(Character.getNumericValue(nextc));
                        CurrentState=100;
                        break;
                    }
                    case 100:
                    {
                        if(nextc=='?')
                            CurrentState=11;
                        else
                        if(nextc=='!')
                            CurrentState=9;
                        else
                        if(nextc==':')
                            CurrentState=3;
                        else
                        if(nextc=='.')
                            CurrentState=1;
                        else
                            next=-1;
                    }
                }
            }
            while(next!=-1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        cards.addAll(weaponArrayList);
        return cards;
    }


}
