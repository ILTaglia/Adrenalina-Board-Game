package controller;
import model.Player;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class CorrispondencesTest {
    @Test
    public void testfirst()
    {
        Player p1 = new Player("Giovanni", "green","12345");
        Player p2 = new Player("Rossi", "pink", "1234567");
        ArrayList<Player> lista =new ArrayList<Player>();
        ArrayList<ArrayList> risultato = new ArrayList<ArrayList>();
        CheckCorrispondences check= new CheckCorrispondences();
        risultato=check.checkID(p1,0,lista);
        Player p3=(Player) risultato.get(0).get(0);
        assertEquals(p3.getname(),"Giovanni");
        assertEquals(risultato.get(1).get(0),1);
    }

    @Test
    public void testsecondplayer()
    {
        Player p1 = new Player("Giovanni", "green","12345");
        Player p2 = new Player("Rossi", "pink", "1234567");
        ArrayList<Player> lista =new ArrayList<Player>();
        ArrayList<ArrayList> risultato = new ArrayList<ArrayList>();
        CheckCorrispondences check= new CheckCorrispondences();
        risultato=check.checkID(p1,0,lista);
        lista=(ArrayList<Player>)risultato.get(0);
        risultato=check.checkID(p2,1,lista);
        lista=(ArrayList<Player>)risultato.get(0);
        Player p3=(Player) risultato.get(0).get(1);
        assertEquals(p3.getname(),"Rossi");
        assertEquals(risultato.get(1).get(0),1);
    }

    @Test
    public void testalreadyinsert()
    {
        Player p1 = new Player("Giovanni", "green","12345");
        Player p2 = new Player("Rossi", "pink", "1234567");
        Player p5 = new Player("Carlo", "grey", "12347");
        ArrayList<Player> lista =new ArrayList<Player>();
        ArrayList<ArrayList> risultato = new ArrayList<ArrayList>();
        CheckCorrispondences check= new CheckCorrispondences();
        risultato=check.checkID(p1,0,lista);
        lista=(ArrayList<Player>)risultato.get(0);
        risultato=check.checkID(p2,1,lista);
        lista=(ArrayList<Player>)risultato.get(0);
        System.out.println("Prova");
        risultato=check.checkID(p2,2,lista);
        System.out.println("Prova");
        lista=(ArrayList<Player>)risultato.get(0);
        assertEquals(risultato.get(1).get(0),-1);
    }

}
