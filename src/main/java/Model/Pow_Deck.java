package Model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Pow_Deck extends Deck{
    private ArrayList <PowCard> PC;
    public Pow_Deck(String fileName){
        stack =new ArrayList<>();
        ArrayList<Card> S=new ArrayList<>();
        stackDiscarded =new ArrayList<>();
        ArrayList <Integer> A;
        int tipo=0, colore=0, costo=0, moveme=0,life=0,marks=0;
        ArrayList<PowCard>O;
        O=new ArrayList<PowCard>();
        int CS=1;
        try (FileReader file = new FileReader((fileName+".pubg"))) {
            int next;
            do {
                next=file.read();
                char nextc=(char)next;
                switch(CS) {
                    case 1: {
                        tipo=Character.getNumericValue(nextc);
                        CS=2;
                        break;
                    }
                    case 2: {
                        colore=Character.getNumericValue(nextc);
                        CS=3;
                        break;
                    }
                    case 3: {
                        costo=Character.getNumericValue(nextc);
                        CS=4;
                        break;
                    }
                    case 4: {
                        moveme=Character.getNumericValue(nextc);
                        CS=5;
                        break;
                    }
                    case 5: {
                        life=Character.getNumericValue(nextc);
                        CS=6;
                        break;
                    }
                    case 6: {
                        marks=Character.getNumericValue(nextc);
                        CS=7;
                        break;
                    }
                    case 7: {
                        O.add( new Pow_Factory().getInstanceof(tipo,colore,costo,moveme,life,marks));
                        O.get(O.size()-1).used =false;
                        if(nextc=='!') {
                            CS=1;
                        }
                        else {
                            next = -1;
                            //this.PC = O;
                            S.addAll(O);
                            this.stack =S;
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
    public PowCard getPow(int i)
    {
        return this.PC.get(i);
    }
    }

