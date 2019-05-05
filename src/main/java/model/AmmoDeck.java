package model;

import exceptions.InvalidColorException;

import java.util.ArrayList;

public class AmmoDeck extends Deck {
//Rosso:0 Blue:1 Giallo:2
//ATTENZIONE: Non chiamo Shuffle, sarà il controller a farlo a inizio partita

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

        //TODO Migliorare struttura dati per deck
        stack =new ArrayList<>();
        stackDiscarded =new ArrayList<>();
        int i;
        for(i=0;i<18;i++) this.stack.add(new AmmoTile(matrixAmmoDeck[i][0], matrixAmmoDeck[i][1], matrixAmmoDeck[i][2]));
        for(i=0;i<18;i++) this.stack.add(new AmmoPowTile(matrixAmmoPowDeck[i][0], matrixAmmoPowDeck[i][1]));




    }
}
