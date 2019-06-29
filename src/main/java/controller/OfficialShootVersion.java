package controller;

import model.*;

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

    private int typeTarget;
    private List <Integer> extra;
    private int distance;
    private int moveme;
    private int moveyou;
    private int typeattack; //Says if it is a Finite distance, more distance ecc
    private List<Effect> listofeffect;

    //########  END ATTACK INFO #########

    private List<Player> attackableplayers;
    private List<Coordinate> attackablecells;
    private Player victimplayer;
    private Coordinate victimcell;


    //########  EFFECT INFO #########
    private Effect currenteffect;
    private int Ideffect;
    private List <Damage> damages;

    //########  END EFFECT INFO #########










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
        this.listofeffect=new ArrayList<Effect>();
        this.attackablecells=new ArrayList<Coordinate>();
        this.attackableplayers=new ArrayList<Player>();
        this.victimplayer=null;
        this.victimcell=null;
        this.currenteffect=null;
        this.Ideffect=0;
        this.damages=new ArrayList<Damage>();
        this.typeTarget=0;
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
        loadinfo();

    }


    //############################################
    //##        SELECT OTHERS ATTACKS ATTACK      #####
    //############################################

    public void setsuccessiveattack()
    {
        this.actualattack=this.attacks.get(0);
        this.attacks.remove(0);
        loadinfo();

    }

    //############################################
    //##        PAYMENT FOR EXTRAS   #####
    //############################################
    public boolean payextra()
    {
        ManagingWeapons manage=new ManagingWeapons(this.match);
        return manage.unlockExtraFunction(this.player,this.extra);
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
        for(int i=0;i<this.actualattack.getNumberEffect();i++)
        {
            this.listofeffect.add(this.actualattack.getEffect(i));
        }

    }


        //############################################
        //##        ATTACK LAUNCHER              #####
        //############################################

        //This will start the attack defining the type of the attack and calling the correct method for that attack
        //Al termine verifica se era nel primo attacco, nel caso aggiorna i flag

    public void attacklauncher()
    {
        if(payextra()) //Caso pagamento riuscito
        {
            if(this.typeattack==1||this.typeattack==2||this.typeattack==3||this.typeattack==4||this.typeattack==5||this.typeattack==9||this.typeattack==11)
            {
                standardattack();
            }

            if(this.flagfirstattack==0)
            {
                this.flagfirstattack=1;
            }
        }


    }



    //############################################
    //##        STANDARD EXECUTION           #####
    //############################################

    //Is used only by
    //Finite distance 	1
    //Undefined distance	2
    //MoreDistance	3
    //Cardinal	4
    //Not seen	5
    //Allroom	9
    //Infiniteline	11


    //L'attacco come prima cosa controlla se ci siano degli spostamenti che posso fare, in tal caso li esegue
    //Subito dopo elabora la lista di giocatori e celle attaccabili
    //Avvia i metodi di esecuzione effetti finchè ce ne siano   ->No, rinviato a livello superiore perchè l'utente può scegliere quando fermarsi



    public void standardattack()
    {
        if(this.moveme!=0)
        {
            run(this.player,this.moveme);
        }
        generatelistattackable(this.player);

    }

    //############################################
    //##        INFO ABOUT TYPE OF ATTACK     #####
    //############################################

    public int getTypeAttack()
    {
        return this.typeattack;
    }





    //############################################
    //##        RUN MOVEMENT                 #####
    //############################################

    public void run(Player persontomove, int maxnumbermovement)
    {
        //TODO INTERFACCIARLO CON MOVEMENT


    }


    //############################################
    //##        CREATE LIST ATTACKABLE        #####
    //############################################

    public void generatelistattackable(Player viewer)
    {
        createListAttackable.createlist(this.match,this.actualattack,viewer);
        this.attackableplayers=createListAttackable.getAttackableplayers();
        this.attackablecells=createListAttackable.getAttackablecells();
    }






    //############################################
    //##        RETURNING CHOSEN LIST OF ATTACKABLE PLAYERS/CELLS#####
    //############################################

    public List getlistattackable(int type)
    {
        if(type==1)
        {
            return this.attackableplayers;
        }
        return this.attackablecells;
    }

    //############################################
    //##        LOADING INFO CURRENT EFFECT   #####
    //############################################

    public boolean loadeffect()
    {
        if(this.listofeffect.size()==0)
        {
            return false;
        }
        else
        {
            this.currenteffect=this.listofeffect.get(0);
            this.listofeffect.remove(0);
            for(int i=0;i<this.currenteffect.getnumberdamage();i++)
            {
                this.damages.add(this.currenteffect.getDamage(i));
            }
            this.Ideffect=this.currenteffect.getId();
            this.typeTarget=this.currenteffect.getType();
            return true;
        }
    }

    //############################################
    //##        RETURNING THE TYPE OF BERSAGLIO   #####
    //############################################

    // 1 player
    // 2 cella

    public int getTypeTarget()
    {
        return this.typeTarget;
    }




    //############################################
    //##        SETTING PLAYER TO ATTACK       #####
    //############################################

    //Setta e attacca il player

    public boolean setvictimplayer(Player victim)
    {
        int flag=0;
        for(int i=0;i<this.damages.size();i++)
        {
            if(shootManagement.shoot(this.match,this.attackableplayers,this.player,victim,this.Ideffect,this.damages.get(i))!=0)
            {
                flag=1;
            }
        }
        if(flag==0)
        {
            if(this.moveyou!=0)
            {
                run(victim, moveyou);
                this.moveyou=0;
            }
            return true;
        }
        else
        {
            return false;
        }
    }

    //############################################
    //##        SETTING CELL TO ATTACK       #####
    //############################################

    //Setta e attacca la cella

    public boolean setvictimcell(Coordinate victim)
    {
        int flag=0;
        for(int i=0;i<this.damages.size();i++)
        {
            if(shootManagement.shoot(this.match,this.attackablecells,this.player,victim,this.Ideffect,this.damages.get(i))!=0)
            {
                flag=1;
            }
        }
        if(flag==0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    //############################################
    //##        METODI DI CONTROLLO       #####
    //############################################

    public boolean checkotherattacks()
    {
        if(this.attacks.size()!=0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean checkothereffects()
    {
        if(this.listofeffect.size()!=0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }





    //############################################
    //##        METODI DI GET E SET          #####
    //############################################

    public int getmoveme()
    {
        return this.moveme;
    }

    public Player getPlayer()
    {
        return this.player;
    }

    public int getFlagfirstattack()
    {
        return this.flagfirstattack;
    }

    public void setFlagfirstattack(int i)
    {
        this.flagfirstattack=i;
    }

















}
