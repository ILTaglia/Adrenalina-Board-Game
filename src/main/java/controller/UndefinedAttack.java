package controller;

import model.*;

import java.util.ArrayList;

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

}
