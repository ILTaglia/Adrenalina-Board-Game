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
        ammo_deck.Shuffle_Stack();
    }
    //Primo semplice test per carta pescata
    @Test
    public void Draw_CardTest(){
        in_size_stack=ammo_deck.size_Stack();

        ammo_card=(AmmoCard)ammo_deck.Draw_Card();

        assertEquals(in_size_stack-1,ammo_deck.size_Stack());
        assertEquals(0,ammo_deck.size_Stack_Discarded());
        assert(!ammo_deck.contains_Stack(ammo_card));
    }

    //Primo semplice test per carta scartata
    @Test
    public void Discard_CardTest(){
        ammo_card=(AmmoCard)ammo_deck.Draw_Card();
        in_size_stack=ammo_deck.size_Stack_Discarded();
        ammo_deck.Discard_Card(ammo_card);
        assertEquals(1,ammo_deck.size_Stack_Discarded());
        assert(ammo_deck.contains_Stack_Discarded(ammo_card));
    }

    //Testo il caso in cui il mazzo finisca e verifico che si possa comunque pescare una nuova carta
    @Test
    public void Draw_CardShuffleTest(){
        for(int i=0;i<36;i++){
            ammo_card= (AmmoCard) ammo_deck.Draw_Card();
            ammo_deck.Discard_Card(ammo_card);
        }
        assertEquals(0,ammo_deck.size_Stack());
        assertEquals(36,ammo_deck.size_Stack_Discarded());
        ammo_card= (AmmoCard) ammo_deck.Draw_Card();
        assertEquals(0,ammo_deck.size_Stack_Discarded());
        assertEquals(35, ammo_deck.size_Stack());
    }
    //Testo metodo Shuffle_Discarded() sul mazzo scartate, aspettandosi che Stack sia diverso da Stack_Discarded prima della chiamata del metodo
    @Test
    public void Shuffle_DiscardedTest(){
        for(int i=0;i<36;i++){
            ammo_card= (AmmoCard) ammo_deck.Draw_Card();
            ammo_deck.Discard_Card(ammo_card);
        }
        //Ho svuotato Stack e riempito Stack_Discarded
        String s=ammo_deck.Stack_Discarded_toString();
        ammo_deck.Shuffle_Discarded();
        assertNotEquals(s,ammo_deck.Stack_toString());
    }

    //Testo metodo Shuffle_Stack() usando il metodo toString che restituisce tutto il mazzo
    @Test
    public void Shuffle_StackTest(){
        String s=ammo_deck.Stack_toString();
        ammo_deck.Shuffle_Stack();
        assertNotEquals(s,ammo_deck.Stack_toString());
    }


}
