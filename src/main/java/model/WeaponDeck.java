package model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
//TODO IMPORTANTE: Classe Deck da usare !
public class WeaponDeck extends Deck{
    private ArrayList<Weapon> W;

    public int getnumbercards()
    {
        return this.W.size();
    }

    //TODO: questo metodo può essere rimosso e si può usare il drawCard di Deck


    public WeaponDeck(){
        stack =new ArrayList<>();
        stackDiscarded =new ArrayList<>();
        //W=new ArrayList<Weapon>();
        ArrayList <Integer> A;

    }

    public void setWeapons(String filename)
    {
        this.stack =this.readWeaponCards( filename);
    }

    public ArrayList<Card> readWeaponCards(String fileName)
    {
        ArrayList<Weapon> O;
        O=new ArrayList<Weapon>();
        ArrayList<Card> S=new ArrayList<>();
        String stringa=new String();
        int CS=1;
        int cont=0;
        int contextra=0;
        try (FileReader file = new FileReader(fileName+".mdr")) {
            int next;
            do {
                next=file.read();
                char nextc=(char)next;
                switch(CS)
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
                                O.add(new Weapon(stringa));
                                stringa=new String();
                                CS=2;
                            }
                        }
                        break;
                    }
                    case 2:
                    {
                        if(nextc!=' ')
                        {
                            O.get(O.size()-1).addCost(Character.getNumericValue(nextc));
                        }
                        else
                        if(nextc==' ')
                        {
                            CS=3;
                            //cont++;
                        }
                        break;
                    }
                    case 3:
                    {
                        if(nextc=='f')
                        {
                            O.get(O.size()-1).addAttack(1,0,0,0,0);
                        }
                        else
                        if(nextc=='u')
                        {
                            O.get(O.size()-1).addAttack(2,0,0,0,0);
                        }
                        else
                        if(nextc=='m')
                        {
                            O.get(O.size()-1).addAttack(3,0,0,0,0);
                        }
                        else
                            O.get(O.size()-1).addAttack(4,0,0,0,0);

                        CS=4;
                        break;
                    }
                    case 4:
                    {
                        O.get(O.size()-1).getAttack(O.get(O.size()-1).getNumberAttack()-1).addExtra(Character.getNumericValue(nextc));
                        contextra++;
                        if(contextra==3)
                        {
                            CS=5;
                            contextra=0;
                        }
                        break;
                    }
                    case 5:
                    {
                        O.get(O.size()-1).getAttack(O.get(O.size()-1).getNumberAttack()-1).setTypePlayer(Character.getNumericValue(nextc));
                        CS=6;
                        break;
                    }
                    case 6:
                    {
                        O.get(O.size()-1).getAttack(O.get(O.size()-1).getNumberAttack()-1).setDistance(Character.getNumericValue(nextc));
                        CS=7;
                        break;
                    }
                    case 7:
                    {
                        O.get(O.size()-1).getAttack(O.get(O.size()-1).getNumberAttack()-1).setMoveMe(Character.getNumericValue(nextc));
                        CS=8;
                        break;
                    }
                    case 8:
                    {
                        O.get(O.size()-1).getAttack(O.get(O.size()-1).getNumberAttack()-1).setMoveYou(Character.getNumericValue(nextc));
                        CS=9;
                        break;
                    }
                    case 9:
                    {
                        if(nextc=='p')
                        {
                            O.get(O.size()-1).getAttack(O.get(O.size()-1).getNumberAttack()-1).addEffect(new EffectFactory().getinstanceof(1,0));
                        }
                        else
                        {
                            O.get(O.size()-1).getAttack(O.get(O.size()-1).getNumberAttack()-1).addEffect(new EffectFactory().getinstanceof(2,0));
                        }
                        CS=10;
                        break;
                    }
                    case 10:
                    {
                        O.get(O.size()-1).getAttack(O.get(O.size()-1).getNumberAttack()-1).getEffect(O.get(O.size()-1).getAttack(O.get(O.size()-1).getNumberAttack()-1).getNumberEffect()-1).setId(Character.getNumericValue(nextc));
                        CS=11;
                        break;
                    }
                    case 11:
                    {
                        if(nextc=='l')
                        {
                            O.get(O.size()-1).getAttack(O.get(O.size()-1).getNumberAttack()-1).getEffect(O.get(O.size()-1).getAttack(O.get(O.size()-1).getNumberAttack()-1).getNumberEffect()-1).adddamage(1,0);
                        }
                        else
                        {
                            O.get(O.size()-1).getAttack(O.get(O.size()-1).getNumberAttack()-1).getEffect(O.get(O.size()-1).getAttack(O.get(O.size()-1).getNumberAttack()-1).getNumberEffect()-1).adddamage(2,0);
                        }
                        CS=12;
                        break;
                    }
                    case 12:
                    {
                        O.get(O.size()-1).getAttack(O.get(O.size()-1).getNumberAttack()-1).getEffect(O.get(O.size()-1).getAttack(O.get(O.size()-1).getNumberAttack()-1).getNumberEffect()-1).getDamage(O.get(O.size()-1).getAttack(O.get(O.size()-1).getNumberAttack()-1).getEffect(O.get(O.size()-1).getAttack(O.get(O.size()-1).getNumberAttack()-1).getNumberEffect()-1).getnumberdamage()-1).setdamage(Character.getNumericValue(nextc));
                        CS=100;
                        break;
                    }
                    case 100:
                    {
                        if(nextc=='?')
                            CS=11;
                        else
                        if(nextc=='!')
                            CS=9;
                        else
                        if(nextc==':')
                            CS=3;
                        else
                        if(nextc=='.')
                            CS=1;
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
        S.addAll(O);
        return S;
    }


}
