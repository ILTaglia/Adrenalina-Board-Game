package Model;

import Model.Ammo_Tile;
import Model.Player;
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
    public void Collect_CardTest() throws CardAlreadyCollectedException{
        try{
            ammo_tile.Collect_Card(player);
        }catch (CardAlreadyCollectedException e){
            fail();
        }
        assertEquals(2,player.get_ammo(0));
        assertEquals(2,player.get_ammo(1));
        assertEquals(2,player.get_ammo(2));

        assertThrows(CardAlreadyCollectedException.class, ()->ammo_tile.Collect_Card(player));

    }

}