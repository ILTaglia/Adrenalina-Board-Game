package exceptions;

import Model.PowCard;
import Model.Pow_Deck;
import Model.Player;
import org.junit.Before;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MaxNumberofCardsExceptionTest {
    Player player;
    Pow_Deck deck;

    @Before
    public void SetUp(){
        player = new Player("Sirius", "blue", "10583741");
        deck = new Pow_Deck("Pow");
        PowCard powcard1 = (PowCard) deck.Draw_Card();
        PowCard powcard2 = (PowCard) deck.Draw_Card();
        PowCard powcard3 = (PowCard) deck.Draw_Card();
        PowCard powcard4 = (PowCard) deck.Draw_Card();
        try{
            player.add_pow(powcard1);
            player.add_pow(powcard2);
            player.add_pow(powcard3);
        }
        catch (MaxNumberofCardsException e){}
        assertThrows(MaxNumberofCardsException.class, () -> player.add_pow(powcard4));
    }
}