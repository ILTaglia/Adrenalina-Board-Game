package network.messages.playerDataMessage;

import model.Coordinate;

public class OtherPlayerPosition extends InfoMessage{

    private String username;
    private Coordinate coordinate;


    public OtherPlayerPosition(String username, Coordinate coordinate){
        super("New position of player "+ username +"in: X: " +coordinate.getX() + "Y: "+coordinate.getY());
        this.coordinate=coordinate;
        this.username=username;
        this.content="OtherPlayerPosition";
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public String getUsername() {
        return username;
    }
}
