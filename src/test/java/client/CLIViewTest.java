package client;

import exceptions.FullCellException;
import exceptions.MaxNumberPlayerException;
import exceptions.MaxNumberofCardsException;
import model.*;
import controller.Game;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CLIViewTest {
    Game game;
    Match match;
    Player player1;
    Player player2;
    Player player3;
    Player player4;
    Game game2;
    Match match2;
    Game game3;
    Match match3;

    @Before
    public void setUp() throws Exception {
        game = new Game();
        int index = game.getMatchesSize()-1;
        match = game.getMatchByIndex(index);
        player1 = new Player("Sirius", "Blue", "10583741");
        player2 = new Player("Calypso", "Pink", "14253954");
        player3 = new Player("Hermione", "Green", "18263100");
        player4 = new Player("Aries", "Yellow", "18992302");
        try {
            match.addPlayer(player1);
            match.addPlayer(player2);
            match.addPlayer(player3);
            match.addPlayer(player4);
        }
        catch (MaxNumberPlayerException e){}
        match.createDashboard(3);

        player1.setCel(0, 3); //Sirius
        player2.setCel(0, 1); //Calypso
        player3.setCel(2, 2); //Hermione
        player4.setCel(2, 0); //Aries

        WeaponDeck weaponDeck = new WeaponDeck();
        weaponDeck.setWeapons("Armi");
        weaponDeck.drawCard();
        Weapon weapon1 = (Weapon)weaponDeck.drawCard();
        Weapon weapon2 = (Weapon)weaponDeck.drawCard();
        Weapon weapon3 = (Weapon)weaponDeck.drawCard();
        Weapon weapon4 = (Weapon)weaponDeck.drawCard();
        Weapon weapon5 = (Weapon)weaponDeck.drawCard();
        Weapon weapon6 = (Weapon)weaponDeck.drawCard();
        Weapon weapon7 = (Weapon)weaponDeck.drawCard();
        Weapon weapon8 = (Weapon)weaponDeck.drawCard();
        Weapon weapon9 = (Weapon)weaponDeck.drawCard();
        Weapon weapon10 = (Weapon)weaponDeck.drawCard();
        Weapon weapon11 = (Weapon)weaponDeck.drawCard();
        SpawnPointCell c = (SpawnPointCell)match.getDashboard().getmap(0, 2);
        try{
            c.Add_Weapon_Card(weapon1, 0);
            c.Add_Weapon_Card(weapon2, 1);
            c.Add_Weapon_Card(weapon3, 2);
        } catch (FullCellException e){}
        c = (SpawnPointCell)match.getDashboard().getmap(1, 0);
        try{
            c.Add_Weapon_Card(weapon4, 0);
            c.Add_Weapon_Card(weapon5, 1);
            c.Add_Weapon_Card(weapon6, 2);
        } catch (FullCellException e){}
        c = (SpawnPointCell)match.getDashboard().getmap(2, 3);
        try{
            c.Add_Weapon_Card(weapon7, 0);
            c.Add_Weapon_Card(weapon8, 1);
            c.Add_Weapon_Card(weapon9, 2);
        } catch (FullCellException e){}
        try {
            player1.addWeapon(weapon10);
            player1.addWeapon(weapon11);}
        catch (MaxNumberofCardsException e){ System.out.println("You have too many Weapon Cards, please remove one."); }
        game.startGame(match.getId());
    }

    @Test
    public void printMap() {
        View view = new CLIView(match);
        view.printMap();
    }

    @Test
    public void printMap1() {
        game2 = new Game();
        int index = game2.getMatchesSize()-1;
        match2 = game2.getMatchByIndex(index);
        Player p1 = new Player("Nash", "Blue", "10598041");
        Player p2 = new Player("Tesla", "Pink", "14212004");
        Player p3 = new Player("Turing", "Green", "19275500");
        Player p4 = new Player("Shannon", "Yellow", "10012302");
        try {
            match2.addPlayer(p1);
            match2.addPlayer(p2);
            match2.addPlayer(p3);
            match2.addPlayer(p4);
        }
        catch (MaxNumberPlayerException e){}
        match2.createDashboard(1);
        p1.setCel(0, 2); //Nash
        p2.setCel(0, 1); //Tesla
        p3.setCel(2, 2); //Turing
        p4.setCel(1, 0); //Shannon
        View view = new CLIView(match2);
        view.printMap();
    }

    @Test
    public void printMap2() {
        game3 = new Game();
        int index = game3.getMatchesSize()-1;
        match3 = game3.getMatchByIndex(index);
        Player p1 = new Player("Nash", "Blue", "10598041");
        Player p2 = new Player("Tesla", "Pink", "14212004");
        Player p3 = new Player("Turing", "Green", "19275500");
        Player p4 = new Player("Shannon", "Yellow", "10012302");
        try {
            match3.addPlayer(p1);
            match3.addPlayer(p2);
            match3.addPlayer(p3);
            match3.addPlayer(p4);
        }
        catch (MaxNumberPlayerException e){}
        match3.createDashboard(2);
        p1.setCel(0, 2); //Nash
        p2.setCel(0, 1); //Tesla
        p3.setCel(2, 2); //Turing
        p4.setCel(1, 0); //Shannon
        View view = new CLIView(match3);
        view.printMap();
    }

    @Test
    public void showPlayerWeapons() {
        View view = new CLIView(match);
        view.showPlayerWeapons();
    }

    @Test
    public void showPlayerPows() {
    }

    @Test
    public void showSpawnPointWeapons() {
        View view = new CLIView(match);
        player1.setCel(0, 2); //Sirius
        view.printMap();
        view.showSpawnPointWeapons();
    }

    @Test
    public void getWeaponCard() {
    }
}