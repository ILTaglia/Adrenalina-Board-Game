package Model;

import exceptions.CardAlreadyCollectedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;



class Ammo_TileTest {

    private AmmoTile ammo_tile;
    private Player player;

    @BeforeEach
    public void setUp() {
        ammo_tile=new AmmoTile(0,1,2);
        player= new Player("test_player_name","blue","test_player_id");
    }

    @Test
    public void Collect_CardTest(){
        try{
            ammo_tile.collectCard(player);
        }catch (CardAlreadyCollectedException e){
            fail();
        }
        assertEquals(2,player.getAmmo(0));
        assertEquals(2,player.getAmmo(1));
        assertEquals(2,player.getAmmo(2));

    }

    @Test
    public void Collect_CardAmmoExceptionTest(){
        AmmoTile ammo_tile_2;
        ammo_tile_2=new AmmoTile(0,0,1);//Aggiungo 2 per un totale di 4 munizioni rosse e una munizione blu
        try{
            ammo_tile.collectCard(player);
            ammo_tile_2.collectCard(player);
        }catch (CardAlreadyCollectedException e){
            fail();
        }
        //Verifico che funzioni il catch e continui ad aggiungere le munizioni successive
        assertEquals(3,player.getAmmo(0));
        assertEquals(3,player.getAmmo(1));
    }

    @Test
    public void Collect_CardAlreadyCollectedExceptionTest(){
        ammo_tile.Set_Used();
        assertThrows(CardAlreadyCollectedException.class, ()->ammo_tile.collectCard(player));
    }


}