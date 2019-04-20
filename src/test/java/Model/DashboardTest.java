package Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class DashboardTest {
    Player player1 = new Player("Sirius", "blue", "10583741");
    Player player2 = new Player("Calypso", "pink", "14253954");

    @Test
    public void set(){
        Dashboard d1 = new Dashboard(1);
        Dashboard d2 = new Dashboard(2);
        Dashboard d3 = new Dashboard(3);

        d1.setKillshot_track(player1,2);
        d1.setKillshot_track(player2,1);

    }

}