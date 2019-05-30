package controller;

import exceptions.MaxNumberPlayerException;
import exceptions.MaxNumberofCardsException;

import model.*;

import java.util.ArrayList;
import java.util.Scanner;

public class UndefinedAttack {
    Match match;
    Player player;
    CreateListAttackable listAttackable;
    ShootManagement shootManagement;
    ArrayList<TypeAttack> attacks;
    Weapon weapon;
    int type;
    int direction;

    public UndefinedAttack(Match match, Player player, Weapon weapon, int type)
    {
        this.match=match;
        this.player=player;
        ListAttackCreator lista = new ListAttackCreator();
        this.attacks=lista.elaboratelist(weapon,type);
        this.weapon=weapon;
        this.type=type;
        this.shootManagement=new ShootManagement();
    }

    public boolean checkpresenceweapon()
    {
        if(match.getPlayer(this.player.getcolor()).weaponIspresent(this.weapon))
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



    //IMPORTANTE: TUTTE LE RICHIESTE ED ACQUISIZIONI ANDRANNO FATTE MEDIANTE OPPORTUNI METODI DELLA VIEW
    public void usecard()
    {
        if(!checkpresenceweapon())
        {
            System.out.println("Arma non presente!");
        }
        else
        {
            if(!checktype())
            {
                System.out.println("Nessun attacco possibile con questo ID");
            }
            else
            {
                createlistattacks(); //Creo lista di attacchi
                //TODO CONTROLLO MUNIZIONI PER EXTRA
                for(TypeAttack t : this.attacks)
                {
                    refreshvisible(t);
                    for(int i=0;i<t.getNumberEffect();i++)
                    {
                        Effect e = t.getEffect(i);
                        if(e.getType()==0)
                        {
                            int scelta;
                            int cont;
                            do {
                                cont=0;
                                for( Player p : this.listAttackable.getAttackableplayers())
                                {
                                    System.out.println("Digita "+ cont+ " Per attaccare "+ p.getname());
                                    cont++;
                                }
                                Scanner input = new Scanner (System.in);
                                scelta=input.nextInt();
                            }
                            while(scelta<0||scelta>cont-1);
                            Player second = this.listAttackable.getAttackableplayers().get(scelta);
                            for(int k=0;k<e.getnumberdamage();k++)
                            {
                                Damage damage = e.getDamage(k);
                                this.manageplayerattack(second,e.getId(),damage);
                            }
                        }
                    }
                }
            }
        }
    }

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
        player1 = new Player("Giovanni", "Blue", "10583741");
        player2 = new Player("Marco", "Pink", "14253954");
        player3 = new Player("Codecasa", "Green", "18263100");
        player4 = new Player("Bussetti", "Yellow", "18263100");
        player5 = new Player("Norma", "Grey", "18263100");
        try {
            match.addPlayer(player1);
            match.addPlayer(player2);
            match.addPlayer(player3);
            match.addPlayer(player4);
            match.addPlayer(player5);
        } catch (MaxNumberPlayerException e) {
            System.out.println("Too many players in the game.");
        }

        match.createDashboard(3);
        grabweapon = new GrabWeapon();
        ArrayList<String> destination = new ArrayList<>();
        player1.setCel(1, 2);
        player2.setCel(2, 2);
        player3.setCel(2, 1);
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

        } catch (MaxNumberofCardsException e) {
            System.out.println("You have too many Weapon Cards, please remove one.");
        }

    }

}
