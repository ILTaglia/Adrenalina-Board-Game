package controller;

import model.*;

import java.util.List;
import java.util.Scanner;

public class UndefinedAttack {
    Match match;
    Player player;
    CreateListAttackable listAttackable;
    ShootManagement shootManagement;
    List<TypeAttack> attacks;
    Weapon weapon;
    int type;
    int direction;
    int ricorsivebit;

    public UndefinedAttack(Match match, Player player, Weapon weapon, int type)
    {
        this.match=match;
        this.player=player;
        ListAttackCreator lista = new ListAttackCreator();
        this.attacks=lista.elaboratelist(weapon,type);
        this.weapon=weapon;
        this.type=type;
        this.shootManagement=new ShootManagement();
        this.ricorsivebit=0;
    }

    public boolean checkpresenceweapon()
    {
        if(match.getPlayer(this.player.getColor()).weaponIspresent(this.weapon))
            return true;
        return false;
    }

    public boolean checktype()
    {
        createlistattacks();
        if(this.attacks.size()==0)
            return false;
        return true;
    }

    public void createlistattacks() //Crea lista di attacchi per un tipo fornito in precedenza
    {
        ListAttackCreator lista = new ListAttackCreator();
        this.attacks=lista.elaboratelist(this.weapon,this.type);
    }

    public void setType(int type)
    {
        this.type=type;
    }

    public void setWeapon(Weapon weapon)
    {
        this.weapon=weapon;
    }

    public void setDirection(int direction)
    {
        this.direction=direction;
        this.listAttackable.setDirection(direction);
    }

    public void setviewer(Player viewer)
    {
        this.listAttackable.setViewer(viewer);
    }

    public void refreshvisible(TypeAttack attack)
    {

        this.listAttackable=new CreateListAttackable();
        this.listAttackable.setDirection(this.direction);
        this.listAttackable.createlist(this.match,attack,this.player);
    }

    public int manageplayerattack(Player second,int ID, Damage damage)
    {
        return this.shootManagement.shoot(match,this.listAttackable.getAttackableplayers(),player,second,ID, damage);
    }

    public int managecellattack(Coordinate second, int ID, Damage damage)
    {
        return this.shootManagement.shoot(match,this.listAttackable.getAttackablecells(),this.player,second,ID,damage);
    }

    public boolean checkammo(List<Integer> extra) //Returns true if there are ammos needed
    {
        for(int i=0; i<extra.size();i++)
        {
            if(this.player.getAmmo(i)<extra.get(i))
            {
                return false;
            }
        }
        return true;
    }

    public void payextras(List<Integer> extra)
    {
        for(int i=0; i<extra.size();i++)
        {
            //TODO CANCELLARE MUNIZIONI
        }
    }

    public void movefirstplayer(int n)
    {
        //TODO CONSENTIRE MOVIMENTO
    }

    public void chosedirection()
    {
        do {
            System.out.println("Inserire una direzione");
            Scanner input = new Scanner (System.in);
            this.direction=input.nextInt();
        }
        while(this.direction<0||this.direction>3);

    }

    public void simpleattack(TypeAttack attack)
    {
        for(int i=0;i<attack.getNumberEffect();i++)
        {
            refreshvisible(attack);
            Effect effect = attack.getEffect(i);
            if(effect.getType()==0) //Caso Player effect
            {
                playereffect(effect);
            }
            else //caso cell effect
            {
                celleffect(effect);
            }
        }
    }

    public void playereffect(Effect effect)
    {
        int scelta;
        int cont;
        int stato=0;
        do {
            do {
                cont = 0;
                for (Player p : this.listAttackable.getAttackableplayers()) {
                    System.out.println("Digita " + cont + " Per attaccare " + p.getName());
                    cont++;
                }
                System.out.println("Se desideri interrompere l'attacco digita -1");
                Scanner input = new Scanner(System.in);
                scelta = input.nextInt();
            }
            while (scelta < -1 || scelta > cont - 1);
            stato = 0;
            if (scelta != -1) {
                Player second = this.listAttackable.getAttackableplayers().get(scelta);
                for (int k = 0; k < effect.getnumberdamage(); k++) {
                    Damage damage = effect.getDamage(k);
                    stato = this.manageplayerattack(second, effect.getId(), damage);
                    System.out.println("Status attacco : " + stato);
                }
                if(this.ricorsivebit==1&&stato==0)
                {
                    setviewer(second);
                }
            }
        }
        while(stato!=0);

    }

    public void celleffect(Effect effect)
    {
        int scelta;
        int cont;
        int stato=0;
        do {
            do {
                cont = 0;
                for (Coordinate c : this.listAttackable.getAttackablecells()) {
                    System.out.println("Digita " + cont + " Per attaccare " + c.getX()+";"+c.getY());
                    cont++;
                }
                System.out.println("Se desideri interrompere l'attacco digita -1");
                Scanner input = new Scanner(System.in);
                scelta = input.nextInt();
            }
            while (scelta < -1 || scelta > cont - 1);
            stato = 0;
            if (scelta != -1) {
                Coordinate second = this.listAttackable.getAttackablecells().get(scelta);
                for (int k = 0; k < effect.getnumberdamage(); k++) {
                    Damage damage = effect.getDamage(k);
                    stato = this.managecellattack(second, effect.getId(), damage);
                    System.out.println("Status attacco : " + stato);
                }
            }
        }
        while(stato!=0);
    }

    public void whilemovingattack(TypeAttack attack)
    {
        for(int j=0;j<attack.getMoveMe();j++)
        {
            for(int i=0;i<attack.getNumberEffect();i++)
            {
                Effect effect = attack.getEffect(i);
                refreshvisible(attack);
                if(effect.getType()==0) //Caso Player effect
                {
                    playereffect(effect);
                }
                else //caso cell effect
                {
                    celleffect(effect);
                }
            }
            //TODO MOVIMENTO IN QUELLA POSIZIONE INDICATA DALLA DIRECTION
        }

    }

    public void allaroundattack(TypeAttack attack)
    {
        for(int j=0;j<4;j++)
        {
            this.direction=j;
            for(int i=0;i<attack.getNumberEffect();i++)
            {
                Effect effect = attack.getEffect(i);
                refreshvisible(attack);
                if(effect.getType()==0) //Caso Player effect
                {
                    playereffect(effect);
                }
                else //caso cell effect
                {
                    celleffect(effect);
                }
            }
        }
    }


    //IMPORTANTE: TUTTE LE RICHIESTE ED ACQUISIZIONI ANDRANNO FATTE MEDIANTE OPPORTUNI METODI DELLA VIEW
    public void usecard()
    {
        int attacco=0, chose=1;
        if(!checkpresenceweapon())
        {
            System.out.println("Arma non presente!");
        }
        else
        {
            while(!checktype())
            {
                System.out.println("Nessun attacco possibile con questo ID");
                System.out.println("Inserire un nuovo id");
                Scanner input = new Scanner (System.in);
                this.setType(input.nextInt());
            }
                createlistattacks(); //Creo lista di attacchi
                //TODO CONTROLLO MUNIZIONI PER EXTRA
                for(TypeAttack t : this.attacks)
                {
                    if(attacco!=0)
                    {
                        System.out.println("Digitare 1 se si desidera usare questro effetto");
                        Scanner input2 = new Scanner (System.in);
                        chose=input2.nextInt();
                    }
                    if(attacco==0||(chose==1&&checkammo(t.getExtras()))) //Caso in cui decido di attaccare o si tratta del primo attacco
                    {
                        attacco++;
                        refreshvisible(t);

                        if(t.getType()==1||t.getType()==2||t.getType()==3||t.getType()==4||t.getType()==5||t.getType()==7||t.getType()==9||t.getType()==10||t.getType()==11||t.getType()==12) //Caso in cui posso muovermi subito
                        {
                            movefirstplayer(t.getMoveMe());
                        }

                        if(t.getType()==4||t.getType()==6||t.getType()==9||t.getType()==11) //Caso in cui devo scegliere una direzione
                        {
                            chosedirection();
                        }

                        if(t.getType()==1||t.getType()==2||t.getType()==3||t.getType()==4||t.getType()==5||t.getType()==11) //Gestione attacco semplice
                        {
                            simpleattack(t);
                        }
                        if(t.getType()==6)
                        {
                            whilemovingattack(t);
                        }
                        if(t.getType()==7)
                        {
                            allaroundattack(t);
                        }
                        if(t.getType()==11)
                        {
                            this.ricorsivebit=1;
                            simpleattack(t);
                        }
/*
                        for(int i=0;i<t.getNumberEffect();i++)
                        {
                            Effect e = t.getEffect(i);
                            if(e.getType()==0) //Se ho dei player effect
                            {
                                int scelta;
                                int cont;
                                do {
                                    cont=0;
                                    for( Player p : this.listAttackable.getAttackableplayers())
                                    {
                                        System.out.println("Digita "+ cont+ " Per attaccare "+ p.getName());
                                        cont++;
                                    }
                                    Scanner input = new Scanner (System.in);
                                    scelta=input.nextInt();
                                }
                                while(scelta<0||scelta>cont-1);
                                Player second = this.listAttackable.getAttackableplayers().get(scelta);
                                for(int k=0;k<e.getNumberDamage();k++)
                                {
                                    Damage damage = e.getDamage(k);
                                    System.out.println("Status attacco : " +this.manageplayerattack(second,e.getId(),damage));
                                }
                            }
                            else
                            {
                                int scelta;
                                int cont;
                                do {
                                    cont=0;
                                    for( Coordinate p : this.listAttackable.getAttackablecells())
                                    {
                                        System.out.println("Digita "+ cont+ " Per attaccare "+ p.getX()+";"+p.getY());
                                        cont++;
                                    }
                                    Scanner input = new Scanner (System.in);
                                    scelta=input.nextInt();
                                }
                                while(scelta<0||scelta>cont-1);
                                Coordinate second = this.listAttackable.getAttackablecells().get(scelta);
                                for(int k=0;k<e.getNumberDamage();k++)
                                {
                                    Damage damage = e.getDamage(k);
                                    System.out.println("Status attacco : " +this.managecellattack(second,e.getId(),damage));
                                }
                            }
                        }*/
                    }

                }
            }
    }
    /*
    public static void main(String args[])
    {
        Match match;
        Player player1;
        Player player2;
        Player player3;
        Player player4;
        Player player5;
        GrabWeapon grabweapon;

        match = new Match();

        try {
            match.createPlayer("Giovanni", "Blue", "10583741");
            match.createPlayer("Marco", "Pink", "14253954");
            match.createPlayer("Codecasa", "Green", "18263100");
            match.createPlayer("Bussetti", "Yellow", "18263100");
            match.createPlayer("Norma", "Grey", "18263100");
        } catch (MaxNumberPlayerException e) {
            System.out.println("Too many players in the game.");
        }

        player1 = match.getPlayerByIndex(0);
        player2 = match.getPlayerByIndex(1);
        player3 = match.getPlayerByIndex(2);
        player4 = match.getPlayerByIndex(3);
        player5 = match.getPlayerByIndex(4);

        match.createDashboard(3);
        grabweapon = new GrabWeapon();
        ArrayList<String> destination = new ArrayList<>();
        player1.setCel(1, 2);
        player2.setCel(2, 2);
        player3.setCel(0, 2);
        player4.setCel(2, 0);
        player5.setCel(0, 1);

        WeaponDeck weaponDeck = new WeaponDeck();
        weaponDeck.setWeapons("Armi");
        weaponDeck.drawCard();
        Weapon weapon1 = (Weapon) weaponDeck.drawCard();
        Weapon weapon2 = (Weapon) weaponDeck.drawCard();
        Weapon weapon3 = (Weapon) weaponDeck.drawCard();
        Weapon weapon4 = (Weapon) weaponDeck.drawCard();
        Weapon weapon5 = (Weapon) weaponDeck.drawCard();
        Weapon weapon6 = (Weapon) weaponDeck.drawCard();
        try {
            player1.addWeapon(weapon1);
            player1.addWeapon(weapon2);
            player1.addWeapon(weapon3);
            player2.addWeapon(weapon4);
            player2.addWeapon(weapon5);
            player2.addWeapon(weapon6);

        } catch (MaxNumberofCardsException e) {
            System.out.println("You have too many Weapon Cards, please discardWeapon one.");
        }
        System.out.println(weapon1.getName());
        System.out.println(weapon2.getName());
        System.out.println(weapon3.getName());
        UndefinedAttack attack = new UndefinedAttack(match,player1,weapon1,0);
        attack.usecard();
        System.out.println("Danni di "+player2.getName() + " = " + player2.getTotalDamage());
        System.out.println("Danni di "+player3.getName() + " = " + player3.getTotalDamage());
        System.out.println("Danni di "+player4.getName() + " = " + player4.getTotalDamage());
        System.out.println("Danni di "+player5.getName() + " = " + player5.getTotalDamage());
        attack = new UndefinedAttack(match,player1,weapon4,0);
        attack.usecard();
        System.out.println(weapon4.getName());
        System.out.println(weapon5.getName());
        System.out.println(weapon6.getName());
    }
    */

}
