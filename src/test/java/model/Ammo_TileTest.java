package model;

import exceptions.CardAlreadyCollectedException;
import exceptions.MoreThanTreeAmmosException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;



class Ammo_TileTest {

    private AmmoTile ammo_tile;
    private Player player;

    @BeforeEach
    public void setUp() {
        ammo_tile=new AmmoTile(0,1,2);
        player= new Player("test_player_name","Blue","test_player_id");
    }

    //TODO: correggere test
    @Test
    public void Collect_CardTest(){
        assertEquals(0, ammo_tile.getType());
        try{
            ammo_tile.collectCard(player);
        }catch (CardAlreadyCollectedException e){fail();}
        catch (MoreThanTreeAmmosException e){}
        assertEquals(2,player.getAmmo(0));
        assertEquals(2,player.getAmmo(1));
        assertEquals(2,player.getAmmo(2));

    }
    @Test
    public void Collect_CardAmmoExceptionTest(){
        AmmoTile ammo_tile2;
        ammo_tile2=new AmmoTile(0,1,0);//Aggiungo 2 per un totale di 4 munizioni rosse e una munizione blu
        try{
            ammo_tile.collectCard(player);
        }catch (CardAlreadyCollectedException e){fail();}
        catch (MoreThanTreeAmmosException e){}
        try{ammo_tile2.collectCard(player);}
        catch (CardAlreadyCollectedException e){fail();}
        catch (MoreThanTreeAmmosException e){}
        //Verifico che funzioni il catch e continui ad aggiungere le munizioni successive
        assertEquals(3,player.getAmmo(0));
        assertEquals(3,player.getAmmo(1));
        assertEquals(2,player.getAmmo(2));
    }

    @Test
    public void Collect_CardAlreadyCollectedExceptionTest(){
        ammo_tile.setUsed();
        assertThrows(CardAlreadyCollectedException.class, ()->ammo_tile.collectCard(player));

        int a=Character.getNumericValue(ammo_tile.toString().charAt(0));
        int b=Character.getNumericValue(ammo_tile.toString().charAt(1));
        int c=Character.getNumericValue(ammo_tile.toString().charAt(2));
        assertEquals(0, a);
        assertEquals(1, b);
        assertEquals(2, c);
    }


}