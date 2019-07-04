package controller;

import model.*;

import java.util.ArrayList;
import java.util.List;

public class OfficialShootVersion {
    /**
     * match is the match where the attack is performed
     * player is the attacker
     * weapon is the gun used to attack
     * lista is the list of index of all the typeplayer number possibles for all the type-attack of the card chosen
     * type is the type of attack chosen by the player
     * attacks is the list of attacks with the same typeplayer index given by the player
     * flagfirstattack is a flag indicating if I have performed the first attack, it is useful because the first attack is obligate
     * actualattack is the attack I am going to perform at the moment
     * firstattacksettedflag is a flag indicating if i have loaded all the info of the first attack on the structure
     *
     */
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

    /**
     * typeTarget is a number that define if the attack is a cell one or a player one (1,2)
     * typeattack is has to be the same index of variable type, if not there have been errors
     * extra is the list of extra payment needed for that attack
     * distance is the distance field of the attack class
     * moveme is the max number of steps the player who attack can perform
     * moveyou is the number of steps the attacked can perform after the attack
     * typeattack is has to be the same index of variable type, if not there have been errors
     * listofeffect contains the list of all the effects i have with that attack
     */
    private int typeTarget;
    private List <Integer> extra;
    private int distance;
    private int moveme;
    private int moveyou;
    private int typeattack; //Says if it is a Finite distance, more distance ecc
    private List<Effect> listofeffect;

    //########  END ATTACK INFO #########

    /**
     *attackableplayers is a list of attackable players
     * attackablecells is a list of attackable cells
     * victimplayer is the victim of the attack
     * victimcell is the victim of the attack
     */
    private List<Player> attackableplayers;
    private List<Coordinate> attackablecells;
    private Player victimplayer;
    private Coordinate victimcell;


    //########  EFFECT INFO #########
    /**
     * currenteffect is the effect I am performing just now
     * Ideffect is an integer that indicate what effect it is
     * damages is the list of all the damage for the current effect
     */
    private Effect currenteffect;
    private int Ideffect;
    private List <Damage> damages;

    //########  END EFFECT INFO #########


    //############VARIABILI DI STATUS###############
    /*Lo status contiene un valore identificativo dello stato in cui si trova per gestire i risvegli dopo le risposte dal client
    *
    * Sono presenti i seguenti stati:
    *
    * 1. Sono in attesa di una risposta sull'arma da utilizzare
    * 2. Sono in attesa di risposta del tipo di serie di attacchi da eseguire
    * 3. Sono in attesa della risposta sul pagamento
    * 4. Sono in attesa di risposta del giocatore da attaccare
    * 5. Sono in attesa di risposta della cella da attaccare
    * 6. Sono in attesa del susseguirsi dello spostamento del player attaccato
    * 7. Sono in attesa dello spostamento del player principale
    * 8. Sono in attesa di risuposta sull'intenzione di proseguire o meno con un prossimo attacco
    * 9. SOno in attesa di una direzione
    *
    *
    * */
    /**
     * status is the status i have in the game class, is needed to know what i have to do every time the user give us an index
     * attackmethod is an integer that indicates the type of attack, it is used to kno the exact sequence of attack step i have to do
     */
    private int status;


    //############VARIABILI DI METODO DI ATTACCO###############
    /*La variabile in questione tiene traccia del tipo di attacco intrapreso. da questo ne deriva infatti una diversa gestione nella game
    *
    * i valori attribuibili sono i seguenti e con i significati posti a lato:
    *
    * 1. Standard Attack
    * 2. Ricorsivfe Attack
    * 3. All room
    * */

    private int attackmethod;











    //############################################
    //##        INITIALIZE LIST                #####
    //############################################

    /**
     *
     * @param match is the match
     * @param player is the player
     */
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
        this.status=0;
        this.attackmethod=0;







    }

    //############################################
    //##        CHECK PLAYER IN MATCH        #####
    //############################################

    /**
     * Method to check player in match
     * @return true or false if the player is in the match or not
     */
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

    /**
     *
     * @return the list of weapons to verify the choice of the weapon
     */
    public List<Weapon> getguns()
    {
        return this.player.getWeapons();
    }

    //############################################
    //##        SELECT WEAPON               #####
    //############################################

    /**
     *
     * @param weapon is the weapon
     * @return true is the player owns the weapon, 0 otherwise
     */
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

    /**
     *
     * @return a list of the type of all the typleplayer containtained in the effects of the weapon
     */
    public List<Integer> gettypes()
    {
        List <Integer> lista= new ArrayList<>();
        for(int i=0;i<this.weapon.getNumberAttack();i++)
        {
            int index = weapon.getAttack(i).getTypePlayer();
            //if(!lista.contains(index))
            //{
                lista.add(index);
           //}
        }
        this.lista=lista;
        return lista;
    }

    //############################################
    //##        SELECT TYPE SERIE           #####
    //############################################

    /**
     *
     * @param type is the type to be set
     * @return true if lista contains type, 0 otherwise
     */
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

    /**
     *
     * @return a list of the type of attack generated
     */
    public List <TypeAttack> generateattacks()
    {
        List <TypeAttack> listaattacchi = new ArrayList<>();
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

    /**
     * Select first attack
     */
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

    /**
     * Select others attacks
     */
    public void setsuccessiveattack()
    {
        this.actualattack=this.attacks.get(0);
        this.attacks.remove(0);
        loadinfo();

    }

    //############################################
    //##        PAYMENT FOR EXTRAS   #####
    //############################################

    /**
     *
     * @return true if extra functions are allowed (player has enough ammos to unlock), 0 otherwise
     */
    public boolean payextra()
    {
        ManagingWeapons manage=new ManagingWeapons(this.match);
        return manage.unlockExtraFunction(this.player,this.extra);
    }



    //############################################
    //##        LOAD ATTACK INFO              #####
    //############################################

    /**
     * Method to load info
     */
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

        //Al termine verifica se era nel primo attacco, nel caso aggiorna i flag

    /**
     * Method to start the attack defining the type of the attack and calling the correct method for that attack
     */
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

    /**
     * Method for standard attack
     */
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

    /**
     *
     * @return type of attack
     */
    public int getTypeAttack()
    {
        return this.typeattack;
    }





    //############################################
    //##        RUN MOVEMENT                 #####
    //############################################

    /**
     *
     * @param persontomove is the enemy to move
     * @param maxnumbermovement is the max number of steps the enemy can be moved
     */
    public void run(Player persontomove, int maxnumbermovement)
    {
        //TODO INTERFACCIARLO CON MOVEMENT


    }


    //############################################
    //##        CREATE LIST ATTACKABLE        #####
    //############################################

    /**
     *
     * @param viewer is the player
     */
    public void generatelistattackable(Player viewer)
    {
        createListAttackable.createlist(this.match,this.actualattack,viewer);
        this.attackableplayers=createListAttackable.getAttackableplayers();
        this.attackablecells=createListAttackable.getAttackablecells();
    }






    //############################################
    //##        RETURNING CHOSEN LIST OF ATTACKABLE PLAYERS/CELLS#####
    //############################################

    /**
     *
     * @param type is the required type
     * @return the list of attackable according to the type
     */
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

    /**
     *
     * @return a boolean checking the size of the list effect
     */
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

    /**
     *
     * @return the type of target
     */
    public int getTypeTarget()
    {
        return this.typeTarget;
    }




    //############################################
    //##        SETTING PLAYER TO ATTACK       #####
    //############################################

    //Setta e attacca il player

    /**
     *
     * @param victim is the player victim
     * @return a boolean according to the victim player
     */
    public boolean setvictimplayer(Player victim)
    {
        int flag=0;
        for(int i=0;i<this.damages.size();i++)
        {
            if(shootManagement.shoot(this.match,this.attackableplayers,this.player,victim,this.Ideffect,this.damages.get(i))!=0)
            {
                flag=1;
                if(this.attackmethod==2)
                {
                    this.createListAttackable.setViewer(victim);
                }
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

    /**
     *
     * @param victim is the victim cell
     * @return a boolean according to the victim cell
     */
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

    /**
     *
     * @return a boolean after checking
     */
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

    /**
     *
     * @return a boolean after checking
     */
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

    /**
     *
     * @return moveme
     */
    public int getmoveme()
    {
        return this.moveme;
    }

    /**
     *
     * @return moveyou
     */
    public int getMoveyou()
    {
        return this.moveyou;
    }

    /**
     *
     * @return the player
     */
    public Player getPlayer()
    {
        return this.player;
    }

    /**
     *
     * @return the flag of the first attack
     */
    public int getFlagfirstattack()
    {
        return this.flagfirstattack;
    }

    /**
     *
     * @param i is the flag of the first attack to set
     */
    public void setFlagfirstattack(int i)
    {
        this.flagfirstattack=i;
    }

    /**
     *
     * @return status
     */
    public int getStatus()
    {
        return this.status;
    }

    /**
     *
     * @param status is status to be set
     */
    public void setstatus(int status)
    {
        this.status=status;
    }

    /**
     *
     * @return attackmethod
     */
    public int getAttackmethod()
    {
        return this.attackmethod;
    }

    /**
     *
     * @param method is attackmethod to be set
     */
    public void setAttackmethod(int method)
    {
        this.attackmethod=method;
    }

    public Weapon getWeapon()
    {
        return this.weapon;
    }



    //----------------------------Metodi utili per gestione attackable----------------------------------------------------------//

    /**
     *
     * @param direction is teh direction of shoot to be set
     * @return true if the direction is valid, false otherwise
     */
    public boolean setdirectiontoshoot(int direction)
    {
        if(direction>=0&&direction<=3)
        {
            createListAttackable.setDirection(direction);
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     *
     * @param viewer is the player
     */
    public void setviewer(Player viewer)
    {
        this.createListAttackable.setViewer(viewer);
    }














}
