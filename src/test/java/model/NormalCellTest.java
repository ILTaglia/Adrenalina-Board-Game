package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class NormalCellTest {

    Match match;
    Player player1;
    Player player2;
    Player player3;
    NormalCell normalCell;

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
    public void getCardType() {
        match.createDashboard(1);
        Dashboard d = match.getDashboard();
        match.fillDashboard();
        normalCell = (NormalCell)d.getMap(0, 1);
        if(normalCell.getAmmoCard().toString().length()==3) assertEquals(0, normalCell.getCardType());
        if(normalCell.getAmmoCard().toString().length()==2) assertEquals(1, normalCell.getCardType());
    }
}