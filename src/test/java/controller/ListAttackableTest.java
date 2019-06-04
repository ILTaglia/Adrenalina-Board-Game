package controller;
import exceptions.*;
import model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
public class ListAttackableTest {
    Match match;
    Player player1;
    Player player2;
    Player player3;
    Player player4;
    Player player5;
    GrabWeapon grabweapon;

    @Before
    public void setUp() throws Exception {
        match = new Match();
        match.createPlayer("Giovanni", "Blue", "10583741");
        match.createPlayer("Marco", "Pink", "14253954");
        match.createPlayer("Codecasa", "Green", "18263100");
        match.createPlayer("Bussetti", "Yellow", "18263100");
        match.createPlayer("Norma", "Grey", "18263100");
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

    @Test
    public void undefined()
    {
        TypeAttack attack = new AttackFactory().getinstanceof(1,0,1,0,0);
        CreateListAttackable lister = new CreateListAttackable();
        lister.createlist(match,attack,player2);
        assertTrue(lister.getAttackableplayers().contains(player1));
        assertTrue(lister.getAttackableplayers().contains(player3));
        assertFalse(lister.getAttackableplayers().contains(player2));
        assertFalse(lister.getAttackableplayers().contains(player4));
        assertFalse(lister.getAttackableplayers().contains(player5));
        assertEquals(lister.getAttackableplayers().size(),2);
        attack = new AttackFactory().getinstanceof(1,0,2,0,0);
        lister.createlist(match,attack,player2);
        assertTrue(lister.getAttackableplayers().contains(player4));
        assertFalse(lister.getAttackableplayers().contains(player3));
        assertFalse(lister.getAttackableplayers().contains(player2));
        assertFalse(lister.getAttackableplayers().contains(player1));
        assertFalse(lister.getAttackableplayers().contains(player5));
        assertEquals(lister.getAttackableplayers().size(),1);
    }


    @Test
    public void undefinedd()
    {
        TypeAttack attack = new AttackFactory().getinstanceof(2,0,1,0,0);
        CreateListAttackable lister = new CreateListAttackable();
        lister.createlist(match,attack,player2);
        assertTrue(lister.getAttackableplayers().contains(player1));
        assertTrue(lister.getAttackableplayers().contains(player3));
        assertTrue(lister.getAttackableplayers().contains(player4));
        assertFalse(lister.getAttackableplayers().contains(player2));
        assertFalse(lister.getAttackableplayers().contains(player5));
        assertEquals(lister.getAttackableplayers().size(),3);
    }

    @Test
    public void moredistanced()
    {
        TypeAttack attack = new AttackFactory().getinstanceof(3,0,1,0,0);
        CreateListAttackable lister = new CreateListAttackable();
        lister.createlist(match,attack,player2);
        assertTrue(lister.getAttackableplayers().contains(player4));
        assertFalse(lister.getAttackableplayers().contains(player1));
        assertFalse(lister.getAttackableplayers().contains(player2));
        assertFalse(lister.getAttackableplayers().contains(player3));
        assertFalse(lister.getAttackableplayers().contains(player5));
        assertEquals(lister.getAttackableplayers().size(),1);
        attack = new AttackFactory().getinstanceof(3,0,0,0,0);
        lister.createlist(match,attack,player2);
        assertTrue(lister.getAttackableplayers().contains(player4));
        assertTrue(lister.getAttackableplayers().contains(player1));
        assertFalse(lister.getAttackableplayers().contains(player2));
        assertTrue(lister.getAttackableplayers().contains(player3));
        assertFalse(lister.getAttackableplayers().contains(player5));
        assertEquals(lister.getAttackableplayers().size(),3);
    }

    @Test
    public void cardinald()
    {
        TypeAttack attack = new AttackFactory().getinstanceof(4,0,1,0,0);
        CreateListAttackable lister = new CreateListAttackable();
        lister.setDirection(3);
        lister.createlist(match,attack,player2);
        assertTrue(lister.getAttackableplayers().contains(player4));
        assertFalse(lister.getAttackableplayers().contains(player1));
        assertFalse(lister.getAttackableplayers().contains(player2));
        assertTrue(lister.getAttackableplayers().contains(player3));
        assertFalse(lister.getAttackableplayers().contains(player5));
        assertEquals(lister.getAttackableplayers().size(),2);
        lister.setDirection(0);
        lister.createlist(match,attack,player2);
        assertFalse(lister.getAttackableplayers().contains(player4));
        assertTrue(lister.getAttackableplayers().contains(player1));
        assertFalse(lister.getAttackableplayers().contains(player2));
        assertFalse(lister.getAttackableplayers().contains(player3));
        assertFalse(lister.getAttackableplayers().contains(player5));
        assertEquals(lister.getAttackableplayers().size(),1);
    }

    @Test
    public void notseend()
    {
        TypeAttack attack = new AttackFactory().getinstanceof(5,0,1,0,0);
        CreateListAttackable lister = new CreateListAttackable();
        lister.createlist(match,attack,player2);
        assertFalse(lister.getAttackableplayers().contains(player4));
        assertFalse(lister.getAttackableplayers().contains(player1));
        assertFalse(lister.getAttackableplayers().contains(player2));
        assertFalse(lister.getAttackableplayers().contains(player3));
        assertTrue(lister.getAttackableplayers().contains(player5));
        assertEquals(lister.getAttackableplayers().size(),1);

    }

    @Test
    public void whilemoving()
    {
        TypeAttack attack = new AttackFactory().getinstanceof(6,0,1,0,0);
        CreateListAttackable lister = new CreateListAttackable();
        lister.setDirection(0);
        lister.createlist(match,attack,player2);
        assertFalse(lister.getAttackableplayers().contains(player4));
        assertTrue(lister.getAttackableplayers().contains(player1));
        assertFalse(lister.getAttackableplayers().contains(player2));
        assertFalse(lister.getAttackableplayers().contains(player3));
        assertFalse(lister.getAttackableplayers().contains(player5));
        assertEquals(lister.getAttackableplayers().size(),1);

    }

    @Test
    public void allaroundd()
    {
        TypeAttack attack = new AttackFactory().getinstanceof(7,0,1,0,0);
        CreateListAttackable lister = new CreateListAttackable();
        lister.createlist(match,attack,player2);

        assertFalse(lister.getAttackableplayers().contains(player4));
        assertTrue(lister.getAttackableplayers().contains(player1));
        assertFalse(lister.getAttackableplayers().contains(player2));
        assertTrue(lister.getAttackableplayers().contains(player3));
        assertFalse(lister.getAttackableplayers().contains(player5));
        assertEquals(lister.getAttackableplayers().size(),2);

        for(Player p : lister.getAttackableplayers())
        {
            System.out.println(p.getname());
        }
    }

    @Test
    public void aftermovingd()
    {
        //TODO NESSUNA ARMA CON TALE CARATTERISTICA, NEL CASO DI DLC BISOGNA AGGIORNARE QUESTA PARTE
        TypeAttack attack = new AttackFactory().getinstanceof(8,0,1,0,0);
        CreateListAttackable lister = new CreateListAttackable();
        lister.createlist(match,attack,player2);
        for(Player p : lister.getAttackableplayers())
        {
            System.out.println(p.getname());
        }
    }

    @Test
    public void allroomd()
    {
        TypeAttack attack = new AttackFactory().getinstanceof(9,0,1,0,0);
        CreateListAttackable lister = new CreateListAttackable(3,2);
        lister.createlist(match,attack,player2);
        assertTrue(lister.getAttackableplayers().contains(player3));
        assertTrue(lister.getAttackableplayers().contains(player4));
        assertFalse(lister.getAttackableplayers().contains(player1));
        assertFalse(lister.getAttackableplayers().contains(player2));
        assertFalse(lister.getAttackableplayers().contains(player5));
        assertEquals(lister.getAttackableplayers().size(),2);
    }

    @Test
    public void ricorsived()
    {
        TypeAttack attack = new AttackFactory().getinstanceof(10,0,1,0,0);
        CreateListAttackable lister = new CreateListAttackable();
        player5.setCel(1, 1);
        lister.createlist(match,attack,player2);
        assertTrue(lister.getAttackableplayers().contains(player3));
        assertTrue(lister.getAttackableplayers().contains(player4));
        assertTrue(lister.getAttackableplayers().contains(player1));
        assertFalse(lister.getAttackableplayers().contains(player2));
        assertFalse(lister.getAttackableplayers().contains(player5));
        assertEquals(lister.getAttackableplayers().size(),3);
        lister.setViewer(player3);
        lister.createlist(match,attack,player2);
        assertFalse(lister.getAttackableplayers().contains(player3));
        assertTrue(lister.getAttackableplayers().contains(player4));
        assertTrue(lister.getAttackableplayers().contains(player5));
        assertTrue(lister.getAttackableplayers().contains(player2));
        assertTrue(lister.getAttackableplayers().contains(player1));
        assertEquals(lister.getAttackableplayers().size(),4);
    }

    @Test
    public void infinitelined()
    {
        TypeAttack attack = new AttackFactory().getinstanceof(11,0,1,0,0);
        CreateListAttackable lister = new CreateListAttackable(3,2);
        player2.setCel(2, 3);
        lister.createlist(match,attack,player2);
        assertFalse(lister.getAttackableplayers().contains(player4));
        assertTrue(lister.getAttackableplayers().contains(player3));
        assertFalse(lister.getAttackableplayers().contains(player5));
        assertFalse(lister.getAttackableplayers().contains(player2));
        assertFalse(lister.getAttackableplayers().contains(player1));
        assertEquals(lister.getAttackableplayers().size(),1);
        player2.setCel(2, 2);
        lister.createlist(match,attack,player2);
        assertTrue(lister.getAttackableplayers().contains(player4));
        assertTrue(lister.getAttackableplayers().contains(player3));
        assertFalse(lister.getAttackableplayers().contains(player5));
        assertFalse(lister.getAttackableplayers().contains(player2));
        assertFalse(lister.getAttackableplayers().contains(player1));
        assertEquals(lister.getAttackableplayers().size(),2);
    }

    @Test
    public void movingtomed()
    {
        TypeAttack attack = new AttackFactory().getinstanceof(12,0,1,0,0);
        CreateListAttackable lister = new CreateListAttackable(0,0);
        player2.setCel(2, 3);
        lister.createlist(match,attack,player2);
        assertTrue(lister.getAttackableplayers().contains(player1));
        assertFalse(lister.getAttackableplayers().contains(player3));
        assertFalse(lister.getAttackableplayers().contains(player5));
        assertFalse(lister.getAttackableplayers().contains(player2));
        assertFalse(lister.getAttackableplayers().contains(player2));
        assertEquals(lister.getAttackableplayers().size(),1);
        attack = new AttackFactory().getinstanceof(12,0,1,0,2);
        lister.createlist(match,attack,player2);
        assertTrue(lister.getAttackableplayers().contains(player1));
        assertTrue(lister.getAttackableplayers().contains(player3));
        assertTrue(lister.getAttackableplayers().contains(player4));
        assertTrue(lister.getAttackableplayers().contains(player5));
        assertFalse(lister.getAttackableplayers().contains(player2));
        assertEquals(lister.getAttackableplayers().size(),4);

    }


}
