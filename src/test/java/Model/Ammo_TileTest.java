package Model;

import exceptions.CardAlreadyCollectedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;



class Ammo_TileTest {

    private Ammo_Tile ammo_tile;
    private Player player;

    @BeforeEach
    public void setUp() {
        ammo_tile=new Ammo_Tile(0,1,2);
        player= new Player("test_player_name","blue","test_player_id");
    }

    @Test
    public void Collect_CardTest(){
        try{
            ammo_tile.Collect_Card(player);
        }catch (CardAlreadyCollectedException e){
            fail();
        }
        assertEquals(2,player.getAmmo(0));
        assertEquals(2,player.getAmmo(1));
        assertEquals(2,player.getAmmo(2));

    }

    @Test
    public void Collect_CardAmmoExceptionTest(){
        Ammo_Tile ammo_tile_2;
        ammo_tile_2=new Ammo_Tile(0,0,1);//Aggiungo 2 per un totale di 4 munizioni rosse e una munizione blu
        try{
            ammo_tile.Collect_Card(player);
            ammo_tile_2.Collect_Card(player);
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
        assertThrows(CardAlreadyCollectedException.class, ()->ammo_tile.Collect_Card(player));
    }


}