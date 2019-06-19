package model;

import exceptions.FullCellException;
import exceptions.MaxNumberofCardsException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SpawnPointCellTest {
    Match match;
    Player player1;
    Player player2;
    Player player3;
    SpawnPointCell spawnPointCell;

    @Before
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
}