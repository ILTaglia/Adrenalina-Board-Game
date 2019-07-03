package model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PowDeck extends Deck{
    /**
     * List of PowCards
     */
    private ArrayList <PowCard> PowCards;

    /**
     * stack is the ArrayList containing the cards to be collected.
     * stackDiscarded is the ArrayList containing the cards already used and discarded.
     *
     * At the beginning stack is full of all PowCards, while stackDiscarded is empty.
     */
    /**
     *
     * @param fileName to read the PowCards
     */
    public PowDeck(String fileName){
        stack =new ArrayList<>();
        ArrayList<Card> Stack=new ArrayList<>();
        stackDiscarded =new ArrayList<>();
        ArrayList <Integer> A;
        int tipo=0, colore=0, costo=0, moveme=0,life=0,marks=0;
        ArrayList<PowCard>powCardArrayList;
        powCardArrayList=new ArrayList<>();
        int CurrentState=1;
        try (FileReader file = new FileReader((fileName+".pubg"))) {
            int next;
            do {
                next=file.read();
                char nextc=(char)next;
                switch(CurrentState) {
                    case 1: {
                        tipo=Character.getNumericValue(nextc);
                        CurrentState=2;
                        break;
                    }
                    case 2: {
                        colore=Character.getNumericValue(nextc);
                        CurrentState=3;
                        break;
                    }
                    case 3: {
                        costo=Character.getNumericValue(nextc);
                        CurrentState=4;
                        break;
                    }
                    case 4: {
                        moveme=Character.getNumericValue(nextc);
                        CurrentState=5;
                        break;
                    }
                    case 5: {
                        life=Character.getNumericValue(nextc);
                        CurrentState=6;
                        break;
                    }
                    case 6: {
                        marks=Character.getNumericValue(nextc);
                        CurrentState=7;
                        break;
                    }
                    case 7: {
                        powCardArrayList.add( new PowFactory().getInstanceof(tipo,colore,costo,moveme));
                        powCardArrayList.get(powCardArrayList.size()-1).used =false;
                        if(nextc=='!') {
                            CurrentState=1;
                        }
                        else {
                            next = -1;
                            this.PowCards = powCardArrayList;
                            Stack.addAll(powCardArrayList);
                            this.stack =Stack;
                        }
                        break;
                    }
                }
            }
            while(next!=-1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param i index of required PowCard
     * @return the PowCard
     */
    public PowCard getPow(int i)
    {
        return this.PowCards.get(i);
    }
    }

