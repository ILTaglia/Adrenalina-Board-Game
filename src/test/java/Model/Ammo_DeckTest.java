package Model;

import Model.Ammo_Card;
import Model.Ammo_Deck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Ammo_DeckTest {

    private Ammo_Deck ammo_deck;
    private Ammo_Card ammo_card;
    private int in_size_stack;


    @BeforeEach
    public void setUp() {
        ammo_deck = new Ammo_Deck();
        ammo_deck.Shuffle_Stack();
    }

    //Primo semplice test per carta pescata
    @Test
    public void Draw_CardTest(){
        in_size_stack=ammo_deck.size_Stack();

        ammo_card=(Ammo_Card)ammo_deck.Draw_Card();

        assertEquals(in_size_stack-1,ammo_deck.size_Stack());
        assertEquals(0,ammo_deck.size_Stack_Discarded());
        assert(!ammo_deck.contains_Stack(ammo_card));
    }

    //Primo semplice test per carta scartata
    @Test
    public void Discard_CardTest(){
        ammo_card=(Ammo_Card)ammo_deck.Draw_Card();
        in_size_stack=ammo_deck.size_Stack_Discarded();
        ammo_deck.Discard_Card(ammo_card);

        assertEquals(1,ammo_deck.size_Stack_Discarded());
        assert(ammo_deck.contains_Stack_Discarded(ammo_card));
    }

    //Testo il caso in cui il mazzo finisca e si chiami Shuffle
    @Test
    public void Draw_CardShuffleTest(){
        for(int i=0;i<36;i++){
            ammo_card= (Ammo_Card) ammo_deck.Draw_Card();
            ammo_deck.Discard_Card(ammo_card);
        }
        assertEquals(0,ammo_deck.size_Stack());
        assertEquals(36,ammo_deck.size_Stack_Discarded());
        String s=ammo_deck.toString();
        ammo_card= (Ammo_Card) ammo_deck.Draw_Card();
        assertEquals(0,ammo_deck.size_Stack_Discarded());
        assertEquals(35, ammo_deck.size_Stack());
        assertNotEquals(s,ammo_deck.toString());
    }
    //Testo metodo Shuffle usando il metoodtostring che restituisce tutto il mazzo
    @Test
    public void Shuffle_StackTest(){
        String s=ammo_deck.toString();
        ammo_deck.Shuffle_Stack();

        assertNotEquals(s,ammo_deck.toString());
    }


}
