package controller;
import model.Coordinate;
import model.Player;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CorrispondencesTest {
    @Test
    public void testfirst()
    {
        Player p1 = new Player("Giovanni", "Green","12345");
        Player p2 = new Player("Rossi", "Pink", "1234567");
        List<Player> lista =new ArrayList<Player>();
        List<List> risultato = new ArrayList<List>();
        CheckCorrispondences check= new CheckCorrispondences();
        risultato=check.checkID(p1,0,lista);
        Player p3=(Player) risultato.get(0).get(0);
        assertEquals(p3.getName(),"Giovanni");
        assertEquals(risultato.get(1).get(0),1);
    }

    @Test
    public void testsecondplayer()
    {
        Player p1 = new Player("Giovanni", "Green","12345");
        Player p2 = new Player("Rossi", "Pink", "1234567");
        List<Player> lista =new ArrayList<Player>();
        List<List> risultato = new ArrayList<List>();
        CheckCorrispondences check= new CheckCorrispondences();
        risultato=check.checkID(p1,0,lista);
        lista=(ArrayList<Player>)risultato.get(0);
        risultato=check.checkID(p2,1,lista);
        lista=(ArrayList<Player>)risultato.get(0);
        Player p3=(Player) risultato.get(0).get(1);
        assertEquals(p3.getName(),"Rossi");
        assertEquals(risultato.get(1).get(0),1);
    }

    @Test
    public void testalreadyinsert()
    {
        Player p1 = new Player("Giovanni", "Green","12345");
        Player p2 = new Player("Rossi", "Pink", "1234567");
        Player p5 = new Player("Carlo", "Grey", "12347");
        List<Player> lista =new ArrayList<Player>();
        List<List> risultato = new ArrayList<List>();
        CheckCorrispondences check= new CheckCorrispondences();
        risultato=check.checkID(p1,0,lista);
        lista=(ArrayList<Player>)risultato.get(0);
        risultato=check.checkID(p2,1,lista);
        lista=(ArrayList<Player>)risultato.get(0);
        risultato=check.checkID(p2,2,lista);
        lista=(ArrayList<Player>)risultato.get(0);
        assertEquals(risultato.get(1).get(0),-1);
    }

    @Test
    public void testcorrectID()
    {
        Player p1 = new Player("Giovanni", "Green","12345");
        Player p2 = new Player("Rossi", "Pink", "1234567");
        Player p5 = new Player("Carlo", "Grey", "12347");
        List<Player> lista =new ArrayList<Player>();
        List<List> risultato = new ArrayList<List>();
        CheckCorrispondences check= new CheckCorrispondences();
        risultato=check.checkID(p1,0,lista);
        lista=(ArrayList<Player>)risultato.get(0);
        risultato=check.checkID(p2,1,lista);
        lista=(ArrayList<Player>)risultato.get(0);
        risultato=check.checkID(p1,0,lista);
        lista=(ArrayList<Player>)risultato.get(0);
        assertEquals(risultato.get(1).get(0),1);
    }

    @Test
    public void testfirstcell()
    {
        Coordinate c1= new Coordinate(1,2);
        Coordinate c2= new Coordinate(3,2);
        Coordinate c3= new Coordinate(3,3);
        List<Coordinate> lista = new ArrayList<Coordinate>();
        List<List>risultato=new ArrayList<List>();
        CheckCorrispondences check=new CheckCorrispondences();
        risultato = check.checkID(c1,0,lista);
        lista= (List<Coordinate>)risultato.get(0);
        assertEquals(risultato.get(1).get(0),1);
    }

    @Test
    public void testotherscell()
    {
        Coordinate c1= new Coordinate(1,2);
        Coordinate c2= new Coordinate(3,2);
        Coordinate c3= new Coordinate(3,3);
        List<Coordinate> lista = new ArrayList<Coordinate>();
        List<List>risultato=new ArrayList<List>();
        CheckCorrispondences check=new CheckCorrispondences();
        risultato = check.checkID(c1,0,lista);
        lista= (List<Coordinate>)risultato.get(0);
        risultato = check.checkID(c2,1,lista);
        lista= (List<Coordinate>)risultato.get(0);
        risultato = check.checkID(c3,2,lista);
        lista= (List<Coordinate>)risultato.get(0);
        assertEquals(risultato.get(1).get(0),1);
    }

    @Test
    public void testduplicatecell()
    {
        Coordinate c1= new Coordinate(1,2);
        Coordinate c2= new Coordinate(3,2);
        Coordinate c3= new Coordinate(3,3);
        List<Coordinate> lista = new ArrayList<Coordinate>();
        List<List>risultato=new ArrayList<List>();
        CheckCorrispondences check=new CheckCorrispondences();
        risultato = check.checkID(c1,0,lista);
        lista= (List<Coordinate>)risultato.get(0);
        risultato = check.checkID(c2,1,lista);
        lista= (List<Coordinate>)risultato.get(0);
        risultato = check.checkID(c2,2,lista);
        lista= (List<Coordinate>)risultato.get(0);
        assertEquals(risultato.get(1).get(0),-1);
        risultato = check.checkID(c1,2,lista);
        lista= (List<Coordinate>)risultato.get(0);
        assertEquals(risultato.get(1).get(0),-1);
    }

    @Test
    public void testcorrectIDcell()
    {
        Coordinate c1= new Coordinate(1,2);
        Coordinate c2= new Coordinate(3,2);
        Coordinate c3= new Coordinate(3,3);
        List<Coordinate> lista = new ArrayList<Coordinate>();
        List<List>risultato=new ArrayList<List>();
        CheckCorrispondences check=new CheckCorrispondences();
        risultato = check.checkID(c1,0,lista);
        lista= (List<Coordinate>)risultato.get(0);
        risultato = check.checkID(c2,1,lista);
        lista= (List<Coordinate>)risultato.get(0);
        risultato = check.checkID(c1,0,lista);
        lista= (List<Coordinate>)risultato.get(0);
        assertEquals(risultato.get(1).get(0),1);
    }

    @Test
    public void toohightID()
    {
        Coordinate c1= new Coordinate(1,2);
        Coordinate c2= new Coordinate(3,2);
        Coordinate c3= new Coordinate(3,3);
        List<Coordinate> lista = new ArrayList<Coordinate>();
        List<List>risultato=new ArrayList<List>();
        CheckCorrispondences check=new CheckCorrispondences();
        risultato = check.checkID(c1,0,lista);
        lista= (List<Coordinate>)risultato.get(0);
        risultato = check.checkID(c2,1,lista);
        lista= (List<Coordinate>)risultato.get(0);
        risultato = check.checkID(c3,3,lista);
        lista= (List<Coordinate>)risultato.get(0);
        assertEquals(risultato.get(1).get(0),-1);
    }

}
