package controller;

import model.Match;
import model.Player;
import model.TypeAttack;
import model.Weapon;

import java.util.ArrayList;
import java.util.List;

public class OfficialShootVersion {
    private ShootManagement shootManagement;
    private CreateListAttackable createListAttackable;
    private Match match;
    private Player player;
    private Weapon weapon;
    private List<Integer> lista;
    private int type;
    private List<TypeAttack> attacks;
    private int flagfirstattack;
    private TypeAttack actualattack;
    private int firstattacksettedflag;

    //########  ATTACK INFO #########

    private List <Integer> extra;
    private int distance;
    private int moveme;
    private int moveyou;
    private int typeattack; //Says if it is a Finite distance, more distance ecc

    //########  END ATTACK INFO #########







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
        this.flagfirstattack=0;
        this.actualattack=null;
        this.firstattacksettedflag=0;
        this.extra= new ArrayList<Integer>();
        this.distance=0;
        this.moveme=0;
        this.moveyou=0;
        this.typeattack=0;
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
    //##        AUTOSELECT FIRST ATTACK      #####
    //############################################

    public void setfirstattack()
    {
        this.actualattack=this.attacks.get(0);
        this.firstattacksettedflag=1;
        this.attacks.remove(0);
    }


    //############################################
    //##        LOAD ATTACK INFO              #####
    //############################################

    public void loadinfo()
    {
        this.extra=this.actualattack.getExtras();
        this.distance=this.actualattack.getDistance();
        this.moveme=this.actualattack.getMoveMe();
        this.moveyou=this.actualattack.getMoveYou();
        this.typeattack= this.actualattack.getType();
    }


    //############################################
    //##        ATTACK LAUNCHER              #####
    //############################################

    //This will start the attack defining the type of the attack and calling the correct method for that attack










}
