package model;

import controller.GrabWeapon;
import exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class MatchTest {
    Match match;
    Player player1;
    Player player2;
    Player player3;
    Player player4;
    Player player5;


    @BeforeEach
    public void SetUp(){
        match = new Match();
        match.createPlayer("Sirius", "Blue", "10583741");
        match.createPlayer("Calypso", "Pink", "14253954");
        match.createPlayer("Hermione", "Green", "18263100");
        match.createPlayer("Aries", "Yellow", "18992302");

        player1 = match.getPlayerByIndex(0);
        player2 = match.getPlayerByIndex(1);
        player3 = match.getPlayerByIndex(2);
        player4 = match.getPlayerByIndex(3);
    }

    @Test
    public void set_round() {
        assertEquals(1, match.getRound());
        match.createPlayer("Karka", "Grey", "18114320");
        player5 = match.getPlayerByIndex(4);
        player5.setAction();
        player5.setAction();
        match.setRound();
        assertEquals(2, match.getRound());
    }

    @Test
    public void get_round() {
        assertEquals(1, match.getRound());
    }

    @Test
    public void get_player_size(){
        assertEquals(4, match.getPlayersSize());
    }

    @Test
    public void get_check() {
        assertFalse(match.getCheck());
        match.createDashboard(2);
        assertTrue(match.getCheck());
    }

    @Test
    public void get_player(){
        assertEquals("Sirius", player1.getName());
        assertEquals("Calypso", player2.getName());
        assertEquals("Hermione", player3.getName());
        assertEquals("Aries", player4.getName());
        try{
            player1 = match.getPlayer(1); //Hermione
            player2 = match.getPlayer(0); //Sirius
            player3 = match.getPlayer(2); //Aries
            player4 = match.getPlayer(3); //Calypso
        }
        catch (InvalidColorException e){}
        assertEquals("Hermione", player1.getName());
        assertEquals("Sirius", player2.getName());
        assertEquals("Aries", player3.getName());
        assertEquals("Calypso", player4.getName());

        player1.setActive();
        assertEquals(0, player1.getAction());
        assertTrue(player1.getActive());
        assertFalse(player2.getActive());
        assertFalse(player3.getActive());
        assertFalse(player4.getActive());
        assertEquals(player1, match.getActivePlayer());
        player1.setAction();
        assertTrue(player1.getActive());
        assertEquals(1, player1.getAction());
        player1.setAction();
        assertTrue(player1.getActive());
        assertEquals(2, player1.getAction());
        player1.setActive();
        player2.setActive();
        assertFalse(player1.getActive());
        assertTrue(player2.getActive());
        assertFalse(player3.getActive());
        assertFalse(player4.getActive());
        assertEquals(player2, match.getActivePlayer());

        //Note, I put Hermione as the first player
        assertEquals(player1, match.getPlayerByID("18263100"));
        assertNotEquals(player2, match.getPlayerByID("18263100"));
    }

    @Test
    public void spawn(){
        match.createDashboard(3);
        match.fillDashboard();
        match.firstTurnPow();
        assertEquals(-1, player1.getCel().getX());
        assertEquals(-1, player1.getCel().getY());
        assertEquals(2, player1.getNumberPow());
        //hypothesis that player1 wants to spawn with its first PowCard
        if(player1.getPowByIndex(0).getColor()==0){
            match.spawnPlayer(player1, 0, 1, 0);
            assertEquals(1, player1.getCel().getX());
            assertEquals(0, player1.getCel().getY());
        }
        if(player1.getPowByIndex(0).getColor()==1){
            match.spawnPlayer(player1, 0, 0, 2);
            assertEquals(0, player1.getCel().getX());
            assertEquals(2, player1.getCel().getY());
        }
        if(player1.getPowByIndex(0).getColor()==2){
            match.spawnPlayer(player1, 0, 2, 3);
            assertEquals(2, player1.getCel().getX());
            assertEquals(3, player1.getCel().getY());
        }
    }

    @Test
    public void players(){
        match.createDashboard(2);
        match.fillDashboard();
        match.shuffleAllDecks();
        assertTrue(match.getPlayers().contains(player1));
        assertTrue(match.getPlayers().contains(player2));
        assertTrue(match.getPlayers().contains(player3));
        assertTrue(match.getPlayers().contains(player4));
        assertFalse(match.getPlayers().contains(player5));

        player1.setDamage(3, 2);
        assertFalse(match.getNoDamagedPlayers().contains(player1));
        assertTrue(match.getNoDamagedPlayers().contains(player2));

        match.firstTurnPow();
        assertEquals(2,player1.getPows().size());
        assertEquals(2,player2.getPows().size());
        assertEquals(2,player3.getPows().size());
        assertEquals(2,player4.getPows().size());
    }

    @Test
    public void score(){
        match.createPlayer("Karka", "Grey", "18114320");
        player5 = match.getPlayerByIndex(4);
        assertEquals(0, player1.getScore()); //color 0
        assertEquals(0, player2.getScore()); //color 3
        assertEquals(0, player3.getScore()); //color 1
        assertEquals(0, player4.getScore()); //color 2
        assertEquals(0, player5.getScore()); //color 4
        List<Integer> score = new ArrayList<>();
        score.add(4); //0
        score.add(2); //1
        score.add(1); //2
        score.add(0); //3
        score.add(3); //4
        match.assignScore(score, player2);

        //Note that player1, 2... are not in order of color, but in order of index, by the order
        //in which they have registered to the match
        assertEquals(4, player1.getScore());
        assertEquals(0, player2.getScore());
        assertEquals(2, player3.getScore());
        assertEquals(1, player4.getScore());
        assertEquals(3, player5.getScore());
    }

    @Test
    public void dashboard(){
        match.createDashboard(1);
        Dashboard d = match.getDashboard();
        match.fillDashboard();
        match.shuffleAllDecks();
        player1.setActive();

        SpawnPointCell c = (SpawnPointCell)d.getMap(0, 2);
        assertEquals(3, c.getSpawnPointCellWeapons().size());
        c = (SpawnPointCell)d.getMap(1, 0);
        assertEquals(3, c.getSpawnPointCellWeapons().size());
        c = (SpawnPointCell)d.getMap(2, 3);
        assertEquals(3, c.getSpawnPointCellWeapons().size());

        match.addWeaponCard(c, 1);
        assertEquals(3, c.getSpawnPointCellWeapons().size());
        Ammo redAmmo = new Ammo(0);
        Ammo blueAmmo = new Ammo(1);
        Ammo yellowAmmo = new Ammo(2);
        try{
            player1.addAmmo(redAmmo);
            player1.addAmmo(redAmmo);
            player1.addAmmo(blueAmmo);
            player1.addAmmo(blueAmmo);
            player1.addAmmo(yellowAmmo);
            player1.addAmmo(yellowAmmo);
        } catch(MoreThanTreeAmmosException e){}
        assertEquals(3, player1.getAmmo(2));
        assertThrows(MoreThanTreeAmmosException.class, () -> player1.addAmmo(yellowAmmo));
        try{match.removeAmmo(1, yellowAmmo);}
        catch(NotEnoughAmmosException e){}
        assertEquals(2, player1.getAmmo(2));
        player1.setCel(2, 3);
        assertEquals(0, player1.getNumberWeapon());
        List<Weapon> beforeGrabbingWeapons = c.getSpawnPointCellWeapons();
        GrabWeapon grabweapon = new GrabWeapon();
        try{
            grabweapon.grabWeapon(match, player1, 1);
        } catch(MaxNumberofCardsException e){}
        List<Weapon> afterGrabbingWeapons = c.getSpawnPointCellWeapons();
        //verifica che nella spawnpoint non ci sia più l'arma pescata dal player
        assertEquals(beforeGrabbingWeapons.get(0), afterGrabbingWeapons.get(0));
        assertNull(afterGrabbingWeapons.get(1));
        //assertEquals(beforeGrabbingWeapons.get(1), player1.getWeaponByIndex(0));
        assertEquals(beforeGrabbingWeapons.get(2), afterGrabbingWeapons.get(2));
        assertEquals(1, player1.getNumberWeapon());

        match.setWeaponCard(c, 1);
        assertEquals(3, c.getSpawnPointCellWeapons().size());

        try{
            player1.removeAmmo(2, blueAmmo);
        } catch(NotEnoughAmmosException e){}
        assertThrows(NotEnoughAmmosException.class,() -> player1.removeAmmo(2, blueAmmo));
        assertThrows(NotEnoughAmmosException.class,() -> player2.removeAmmo(2, blueAmmo));

        NormalCell normalCell = (NormalCell)d.getMap(2, 2);
        match.addAmmoCard(normalCell);
        match.addAmmoCard(normalCell);
    }

    @Test
    public void exception_test(){
        assertThrows(InvalidColorException.class,()-> match.getPlayer(6));
        match.createPlayer("Karka", "Grey", "18114320");
        player5 = match.getPlayerByIndex(4);
        Match m2 = new Match();
        m2.createPlayer("Sirius", "Blue", "10583741");
        m2.createPlayer("Calypso", "Pink", "14253954");
    }

    @Test
    public void assignCard(){
        //non è stato fatto l'assegnamento dei due powcard a inizio partita nel setup, tutti a zero.
        assertEquals(0, player3.getPows().size());
        try{
            match.assignPowCard(player3);
        } catch(MaxNumberofCardsException e){}
        assertEquals(1, player3.getPows().size());
        try{
            match.assignPowCard(player3);
            match.assignPowCard(player3);
        } catch(MaxNumberofCardsException e){}
        assertEquals(3, player3.getPows().size());
        assertThrows(MaxNumberofCardsException.class,() -> match.assignPowCard(player3));

        PowDeck powDeck = new PowDeck("Pow");
        PowCard powCard = (PowCard) powDeck.drawCard();
        match.discardPowCard(powCard);
        WeaponDeck weaponDeck = new WeaponDeck();
        weaponDeck.setWeapons("Armi");
        Weapon weapon = (Weapon) weaponDeck.drawCard();
        match.discardWeaponCard(weapon);
    }

    @Test
    public void players_list1(){
        match.createDashboard(3);
        match.fillDashboard();

        player1.setCel(0, 3);
        player2.setCel(0, 1);
        player3.setCel(2, 2);
        player4.setCel(2, 1);

        assertTrue(match.getVisiblePlayers(player1).contains(player2));
        assertTrue(match.getVisiblePlayers(player1).contains(player3));
        assertEquals(2, match.getVisiblePlayers(player1).size());
        assertEquals(0, match.getVisiblePlayers(player2).size());
        assertTrue(match.getVisiblePlayers(player3).contains(player4));
        assertEquals(1, match.getVisiblePlayers(player3).size());
        assertTrue(match.getVisiblePlayers(player4).contains(player3));
        assertEquals(1, match.getVisiblePlayers(player4).size());


        assertTrue(match.getSameLinePlayers(player1).contains(player2));
        assertEquals(1, match.getSameLinePlayers(player1).size());
        assertTrue(match.getSameLinePlayers(player2).contains(player1));
        assertEquals(1, match.getSameLinePlayers(player2).size());
        assertTrue(match.getSameLinePlayers(player3).contains(player4));
        assertEquals(1, match.getSameLinePlayers(player3).size());
        assertTrue(match.getSameLinePlayers(player4).contains(player3));
        assertEquals(1, match.getSameLinePlayers(player4).size());


        assertEquals(0, match.getSameColumnPlayers(player1).size());
        assertTrue(match.getSameColumnPlayers(player2).contains(player4));
        assertEquals(1, match.getSameColumnPlayers(player2).size());
        assertEquals(0, match.getSameColumnPlayers(player3).size());
        assertTrue(match.getSameColumnPlayers(player4).contains(player2));
        assertEquals(1, match.getSameColumnPlayers(player4).size());

        assertEquals(2, match.getPlayersMD(player1, player2));
        assertEquals(2, match.getPlayersMD(player2, player4));
        assertEquals(1, match.getPlayersMD(player3, player4));
        assertEquals(-1, match.getPlayersMD(player1, player3));
    }

    @Test
    public void players_list2(){
        match.createDashboard(3);

        player1.setCel(0, 2);
        player2.setCel(0, 1);
        player3.setCel(2, 2);
        player4.setCel(1, 1);


        assertTrue(match.getVisiblePlayers(player1).contains(player2));
        assertTrue(match.getVisiblePlayers(player1).contains(player3));
        assertEquals(2, match.getVisiblePlayers(player1).size());
        assertTrue(match.getVisiblePlayers(player2).contains(player1));
        assertTrue(match.getVisiblePlayers(player2).contains(player4));
        assertEquals(2, match.getVisiblePlayers(player2).size());
        assertEquals(0, match.getVisiblePlayers(player3).size());
        assertTrue(match.getVisiblePlayers(player4).contains(player1));
        assertTrue(match.getVisiblePlayers(player4).contains(player2));
        assertEquals(2, match.getVisiblePlayers(player4).size());


        assertTrue(match.getSameLinePlayers(player1).contains(player2));
        assertEquals(1, match.getSameLinePlayers(player1).size());
        assertTrue(match.getSameLinePlayers(player2).contains(player1));
        assertEquals(1, match.getSameLinePlayers(player2).size());
        assertEquals(0, match.getSameLinePlayers(player3).size());
        assertEquals(0, match.getSameLinePlayers(player4).size());


        assertTrue(match.getSameColumnPlayers(player1).contains(player3));
        assertEquals(1, match.getSameColumnPlayers(player1).size());
        assertTrue(match.getSameColumnPlayers(player2).contains(player4));
        assertEquals(1, match.getSameColumnPlayers(player2).size());
        assertTrue(match.getSameColumnPlayers(player3).contains(player1));
        assertEquals(1, match.getSameColumnPlayers(player3).size());
        assertTrue(match.getSameColumnPlayers(player4).contains(player2));
        assertEquals(1, match.getSameColumnPlayers(player4).size());


        assertEquals(1, match.getPlayersMD(player1, player2));
        assertEquals(1, match.getPlayersMD(player2, player4));
        assertEquals(-1, match.getPlayersMD(player3, player4));
    }

    @Test
    public void visiblecellsforLanciafiamme(){
        match.createDashboard(3);

        player1.setCel(0, 3); //Sirius
        player2.setCel(0, 1); //Calypso
        player3.setCel(2, 2); //Hermione
        player4.setCel(2, 1); //Aries

        assertEquals(1, match.getVisiblePlayersByPort(player3, 0).size());
        assertTrue(match.getVisiblePlayersByPort(player3, 0).contains(player2));

        assertEquals(1, match.getVisiblePlayersByPort(player3, 3).size());
        assertTrue(match.getVisiblePlayersByPort(player3, 3).contains(player4));

        player3.setCel(2,3);
        assertEquals(1, match.getVisiblePlayersByPort(player3, 3).size());
        assertTrue(match.getVisiblePlayersByPort(player3, 0).contains(player1));

        assertEquals(0, match.getVisiblePlayersByPort(player2, 2).size());
        assertEquals(1, match.getVisiblePlayersByPort(player2, 1).size());
        assertTrue(match.getVisiblePlayersByPort(player2, 1).contains(player1));
    }

    @Test
    public void cellsTests1() {
        match.createDashboard(3);

        player1.setCel(0, 3);
        player2.setCel(0, 1);
        player3.setCel(2, 2);
        player4.setCel(2, 1);

        int x = player1.getCel().getX();
        int y = player1.getCel().getY();
        Coordinate c = new Coordinate(x, y);

        assertEquals(0, match.getSameLineCells(c).get(0).getX());
        assertEquals(0, match.getSameLineCells(c).get(0).getY());
        assertEquals(0, match.getSameLineCells(c).get(1).getX());
        assertEquals(1, match.getSameLineCells(c).get(1).getY());
        assertEquals(0, match.getSameLineCells(c).get(2).getX());
        assertEquals(2, match.getSameLineCells(c).get(2).getY());
        assertEquals(3, match.getSameLineCells(c).size());
        assertEquals(1, match.getSameColumnCells(c).get(0).getX());
        assertEquals(3, match.getSameColumnCells(c).get(0).getY());
        assertEquals(2, match.getSameColumnCells(c).get(1).getX());
        assertEquals(3, match.getSameColumnCells(c).get(1).getY());
        assertEquals(2, match.getSameColumnCells(c).size());

        x = player3.getCel().getX();
        y = player3.getCel().getY();
        c.set(x, y);
        assertEquals(2, match.getSameLineCells(c).get(0).getX());
        assertEquals(0, match.getSameLineCells(c).get(0).getY());
        assertEquals(2, match.getSameLineCells(c).get(1).getX());
        assertEquals(1, match.getSameLineCells(c).get(1).getY());
        assertEquals(2, match.getSameLineCells(c).get(2).getX());
        assertEquals(3, match.getSameLineCells(c).get(2).getY());
        assertEquals(3, match.getSameLineCells(c).size());
        assertEquals(0, match.getSameColumnCells(c).get(0).getX());
        assertEquals(2, match.getSameColumnCells(c).get(0).getY());
        assertEquals(1, match.getSameColumnCells(c).get(1).getX());
        assertEquals(2, match.getSameColumnCells(c).get(1).getY());
        assertEquals(2, match.getSameColumnCells(c).size());
    }
    @Test
    public void cardinal() {
        match.createDashboard(3);

        player1.setCel(0, 3); //Sirius
        player2.setCel(0, 1); //Calypso
        player3.setCel(2, 2); //Hermione
        player4.setCel(2, 1); //Aries

        assertEquals(0, match.getRightPlayers(player1).size());
        assertTrue(match.getRightPlayers(player2).contains(player1));
        assertEquals(1, match.getRightPlayers(player2).size());
        assertEquals(0, match.getRightPlayers(player3).size());
        assertTrue(match.getRightPlayers(player4).contains(player3));
        assertEquals(1, match.getRightPlayers(player4).size());

        assertTrue(match.getLeftPlayers(player1).contains(player2));
        assertEquals(1, match.getLeftPlayers(player1).size());
        assertEquals(0, match.getLeftPlayers(player2).size());
        assertTrue(match.getLeftPlayers(player3).contains(player4));
        assertEquals(1, match.getLeftPlayers(player3).size());
        assertEquals(0, match.getLeftPlayers(player4).size());

        assertEquals(0, match.getUpPlayers(player1).size());
        assertEquals(0, match.getUpPlayers(player2).size());
        assertEquals(0, match.getUpPlayers(player3).size());
        assertTrue(match.getUpPlayers(player4).contains(player2));
        assertEquals(1, match.getUpPlayers(player4).size());

        assertEquals(0, match.getDownPlayers(player1).size());
        assertTrue(match.getDownPlayers(player2).contains(player4));
        assertEquals(1, match.getDownPlayers(player2).size());
        assertEquals(0, match.getDownPlayers(player3).size());
        assertEquals(0, match.getDownPlayers(player4).size());
    }

    @Test
    public void cellTest2(){
        match.createDashboard(3);

        Coordinate c1 = new Coordinate(0,3);

        assertEquals(7, match.getVisibleCells(c1).size());
        assertEquals(0, match.getVisibleCells(c1).get(0).getX());
        assertEquals(3, match.getVisibleCells(c1).get(0).getY());
        assertEquals(1, match.getVisibleCells(c1).get(1).getX());
        assertEquals(2, match.getVisibleCells(c1).get(1).getY());
        assertEquals(1, match.getVisibleCells(c1).get(2).getX());
        assertEquals(3, match.getVisibleCells(c1).get(2).getY());
        assertEquals(2, match.getVisibleCells(c1).get(3).getX());
        assertEquals(2, match.getVisibleCells(c1).get(3).getY());
        assertEquals(2, match.getVisibleCells(c1).get(4).getX());
        assertEquals(3, match.getVisibleCells(c1).get(4).getY());
        assertEquals(0, match.getVisibleCells(c1).get(5).getX());
        assertEquals(1, match.getVisibleCells(c1).get(5).getY());
        assertEquals(0, match.getVisibleCells(c1).get(6).getX());
        assertEquals(2, match.getVisibleCells(c1).get(6).getY());

        c1.set(1, 1);
        assertEquals(5, match.getVisibleCells(c1).size());
        assertEquals(1, match.getVisibleCells(c1).get(0).getX());
        assertEquals(1, match.getVisibleCells(c1).get(0).getY());
        assertEquals(0, match.getVisibleCells(c1).get(1).getX());
        assertEquals(1, match.getVisibleCells(c1).get(1).getY());
        assertEquals(0, match.getVisibleCells(c1).get(2).getX());
        assertEquals(2, match.getVisibleCells(c1).get(2).getY());
        assertEquals(2, match.getVisibleCells(c1).get(3).getX());
        assertEquals(0, match.getVisibleCells(c1).get(3).getY());
        assertEquals(2, match.getVisibleCells(c1).get(4).getX());
        assertEquals(1, match.getVisibleCells(c1).get(4).getY());

        c1.set(0, 0);
        assertEquals(4, match.getVisibleCells(c1).size());
        assertEquals(0, match.getVisibleCells(c1).get(0).getX());
        assertEquals(0, match.getVisibleCells(c1).get(0).getY());
        assertEquals(1, match.getVisibleCells(c1).get(1).getX());
        assertEquals(0, match.getVisibleCells(c1).get(1).getY());
        assertEquals(0, match.getVisibleCells(c1).get(2).getX());
        assertEquals(1, match.getVisibleCells(c1).get(2).getY());
        assertEquals(0, match.getVisibleCells(c1).get(3).getX());
        assertEquals(2, match.getVisibleCells(c1).get(3).getY());

        c1.set(2, 2);
        assertEquals(6, match.getVisibleCells(c1).size());
        assertEquals(1, match.getVisibleCells(c1).get(0).getX());
        assertEquals(2, match.getVisibleCells(c1).get(0).getY());
        assertEquals(1, match.getVisibleCells(c1).get(1).getX());
        assertEquals(3, match.getVisibleCells(c1).get(1).getY());
        assertEquals(2, match.getVisibleCells(c1).get(2).getX());
        assertEquals(2, match.getVisibleCells(c1).get(2).getY());
        assertEquals(2, match.getVisibleCells(c1).get(3).getX());
        assertEquals(3, match.getVisibleCells(c1).get(3).getY());
        assertEquals(2, match.getVisibleCells(c1).get(4).getX());
        assertEquals(0, match.getVisibleCells(c1).get(4).getY());
        assertEquals(2, match.getVisibleCells(c1).get(5).getX());
        assertEquals(1, match.getVisibleCells(c1).get(5).getY());
    }
    @Test
    public void cellTest3() {
        match.createDashboard(3);

        player1.setCel(0, 3); //Sirius
        player2.setCel(0, 3); //Calypso
        player3.setCel(2, 2); //Hermione
        player4.setCel(2, 1); //Aries

        Coordinate c1 = new Coordinate(0, 3);
        Coordinate c2 = new Coordinate(2, 2);
        Coordinate c3 = new Coordinate(2, 1);

        assertTrue(match.getSameCellsPlayers(c1).contains(player1));
        assertTrue(match.getSameCellsPlayers(c1).contains(player2));
        assertEquals(2, match.getSameCellsPlayers(c1).size());
        assertTrue(match.getSameCellsPlayers(c2).contains(player3));
        assertEquals(1, match.getSameCellsPlayers(c2).size());
        assertTrue(match.getSameCellsPlayers(c3).contains(player4));
        assertEquals(1, match.getSameCellsPlayers(c3).size());
    }

    @Test
    public void cellTest4() {
        match.createDashboard(3);

        Coordinate c1 = new Coordinate(0, 3);
        Coordinate c2 = new Coordinate(0, 1);
        Coordinate c3 = new Coordinate(2, 2);
        Coordinate c4 = new Coordinate(2, 1);

        assertEquals(3, match.getLeftCells(c1).size());
        assertEquals(0, match.getLeftCells(c1).get(0).getX());
        assertEquals(2, match.getLeftCells(c1).get(0).getY());
        assertEquals(0, match.getLeftCells(c1).get(1).getX());
        assertEquals(1, match.getLeftCells(c1).get(1).getY());
        assertEquals(0, match.getLeftCells(c1).get(2).getX());
        assertEquals(0, match.getLeftCells(c1).get(2).getY());

        assertEquals(1, match.getLeftCells(c2).size());
        assertEquals(0, match.getLeftCells(c2).get(0).getX());
        assertEquals(0, match.getLeftCells(c2).get(0).getY());

        assertEquals(2, match.getLeftCells(c3).size());
        assertEquals(2, match.getLeftCells(c3).get(0).getX());
        assertEquals(1, match.getLeftCells(c3).get(0).getY());
        assertEquals(2, match.getLeftCells(c3).get(1).getX());
        assertEquals(0, match.getLeftCells(c3).get(1).getY());

        assertEquals(1, match.getLeftCells(c4).size());
        assertEquals(2, match.getLeftCells(c4).get(0).getX());
        assertEquals(0, match.getLeftCells(c4).get(0).getY());

        assertEquals(0, match.getRightCells(c1).size());
        assertEquals(2, match.getRightCells(c2).size());
        assertEquals(0, match.getRightCells(c2).get(0).getX());
        assertEquals(2, match.getRightCells(c2).get(0).getY());
        assertEquals(0, match.getRightCells(c2).get(1).getX());
        assertEquals(3, match.getRightCells(c2).get(1).getY());

        assertEquals(0, match.getUpCells(c1).size());
        assertEquals(2, match.getDownCells(c1).size());
        assertEquals(1, match.getDownCells(c1).get(0).getX());
        assertEquals(3, match.getDownCells(c1).get(0).getY());
        assertEquals(2, match.getDownCells(c1).get(1).getX());
        assertEquals(3, match.getDownCells(c1).get(1).getY());

        assertEquals(2, match.getUpCells(c3).size());
        assertEquals(1, match.getUpCells(c3).get(0).getX());
        assertEquals(2, match.getUpCells(c3).get(0).getY());
        assertEquals(0, match.getUpCells(c3).get(1).getX());
        assertEquals(2, match.getUpCells(c3).get(1).getY());
        assertEquals(0, match.getDownCells(c3).size());

    }

    @Test
    public void getcellsMD(){
        Coordinate cell1 = new Coordinate(2, 3);
        Coordinate cell2 = new Coordinate(0, 1);
        assertEquals(4, match.getCellsMD(cell1, cell2));
    }

    @Test
    public void noDamagedPlayers(){
        player1.setDamage(2, player2.getColor());
        assertEquals(2, player1.getNumberDamage(player2.getColor()));
        player2.setDamage(1, player3.getColor());
        assertEquals(1, player2.getNumberDamage(player3.getColor()));
        assertFalse(match.getNoDamagedPlayers().contains(player1));
        assertFalse(match.getNoDamagedPlayers().contains(player2));
        assertTrue(match.getNoDamagedPlayers().contains(player3));
        assertTrue(match.getNoDamagedPlayers().contains(player4));

        assertEquals(player1, match.getPlayer(0));
        assertThrows(InvalidColorException.class, () -> match.getPlayer(7));
    }

    @Test
    public void setDead(){
        match.createDashboard(3);
        int[][] killShotTrack = match.getDashboard().getKillShotTrack();
        //player1 kills player2
        match.playerDeath(player1, player2, false);
        assertEquals(0, player1.getMarks(player2.getColor()));
        assertEquals(player1.getColor(), killShotTrack[0][0]);
        assertEquals(-1, killShotTrack[1][0]);

        //player3 kills player4
        match.playerDeath(player3, player4, true);
        assertEquals(1, player3.getMarks(player4.getColor()));
        assertEquals(player3.getColor(), killShotTrack[0][1]);
        assertEquals(player3.getColor(), killShotTrack[1][1]);
    }
}