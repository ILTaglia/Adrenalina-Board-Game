package Model;

import java.util.*;

public abstract class Deck {

    protected ArrayList<Card> stack;
    protected ArrayList<Card> stackDiscarded;

    public Card drawCard(){
        try{
            return stack.remove(0);
        }catch (IndexOutOfBoundsException e){
            shuffleDiscarded();
            return stack.remove(0);
        }
    }

    public void discardCard(Card c){
        stackDiscarded.add(c);
    }
    public void shuffleStack(){
        Collections.shuffle(stack);
    }

    public void shuffleDiscarded(){
        Collections.shuffle(stackDiscarded);
        stack.addAll(stackDiscarded);
        stackDiscarded.clear();
        stack.forEach(card -> card.setAvailable());
    }

    public String stackToString() {
        return stack.stream().map(card -> card.toString()).reduce("", (a, b) -> a + b);
    }
    public String stackDiscardedToString() {
        return stackDiscarded.stream().map(card -> card.toString()).reduce("", (a, b) -> a + b);
    }

    public int sizeStack(){
        try {
            return stack.size();
        }catch (NullPointerException e){
            return 0;
        }
    }
    public int sizeStackDiscarded(){
        return stackDiscarded.size();
    }
    public boolean containsStack(Card card){
        return stack.contains(card);
    }

    public boolean containsStackDiscarded(Card card){
        return stackDiscarded.contains(card);
    }

    /*
    public boolean containsAll_Stack(ArrayList<Card> List){
        return stack.containsAll(List);
    }
    public boolean containsAll_Stack_Discarded(ArrayList<Card> List){
        return stackDiscarded.containsAll(List);
    }
    */
}
