package Model;

import Model.Weapon;

import java.io.*;
import java.util.ArrayList;

public class Lettore {
    public static void main(String []args)
    {
        ArrayList<Weapon>O;
        O=new ArrayList<Weapon>();
        String stringa=new String();
        int CS=1;
        int cont=0;
        int contextra=0;
        try (FileReader file = new FileReader("Testo"+".mdr")) {
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
                            O.get(O.size()-1).addcost(Character.getNumericValue(nextc));
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
                        O.get(O.size()-1).getAttack(O.get(O.size()-1).getnumberattack()-1).addextra(Character.getNumericValue(nextc));
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
                        O.get(O.size()-1).getAttack(O.get(O.size()-1).getnumberattack()-1).setTypeplayer(Character.getNumericValue(nextc));
                        CS=6;
                        break;
                    }
                    case 6:
                    {
                        O.get(O.size()-1).getAttack(O.get(O.size()-1).getnumberattack()-1).setDistance(Character.getNumericValue(nextc));
                        CS=7;
                        break;
                    }
                    case 7:
                    {
                        O.get(O.size()-1).getAttack(O.get(O.size()-1).getnumberattack()-1).setMoveme(Character.getNumericValue(nextc));
                        CS=8;
                        break;
                    }
                    case 8:
                    {
                        O.get(O.size()-1).getAttack(O.get(O.size()-1).getnumberattack()-1).setMoveyou(Character.getNumericValue(nextc));
                        CS=9;
                        break;
                    }
                    case 9:
                    {
                        if(nextc=='p')
                        {
                            O.get(O.size()-1).getAttack(O.get(O.size()-1).getnumberattack()-1).addeffect(new Effect_factory().getinstanceof(1,0));
                        }
                        else
                        {
                            O.get(O.size()-1).getAttack(O.get(O.size()-1).getnumberattack()-1).addeffect(new Effect_factory().getinstanceof(2,0));
                        }
                        CS=10;
                        break;
                    }
                    case 10:
                    {
                        O.get(O.size()-1).getAttack(O.get(O.size()-1).getnumberattack()-1).getEffect(O.get(O.size()-1).getAttack(O.get(O.size()-1).getnumberattack()-1).getnumbereffect()-1).setId(Character.getNumericValue(nextc));
                        CS=11;
                        break;
                    }
                    case 11:
                    {
                        if(nextc=='l')
                        {
                            O.get(O.size()-1).getAttack(O.get(O.size()-1).getnumberattack()-1).getEffect(O.get(O.size()-1).getAttack(O.get(O.size()-1).getnumberattack()-1).getnumbereffect()-1).adddamage(1,0);
                        }
                        else
                        {
                            O.get(O.size()-1).getAttack(O.get(O.size()-1).getnumberattack()-1).getEffect(O.get(O.size()-1).getAttack(O.get(O.size()-1).getnumberattack()-1).getnumbereffect()-1).adddamage(2,0);
                        }
                        CS=12;
                        break;
                    }
                    case 12:
                    {
                        O.get(O.size()-1).getAttack(O.get(O.size()-1).getnumberattack()-1).getEffect(O.get(O.size()-1).getAttack(O.get(O.size()-1).getnumberattack()-1).getnumbereffect()-1).getDamage(O.get(O.size()-1).getAttack(O.get(O.size()-1).getnumberattack()-1).getEffect(O.get(O.size()-1).getAttack(O.get(O.size()-1).getnumberattack()-1).getnumbereffect()-1).getnumberdamage()-1).setdamage(Character.getNumericValue(nextc));
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
        for(Weapon A : O)
        {
            System.out.println("Oggetto con nome "+A.getName());
            for (int i=0;i<A.getnumbercost();i++)
            {
                System.out.println("Prezzo "+ A.getcost(i));
            }
            for(int e=0;e<A.getnumberattack();e++)
            {
                ArrayList<Integer> u;
                u=A.getAttack(e).getextras();
                for(int r : u)
                {
                    System.out.println("L'extra da pagare e' "+r);
                }
                System.out.println("Il tipo di player e' "+ A.getAttack(e).getTypeplayer());
                System.out.println("La distanza e' "+ A.getAttack(e).getDistance());
                System.out.println("Il mio movimento e' "+ A.getAttack(e).getMoveme());
                System.out.println("Il movimento che infliggo e' "+ A.getAttack(e).getMoveyou());
                for(int k=0;k<A.getAttack(e).getnumbereffect();k++)
                {
                    System.out.println("L'id del player o cella che subisce l'effetto e' "+A.getAttack(e).getEffect(k).getId());
                }
            }
        }
    }
}
