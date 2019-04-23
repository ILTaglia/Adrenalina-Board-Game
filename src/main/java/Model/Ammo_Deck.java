package Model;

import exceptions.InvalidColorException;

import java.util.ArrayList;

public class Ammo_Deck extends Deck {
//Rosso:0 Blue:1 Giallo:2
//ATTENZIONE: Non chiamo Shuffle, sarà il controller a farlo a inizio partita

    private int[][] matrix_ammo_deck = {
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
    private int[][] matrix_ammo_pow_deck ={
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
    
    public Ammo_Deck() throws InvalidColorException {

        //TODO Migliorare struttura dati per deck
        Stack=new ArrayList<>();
        Stack_Discarded=new ArrayList<>();
        int i;
        for(i=0;i<18;i++) this.Stack.add(new Ammo_Tile(matrix_ammo_deck[i][0], matrix_ammo_deck[i][1], matrix_ammo_deck[i][2]));
        for(i=0;i<18;i++) this.Stack.add(new Ammo_Pow_Tile(matrix_ammo_pow_deck[i][0], matrix_ammo_pow_deck[i][1]));



        /*
        for (i= 0;i<3;i++) {
            this.Stack.add(new Ammo_Tile(0,1,1));
        }
        for(i=0;i<3;i++) {
            this.Stack.add(new Ammo_Tile(2,1,1));
        }
        for(i=0;i<3;i++) {
            this.Stack.add(new Ammo_Tile(2,0,0));
        }
        for(i=0;i<3;i++) {
            this.Stack.add(new Ammo_Tile(1,0,0));
        }
        for(i=0;i<3;i++) {
            this.Stack.add(new Ammo_Tile(0,2,2));
        }
        for(i=0;i<3;i++) {
            this.Stack.add(new Ammo_Tile(1,2,2));
        }
        for(i=0;i<2;i++){
            this.Stack.add(new Ammo_Pow_Tile(0,0));
        }
        for(i=0;i<2;i++){
            this.Stack.add(new Ammo_Pow_Tile(1,1));
        }
        for(i=0;i<2;i++){
            this.Stack.add(new Ammo_Pow_Tile(2,2));
        }
        for(i=0;i<4;i++){
            this.Stack.add(new Ammo_Pow_Tile(0,1));
        }
        for(i=0;i<4;i++){
            this.Stack.add(new Ammo_Pow_Tile(0,2));
        }
        for(i=0;i<4;i++) {
            this.Stack.add(new Ammo_Pow_Tile(1, 2));
        }
        */
    }
}
