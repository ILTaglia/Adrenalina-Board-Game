package model;

import java.util.*;

public abstract class Deck {
    /**
     * stack is the ArrayList containing the cards to be collected.
     * stackDiscarded is the ArrayList containing the cards already used and discarded.
     *
     * At the beginning stack is full of all AmmoTiles and AmmoPowTiles created, while stackDiscarded is empty.
     */
    protected ArrayList<Card> stack;
    protected ArrayList<Card> stackDiscarded;

    /**
     *
     * @return a new Card taken from the deck.
     */
    public Card drawCard(){
        try{
            return stack.remove(0);
        }catch (IndexOutOfBoundsException e){
            shuffleDiscarded();
            return stack.remove(0);
        }
    }

    /**
     *
     * @param c is the card to be discarded
     */
    public void discardCard(Card c){
        stackDiscarded.add(c);
    }

    /**
     * Method to shuffle stack deck
     */
    public void shuffleStack(){
        Collections.shuffle(stack);
    }

    /**
     * Method to shuffle discarded stack deck
     */
    public void shuffleDiscarded(){
        Collections.shuffle(stackDiscarded);
        stack.addAll(stackDiscarded);
        stackDiscarded.clear();
        stack.forEach(card -> card.setAvailable());
    }

    /**
     *
     * @return a String of the card in the stack deck
     */
    public String stackToString() {
        return stack.stream().map(card -> card.toString()).reduce("", (a, b) -> a + b);
    }

    /**
     *
     * @return a String of the card in the discarded stack deck
     */
    public String stackDiscardedToString() {
        return stackDiscarded.stream().map(card -> card.toString()).reduce("", (a, b) -> a + b);
    }

    /**
     *
     * @return a the size of the stack deck
     */
    public int sizeStack(){
        try {
            return stack.size();
        }catch (NullPointerException e){
            return 0;
        }
    }

    /**
     *
     * @return a the size of the discarded stack deck
     */
    public int sizeStackDiscarded(){
        return stackDiscarded.size();
    }

    /**
     *
     * @param card is the card to be checked
     * @return true if the card is contained in the stack deck, false otherwise
     */
    public boolean containsStack(Card card){
        return stack.contains(card);
    }

    /**
     *
     * @param card is the card to be checked
     * @return true if the card is contained in the discarded stack deck, false otherwise
     */
    public boolean containsStackDiscarded(Card card){
        return stackDiscarded.contains(card);
    }
}
