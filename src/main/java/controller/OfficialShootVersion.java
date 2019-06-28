package controller;

import model.Match;
import model.Player;
import model.TypeAttack;
import model.Weapon;

import java.util.ArrayList;
import java.util.List;

public class OfficialShootVersion {
    ShootManagement shootManagement;
    CreateListAttackable createListAttackable;
    Match match;
    Player player;
    Weapon weapon;
    List<Integer> lista;
    int type;
    List<TypeAttack> attacks;


    //############################################
    //##        INITIALIZE LIST                #####
    //############################################

    public OfficialShootVersion(Match match, Player player)
    {
        this.createListAttackable=new CreateListAttackable();
        this.shootManagement=new ShootManagement();
        this.match=match;
        this.player=player;
        this.weapon=null;
        this.lista=new ArrayList<Integer>();
        this.attacks=new ArrayList<TypeAttack>();
    }

    //############################################
    //##        CHECK PLAYER IN MATCH        #####
    //############################################
    public boolean checkplayer()
    {
        if(match.getPlayers().contains(this.player))
        {
            return true;
        }
        else
            return false;


    }


    //############################################
    //##        GENERATE LIST OF GUNS         #####
    //############################################


    //genero la lista delle armi e la restituisco. sta poi a chi le riceve verificare la scelta dell'arma
    public List<Weapon> getguns()
    {
        return this.player.getWeapons();
    }

    //############################################
    //##        SELECT WEAPON               #####
    //############################################

    public boolean chooseweapon(Weapon weapon)
    {
        if(this.player.getWeapons().contains(weapon))  //TODO E VERIFICARE CHE SIA CARICA
        {
            this.weapon=weapon;
            return true;
        }
        else
            return false;
    }

    //############################################
    //##        SHOW PRESENT TYPES           #####
    //############################################

    public List<Integer> gettypes()
    {
        List <Integer> lista= new ArrayList<Integer>();
        for(int i=0;i<this.weapon.getNumberAttack();i++)
        {
            int index = weapon.getAttack(i).getTypePlayer();
            if(!lista.contains(index))
            {
                lista.add(index);
            }
        }
        this.lista=lista;
        return lista;
    }

    //############################################
    //##        SELECT TYPE SERIE           #####
    //############################################

    public boolean settype(int type)
    {
        if(this.lista.contains(type))
        {
            this.type=type;
            return true;
        }
        else
            return false;
    }

    //############################################
    //##        GENERATE TYPE ATTACK LIST    #####
    //############################################

    public List <TypeAttack> generateattacks()
    {
        List <TypeAttack> listaattacchi = new ArrayList<TypeAttack>();
        for(int i=0;i<this.weapon.getNumberAttack();i++)
        {
            if(this.weapon.getAttack(i).getTypePlayer()==this.type)
            {
                listaattacchi.add(this.weapon.getAttack(i));
            }
        }
        this.attacks=listaattacchi;
        return listaattacchi;
    }

    //############################################
    //##        GENERATE TYPE ATTACK LIST    #####
    //############################################




}
