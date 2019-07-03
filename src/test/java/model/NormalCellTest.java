package model;

import exceptions.CardAlreadyCollectedException;
import exceptions.MoreThanTreeAmmosException;
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

    @Test
    public void collectCard(){
        match.createDashboard(1);
        Dashboard d = match.getDashboard();
        NormalCell normalCell1 = (NormalCell)d.getMap(1, 1);
        match.fillDashboard();
        player1.setActive();
        player1.setCel(1, 1);
        assertEquals(1, player1.getAmmo(0));
        assertEquals(1, player1.getAmmo(1));
        assertEquals(1, player1.getAmmo(2));
        AmmoCard ammoCard = normalCell1.getAmmoCard();
        try{
            normalCell1.collectCard(player1);
        } catch(CardAlreadyCollectedException| MoreThanTreeAmmosException e){}
        int totalAmmos = player1.getAmmo(0)+player1.getAmmo(1)+player1.getAmmo(2);
        if(ammoCard.type==0){assertEquals(6, totalAmmos);}
        else{assertEquals(5, totalAmmos);}
    }
}