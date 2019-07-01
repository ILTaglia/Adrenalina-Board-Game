package model;

import org.junit.Test;

import static org.junit.Assert.*;

public class PowCardTest {

    @Test
    public void Teleporter(){
        Teleporter teleporter1 = new Teleporter(0, 1, 2, 0, 1);
        Teleporter teleporter2 = new Teleporter(2, 2);

        assertEquals("Teletrasporto", teleporter1.getName());
        assertEquals(0, teleporter1.getColor());
        assertEquals(1, teleporter1.getCost());
        assertEquals(2, teleporter1.getMoveme());
        assertEquals(0, teleporter1.getLife());
        assertEquals(1, teleporter1.getMarks());
        assertEquals(2, teleporter2.getColor());
        assertEquals(2, teleporter2.getCost());
        assertEquals(0, teleporter2.getMoveme());
        assertEquals(0, teleporter2.getLife());
        assertEquals(0, teleporter2.getMarks());

        teleporter1.setColor(1);
        teleporter1.setCost(2);
        teleporter1.setMoveme(1);
        teleporter1.setLife(1);
        teleporter1.setMarks(0);
        assertEquals(1, teleporter1.getColor());
        assertEquals(2, teleporter1.getCost());
        assertEquals(1, teleporter1.getMoveme());
        assertEquals(1, teleporter1.getLife());
        assertEquals(0, teleporter1.getMarks());
    }

    @Test
    public void Granade(){
        Granade granade1 = new Granade(0, 1, 2, 0, 1);
        Granade granade2 = new Granade(2, 2, 2);

        assertEquals("Granata Venom", granade1.getName());
        assertEquals(0, granade1.getColor());
        assertEquals(1, granade1.getCost());
        assertEquals(2, granade1.getMoveme());
        assertEquals(0, granade1.getLife());
        assertEquals(1, granade1.getMarks());
        assertEquals(2, granade2.getColor());
        assertEquals(2, granade2.getCost());
        assertEquals(0, granade2.getMoveme());
        assertEquals(0, granade2.getLife());
        assertEquals(2, granade2.getMarks());

        granade1.setColor(1);
        granade1.setCost(2);
        granade1.setMoveme(1);
        granade1.setLife(1);
        granade1.setMarks(0);
        assertEquals(1, granade1.getColor());
        assertEquals(2, granade1.getCost());
        assertEquals(1, granade1.getMoveme());
        assertEquals(1, granade1.getLife());
        assertEquals(0, granade1.getMarks());
    }

    @Test
    public void TargetingScope(){
        TargetingScope targetingScope1 = new TargetingScope(0, 1, 2, 0, 1);
        TargetingScope targetingScope2 = new TargetingScope(2, 2, 2);

        assertEquals("Mirino", targetingScope1.getName());
        assertEquals(0, targetingScope1.getColor());
        assertEquals(1, targetingScope1.getCost());
        assertEquals(2, targetingScope1.getMoveme());
        assertEquals(0, targetingScope1.getLife());
        assertEquals(1, targetingScope1.getMarks());
        assertEquals(2, targetingScope2.getColor());
        assertEquals(2, targetingScope2.getCost());
        assertEquals(0, targetingScope2.getMoveme());
        assertEquals(2, targetingScope2.getLife());
        assertEquals(0, targetingScope2.getMarks());

        targetingScope1.setColor(1);
        targetingScope1.setCost(2);
        targetingScope1.setMoveme(1);
        targetingScope1.setLife(1);
        targetingScope1.setMarks(0);
        assertEquals(1, targetingScope1.getColor());
        assertEquals(2, targetingScope1.getCost());
        assertEquals(1, targetingScope1.getMoveme());
        assertEquals(1, targetingScope1.getLife());
        assertEquals(0, targetingScope1.getMarks());
    }

    @Test
    public void Newton(){
        Newton newton1 = new Newton(0, 1, 2, 0, 1);
        Newton newton2 = new Newton(2, 2, 2);

        assertEquals("Raggio Cinetico", newton1.getName());
        assertEquals(0, newton1.getColor());
        assertEquals(1, newton1.getCost());
        assertEquals(2, newton1.getMoveme());
        assertEquals(0, newton1.getLife());
        assertEquals(1, newton1.getMarks());
        assertEquals(2, newton2.getColor());
        assertEquals(2, newton2.getCost());
        assertEquals(2, newton2.getMoveme());
        assertEquals(0, newton2.getLife());
        assertEquals(0, newton2.getMarks());

        newton1.setColor(1);
        newton1.setCost(2);
        newton1.setMoveme(1);
        newton1.setLife(1);
        newton1.setMarks(0);
        assertEquals(1, newton1.getColor());
        assertEquals(2, newton1.getCost());
        assertEquals(1, newton1.getMoveme());
        assertEquals(1, newton1.getLife());
        assertEquals(0, newton1.getMarks());
    }

}