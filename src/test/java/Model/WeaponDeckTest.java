package Model;

import Model.Player;
import Model.Ammo;
import Model.Weapon;
import Model.Coordinate;
import exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;


public class WeaponDeckTest {


    @Test
    public void testfirstCardName()
    {
        Weapon_Deck W;
        W= new Weapon_Deck();
        W.setWeapons("Test");
        assertEquals(W.getCard(0).getName(),"Prova");
    }

    @Test
    public void testfirstCardCosts()
    {
        Weapon_Deck W;
        W= new Weapon_Deck();
        W.setWeapons("Test");
        assertEquals(W.getCard(0).getcost(0),1);
        assertEquals(W.getCard(0).getcost(1),2);
        assertEquals(W.getCard(0).getcost(2),3);
        assertEquals(W.getCard(0).getcost(3),4);
    }
    @Test
    public void testfirstcardfirstattackextras()
    {
        Weapon_Deck W;
        W= new Weapon_Deck();
        W.setWeapons("Test");
        assertEquals(W.getCard(0).getAttack(0).getextras().get(0),4);
        assertEquals(W.getCard(0).getAttack(0).getextras().get(1),5);
        assertEquals(W.getCard(0).getAttack(0).getextras().get(2),6);
    }

    @Test
    public void testfirstcardfirstattackTDMM() {
        Weapon_Deck W;
        W = new Weapon_Deck();
        W.setWeapons("Test");
        assertEquals(W.getCard(0).getAttack(0).getTypeplayer(), 1);
        assertEquals(W.getCard(0).getAttack(0).getDistance(), 2);
        assertEquals(W.getCard(0).getAttack(0).getMoveme(), 3);
        assertEquals(W.getCard(0).getAttack(0).getMoveyou(), 4);
    }

    @Test
    public void testfirstcardfirstcardfirsteffectid()
    {
        Weapon_Deck W;
        W = new Weapon_Deck();
        W.setWeapons("Test");
        assertEquals(W.getCard(0).getAttack(0).getEffect(0).getId(),9);
    }

    @Test
    public void testfirstcardfirstcardfirsteffectfirstDamage()
    {
        Weapon_Deck W;
        W = new Weapon_Deck();
        W.setWeapons("Test");
        assertEquals(W.getCard(0).getAttack(0).getEffect(0).getDamage(0).getdamage(),9);
    }

    @Test
    public void numbercardTest()
    {
        Weapon_Deck W;
        W = new Weapon_Deck();
        W.setWeapons("Test");
        assertEquals(W.getnumbercards(),3);
    }

    @Test
    public void otherCardsTest()
    {
        Weapon_Deck W;
        W = new Weapon_Deck();
        W.setWeapons("Test");
        assertEquals(W.getCard(2).getcost(1),1);
        assertEquals(W.getCard(2).getName(),"Distruttore");
        assertEquals(W.getCard(2).getAttack(1).getextras().get(0),1);
        assertEquals(W.getCard(2).getAttack(1).getEffect(0).getId(),2);
        assertEquals(W.getCard(2).getAttack(1).getEffect(0).getDamage(0).getdamage(),1);
        assertEquals(W.getCard(2).getAttack(0).getEffect(0).getDamage(1).getdamage(),1);
    }

}
