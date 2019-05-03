package Controller;
import Model.*;
import exceptions.InvalidDirectionException;
import sun.jvm.hotspot.debugger.posix.elf.ELFSectionHeader;

import java.util.ArrayList;

public class Attack_General {
    public int Attack_General(Match m, Player first, Player second, Weapon weapon, int type, int direction)
    {
        Player viewer=first; //Used to change viewer in case of torpedine
        ArrayList<Player>A=new ArrayList<Player>();
        if(m.getplayer(first.getcolor()).weaponIspresent(weapon))
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
                                    viewer = damage_set(m, first, second, viewer, A, attack, effect);
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
                                    viewer = damage_set(m, first, second, viewer, A, attack, effect);
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
                                    if(direction==0) //0 means up, 1 means right, 2 means down, 3 means left
                                    {
                                        visible.addAll(m.getUpPlayers(viewer));
                                    }
                                    else
                                        if(direction==1)
                                        {
                                            visible.addAll(m.getRightPlayers(viewer));
                                        }
                                        else
                                            if(direction==2)
                                            {
                                                visible.addAll(m.getDownPlayers(viewer));
                                            }
                                            else
                                            {
                                                visible.addAll(m.getLeftPlayers(viewer));
                                            }
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
                                        viewer = damage_set(m, first, second, viewer, A, attack, effect);
                                    }
                                }
                                else
                                {
                                    //TODO CASO CELL EFFECT
                                }
                            }
                        }
                        else //for all the attacks that can't pass over walls
                        {
                            for(int k=0;k<attack.getnumbereffect();k++)
                            {
                                Effect effect= attack.getEffect(k);
                                if(effect.getClass().getName()=="Model.Player_effect")
                                {
                                    for(int d=1;d<attack.getDistance();d++) //for all the cells of the Distance
                                    {
                                        if(moveme==0) //for all the guns i shoot but i don't move from my position
                                        {
                                            ArrayList<Player> temporal =new ArrayList<Player>();
                                            temporal=m.getVisiblePlayers(viewer);
                                            ArrayList<Player> directed = new ArrayList<Player>();
                                            directed = getDirectedPlayer(m, direction, viewer);
                                            for(Player p : temporal) //If player i can see are not on the correct distance or not on the direction i choosed, are deleted
                                            {
                                                if(!directed.contains(p)||m.getPlayersMD(viewer,p)!=d)
                                                {
                                                    temporal.remove(p);
                                                }
                                            }
                                            viewer = AimAndShoot(m, first, second, viewer, A, attack, effect, temporal);
                                        }
                                        else //For all the people I shoot but I also move on that direction
                                        {
                                            //TODO PERMETTO DI SPOSTARSI NELLA DIREZIONE INDICATA
                                            ArrayList<Player> temporal =new ArrayList<Player>();
                                            temporal=m.getVisiblePlayers(viewer);
                                            for(Player p : temporal)
                                            {
                                                if(m.getPlayersMD(viewer,second)!=0)
                                                {
                                                    temporal.remove(p);
                                                }
                                            }
                                            viewer = AimAndShoot(m, first, second, viewer, A, attack, effect, temporal);
                                        }
                                    }
                                }
                                else
                                {
                                    //TODO CASO CELL EFFECT
                                }
                            }

                        }
                    }
            }
        }
        return 0;
    }

    private ArrayList<Player> getDirectedPlayer(Match m, int direction, Player viewer) {
        ArrayList<Player> directed;
        if(direction==0) //direction 0 for up, 1 to right, 2 to down 3 to left
        {
            directed=m.getUpPlayers(viewer);
        }
        else
        {
            if(direction==1)
            {
                directed=m.getRightPlayers(viewer);
            }
            else
                if(direction==2)
                {
                    directed=m.getDownPlayers(viewer);
                }
                else
                {
                    directed=m.getLeftPlayers(viewer);
                }
        }
        return directed;
    }


    //Denote if the player can be attacked, then attack the player
    private Player AimAndShoot(Match m, Player first, Player second, Player viewer, ArrayList<Player> a, Type_attack attack, Effect effect, ArrayList<Player> temporal) {
        if(temporal.contains(second))
        {
            if(a.size()-1<effect.getId())
            {
                if(!a.contains(second))
                {
                    a.add(second);
                }
                else
                {
                    //TODO IMPOSSIBILE ATTACCARE QUEL GIOCATORE
                }
                viewer = damage_set(m, first, second, viewer, a, attack, effect);
            }

        }
        else
        {
            //TODO IMPOSSIBILE ATTACCARE GIOCATORE
        }
        return viewer;
    }


    //Assigns damages and marks to a player
    private Player damage_set(Match m, Player first, Player second, Player viewer, ArrayList<Player> a, Type_attack attack, Effect effect) {
        if(a.get(effect.getId()).equals(second))
        {
            for(int k=0;k<effect.getnumberdamage();k++)
            {
                Damage damage = effect.getDamage(k);
                if(damage.getClass().getName()=="Model.Life") //caso di attacco che toglie vita
                {
                    m.getplayer(second.getcolor()).setdamage(damage.getdamage(),first.getcolor());
                }
                else //Caso di attacco che mette marchi
                {
                    m.getplayer(second.getcolor()).setmarks(damage.getdamage(), first.getcolor());
                }
            }
            if(attack.getTypeplayer()==1) //Aggiorno chi vede in caso di torpedine
            {
                viewer=second;
            }
        }
        return viewer;
    }

}
