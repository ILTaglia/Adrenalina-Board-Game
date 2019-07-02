package model;

import exceptions.InvalidColorException;

import java.util.ArrayList;

public class AmmoDeck extends Deck {
    /**
     * Conventions for Ammos:
     * 0 = Red
     * 1 = Blue
     * 2 = Yellow
     * The cards are created in order. In controller there is a shuffle method to change the order.
     */


    private int[][] matrixAmmoDeck = {
            {0,1,1},
            {0,1,1},
            {0,1,1},
            {2,1,1},
            {2,1,1},
            {2,1,1},
            {2,0,0},
            {2,0,0},
            {2,0,0},
            {1,0,0},
            {1,0,0},
            {1,0,0},
            {0,2,2},
            {0,2,2},
            {0,2,2},
            {1,2,2},
            {1,2,2},
            {1,2,2},
    };
    private int[][] matrixAmmoPowDeck ={
            {0,0},
            {0,0},
            {1,1},
            {1,1},
            {2,2},
            {2,2},
            {0,1},
            {0,1},
            {0,1},
            {0,1},
            {0,2},
            {0,2},
            {0,2},
            {0,2},
            {1,2},
            {1,2},
            {1,2},
            {1,2},
    };
    
    public AmmoDeck() throws InvalidColorException {

        /**
         * stack is the ArrayList containing the cards to be collected.
         * stackDiscarded is the ArrayList containing the cards already used and discarded.
         *
         * At the beginning stack is full of all AmmoTiles and AmmoPowTiles created, while stackDiscarded is empty.
         */
        stack =new ArrayList<>();
        stackDiscarded =new ArrayList<>();
        int i;
        for(i=0;i<18;i++) this.stack.add(new AmmoTile(matrixAmmoDeck[i][0], matrixAmmoDeck[i][1], matrixAmmoDeck[i][2]));
        for(i=0;i<18;i++) this.stack.add(new AmmoPowTile(matrixAmmoPowDeck[i][0], matrixAmmoPowDeck[i][1]));




    }
}
