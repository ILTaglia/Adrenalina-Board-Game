package Model;

import exceptions.*;

import java.util.ArrayList;

public class Ammo_Pow_Tile extends Ammo_Card {


    public Ammo_Pow_Tile(int color1, int color2){
        Refill_Ammo=new ArrayList<>();

        Refill_Ammo.add(new Ammo(color1));
        Refill_Ammo.add(new Ammo(color2));

        Used=false;
    }

    @Override
    public void Collect_Card(Player player) throws CardAlreadyCollectedException {
        super.Collect_Card(player);
        //TODO Implementare Observer Observable per forza
    }
    @Override
    public String toString(){
        return Refill_Ammo.stream().map(ammo->ammo.toString()).reduce("",(a,b)->a + b)+"Pow";
    }

}
