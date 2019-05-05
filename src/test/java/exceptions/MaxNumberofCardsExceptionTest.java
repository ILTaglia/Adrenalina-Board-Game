package exceptions;

import model.PowCard;
import model.PowDeck;
import model.Player;
import org.junit.Before;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MaxNumberofCardsExceptionTest {
    Player player;
    PowDeck deck;

    @Before
    public void SetUp(){
        player = new Player("Sirius", "blue", "10583741");
        deck = new PowDeck("Pow");
        PowCard powcard1 = (PowCard) deck.drawCard();
        PowCard powcard2 = (PowCard) deck.drawCard();
        PowCard powcard3 = (PowCard) deck.drawCard();
        PowCard powcard4 = (PowCard) deck.drawCard();
        try{
            player.addPow(powcard1);
            player.addPow(powcard2);
            player.addPow(powcard3);
        }
        catch (MaxNumberofCardsException e){}
        assertThrows(MaxNumberofCardsException.class, () -> player.addPow(powcard4));
    }
}