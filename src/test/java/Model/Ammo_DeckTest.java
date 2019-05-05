package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Ammo_DeckTest {

    private AmmoDeck ammo_deck;
    private AmmoCard ammo_card;
    private int in_size_stack;


    @BeforeEach
    public void setUp() {
        ammo_deck = new AmmoDeck();
        ammo_deck.shuffleStack();
    }
    //Primo semplice test per carta pescata
    @Test
    public void Draw_CardTest(){
        in_size_stack=ammo_deck.sizeStack();

        ammo_card=(AmmoCard)ammo_deck.drawCard();

        assertEquals(in_size_stack-1,ammo_deck.sizeStack());
        assertEquals(0,ammo_deck.sizeStackDiscarded());
        assert(!ammo_deck.containsStack(ammo_card));
    }

    //Primo semplice test per carta scartata
    @Test
    public void Discard_CardTest(){
        ammo_card=(AmmoCard)ammo_deck.drawCard();
        in_size_stack=ammo_deck.sizeStackDiscarded();
        ammo_deck.discardCard(ammo_card);
        assertEquals(1,ammo_deck.sizeStackDiscarded());
        assert(ammo_deck.containsStackDiscarded(ammo_card));
    }

    //Testo il caso in cui il mazzo finisca e verifico che si possa comunque pescare una nuova carta
    @Test
    public void Draw_CardShuffleTest(){
        for(int i=0;i<36;i++){
            ammo_card= (AmmoCard) ammo_deck.drawCard();
            ammo_deck.discardCard(ammo_card);
        }
        assertEquals(0,ammo_deck.sizeStack());
        assertEquals(36,ammo_deck.sizeStackDiscarded());
        ammo_card= (AmmoCard) ammo_deck.drawCard();
        assertEquals(0,ammo_deck.sizeStackDiscarded());
        assertEquals(35, ammo_deck.sizeStack());
    }
    //Testo metodo shuffleDiscarded() sul mazzo scartate, aspettandosi che stack sia diverso da stackDiscarded prima della chiamata del metodo
    @Test
    public void Shuffle_DiscardedTest(){
        for(int i=0;i<36;i++){
            ammo_card= (AmmoCard) ammo_deck.drawCard();
            ammo_deck.discardCard(ammo_card);
        }
        //Ho svuotato stack e riempito stackDiscarded
        String s=ammo_deck.stackDiscardedToString();
        ammo_deck.shuffleDiscarded();
        assertNotEquals(s,ammo_deck.stackToString());
    }

    //Testo metodo shuffleStack() usando il metodo toString che restituisce tutto il mazzo
    @Test
    public void Shuffle_StackTest(){
        String s=ammo_deck.stackToString();
        ammo_deck.shuffleStack();
        assertNotEquals(s,ammo_deck.stackToString());
    }


}
