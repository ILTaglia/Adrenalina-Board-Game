package network.messages.playerDataMessage;

import model.Coordinate;

public class OtherPlayerPosition extends InfoMessage{

    private String username;
    private Coordinate coordinate;


    /**
     *
     * @param username is the name of the player
     * @param coordinate is the coordinate of the player
     */
    public OtherPlayerPosition(String username, Coordinate coordinate){
        super("New position of player "+ username +"in: X: " +coordinate.getX() + "Y: "+coordinate.getY());
        this.coordinate=coordinate;
        this.username=username;
        this.content="OtherPlayerPosition";
    }

    /**
     *
     * @return the coordinate
     */
    public Coordinate getCoordinate() {
        return coordinate;
    }

    /**
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }
}
