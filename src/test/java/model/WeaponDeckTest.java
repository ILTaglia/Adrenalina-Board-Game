package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;


public class WeaponDeckTest {

    private WeaponDeck W;
    private Weapon weapon;
    @BeforeEach
    public void start()
    {
        W= new WeaponDeck();
        W.setWeapons("Test");
        weapon = (Weapon) W.drawCard();
    }



    @Test
    public void testfirstCardName()
    {

        assertEquals(weapon.getName(),"Prova");
    }

    @Test
    public void testfirstCardCosts()
    {
        assertEquals(weapon.getCost(0),1);
        assertEquals(weapon.getCost(1),2);
        assertEquals(weapon.getCost(2),3);
        assertEquals(weapon.getCost(3),4);
    }
    @Test
    public void testfirstcardfirstattackextras()
    {
        assertEquals(weapon.getAttack(0).getextras().get(0),4);
        assertEquals(weapon.getAttack(0).getextras().get(1),5);
        assertEquals(weapon.getAttack(0).getextras().get(2),6);
    }

    @Test
    public void testfirstcardfirstattackTDMM() {
        assertEquals(weapon.getAttack(0).getTypeplayer(), 1);
        assertEquals(weapon.getAttack(0).getDistance(), 2);
        assertEquals(weapon.getAttack(0).getMoveme(), 3);
        assertEquals(weapon.getAttack(0).getMoveyou(), 4);
    }

    @Test
    public void testfirstcardfirstcardfirsteffectid()
    {
        assertEquals(weapon.getAttack(0).getEffect(0).getId(),9);
    }

    @Test
    public void testfirstcardfirstcardfirsteffectfirstDamage()
    {
        assertEquals(weapon.getAttack(0).getEffect(0).getDamage(0).getdamage(),9);
    }

    @Test
    public void numbercardTest()
    {
        assertEquals(W.sizeStack(),2);
        assertEquals(W.sizeStackDiscarded(),0);
    }

    @Test
    public void otherCardsTest()
    {
        weapon = (Weapon) W.drawCard();
        weapon = (Weapon) W.drawCard();
        assertEquals(weapon.getCost(1),1);
        assertEquals(weapon.getName(),"Distruttore");
        assertEquals(weapon.getAttack(1).getextras().get(0),1);
        assertEquals(weapon.getAttack(1).getEffect(0).getId(),2);
        assertEquals(weapon.getAttack(1).getEffect(0).getDamage(0).getdamage(),1);
        assertEquals(weapon.getAttack(0).getEffect(0).getDamage(1).getdamage(),1);
    }

}
