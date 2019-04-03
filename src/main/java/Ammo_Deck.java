import java.util.ArrayList;

public class Ammo_Deck extends Deck {
//Rosso:0__Blue:1__Giallo:2
    //ATTENZIONE: Non chiamo Shuffle, sarà il controller a farlo a inizio partita
    
    public Ammo_Deck(){
        /*
        TODO
        Ricontrollare se evitabili i for
        */
        Stack=new ArrayList<Card>();
        int i;
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
        for(i=0;i<4;i++){
            this.Stack.add(new Ammo_Pow_Tile(1,2));
        }
    }
}
