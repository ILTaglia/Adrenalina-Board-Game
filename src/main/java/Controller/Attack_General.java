package Controller;
import Model.*;
import exceptions.InvalidDirectionException;
import sun.jvm.hotspot.debugger.posix.elf.ELFSectionHeader;

import java.util.ArrayList;

public class Attack_General {
    public int Attack_General(Match m, Player first, Player second, Weapon weapon, int type)
    {
        Player viewer=first; //Used to change viewer in case of torpedine
        ArrayList<Player>A=new ArrayList<Player>();
        if(m.get_player(first.getcolor()).weaponIspresent(weapon))
        {
            for(int i=0;i<weapon.getnumberattack();i++)
            {
                int moveme=0;
                int moveyou=0;
                Type_attack attack= weapon.getAttack(i);
                if(attack.getMoveme()>5)
                {
                    moveme=attack.getMoveme()-5;
                    //TODO permettere movimento a first prima del turno
                }
                else
                {
                    moveme=attack.getMoveme();
                }
                if(attack.getMoveyou()>5)
                {
                    moveyou=attack.getMoveyou()-5;
                    //TODO permette movimento a second prima del turno
                }
                else
                {
                    moveyou=attack.getMoveyou();
                }
                if(attack.getClass().getName()=="Model.Undefined_distance") //In case I have an Undefined_distance attack
                {
                    ArrayList<Player> visible= new ArrayList<Player>();
                    visible=m.getVisiblePlayers(viewer);
                    if(attack.getDistance()==0) //Caso in cui ho un classico undefine distance
                    {
                        for(int j=0;j<attack.getnumbereffect();j++)
                        {
                            Effect effect= attack.getEffect(j);
                            if(effect.getClass().getName()=="Model.Player_effect") //Caso in cui ho un player effect
                            {
                                if(visible.contains(second))
                                {
                                    if(effect.getId()>A.size()-1) //Controllo in caso di nuovi giocatori non ancora attaccati da attaccare
                                    {
                                        if(!A.contains(second))
                                        {
                                            A.add(second);
                                        }
                                        else
                                        {
                                            //TODO ERRORE, GIOCATORE NON COLPIBILE
                                        }
                                    }
                                    if(A.get(effect.getId()).equals(second))
                                    {
                                        for(int k=0;k<effect.getnumberdamage();k++)
                                        {
                                            Damage damage = effect.getDamage(k);
                                            if(damage.getClass().getName()=="Model.Life") //caso di attacco che toglie vita
                                            {
                                                m.get_player(second.getcolor()).setdamage(damage.getdamage(),first.getcolor());
                                            }
                                            else //Caso di attacco che mette marchi
                                            {
                                                m.get_player(second.getcolor()).setmarks(damage.getdamage(), first.getcolor());
                                            }
                                        }
                                        if(attack.getTypeplayer()==1) //Aggiorno chi vede in caso di torpedine
                                        {
                                            viewer=second;
                                        }
                                    }
                                }
                                else {
                                    //TODO ERRORE GIOCATORE NON COLPIBILE
                                }
                            }
                            else
                            {
                                //TODO CASO DI CELL EFFECT
                            }

                        }

                    }
                    else
                        //TODO Caso in cui colpisco chi non vedo
                    {
                        for(int j=0;j<attack.getnumbereffect();j++)
                        {
                            Effect effect= attack.getEffect(j);
                            if(effect.getClass().getName()=="Model.Player_effect") //Caso in cui ho un player effect
                            {
                                if(!visible.contains(second))
                                {
                                    if(effect.getId()>A.size()-1) //Controllo in caso di nuovi giocatori non ancora attaccati da attaccare
                                    {
                                        if(!A.contains(second))
                                        {
                                            A.add(second);
                                        }
                                        else
                                        {
                                            //TODO ERRORE, GIOCATORE NON COLPIBILE
                                        }
                                    }
                                    if(A.get(effect.getId()).equals(second))
                                    {
                                        for(int k=0;k<effect.getnumberdamage();k++)
                                        {
                                            Damage damage = effect.getDamage(k);
                                            if(damage.getClass().getName()=="Model.Life") //caso di attacco che toglie vita
                                            {
                                                m.get_player(second.getcolor()).setdamage(damage.getdamage(),first.getcolor());
                                            }
                                            else //Caso di attacco che mette marchi
                                            {
                                                m.get_player(second.getcolor()).setmarks(damage.getdamage(), first.getcolor());
                                            }
                                        }
                                        if(attack.getTypeplayer()==1) //Aggiorno chi vede in caso di torpedine
                                        {
                                            viewer=second;
                                        }
                                    }
                                }
                                else {
                                    //TODO ERRORE GIOCATORE NON COLPIBILE
                                }
                            }
                        }

                    }
                    //In caso di moveme o moveyou permetto di assegnarli
                    if(moveme!=0)
                    {
                        //TODO assegno movimento a first
                        moveme=0;
                    }
                    if(moveyou!=0)
                    {
                        //TODO assegno movimento a second
                        moveyou=0;
                    }
                }
                else
                    if(attack.getClass().getName()=="Model.Finite_Distance") //Caso di attacco tipo finite distance
                    {
                        ArrayList<Player>visible =new ArrayList<Player>();
                        visible=m.getVisiblePlayers(viewer);
                        for(Player p : visible)
                        {
                            if(m.getPlayersMD(viewer,p)!=attack.getDistance())
                            {
                                visible.remove(p);
                            }
                        }
                        //TODO Stesso codice di undefined distance
                    }
                else
                    if(attack.getClass().getName()=="Model.More_distance") //Caso attacco di tipo more distance
                    {
                        ArrayList<Player>visible =new ArrayList<Player>();
                        visible=m.getVisiblePlayers(viewer);
                        for(Player p: visible)
                        {
                            if(m.getPlayersMD(viewer,second)<=attack.getDistance())
                            {
                                visible.remove(p);
                            }
                        }
                        //TODO STESSO CODICE DI UNDEFINED DISTANCE
                    }
                else //Caso attacco di tipo cardinal
                    {
                        ArrayList<Player> visible= new ArrayList<Player>();
                        ArrayList<Player> directx = new ArrayList<Player>();
                        ArrayList<Player> directy= new ArrayList<Player>();
                        visible=m.getVisiblePlayers(viewer);
                        directx=m.getSameLinePlayers(viewer);
                        directy=m.getSameColumnPlayers(viewer);
                        if(attack.getDistance()==0) //If i can pass through walls
                        {
                            for(int k=0; k<attack.getnumbereffect();k++)
                            {
                                Effect effect= attack.getEffect(k);
                                if(effect.getClass().getName()=="Player_effect")
                                {
                                    visible.clear();
                                    visible.addAll(directx);
                                    visible.addAll(directy);
                                    if(visible.contains(second))
                                    {
                                        if(effect.getId()>A.size()-1) //Controllo in caso di nuovi giocatori non ancora attaccati da attaccare
                                        {
                                            if(!A.contains(second))
                                            {
                                                A.add(second);
                                            }
                                            else {
                                                //TODO ERRORE, GIOCATORE NON COLPIBILE
                                            }
                                        }
                                        if(A.get(effect.getId()).equals(second))
                                        {
                                            for(int w=0;k<effect.getnumberdamage();w++)
                                            {
                                                Damage damage = effect.getDamage(w);
                                                if(damage.getClass().getName()=="Model.Life") //caso di attacco che toglie vita
                                                {
                                                    m.get_player(second.getcolor()).setdamage(damage.getdamage(),first.getcolor());
                                                }
                                                else //Caso di attacco che mette marchi
                                                {
                                                    m.get_player(second.getcolor()).setmarks(damage.getdamage(), first.getcolor());
                                                }
                                            }
                                            if(attack.getTypeplayer()==1) //Aggiorno chi vede in caso di torpedine
                                            {
                                                viewer=second;
                                            }
                                        }
                                    }
                                }
                                else
                                {
                                    //TODO CASO CELL EFFECT
                                }
                            }
                        }
                        else
                        {
                            for(int k=0;k<attack.getnumbereffect();k++)
                            {
                                int d=0;
                                Effect effect= attack.getEffect(k);
                                if(effect.getClass().getName()=="Model.Player_effect")
                                {
                                    if(moveme==0)
                                    {
                                        ArrayList<Player> temporal =new ArrayList<Player>();
                                        //TODO CALCOLO CHI POSSO ATTACCARE
                                    }
                                    else
                                    {
                                        //TODO MI SPOSTO E CALCOLO CHI POSSO ATTACCARE
                                    }
                                    d++;
                                }
                                else
                                {
                                    //TODO CASO CELL EFFECT
                                }
                            }
                            int d=0; //fa da conteggio per il numero di celle di cui ho deciso di attaccare
                            //TODO CASO NON PASSAGGIO ATTRAVERSO MURI
                        }
                    }
            }
        }
        return 0;
    }

}
