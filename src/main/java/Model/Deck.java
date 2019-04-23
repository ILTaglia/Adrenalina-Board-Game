package Model;

import java.util.*;

public abstract class Deck {

    protected ArrayList<Card> Stack;
    protected ArrayList<Card> Stack_Discarded;

    public Card Draw_Card(){
        try{
            return Stack.remove(0);
        }catch (IndexOutOfBoundsException e){
            Shuffle_Discarded();
            return Stack.remove(0);
        }
    }

    public void Discard_Card(Card c){
        Stack_Discarded.add(c);
    }
    public void Shuffle_Stack(){
        Collections.shuffle(Stack);
    }

    public void Shuffle_Discarded(){
        Collections.shuffle(Stack_Discarded);
        Stack.addAll(Stack_Discarded);
        Stack_Discarded.clear();
        Stack.forEach(card -> card.Set_Available());
    }

    public String Stack_toString() {
        return Stack.stream().map(card -> card.toString()).reduce("", (a, b) -> a + b);
    }
    public String Stack_Discarded_toString() {
        return Stack_Discarded.stream().map(card -> card.toString()).reduce("", (a, b) -> a + b);
    }

    public int size_Stack(){
        try {
            return Stack.size();
        }catch (NullPointerException e){
            return 0;
        }
    }
    public int size_Stack_Discarded(){
        return Stack_Discarded.size();
    }
    public boolean contains_Stack(Card card){
        return Stack.contains(card);
    }

    public boolean contains_Stack_Discarded(Card card){
        return Stack_Discarded.contains(card);
    }
    /*
    public boolean containsAll_Stack(ArrayList<Card> List){
        return Stack.containsAll(List);
    }
    public boolean containsAll_Stack_Discarded(ArrayList<Card> List){
        return Stack_Discarded.containsAll(List);
    }
    */
}
