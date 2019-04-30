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

    private Weapon_Deck W;
    private Weapon weapon;
    @BeforeEach
    public void start()
    {
        W= new Weapon_Deck();
        W.setWeapons("Test");
        weapon = (Weapon) W.Draw_Card();
    }



    @Test
    public void testfirstCardName()
    {

        assertEquals(weapon.getName(),"Prova");
    }

    @Test
    public void testfirstCardCosts()
    {
        assertEquals(weapon.getcost(0),1);
        assertEquals(weapon.getcost(1),2);
        assertEquals(weapon.getcost(2),3);
        assertEquals(weapon.getcost(3),4);
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
        assertEquals(W.size_Stack(),2);
        assertEquals(W.size_Stack_Discarded(),0);
    }

    @Test
    public void otherCardsTest()
    {
        weapon = (Weapon) W.Draw_Card();
        weapon = (Weapon) W.Draw_Card();
        assertEquals(weapon.getcost(1),1);
        assertEquals(weapon.getName(),"Distruttore");
        assertEquals(weapon.getAttack(1).getextras().get(0),1);
        assertEquals(weapon.getAttack(1).getEffect(0).getId(),2);
        assertEquals(weapon.getAttack(1).getEffect(0).getDamage(0).getdamage(),1);
        assertEquals(weapon.getAttack(0).getEffect(0).getDamage(1).getdamage(),1);
    }

}
