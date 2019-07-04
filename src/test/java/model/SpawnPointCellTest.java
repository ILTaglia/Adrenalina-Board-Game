package model;

import controller.GrabWeapon;
import controller.OfficialShootVersion;
import exceptions.FullCellException;
import exceptions.MaxNumberofCardsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SpawnPointCellTest {
    Match match;
    Player player1;
    Player player2;
    Player player3;
    SpawnPointCell spawnPointCell;

    @BeforeEach
    public void setUp() throws Exception {
        match = new Match();
        match.createPlayer("Sirius", "Blue", "10583741");
        match.createPlayer("Calypso", "Pink", "14253954");
        match.createPlayer("Hermione", "Green", "18263100");
        player1 = match.getPlayerByIndex(0);
        player2 = match.getPlayerByIndex(1);
        player3 = match.getPlayerByIndex(2);

    }

    @Test
    public void add_Weapon_Card() {
        match.createDashboard(1);
        Dashboard d = match.getDashboard();
        match.fillDashboard();
        spawnPointCell = (SpawnPointCell)d.getMap(0, 2);
        assertEquals(0, spawnPointCell.getType());

        WeaponDeck weaponDeck = new WeaponDeck();
        weaponDeck.setWeapons("Armi");
        Weapon weapon = (Weapon) weaponDeck.drawCard();
        assertThrows(FullCellException.class,()->spawnPointCell.addWeaponCard(weapon, 1));
        assertEquals(3, spawnPointCell.getSpawnPointCellWeapons().size());
        spawnPointCell.collectWeapon(1);
        assertNull(spawnPointCell.getSpawnPointCellWeapons().get(1));
        try{ spawnPointCell.addWeaponCard(weapon, 1);}
        catch(FullCellException e){}
        spawnPointCell.collectWeapon(0);
        spawnPointCell.collectWeapon(1);
        Weapon weapon1 = (Weapon) weaponDeck.drawCard();
        spawnPointCell.setWeaponCard(weapon1, 2);
    }

    @Test
    public void getSpawnPointCellWeapons() {
        match.createDashboard(1);
        Dashboard d = match.getDashboard();
        match.fillDashboard();
        match.shuffleAllDecks();

        spawnPointCell = (SpawnPointCell)d.getMap(0, 2);
        assertEquals(3, spawnPointCell.getSpawnPointCellWeapons().size());
    }

    @Test
    public void spawnANDgrab(){
        match.createDashboard(3);
        player1.setActive();
        player1.setCel(0, 2);
        WeaponDeck weaponDeck = new WeaponDeck();
        weaponDeck.setWeapons("Armi");
        Weapon weapon = (Weapon) weaponDeck.drawCard();
        assertEquals("Distruttore", weapon.getName());
        assertEquals(0, weapon.getCostToGrab().get(0));
        assertEquals(1, weapon.getCostToGrab().get(1));
        assertEquals(0, weapon.getCostToGrab().get(2));
        assertEquals(0, weapon.getCostToRecharge().get(0));
        assertEquals(2, weapon.getCostToRecharge().get(1));
        assertEquals(0, weapon.getCostToRecharge().get(2));
        assertEquals(2, weapon.getNumberAttack());

        assertEquals(0, player1.getNumberWeapon());

        SpawnPointCell cell = (SpawnPointCell)match.getDashboard().getMap(0, 2);
        try{cell.addWeaponCard(weapon, 0);}
        catch(FullCellException e){}
        assertEquals("Distruttore", cell.getSpawnPointCellWeapons().get(0).getName());
        assertEquals(0, cell.getSpawnPointCellWeapons().get(0).getCostToGrab().get(0));
        assertEquals(1, cell.getSpawnPointCellWeapons().get(0).getCostToGrab().get(1));
        assertEquals(0, cell.getSpawnPointCellWeapons().get(0).getCostToGrab().get(2));
        assertEquals(0, cell.getSpawnPointCellWeapons().get(0).getCostToRecharge().get(0));
        assertEquals(2, cell.getSpawnPointCellWeapons().get(0).getCostToRecharge().get(1));
        assertEquals(0, cell.getSpawnPointCellWeapons().get(0).getCostToRecharge().get(2));
        assertEquals(2, cell.getSpawnPointCellWeapons().get(0).getNumberAttack());

        GrabWeapon grab = new GrabWeapon();
        try{grab.grabWeapon(match, player1, 0);}
        catch(MaxNumberofCardsException e){}
        assertEquals("Distruttore", weapon.getName());
        assertEquals(0, player1.getWeaponByIndex(0).getCostToGrab().get(0));
        assertEquals(1, player1.getWeaponByIndex(0).getCostToGrab().get(1));
        assertEquals(0, player1.getWeaponByIndex(0).getCostToGrab().get(2));
        assertEquals(0, player1.getWeaponByIndex(0).getCostToRecharge().get(0));
        assertEquals(2, player1.getWeaponByIndex(0).getCostToRecharge().get(1));
        assertEquals(2, player1.getWeaponByIndex(0).getNumberAttack());
        assertEquals(1, player1.getNumberWeapon());
    }
}